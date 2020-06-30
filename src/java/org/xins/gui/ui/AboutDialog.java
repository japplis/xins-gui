/*
 * $Id: AboutDialog.java,v 1.1 2005/06/06 15:47:08 znerd Exp $
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
import org.xins.gui.action.AboutAction;
import org.xins.gui.model.Project;
import org.xins.gui.resources.Settings;

/**
 * About dialog.
 *
 * @version $Revision: 1.1 $ $Date: 2005/06/06 15:47:08 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class AboutDialog extends OkayCancelDialog {

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
    * Constructs a new <code>AboutDialog</code> for the specified action.
    *
    * @param action
    *    the {@link AboutAction} that owns this dialog, never
    *    <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>action == null</code>.
    */
   public AboutDialog(AboutAction action)
   throws IllegalArgumentException {

      super(action, false);

      // Construct labels
      JLabel label = createJLabel("Text");
      // TODO: Translate label caption

      // Set the layout on the content pane
      JPanel contentPane = getDialogContent();
      contentPane.setLayout(new FlowLayout());
      contentPane.add(label);

      // Fix size
      pack();
      setResizable(false);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   public void resetContents() {
      // empty
   }
}
