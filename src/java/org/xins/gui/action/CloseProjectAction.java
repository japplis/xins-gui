/*
 * $Id: CloseProjectAction.java,v 1.7 2005/06/02 09:50:10 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.xins.gui.AppCenter;

/**
 * Action that will create a new project.
 *
 * @version $Revision: 1.7 $ $Date: 2005/06/02 09:50:10 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class CloseProjectAction
extends ActionBase
implements PropertyChangeListener {

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
    * Constructs a new <code>CloseProjectAction</code> instance.
    */
   public CloseProjectAction() {

      AppCenter appCenter = AppCenter.get();

      setEnabled(appCenter.getProject() != null);

      appCenter.addPropertyChangeListener(this);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   protected void actionPerformedImpl(ActionEvent event) {
      AppCenter.get().setProject(null);
   }

   public void propertyChange(PropertyChangeEvent event) {
      setEnabled((AppCenter.get().getProject() != null));
   }
}
