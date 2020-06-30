/*
 * $Id: SimpleAction.java,v 1.2 2005/06/01 23:30:10 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.awt.event.ActionEvent;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.xins.common.MandatoryArgumentChecker;
import org.xins.common.text.TextUtils;

/**
 * Simple action that does not do anything.
 *
 * @version $Revision: 1.2 $ $Date: 2005/06/01 23:30:10 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class SimpleAction extends ActionBase {

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
    * Constructs a new <code>SimpleAction</code> with the specified ID.
    */
   public SimpleAction(String id)
   throws IllegalArgumentException {
      super(id);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Callback method that is called when this action is triggered
    * (implementation method). This method should only be called from
    * {@link #actionPerformed(ActionEvent)}.
    *
    * @param event
    *    the action performance event, never <code>null</code>.
    */
   protected void actionPerformedImpl(ActionEvent event) {
      // empty
   }
}
