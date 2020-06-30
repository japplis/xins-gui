/*
 * $Id: CutAction.java,v 1.6 2005/06/01 23:30:10 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.awt.event.ActionEvent;

/**
 * Action that will place the selection on the clipboard and then remove it
 * from the current context.
 *
 * @version $Revision: 1.6 $ $Date: 2005/06/01 23:30:10 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class CutAction extends ActionBase {

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
    * Constructs a new <code>CutAction</code> instance.
    */
   public CutAction() {
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
