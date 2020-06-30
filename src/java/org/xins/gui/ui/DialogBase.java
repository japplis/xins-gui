/*
 * $Id: DialogBase.java,v 1.4 2005/06/06 13:02:29 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import javax.swing.JDialog;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;

/**
 * Abstract base class for all dialogs in the XINS GUI application.
 *
 * @version $Revision: 1.4 $ $Date: 2005/06/06 13:02:29 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public abstract class DialogBase extends JDialog {

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
    * Constructs a new <code>DialogBase</code> instance.
    *
    * @param titleKey
    *    the translation key for the title, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>titleKey == null</code>.
    */
   protected DialogBase(String titleKey)
   throws IllegalArgumentException {

      super(AppCenter.get().getMainFrame(),
            AppCenter.get().translate(titleKey));
      // TODO: Throws NPE instead of IllegalArgumentException
      // TODO: When locale changes, then translation should change
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------
}
