/*
 * $Id: NewFunctionDialog.java,v 1.3 2006/03/14 14:37:53 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import info.clearthought.layout.TableLayout;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashSet;
import javax.swing.*;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.action.ActionBase;
import org.xins.gui.action.NewFunctionAction;
import org.xins.gui.model.API;
import org.xins.gui.model.Project;
import org.xins.gui.resources.Settings;
import org.xins.gui.util.PatternInputVerifier;

/**
 * Dialog that asks information to create a new function within the current
 * API.
 *
 * @version $Revision: 1.3 $ $Date: 2006/03/14 14:37:53 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class NewFunctionDialog extends OkayCancelDialog {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * Pattern that the project name must match.
    */
   private static final String NAME_PATTERN = "[a-zA-Z]\\w*";

   /**
    * Pattern that the description must match.
    */
   private static final String DESC_PATTERN = "[a-zA-Z].*";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>NewFunctionDialog</code> for the specified
    * action.
    *
    * @param action
    *    the {@link NewFunctionAction} that owns this dialog, never
    *    <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>action == null</code>.
    */
   public NewFunctionDialog(NewFunctionAction action)
   throws IllegalArgumentException {

      super(action);

      // Construct components
      JLabel      nameLabel = createJLabel("Name"       );
      JLabel      descLabel = createJLabel("Description");
      JScrollPane descPanel = createJTextArea(descLabel, DESC_PATTERN);
      _nameField = createJTextField(nameLabel, NAME_PATTERN, exceptionSet);
      _descArea  = (JTextArea) descPanel.getViewport().getView();

      // Define column widths
      AppCenter appCenter = AppCenter.get();
      Settings settings = appCenter.getSettings();
      double p  = TableLayout.PREFERRED;
      double hg = settings.getHorizontalGap();
      double[] cols = { p, hg, p };

      // Define row heights
      double vg = settings.getVerticalGap();
      double[] rows = { p, vg, p };

      // Set the layout on the content pane
      JPanel contentPane = getDialogContent();
      TableLayout layout = new TableLayout(cols, rows);
      contentPane.setLayout(layout);

      // Add all components
      contentPane.add(nameLabel,  "0, 0");
      contentPane.add(_nameField, "2, 0");
      contentPane.add(descLabel,  "0, 2");
      contentPane.add(descPanel,  "2, 2");

      // Fix size
      setResizable(false);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The <code>JTextField</code> for the function name. Never
    * <code>null</code>.
    */
   private final JTextField _nameField;

   /**
    * The <code>JTextArea</code> for the description. Never
    * <code>null</code>.
    */
   private final JTextArea _descArea;

   /**
    * HashSet that will contain the names of existing APIs
    */
   public static HashSet exceptionSet = new HashSet();

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Resets the contents for this dialog.
    */
   public void resetContents() {

      // Get the current settings
      Settings settings = AppCenter.get().getSettings();

      // Set the text in the text components
      _nameField.setText(settings.getDefaultFunctionName()       );
      _descArea.setText (settings.getDefaultFunctionDescription());

      // Pack at a reasonable size
      pack();
   }
   
   /**
    * Setting new input verifier using _apiNames of the project object.
    *
    */
   
   public void setNewInputVerifier (API currentAPI) {
      
      // Build the new function exception list
      exceptionSet = currentAPI.getFunctionNames();
  
      //define the new input verifier for the _nameField
      InputVerifier verifier = new PatternInputVerifier(NAME_PATTERN,exceptionSet);
      _nameField.setInputVerifier(verifier);   
   }

   /**
    * Determines the function name filled in.
    *
    * @return
    *    the function name given by the user.
    */
   public String getFunctionName() {
      return _nameField.getText();
   }

   /**
    * Determines the function description filled in.
    *
    * @return
    *    the function description given by the user.
    */
   public String getFunctionDescription() {
      return _descArea.getText();
   }
}
