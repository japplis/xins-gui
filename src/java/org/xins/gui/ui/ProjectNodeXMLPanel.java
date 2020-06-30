/*
 * $Id: ProjectNodeXMLPanel.java,v 1.2 2005/06/06 13:51:40 znerd Exp $
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
import org.xins.gui.model.ProjectNode;
import org.xins.gui.resources.Settings;
import org.xins.gui.util.Utils;

/**
 * Panel that displays the XML for the current project node.
 *
 * @version $Revision: 1.2 $ $Date: 2005/06/06 13:51:40 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class ProjectNodeXMLPanel
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
    * Constructs a new <code>ProjectNodeXMLPanel</code> instance.
    */
   public ProjectNodeXMLPanel() {

      // Construct scrollable XML text area
      AppCenter appCenter = AppCenter.get();
      Settings settings = appCenter.getSettings();
      JScrollPane scrollPane = Utils.createJTextArea(null, null);
      scrollPane.setHorizontalScrollBarPolicy(
         JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      _xmlArea = (JTextArea) scrollPane.getViewport().getView();
      _xmlArea.setEditable(false);
      _xmlArea.setLineWrap(false);

      // Construct label and text field for file path
      JLabel pathLabel = new JLabel("File:"); // TODO: Translate
      _pathField = new JTextField();
      _pathField.setEditable(false);

      // Define column widths
      double hb = settings.getHorizontalBorder();
      double hg = settings.getHorizontalGap();
      double p  = TableLayout.PREFERRED;
      double f  = TableLayout.FILL;
      double[] cols = { hb, p, hg, f, hb };

      // Define row heights
      double vb = settings.getVerticalBorder();
      double vg = settings.getVerticalGap();
      double[] rows = { vb, f, vg, p, vb };

      // Set the layout
      TableLayout layout = new TableLayout(cols, rows);
      setLayout(layout);

      // Add all components
      add(scrollPane, "1, 1, 3, 1");
      add(pathLabel,  "1, 3"      );
      add(_pathField, "3, 3"      );

      // Listen to the AppCenter property changes
      appCenter.addPropertyChangeListener(new PropertyChangeHandler());
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The text area for the XML document. Never <code>null</code>.
    */
   private final JTextArea _xmlArea;

   /**
    * The text field that displays the specification file path. Never
    * <code>null</code>.
    */
   private final JTextField _pathField;


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
         _xmlArea.setText("");
         _pathField.setText("");

      // If there is a current node, display the XML for it
      } else {
         _xmlArea.setText(node.getXMLString());
         _pathField.setText(node.getPath());
      }
   }


   //------------------------------------------------------------------------
   // Inner classes
   //------------------------------------------------------------------------

   /**
    * Listener for <code>AppCenter</code> property change events. If the
    * current project node changes, then it updates the label.
    *
    * @version $Revision: 1.2 $ $Date: 2005/06/06 13:51:40 $
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
