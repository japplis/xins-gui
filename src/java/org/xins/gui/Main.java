/*
 * $Id: Main.java,v 1.6 2005/06/04 16:56:21 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * Main class for the XINS GUI application.
 *
 * @version $Revision: 1.6 $ $Date: 2005/06/04 16:56:21 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class Main extends Object {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   /**
    * Main function for the application. This function is called when the XINS
    * GUI application is started.
    *
    * @param args
    *    the arguments for the application, should not be <code>null</code>.
    */
   public static final void main(String[] args) {

      // Check classpath
      if (! checkClassPath()) {
         System.err.println();
         System.err.println("Unable to start application.");
         System.exit(1);
         return;
      }

      // TODO: Allow log level to be specified in argument

      // Initialize logging subsystem
      configureLoggerFallback();

      // Construct application and show main frame
      AppCenter.init();
      AppCenter appCenter = AppCenter.get();
      appCenter.getMainFrame().setVisible(true);
   }

   /**
    * Checks the class path.
    *
    * @return
    *    <code>true</code> if everything is okay.
    */
   private static final boolean checkClassPath() {

      boolean okay = true;

      // Check Log4J library
      try {
         Class.forName("org.apache.log4j.Logger");
      } catch (Throwable exception) {
         System.err.println("FATAL: Log4J library is not in the class path.");
         okay = false;
      }

      // Check XINS/Java Common Library
      try {
         Class.forName("org.xins.common.Library");
      } catch (Throwable exception) {
         System.err.println("FATAL: XINS/Java Common Library is not in the class path.");
         okay = false;
      }

      // Check TableLayout library
      try {
         Class.forName("info.clearthought.layout.TableLayout");
      } catch (Throwable exception) {
         System.err.println("FATAL: Clearthought TableLayout library is not in the class path.");
         okay = false;
      }

      // Check JDOM library
      try {
         Class.forName("org.jdom.Document");
      } catch (Throwable exception) {
         System.err.println("FATAL: JDOM library is not in the class path.");
         okay = false;
      }

      // Otherwise everything seems okay
      return okay;
   }

   /**
    * Initializes the logging subsystem with default settings.
    */
   private static final void configureLoggerFallback() {

      Properties settings = new Properties();

      // Send all log messages to the logger named 'console'
      settings.setProperty("log4j.rootLogger",
                           "ALL, console");

      // Define the type of the logger named 'console'
      settings.setProperty("log4j.appender.console",
                           "org.apache.log4j.ConsoleAppender");

      // Use a pattern-layout for the logger
      settings.setProperty("log4j.appender.console.layout",
                           "org.apache.log4j.PatternLayout");

      // Define the pattern for the logger
      settings.setProperty("log4j.appender.console.layout.ConversionPattern",
                           "%-6p %m%n");

      // Perform Log4J configuration
      PropertyConfigurator.configure(settings);
   }


   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------
}
