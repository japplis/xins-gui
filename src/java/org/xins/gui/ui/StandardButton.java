/*
 * $Id: StandardButton.java,v 1.1 2005/06/04 21:34:01 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import java.net.URL;
import javax.swing.*;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.action.ActionBase;

/**
 * Standard button for the XINS GUI application.
 *
 * @version $Revision: 1.1 $ $Date: 2005/06/04 21:34:01 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public class StandardButton extends JButton {

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
    * Constructs a new <code>StandardButton</code>.
    *
    * @param translationPrefix
    *    the translation prefix, cannot be <code>null</code>.
    *
    * @param id
    *    the button ID, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>translationPrefix == null || id == null</code>.
    */
   public StandardButton(String translationPrefix, String id) {

      // Check preconditions
      MandatoryArgumentChecker.check("translationPrefix", translationPrefix,
                                     "id",                id);

      // Determine the caption
      AppCenter appCenter = AppCenter.get();
      String    base      = translationPrefix + "buttons." + id + '.';
      String    caption   = appCenter.translate(base + "label");

      // Set the caption
      setText(caption);

      // Assign a mnemonic, if defined
      String mnemonicStr = appCenter.translate2(base + "mnemonic");
      if (mnemonicStr != null && mnemonicStr.length() == 1) {
         char m = Character.toUpperCase(mnemonicStr.charAt(0));
         setMnemonic(m);
      }

      // Assign a tooltip, if defined
      String tooltip = appCenter.translate2(base + "comment");
      if (tooltip != null) {
         setToolTipText(tooltip);
      }

      // Assign an icon, if defined
      String haveIcon = appCenter.translate2(base + "haveIcon");
      if (! "false".equalsIgnoreCase(haveIcon)) {
         setIcon(appCenter.getIcon(id + "Button"));
      }

      // Get accelerator
      String accelerator = appCenter.translate2(base + "accel");
      KeyStroke keyStroke;
      if (accelerator == null) {
         keyStroke = null;
      } else {
         keyStroke = KeyStroke.getKeyStroke(accelerator);
         if (keyStroke == null) {
            throw new RuntimeException(
               "Key stroke string \"" + accelerator +
               "\" has an invalid syntax.");
         }
         // TODO: Assign accelerator to button
      }
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------
}
