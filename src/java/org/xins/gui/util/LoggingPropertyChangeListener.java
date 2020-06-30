/*
 * $Id: LoggingPropertyChangeListener.java,v 1.2 2005/06/02 11:40:35 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.xins.gui.AppCenter;

/**
 * Property change listener that logs all property changes.
 *
 * @version $Revision: 1.2 $ $Date: 2005/06/02 11:40:35 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class LoggingPropertyChangeListener
extends Object
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
    * Constructs a new <code>LoggingPropertyChangeListener</code>.
    */
   public LoggingPropertyChangeListener() {
      // empty
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   public void propertyChange(PropertyChangeEvent event) {

      String sourceClass  = event.getSource().getClass().getName();
      String propertyName = event.getPropertyName();
      Object oldValue     = event.getOldValue();
      Object newValue     = event.getNewValue();

      sourceClass = sourceClass.substring(sourceClass.lastIndexOf('.') + 1);

      String oldString;
      if (oldValue == null) {
         oldString = "(null)";
      } else {
         oldString = "\"" + String.valueOf(oldValue) + '"';
      }

      String newString;
      if (newValue == null) {
         newString = "(null)";
      } else {
         newString = "\"" + String.valueOf(newValue) + '"';
      }

      AppCenter.LOG.debug(sourceClass
                        + "::"
                        + propertyName
                        + ' '
                        + oldString
                        + " => "
                        + newString);
   }
}
