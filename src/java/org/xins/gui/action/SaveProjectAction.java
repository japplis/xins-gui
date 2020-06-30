/*
 * $Id: SaveProjectAction.java,v 1.3 2006/03/22 12:25:27 lexu Exp $
 *
 * Copyright 2003-2006 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JFileChooser;
import org.xins.gui.AppCenter;
import org.xins.gui.model.Project;
import org.xins.gui.util.ProjectFileHandler;

/**
 * Action that will save a project.
 *
 * @version $Revision: 1.3 $ $Date: 2006/03/22 12:25:27 $
 * @author Lex Uijthof (<a href="mailto:lex.uijthof@nl.wanadoo.com">lex.uijthof@nl.wanadoo.com</a>)
 */
public final class SaveProjectAction 
extends ActionBase 
implements PropertyChangeListener {
   
   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------
   
   /**
    * Unique ID for this action.
    */
   public static final String ID = "SaveProject";
   
   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------
   
   /**
    * Constructs a new <code>OpenProjectAction</code> instance.
    */
   public SaveProjectAction() {

      // Create and save dialog and only allow directories to be shown.
      _dialog = new JFileChooser();
      _dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      
      // Get the appCenter object and determine if the save button needs to be 
      // active.
      AppCenter appCenter = AppCenter.get();
      setEnabled(appCenter.getProject() != null);
      
      // Listen to property changes in the AppCenter
      appCenter.addPropertyChangeListener(this);
      
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
      
      boolean cancelOrResult = false;
      
      // Get the appCenter object.
      AppCenter appCenter = AppCenter.get();
      
      // Open the save dialog and wait for action.
      while (!cancelOrResult) {
         int returnVal = _dialog.showSaveDialog(appCenter.getMainPanel());
         if (returnVal == JFileChooser.APPROVE_OPTION) {

            // If the directory is choosen, get the project and save it to the
            // selected directory.
            File directory = _dialog.getSelectedFile();
            ProjectFileHandler pjh = new ProjectFileHandler(directory,appCenter);
            cancelOrResult = pjh.saveProject();    
         }
         if (returnVal == JFileChooser.CANCEL_OPTION){
            cancelOrResult = true;
         }
      }
   }

   public void propertyChange(PropertyChangeEvent evt) {
      setEnabled((AppCenter.get().getProject() != null));
   }
   
}
