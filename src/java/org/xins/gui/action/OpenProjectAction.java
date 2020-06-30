/*
 * $Id: OpenProjectAction.java,v 1.5 2006/03/22 09:18:58 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import org.xins.gui.AppCenter;
import org.xins.gui.util.ProjectFileHandler;

/**
 * Action that will create a new project.
 *
 * @version $Revision: 1.5 $ $Date: 2006/03/22 09:18:58 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class OpenProjectAction extends ActionBase {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * Unique ID for this action.
    */
   public static final String ID = "OpenProject";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>OpenProjectAction</code> instance.
    */
   public OpenProjectAction() {
      
      // Create and open dialog and only allow directories to be shown. 
      _dialog = new JFileChooser();
      _dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      
      
   }

   
   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------
   
   /**
    * The dialog to show. Never <code>null</code>.
    */
   private final JFileChooser _dialog;
   
   
   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   protected void actionPerformedImpl(ActionEvent event) {
      
      // Get the appCenter object.
      AppCenter appCenter = AppCenter.get();
      
      // Open the open dialog and wait for action.
      int returnVal = _dialog.showOpenDialog(appCenter.getMainPanel());
      if (returnVal == JFileChooser.APPROVE_OPTION) {
         
         // If the directory is choosen, get the project and save it to the
         // selected directory.
         File file = _dialog.getSelectedFile();
         ProjectFileHandler pjh = new ProjectFileHandler(file,appCenter);
         pjh.openProject();
      }
   }
}
