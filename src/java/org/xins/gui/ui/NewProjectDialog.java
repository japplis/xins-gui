/*
 * $Id: NewProjectDialog.java,v 1.16 2005/06/06 13:03:04 znerd Exp $
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

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.action.ActionBase;
import org.xins.gui.action.NewProjectAction;
import org.xins.gui.model.Project;
import org.xins.gui.resources.Settings;

/**
 * Dialog that asks information to create a new XINS project.
 *
 * @version $Revision: 1.16 $ $Date: 2005/06/06 13:03:04 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class NewProjectDialog extends OkayCancelDialog {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * Pattern that the project name must match.
    */
   private static final String NAME_PATTERN = "[a-zA-Z][\\w-]*";

   /**
    * Pattern that the domain must match.
    */
   private static final String DOMAIN_PATTERN = "[\\w-]+(\\.[\\w-]+)+";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>NewProjectDialog</code> for the specified
    * action.
    *
    * @param action
    *    the {@link NewProjectAction} that owns this dialog, never
    *    <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>action == null</code>.
    */
   public NewProjectDialog(NewProjectAction action)
   throws IllegalArgumentException {

      super(action);

      // Construct labels
      JLabel nameLabel   = createJLabel("Name"  );
      JLabel domainLabel = createJLabel("Domain");

      // Construct text input fields
      _nameField   = createJTextField(nameLabel,   NAME_PATTERN);
      _domainField = createJTextField(domainLabel, DOMAIN_PATTERN);

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
      contentPane.add(nameLabel,    "0, 0");
      contentPane.add(_nameField,   "2, 0");
      contentPane.add(domainLabel,  "0, 2");
      contentPane.add(_domainField, "2, 2");

      // Fix size
      setResizable(false);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The <code>JTextField</code> for the project name. Never
    * <code>null</code>.
    */
   private final JTextField _nameField;

   /**
    * The <code>JTextField</code> for the domain. Never <code>null</code>.
    */
   private final JTextField _domainField;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Resets the contents for this dialog.
    */
   public void resetContents() {

      // Get the current settings
      Settings settings = AppCenter.get().getSettings();

      // Set the text in the text fields
      _nameField.setText  (settings.getDefaultProjectName()  );
      _domainField.setText(settings.getDefaultProjectDomain());

      // Pack at a reasonable size
      pack();
   }

   /**
    * Determines the project name filled in.
    *
    * @return
    *    the project name given by the user.
    */
   public String getProjectName() {
      return _nameField.getText();
   }

   /**
    * Determines the project domain filled in.
    *
    * @return
    *    the project domain given by the user.
    */
   public String getProjectDomain() {
      return _domainField.getText();
   }
}
