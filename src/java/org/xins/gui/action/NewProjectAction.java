/*
 * $Id: NewProjectAction.java,v 1.10 2005/06/04 20:53:32 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.awt.event.ActionEvent;

import org.xins.gui.AppCenter;
import org.xins.gui.model.Project;
import org.xins.gui.ui.NewProjectDialog;

/**
 * Action that will create a new project.
 *
 * @version $Revision: 1.10 $ $Date: 2005/06/04 20:53:32 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class NewProjectAction extends ActionBase {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * Unique ID for this action.
    */
   public static final String ID = "NewProject";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>NewProjectAction</code> instance.
    */
   public NewProjectAction() {
      _dialog = new NewProjectDialog(this);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The dialog to show. Never <code>null</code>.
    */
   private final NewProjectDialog _dialog;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   protected void actionPerformedImpl(ActionEvent event) {

      // Reset the dialog to standard values
      _dialog.resetContents();

      // Show the dialog (waits until dialog is closed)
      boolean okay = _dialog.showDialog();

      // If the dialog was cancelled, just return
      if (! okay) {
         return;
      }

      // Create a new project with the specified details
      Project project = new Project(_dialog.getProjectName(),
                                    _dialog.getProjectDomain());

      // Make the new project the current one
      AppCenter.get().setProject(project);
   }
}
