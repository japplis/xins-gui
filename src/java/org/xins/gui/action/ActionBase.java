/*
 * $Id: ActionBase.java,v 1.18 2005/06/02 12:36:35 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.action;

import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import org.xins.common.MandatoryArgumentChecker;
import org.xins.common.text.TextUtils;

import org.xins.gui.AppCenter;

/**
 * Base class for all actions in the XINS GUI application.
 *
 * @version $Revision: 1.18 $ $Date: 2005/06/02 12:36:35 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public abstract class ActionBase
extends AbstractAction {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   /**
    * Determines the ID for an action, based on the class name.
    *
    * @param className
    *    the name of the class, should not be <code>null</code>.
    */
   private static String determineID(String className) {

      final String PACKAGE_NAME = "org.xins.gui.action";

      // Check preconditions
      if (! (className.startsWith(PACKAGE_NAME + '.')
             && className.endsWith("Action"))) {
         throw new Error("Unable to determine action ID for class \""
                       + className + "\".");
      }

      // Extract action ID
      int startIndex = PACKAGE_NAME.length() + 1;
      int endIndex   = className.length() - 6;
      return className.substring(startIndex, endIndex);
   }


   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>ActionBase</code> instance with the specified
    * unique ID.
    *
    * @param id
    *    the identifier for this action; if <code>null</code> then it will be
    *    detected based on the class name.
    */
   protected ActionBase(String id) {
   
      // Determine actual ID
      id = (id != null)
         ? id
         : determineID(getClass().getName());

      // Store ID
      _id = id;

      // Get AppCenter
      AppCenter appCenter = AppCenter.get();

      // Get localized strings
      String base     = "actions." + _id + '.';
      String label    = appCenter.translate(base + "label"   );
      int    mnemonic = appCenter.translate(base + "mnemonic").charAt(0);
      String comment  = appCenter.translate2(base + "comment" );

      // Get icons, if they exist
      String haveIcon = appCenter.translate2(base + "haveIcon");
      Icon icon = null;
      if (! "false".equalsIgnoreCase(haveIcon)) {
         icon = appCenter.getIcon(_id);
      }

      // Get accelerator
      String accelerator = appCenter.translate2(base + "accel");
      KeyStroke keyStroke;
      if (accelerator == null) {
         keyStroke = null;
      } else {
         keyStroke = KeyStroke.getKeyStroke(accelerator);
         if (keyStroke == null) {
            throw new RuntimeException(
               "Key stroke string \"" + accelerator +
               "\" has an invalid syntax.");
         }
      }

      // Set properties
      putValue(ACTION_COMMAND_KEY, _id                  );
      putValue(DEFAULT,            _id                  );
      putValue(NAME,               label                );
      putValue(MNEMONIC_KEY,       new Integer(mnemonic));
      putValue(SHORT_DESCRIPTION,  comment              );
      putValue(SMALL_ICON,         icon                 );
      putValue(ACCELERATOR_KEY,    keyStroke            );

      // Log
      AppCenter.LOG.debug("Constructed action \"" + _id + "\".");
   }

   /**
    * Constructs a new <code>ActionBase</code> instance.
    */
   protected ActionBase() {
      this((String) null);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The identifier for this action. Never <code>null</code>.
    */
   private final String _id;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Returns the ID for this action.
    *
    * @return
    *    the ID, never <code>null</code>.
    */
   public String getID() {
      return _id;
   }

   /**
    * Callback method that is called when this action is triggered
    * (wrapper method). This method will delegate to
    * {@link #actionPerformedImpl(ActionEvent)}.
    *
    * @param event
    *    the action performance event, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>event == null</code>.
    */
   public final void actionPerformed(ActionEvent event) {

      MandatoryArgumentChecker.check("event", event);

      AppCenter.LOG.debug("Action \"" + _id + "\" performed.");

      actionPerformedImpl(event);
   }

   /**
    * Callback method that is called when this action is triggered
    * (implementation method). This method should only be called from
    * {@link #actionPerformed(ActionEvent)}.
    *
    * @param event
    *    the action performance event, never <code>null</code>.
    */
   protected abstract void actionPerformedImpl(ActionEvent event);
}
