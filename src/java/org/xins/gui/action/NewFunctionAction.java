/*
 * $Id: NewFunctionAction.java,v 1.4 2006/03/14 14:37:33 lexu Exp $
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
import org.xins.gui.model.Function;
import org.xins.gui.ui.NewFunctionDialog;

/**
 * Action that will add a function to an API.
 *
 * @version $Revision: 1.4 $ $Date: 2006/03/14 14:37:33 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class NewFunctionAction
extends ActionBase
implements PropertyChangeListener {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * Unique ID for this action.
    */
   public static final String ID = "NewFunction";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>NewFunctionAction</code> instance.
    */
   public NewFunctionAction() {

      // Determine initial 'enabled' state
      AppCenter appCenter = AppCenter.get();
      this.enabled = (appCenter.getProject() != null);

      // Create the dialog to be shown
      _dialog = new NewFunctionDialog(this);

      // Listen to property changes in the AppCenter
      appCenter.addPropertyChangeListener(this);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The dialog to show. Never <code>null</code>.
    */
   private final NewFunctionDialog _dialog;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   protected void actionPerformedImpl(ActionEvent event) {
  
      // Get the current API object
      AppCenter appCenter = AppCenter.get();
      API api = appCenter.getAPI();
      
      // Setting new inputverifier.      
      _dialog.setNewInputVerifier(api);
      
      // Reset the dialog to standard values
      _dialog.resetContents();

      // Show the dialog (waits until dialog is closed)
      boolean okay = _dialog.showDialog();

      // If the dialog was cancelled, just return
      if (! okay) {
         return;
      }

      // Create a new API with the specified details
      String   name        = _dialog.getFunctionName();
      String   description = _dialog.getFunctionDescription();
      Function function    = new Function(name, description);

      // Add the function to the API
      
      api.add(function);

      // Select the newly created API
      appCenter.setProjectNode(function);
   }

   public void propertyChange(PropertyChangeEvent event) {
      setEnabled(AppCenter.get().getAPI() != null);
   }
}
