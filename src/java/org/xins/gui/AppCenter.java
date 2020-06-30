/*
 * $Id: AppCenter.java,v 1.28 2006/03/21 08:10:46 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui;

import java.awt.Rectangle;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.action.*;
import org.xins.gui.model.*;
import org.xins.gui.resources.*;
import org.xins.gui.ui.*;
import org.xins.gui.util.*;

/**
 * Central access point for the whole XINS GUI application.
 *
 * @version $Revision: 1.28 $ $Date: 2006/03/21 08:10:46 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class AppCenter extends Object {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * Logger for the application. The value of the field is never
    * <code>null</code>.
    */
   public static final Logger LOG = Logger.getLogger("org.xins.gui");

   /**
    * The name of the current project property.
    */
   public static final String PROJECT_PROPERTY = "project";

   /**
    * The name for the current project node property.
    */
   public static final String PROJECT_NODE_PROPERTY = "projectNode";

   /**
    * The one and only instance of this class.
    */
   private static AppCenter SINGLETON;

   /**
    * The directory in the class path that contains the icons.
    */
   private static String ICON_DIRECTORY = "/org/xins/gui/icons/crystal/";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   /**
    * Initializes the <code>AppCenter</code> singleton instance. This class
    * function should be called exactly once, and always before any call to
    * {@link #get()}.
    *
    * @throws Error
    *    if the <code>AppCenter</code> singleton instance has already been
    *    constructed.
    */
   public static void init() throws Error {
      if (SINGLETON != null) {
         throw new Error("The AppCenter singleton instance has already been"
                       + " constructed.");
      } else {
         SINGLETON = new AppCenter();
         SINGLETON.doInit();
      }
   }

   /**
    * Retrieves the <code>AppCenter</code> instance.
    *
    * @return
    *    the current <code>AppCenter</code> instance, never <code>null</code>.
    *
    * @throws Error
    *    if the <code>AppCenter</code> singleton instance has not been
    *    constructed yet.
    */
   public static AppCenter get() throws Error {
      if (SINGLETON == null) {
         throw new Error("The AppCenter singleton instance has not been"
                       + " constructed yet.");
      } else {
         return SINGLETON;
      }
   }


   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>AppCenter</code>.
    */
   public AppCenter() {

      // Initialize PropertyChangeSupport
      _propertyChangeSupport = new PropertyChangeSupport(this);

      // Initialize settings
      _settings = new Settings();

      // Initialize translations
      setLocale(_settings.getLocale());

      // Log all property changes
      addPropertyChangeListener(new LoggingPropertyChangeListener());

      // Create map for actions, indexed by ID
      _actionsByID = new HashMap();

      // Apply some settings already
      if (_settings.isAntiAliasedTextEnabled()) {
         System.setProperty("swing.aatext", "true");
      }
      if (! _settings.isBoldText()) {
         UIManager.put("swing.boldMetal", Boolean.FALSE);
      }
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The <code>ResourceBundle</code> which provides all translations. Never
    * <code>null</code>.
    */
   private ResourceBundle _resourceBundle;

   /**
    * The actions, indexed by their ID. Never <code>null</code>.
    */
   private final HashMap _actionsByID;

   /**
    * The current XINS project. Can be <code>null</code>.
    */
   private Project _project;

   /**
    * The current project node. Can be <code>null</code>.
    */
   private ProjectNode _projectNode;

   /**
    * The <code>ProjectTreeModel</code>. This field is never <code>null</code>.
    */
   private ProjectTreeModel _treeModel;

   /**
    * The settings. Never <code>null</code>.
    */
   private final Settings _settings;

   /**
    * The main <code>JFrame</code>. Never <code>null</code>.
    */
   private JFrame _mainFrame;

   /**
    * The main visual component for the application, to be displayed within
    * the frame. Never <code>null</code>.
    */
   private MainPanel _mainPanel;

   /**
    * Support object that allows this class to easily implement support for
    * <code>PropertyChangeListener</code>s. This field is never
    * <code>null</code>.
    */
   private final PropertyChangeSupport _propertyChangeSupport;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Actually initializes this instance. This is done from {@link #init()}
    * right after construction of this object.
    */
   private void doInit() {

      // Construct main frame
      _mainFrame = new MainFrame();

      // Create action objects
      addAction(new NewProjectAction  ());
      addAction(new OpenProjectAction ());
      addAction(new SaveProjectAction ());
      addAction(new CloseProjectAction());
      addAction(new NewAPIAction      ());
      addAction(new NewFunctionAction ());
      addAction(new UndoAction        ());
      addAction(new RedoAction        ());
      addAction(new CutAction         ());
      addAction(new CopyAction        ());
      addAction(new PasteAction       ());
      addAction(new EditSettingsAction());
      addAction(new HelpAction        ());
      addAction(new AboutAction       ());
      addAction(new ExitAction        ());

      // Initialize ProjectTreeModel
      _treeModel = new ProjectTreeModel();

      // Construct main panel and stick it in the frame
      _mainPanel = new MainPanel();
      _mainFrame.getContentPane().add(_mainPanel);
   }

   /**
    * Adds the specified action to the collection of actions.
    *
    * @param action
    *    the action to add, cannot be <code>null</code>.
    *
    * @throws NullPointerException
    *    if <code>action == null</code>.
    */
   private void addAction(ActionBase action) {
      _actionsByID.put(action.getID(), action);
   }

   /**
    * Activates the specified locale.
    *
    * @param locale
    *    the locale to activate, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>locale == null</code>.
    */
   private void setLocale(Locale locale) {

      // Load resource bundle for that locale
      LOG.debug("Retrieving translations for locale \"" + locale + "\".");
      _resourceBundle = ResourceBundle.getBundle("org.xins.gui.resources.TranslationBundle", locale);
      LOG.info("Translations for locale \"" + locale + "\" are now active.");
   }

   /**
    * Registers the specified object as a <code>PropertyChangeListener</code>
    * for this object.
    *
    * @param listener
    *    the listener to register.
    */
   public void addPropertyChangeListener(PropertyChangeListener listener) {
      _propertyChangeSupport.addPropertyChangeListener(listener);
   }

   /**
    * Unregisters the specified object as a
    * <code>PropertyChangeListener</code> for this object.
    *
    * @param listener
    *    the listener to unregister.
    */
   public void removePropertyChangeListener(PropertyChangeListener listener) {
      _propertyChangeSupport.removePropertyChangeListener(listener);
   }

   /**
    * Returns the settings.
    *
    * @return
    *    the {@link Settings}, never <code>null</code>.
    */
   public Settings getSettings() {
      return _settings;
   }

   /**
    * Changes the current project.
    *
    * @param newProject
    *    the new project, or <code>null</code> if none.
    */
   public void setProject(Project newProject) {

      Project oldProject = _project;

      if (oldProject != newProject) {

         // First deselect the selected project component
         setProjectNode(null);

         // Then change the current project
         _project = newProject;
         _propertyChangeSupport.firePropertyChange(PROJECT_PROPERTY,
                                                   oldProject,
                                                   newProject);

         // Finally select the project as the current node
         setProjectNode(newProject);
      }
   }

   /**
    * Returns the <code>ProjectTreeModel</code>. The tree model will never
    * change.
    *
    * @return
    *    the {@link ProjectTreeModel}, never <code>null</code>.
    */
   public ProjectTreeModel getTreeModel() {
      return _treeModel;
   }

   /**
    * Returns the current project.
    *
    * @return
    *    the current project, or <code>null</code> if none.
    */
   public Project getProject() {
      return _project;
   }

   /**
    * Returns the current API.
    *
    * @return
    *    the current API, or <code>null</code> if none.
    */
   public API getAPI() {
      ProjectNode node = _projectNode;
      while (node != null) {
         if (node instanceof API) {
            return (API) node;
         } else {
            node = node.getParent();
         }
      }

      return null;
   }

   /**
    * Returns the current project node.
    *
    * @return
    *    the current project node, or <code>null</code> if none.
    */
   public ProjectNode getProjectNode() {
      return _projectNode;
   }

   /**
    * Changes the current project node.
    *
    * @param newProjectNode
    *    the project node that is now the current, or <code>null</code> if
    *    none is selected.
    */
   public void setProjectNode(ProjectNode newProjectNode) {

      ProjectNode oldProjectNode = _projectNode;

      if (oldProjectNode != newProjectNode) {
         _projectNode = newProjectNode;
         _propertyChangeSupport.firePropertyChange(PROJECT_NODE_PROPERTY,
                                                   oldProjectNode,
                                                   newProjectNode);
      }
   }

   /**
    * Returns the main frame.
    *
    * @return
    *    the main {@link JFrame}, never <code>null</code>.
    */
   public JFrame getMainFrame() {
      return _mainFrame;
   }

   /**
    * Returns the main visual component for this application.
    *
    * @return
    *    the {@link MainPanel}, never <code>null</code>.
    */
   public MainPanel getMainPanel() {
      return _mainPanel;
   }

   /**
    * Returns the action with the specified ID.
    *
    * @param id
    *    the ID for an action, should not be <code>null</code>.
    *
    * @return
    *    the action with the specified ID, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if there is no action with the specified ID.
    */
   public ActionBase getAction(String id) {
      ActionBase action = (ActionBase) _actionsByID.get(id);
      if (action == null) {
         throw new IllegalArgumentException("Action \"" + id + "\" not found.");
      }
      return action;
   }

   /**
    * Retrieves a required translation with the specified key.
    *
    * @param key
    *    the key for the translation, cannot be <code>null</code>.
    *
    * @return
    *    the translation, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>key == null</code> or if key does not identify an existing
    *    translation key.
    */
   public String translate(String key)
   throws IllegalArgumentException {

      String translation = translate2(key);
      if (translation == null) {
         throw new IllegalArgumentException("No translation found for key \"" + key + "\".");
      }
      return translation;
   }

   /**
    * Retrieves an optional translation with the specified key.
    *
    * @param key
    *    the key for the translation, cannot be <code>null</code>.
    *
    * @return
    *    the translation, can be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>key == null</code>.
    */
   public String translate2(String key)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("key", key);

      // Get from resource bundle
      try {
         return _resourceBundle.getString(key);
      } catch (MissingResourceException exception) {
         return null;
      }
   }

   /**
    * Retrieves the icon with the specified name.
    *
    * @param name
    *    the name for the icon, cannot be <code>null</code>.
    *
    * @return
    *    the icon, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>name == null</code>.
    */
   public Icon getIcon(String name) throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("name", name);

      // Determine the location of the icon
      String resourceID = ICON_DIRECTORY + name + ".png";
      URL    url        = getClass().getResource(resourceID);

      // If the URL is null, then there is no such resource
      if (url == null) {
         throw new Error("Unable to load icon \"" + name + "\".");
      }

      // Construct and return an ImageIcon
      return new ImageIcon(url);
   }
}
