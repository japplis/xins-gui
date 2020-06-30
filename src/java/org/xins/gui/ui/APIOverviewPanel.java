/*
 * $Id: APIOverviewPanel.java,v 1.1 2005/06/06 15:03:31 znerd Exp $
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
 * Panel that displays an overview of the current API.
 *
 * @version $Revision: 1.1 $ $Date: 2005/06/06 15:03:31 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class APIOverviewPanel
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
    * Constructs a new <code>APIOverviewPanel</code> instance.
    */
   public APIOverviewPanel() {

      AppCenter appCenter = AppCenter.get();
      Settings  settings  = appCenter.getSettings();

      // Determine translation prefix
      String tp = "panels.APIOverview.";

      // Construct components
      JLabel nameLabel = Utils.createJLabel(tp, "Name");
      JLabel descLabel = Utils.createJLabel(tp, "Description");
      _nameField       = Utils.createJTextField(nameLabel, null); // TODO: APINodeType.NAME_PATTERN);
      JScrollPane desc = Utils.createJTextArea(descLabel, null); // TODO: APINodeType.DESCRIPTION_PATTERN);
      _descrArea = (JTextArea) desc.getViewport().getView();

      _nameField.setEditable(false);
      _descrArea.setEditable(false);

      // Define column widths
      double hb = settings.getHorizontalBorder();
      double hg = settings.getHorizontalGap();
      double p  = TableLayout.PREFERRED;
      double f  = TableLayout.FILL;
      double[] cols = { hb, p, hg, f, hb };

      // Define row heights
      double vb = settings.getVerticalBorder();
      double vg = settings.getVerticalGap();
      double[] rows = { vb, p, vg, f, vb };

      // Set the layout
      TableLayout layout = new TableLayout(cols, rows);
      setLayout(layout);

      // Add all components
      add(nameLabel,  "1, 1");
      add(_nameField, "3, 1");
      add(descLabel,  "1, 3");
      add(desc,       "3, 3");

      // Listen to the AppCenter property changes
      appCenter.addPropertyChangeListener(new PropertyChangeHandler());
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The text field for the API name. Never <code>null</code>.
    */
   private final JTextField _nameField;

   /**
    * The text area for the API description. Never <code>null</code>.
    */
   private final JTextArea _descrArea;


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

      AppCenter.LOG.debug("APIOverviewPanel: projectChanged.");

      // If the current node is not a Project, display nothing
      if (! (node instanceof API)) {
         _nameField.setText("");
         _descrArea.setText("");

      // Otherwise display some details
      } else {
         API api = (API) node;
         _nameField.setText(api.getName());
         _descrArea.setText(api.getDescription());
         revalidate();
      }
   }


   //------------------------------------------------------------------------
   // Inner classes
   //------------------------------------------------------------------------

   /**
    * Listener for <code>AppCenter</code> property change events. If the
    * current project changes, then it updates the label.
    *
    * @version $Revision: 1.1 $ $Date: 2005/06/06 15:03:31 $
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

         // If the project changes, then this impacts us
         if (AppCenter.PROJECT_NODE_PROPERTY.equals(property)) {
            ProjectNode node = (ProjectNode) event.getNewValue();
            projectNodeChanged(node);
         }
      }
   }
}
