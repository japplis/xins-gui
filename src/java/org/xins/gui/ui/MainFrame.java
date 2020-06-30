/*
 * $Id: MainFrame.java,v 1.2 2005/06/01 23:30:11 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.action.ActionBase;
import org.xins.gui.resources.Settings;

/**
 * Main frame for the XINS GUI application.
 *
 * @version $Revision: 1.2 $ $Date: 2005/06/01 23:30:11 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class MainFrame extends JFrame {

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
    * Constructs a new <code>MainFrame</code> instance.
    *
    */
   public MainFrame()
   throws IllegalArgumentException {

      // Get the AppCenter
      AppCenter appCenter = AppCenter.get();

      // Set the frame title
      setTitle(appCenter.translate("frames.MainFrame.title"));

      // Set the initial size and position
      setBounds(appCenter.getSettings().getInitialFrameBounds());

      // Trigger the 'Exit' action when the close button is pressed
      addWindowListener(new WindowCloser());
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Inner classes
   //------------------------------------------------------------------------

   /**
    * Window listener that will trigger the <em>Exit</em> action when the
    * window is closed.
    *
    * @version $Revision: 1.2 $ $Date: 2005/06/01 23:30:11 $
    * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
    */
   private class WindowCloser extends WindowAdapter {

      //---------------------------------------------------------------------
      // Constructors
      //---------------------------------------------------------------------

      /**
       * Constructs a new <code>WindowCloser</code>.
       */
      public WindowCloser() {
         // empty
      }

      //---------------------------------------------------------------------
      // Fields
      //---------------------------------------------------------------------

      //---------------------------------------------------------------------
      // Methods
      //---------------------------------------------------------------------

      public void windowClosing(WindowEvent event) {
         ActionBase exitAction = AppCenter.get().getAction("Exit");
         ActionEvent actionEvent =
            new ActionEvent(event.getSource(),
                            ActionEvent.ACTION_PERFORMED,
                            exitAction.getID());
         exitAction.actionPerformed(actionEvent);
      }
   }
}
