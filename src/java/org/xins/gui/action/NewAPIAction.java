/*
 * $Id: NewAPIAction.java,v 1.6 2006/03/14 14:39:50 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;

import org.xins.gui.AppCenter;
import org.xins.gui.model.API;
import org.xins.gui.model.Project;
import org.xins.gui.ui.NewAPIDialog;

/**
 * Action that will add an API to the project.
 *
 * @version $Revision: 1.6 $ $Date: 2006/03/14 14:39:50 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class NewAPIAction
extends ActionBase
implements PropertyChangeListener {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * Unique ID for this action.
    */
   public static final String ID = "NewAPI";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>NewAPIAction</code> instance.
    */
   public NewAPIAction() {

      // Determine initial 'enabled' state
      AppCenter appCenter = AppCenter.get();
      this.enabled = (appCenter.getProject() != null);

      // Create the dialog to be shown
      _dialog = new NewAPIDialog(this);

      // Listen to property changes in the AppCenter
      appCenter.addPropertyChangeListener(this);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The dialog to show. Never <code>null</code>.
    */
   private final NewAPIDialog _dialog;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   protected void actionPerformedImpl(ActionEvent event) {
        
      // Get the current project object
      AppCenter appCenter = AppCenter.get();
      Project project = appCenter.getProject();
      
      // Setting new inputverifier.      
      _dialog.setNewInputVerifier(project);
      
      // Reset the dialog to standard values
      _dialog.resetContents();

      // Show the dialog (waits until dialog is closed)
      boolean okay = _dialog.showDialog();

      // If the dialog was cancelled, just return
      if (! okay) {
         return;
      }

      // Create a new API with the specified details
      String apiName     = _dialog.getAPIName();
      String description = _dialog.getAPIDescription();
      API    api         = new API(apiName, description);

      // Add the API to the project
      project.add(api);
      
      // Select the newly created API
      appCenter.setProjectNode(api);
   }

   // TODO: Document
   // TODO: Move to inner class
   public void propertyChange(PropertyChangeEvent event) {
      setEnabled(AppCenter.get().getProject() != null);
   }
}
