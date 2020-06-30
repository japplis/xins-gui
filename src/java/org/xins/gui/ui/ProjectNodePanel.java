/*
 * $Id: ProjectNodePanel.java,v 1.6 2006/02/22 11:53:52 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import info.clearthought.layout.TableLayout;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.*;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.model.*;
import org.xins.gui.resources.Settings;
import org.xins.gui.util.Utils;

/**
 * Panel that displays the current project node.
 *
 * @version $Revision: 1.6 $ $Date: 2006/02/22 11:53:52 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class ProjectNodePanel
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
    * Constructs a new <code>ProjectNodePanel</code> instance.
    */
   public ProjectNodePanel() {

      // Get AppCenter and application settings
      AppCenter appCenter = AppCenter.get();
      Settings settings = appCenter.getSettings();

      // Construct top label, separator and tabs
      _label = new JLabel();
      _label.setIconTextGap(settings.getHorizontalGap());
      JSeparator separator = new JSeparator();
      _tabs = new JTabbedPane();
      _tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

      // Create overview panels
      _projectOverview  = new ProjectOverviewPanel();
      _apiOverview      = new APIOverviewPanel();
      _functionOverview = new FunctionOverviewPanel();

      // Create specification XML panel
      ProjectNodeXMLPanel xmlPanel = new ProjectNodeXMLPanel();

      // Add tabs
      _tabs.addTab("Overview",      null, _projectOverview);
      _tabs.addTab("Specification", null, xmlPanel        );
      // TODO: Translate tab captions

      // Define column widths
      double hb = settings.getHorizontalBorder();
      double f  = TableLayout.FILL;
      double[] cols = { hb, f, hb };

      // Define row heights
      double p  = TableLayout.PREFERRED;
      double vb = settings.getVerticalBorder();
      double vg = settings.getVerticalGap();
      double[] rows = { vb, p, vg, p, vg, f, vb };

      // Set the layout
      TableLayout layout = new TableLayout(cols, rows);
      setLayout(layout);

      // Add all components
      add(_label,    "1, 1");
      add(separator, "1, 3");
      add(_tabs,     "1, 5");

      // Listen to the AppCenter property changes
      appCenter.addPropertyChangeListener(new PropertyChangeHandler());
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The tabbed pane. Never <code>null</code>.
    */
   private final JTabbedPane _tabs;

   /**
    * Overview panel for a project. Never <code>null</code>.
    */
   private final ProjectOverviewPanel _projectOverview;

   /**
    * Overview panel for an API. Never <code>null</code>.
    */
   private final APIOverviewPanel _apiOverview;

   /**
    * Overview panel for a function. Never <code>null</code>.
    */
   private final FunctionOverviewPanel _functionOverview;

   /**
    * The label to display at the top. Never <code>null</code>.
    */
   private final JLabel _label;

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------


   /**
    * Called by the <code>PropertyChangeHandler</code> when the current
    * project node changed.
    *
    * @param node
    *    the new value for the <em>projectNode</em> property in the
    *    {@link AppCenter}, can be <code>null</code>.
    */
   private void projectNodeChanged(ProjectNode node) {

      // If there is no current node, then display nothing
      if (node == null) {
         _label.setText("");
         _label.setIcon(null);

      // If there is a current node, display appropriate name, icon and
      // overview tab
      } else {
         _label.setText(node.getName());
         _label.setIcon(node.getLargeIcon());

         Component overview;
         if (node instanceof Project) {
            overview = _projectOverview;
         } else if (node instanceof API) {
            overview = _apiOverview;
         } else if (node instanceof Function) {
            overview = _functionOverview;
         } else {
            throw new Error(); // TODO
         }

         _tabs.setComponentAt(0, overview);
      }

      repaint();
   }


   //------------------------------------------------------------------------
   // Inner classes
   //------------------------------------------------------------------------

   /**
    * Listener for <code>AppCenter</code> property change events. If the
    * current project node changes, then it updates the label.
    *
    * @version $Revision: 1.6 $ $Date: 2006/02/22 11:53:52 $
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

         // If the project node changes, then this impacts us
         if (AppCenter.PROJECT_NODE_PROPERTY.equals(property)) {

            ProjectNode node = (ProjectNode) event.getNewValue();
            projectNodeChanged(node);
         }
      }
   }
}
