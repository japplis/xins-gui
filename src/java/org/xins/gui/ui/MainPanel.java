/*
 * $Id: MainPanel.java,v 1.25 2006/03/21 08:11:34 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.action.SimpleAction;
import org.xins.gui.model.Project;
import org.xins.gui.resources.Settings;

/**
 * Main visual component for the XINS GUI application, within the main frame
 * (window).
 *
 * @version $Revision: 1.25 $ $Date: 2006/03/21 08:11:34 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class MainPanel
extends JPanel {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>MainPanel</code> instance.
    */
   public MainPanel() {

      // Use double-buffering
      super(new BorderLayout(), true);

      // Get the AppCenter instance
      AppCenter appCenter = AppCenter.get();

      // Add ourselves as a listener
      appCenter.addPropertyChangeListener(new PropertyChangeHandler());

      // Get the settings
      Settings settings = appCenter.getSettings();

      // Stick a menu bar at the top
      JMenuBar menuBar = createJMenuBar();
      add(menuBar, BorderLayout.NORTH);

      // Put a toolbar below that
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      JToolBar toolBar = createJToolbar();
      panel.add(toolBar, BorderLayout.NORTH);

      // Add the toolBar and the empty panel
      add(panel, BorderLayout.CENTER);

      // Get some settings
      boolean continuousRedraw = settings.isContinuouslyRedrawSplitPane();
      double  resizeWeight     = settings.getProjectTreeResizeWeight();

      // Create a split pane with a tree (left) and a contents panel (right)
      ProjectTree tree = new ProjectTree();
      _contents  = new ProjectNodePanel();
      _splitter  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                  continuousRedraw,
                                  tree,
                                  _contents);
      _splitter.setResizeWeight(resizeWeight);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The split pane to display when there is a current project. Never
    * <code>null</code>.
    */
   private final JSplitPane _splitter;

   /**
    * The contents panel. Never <code>null</code>.
    */
   private final JPanel _contents;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Creates the <code>JToolbar</code>.
    *
    * @return
    *    the constructed {@link JToolBar}, never <code>null</code>.
    */
   private JToolBar createJToolbar() {

      JToolBar toolBar = new JToolBar();
      addToolBarButton(toolBar, "NewProject");
      addToolBarButton(toolBar, "OpenProject");
      addToolBarButton(toolBar, "SaveProject");
      addToolBarButton(toolBar, "CloseProject");

      toolBar.addSeparator();
      addToolBarButton(toolBar, "NewAPI");
      addToolBarButton(toolBar, "NewFunction");

      toolBar.addSeparator();
      addToolBarButton(toolBar, "Undo");
      addToolBarButton(toolBar, "Redo");

      toolBar.addSeparator();
      addToolBarButton(toolBar, "Cut");
      addToolBarButton(toolBar, "Copy");
      addToolBarButton(toolBar, "Paste");

      toolBar.addSeparator();
      addToolBarButton(toolBar, "Help");

      toolBar.setFloatable(false);

      return toolBar;
   }

   /**
    * Adds a button to a toolbar.
    *
    * @param toolBar
    *    the tool bar to add a button to, cannot be <code>null</code>.
    *
    * @param actionID
    *    the ID of the action for the button, cannot be <code>null</code>.
    */
   private void addToolBarButton(JToolBar toolBar, String actionID) {
      Action action = AppCenter.get().getAction(actionID);
      JButton button = toolBar.add(action);
      button.setFocusPainted(false);
   }

   /**
    * Creates the <code>JMenuBar</code>.
    *
    * @return
    *    the constructed {@link JMenuBar}, never <code>null</code>.
    */
   private JMenuBar createJMenuBar() {

      JMenuBar menuBar = new JMenuBar();
      
      // File menu
      JMenu fileMenu = addMenu(menuBar, "File");
      addMenuItem(fileMenu, "NewProject");
      addMenuItem(fileMenu, "OpenProject");      
      addMenuItem(fileMenu, "SaveProject");
      addMenuItem(fileMenu, "CloseProject");
      fileMenu.addSeparator();
      addMenuItem(fileMenu, "Exit");

      // Edit menu
      JMenu editMenu = addMenu(menuBar, "Edit");
      addMenuItem(editMenu, "Undo");
      addMenuItem(editMenu, "Redo");
      editMenu.addSeparator();
      addMenuItem(editMenu, "Cut");
      addMenuItem(editMenu, "Copy");
      addMenuItem(editMenu, "Paste");

      // Project menu
      JMenu projMenu = addMenu(menuBar, "Project");
      addMenuItem(projMenu, "NewAPI");
      addMenuItem(projMenu, "NewFunction");

      // View menu
      JMenu viewMenu = addMenu(menuBar, "View");
      addMenuItem(viewMenu, "EditSettings");

      // Help menu
      JMenu helpMenu = addMenu(menuBar, "Help");
      addMenuItem(helpMenu, "Help");
      helpMenu.addSeparator();
      addMenuItem(helpMenu, "About");

      return menuBar;
   }

   /**
    * Creates a <code>JMenu</code> and adds it to the specified
    * <code>JMenuBar</code>.
    *
    * @param menuBar
    *    the {@link JMenuBar} to add a menu to, not <code>null</code>.
    *
    * @param menuID
    *    the unique identifier for the menu, not <code>null</code>.
    *
    * @return
    *    the constructed {@link JMenu}, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>menuBar == null || menuID == null</code>.
    */
   private JMenu addMenu(JMenuBar menuBar, String menuID)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("menuBar",  menuBar,
                                     "menuID",   menuID);

      // Get the appropriate action
      String actionID = menuID + "Menu";
      Action action = new SimpleAction(actionID);

      // Create the JMenu
      JMenu menu = new JMenu(action);

      // Stick the menu in the bar
      menuBar.add(menu);

      return menu;
   }

   /**
    * Creates a <code>JMenuItem</code> and adds it to the specified
    * <code>JMenu</code>.
    *
    * @param menu
    *    the {@link JMenu} to add an item to, not <code>null</code>.
    *
    * @param actionID
    *    the ID for the action to link the item to, not <code>null</code>.
    *
    * @return
    *    the constructed {@link JMenuItem}, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>menuBar == null || actionID == null</code>.
    */
   private JMenuItem addMenuItem(JMenu menu, String actionID)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("menu", menu, "actionID", actionID);

      // Get the appropriate action
      Action action = AppCenter.get().getAction(actionID);

      // Create the JMenuItem
      JMenuItem item = new JMenuItem(action);

      // Stick the item in the menu
      menu.add(item);

      return item;
   }


   //------------------------------------------------------------------------
   // Inner classes
   //------------------------------------------------------------------------

   /**
    * Listener for <code>AppCenter</code> property change events. If the
    * current project or the current project node changes, then it updates the
    * selection in the tree.
    *
    * @version $Revision: 1.25 $ $Date: 2006/03/21 08:11:34 $
    * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
    */
   private class PropertyChangeHandler
   extends Object
   implements PropertyChangeListener {

      //---------------------------------------------------------------------
      // Constructors
      //---------------------------------------------------------------------

      /**
       * Constructs a new <code>PropertyChangeHandler</code>.
       */
      private PropertyChangeHandler() {
         // empty
      }

      //---------------------------------------------------------------------
      // Fields
      //---------------------------------------------------------------------

      //---------------------------------------------------------------------
      // Methods
      //---------------------------------------------------------------------

      // TODO: Document
      public void propertyChange(PropertyChangeEvent event) {

         // Get the source of the event
         Object eventSource = event.getSource();

         // Ignore all events that are not from the AppCenter
         if (! (eventSource instanceof AppCenter)) {
            return;
         }

         // Determine which property changed
         AppCenter appCenter = (AppCenter) eventSource;
         String    property  = event.getPropertyName();

         // If the project changes, then this impacts this main panel
         if (AppCenter.PROJECT_PROPERTY.equals(property)) {

            Project project = (Project) event.getNewValue();
            projectChanged(project);
         }
      }

      /**
       * Called when the project changed.
       *
       * @param project
       *    the new value for the <em>project</em> property in the
       *    {@link AppCenter}, can be <code>null</code>.
       */
      private void projectChanged(Project project) {

         // Get the panel with the toolbar
         JPanel panel = (JPanel) getComponent(1);

         // Determine if the tree is already displayed
         boolean showingTree = (panel.getComponentCount() > 1);

         // If there is no project, then remove the tree component
         if (project == null && showingTree) {
            panel.remove(1);
            repaint();

         // If there is a project, then add the tree component
         } else if (project != null && !showingTree) {
            panel.add(_splitter, BorderLayout.CENTER);
            revalidate();
         }
      }
   }
}
