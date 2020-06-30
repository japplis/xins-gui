/*
 * $Id: AboutAction.java,v 1.6 2005/06/06 15:47:08 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.awt.event.ActionEvent;

import org.xins.gui.ui.AboutDialog;

/**
 * Action that will show the <em>About</em> dialog.
 *
 * @version $Revision: 1.6 $ $Date: 2005/06/06 15:47:08 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class AboutAction extends ActionBase {

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
    * Constructs a new <code>AboutAction</code> instance.
    */
   public AboutAction() {
      _dialog = new AboutDialog(this);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The dialog to show. Never <code>null</code>.
    */
   private final AboutDialog _dialog;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   protected void actionPerformedImpl(ActionEvent event) {
      _dialog.showDialog();
   }
}
