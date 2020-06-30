/*
 * $Id: PasteAction.java,v 1.6 2005/06/01 23:30:10 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.awt.event.ActionEvent;

/**
 * Action that will paste the contents of the clipboard.
 *
 * @version $Revision: 1.6 $ $Date: 2005/06/01 23:30:10 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class PasteAction extends ActionBase {

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
    * Constructs a new <code>PasteAction</code> instance.
    */
   public PasteAction() {
      this.enabled = false;
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   protected void actionPerformedImpl(ActionEvent event) {
      // TODO
   }
}
