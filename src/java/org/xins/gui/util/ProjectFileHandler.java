/*
 * $Id: ProjectFileHandler.java,v 1.2 2006/03/22 12:23:38 lexu Exp $
 *
 * Copyright 2003-2006 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */

package org.xins.gui.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.xins.gui.AppCenter;
import org.xins.gui.model.Project;

public class ProjectFileHandler {
   
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
    * Constructs a new <code>ProjectFileHandler</code> instance.
    *
    * @param directory
    *    The directory where the project needs to be saved to or opened from.
    *
    * @parem appCenter
    *    The appCenter that (can) contain a project that needs to be saved or needs to be opened..
    *
    */
   public ProjectFileHandler (File directory, AppCenter appCenter) {
      
      _appCenter = appCenter;
      _directory = directory;
      _project = appCenter.getProject();
      
   }
   
   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------
   
   /**
    * The appcenter object. Can be <code>null</code>.
    */
   private AppCenter _appCenter;
   
   /**
    * The current XINS project. Can be <code>null</code>.
    */
   private Project _project;
   
   /**
    * The file that needs to be handled. This field is never <code>null</code>.
    */
   private File _directory;
      
   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------
   
   /**
    * Save the current project
    */
   public boolean saveProject () {

      boolean success;
      
      // Create the project directory.
      String projectDir = _directory+File.pathSeparator+_project.getName();
      if (!createDir(projectDir)) {
         return false;
      }
      
      // Create xins-project.xml
      if (!saveFile(new File(projectDir+File.pathSeparator+_project.getPath()),_project.getXMLString())){
         return false;
      }
         
      // Create apis directory
      String projectApisDir = projectDir+File.pathSeparator+"apis";
      if (!createDir(projectApisDir)) {
         return false;
      }
      
      // Loop through the projects API's and create the necesarry directories and files
      for( Iterator apiIter = _project.getAPINames().iterator(); apiIter.hasNext(); ) {
         String apiElement = (String)apiIter.next();
         
         //Create the current API directory
         String projectAPIDir = projectApisDir+File.pathSeparator+apiElement;
         if (!createDir(projectAPIDir)) {
           return false;
         }
         
         // Create this API's impl directory
         String projectImplDir = projectAPIDir+File.pathSeparator+"impl";
         if (!createDir(projectImplDir)) {
           return false;
         }
         
         // Create this API's spec directory
         String projectSpecDir = projectAPIDir+File.pathSeparator+"spec";
         if (!createDir(projectSpecDir)) {
           return false;
         }
         
         // Create api.xml
         if (!saveFile(new File(projectDir+File.pathSeparator+_project.getAPI(apiElement).getPath()),_project.getAPI(apiElement).getXMLString())) {
            return false;
         }
         
         // Create all .fnc files
         for( Iterator fncIter = _project.getAPI(apiElement).getFunctionNames().iterator(); fncIter.hasNext(); ) {
            String fncElement = (String)fncIter.next();
            if (!saveFile(new File(projectDir+File.pathSeparator+
                    _project.getAPI(apiElement).getFunction(fncElement).getPath()),
                    _project.getAPI(apiElement).getFunction(fncElement).getXMLString())) {
               return false;
            }
         }
      }
      return true;
   }
   
   /**
    * Saves the actual file with the given content.
    *
    * @param file
    *    The where the content needs to be saved to.
    *
    * @parem content
    *    The string that needs to be saved into file.
    *
    */
   private boolean saveFile (File file, String content) {
      
      boolean success;
      
      // Check if the file exists.
      success = (new File(file.getName())).exists();
      if (success) {
         
         // If it exists Show error dialog and return nothing;
         JOptionPane.showConfirmDialog(_appCenter.getMainPanel(),
                 "Project not saved.\nDirectory '"+file.getName()+
                 "' Already exists.","Error Saving project",
                 JOptionPane.ERROR_MESSAGE);
         return false;
      }
      
      try {
         FileWriter fileWriter =  new FileWriter(file);
         BufferedWriter writer = new BufferedWriter(fileWriter);
         writer.write(content);
         writer.close();
         writer = null;
      } catch (IOException e) {  
         JOptionPane.showConfirmDialog(_appCenter.getMainPanel(),
                 "Project file '"+file.toString()+"' couldn't be written.",
                 "Error saving Project",
                 JOptionPane.ERROR_MESSAGE);
         e.printStackTrace();
         return false;
      } 
      return true;
   }
   
   /**
    * Create a directory..
    *
    * @param file
    *    The where the content needs to be saved to.
    *
    * @parem content
    *    The string that needs to be saved into file.
    *
    */
   private boolean createDir (String directory) {
      
      boolean success;
      
      // Check if the directory exists.
      success = (new File(directory)).exists();
      if (success) {
         
         // If it exists Show error dialog and return nothing;
         JOptionPane.showConfirmDialog(_appCenter.getMainPanel(),
                 "Project not saved.\nDirectory '"+directory+
                 "' Already exists.","Error Saving project",
                 JOptionPane.ERROR_MESSAGE);
         return false;
      }
      
      // Actually create the Project directory.
      success = (new File(directory)).mkdir();
      if (!success) {
         
         // Directory creation failed
         JOptionPane.showConfirmDialog(_appCenter.getMainPanel(),
                 "Project not saved.\nDirectory '"+directory+
                 "' couldn't be created.","Error Saving project",
                 JOptionPane.ERROR_MESSAGE);
         return false;
      }
      
      return true;
   }
   
   /**
    * Opens the project and read all the necessary files.
    */
   public void openProject () {
      
      // In progress.
      // Create new and empty project based on the _directory name.

   }
    
   private String openFile (File file) {
    
      String content = "";
      try {
         FileReader fileReader = new FileReader(file);
         BufferedReader reader = new BufferedReader(fileReader);
         String line;
         int i = 1;
         while ((line = reader.readLine()) != null) {
            content = content + line;
         }
         fileReader.close();
         fileReader = null;           
      } catch (IOException e) {
         e.printStackTrace();
      } 
      
      return content;
      
   }
   
}
