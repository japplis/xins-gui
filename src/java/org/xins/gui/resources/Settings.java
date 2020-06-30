/*
 * $Id: Settings.java,v 1.11 2006/03/14 14:42:48 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.resources;

import java.awt.Rectangle;

import java.util.List;
import java.util.Locale;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.model.*;

/**
 * Representation of the current configuration settings for the XINS GUI
 * application.
 *
 * @version $Revision: 1.11 $ $Date: 2006/03/14 14:42:48 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class Settings extends Object {

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
    * Constructs a new <code>Settings</code> object.
    */
   public Settings() {
      // empty
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Determines the bounds the main frame should initially have.
    *
    * @return
    *    a {@link Rectangle} that represents the bounds the main frame should
    *    initially have, never <code>null</code>.
    */
   public Rectangle getInitialFrameBounds() {
      return new Rectangle(200, 200, 640, 480); // TODO
   }

   /**
    * Determines the active locale.
    *
    * @return
    *    the active {@link Locale}, never <code>null</code>.
    */
   public Locale getLocale() {
      Locale locale = Locale.getDefault();
      if (locale == null) {
         locale = new Locale("en", "US");
      }
      return locale;
   }

   /**
    * Checks whether Swing text anti-aliasing should be enabled or disabled.
    *
    * @return
    *    <code>true</code> if anti-alising should be enabled.
    */
   public boolean isAntiAliasedTextEnabled() {
      return true;
   }

   /**
    * Checks whether bold text should be the default.
    *
    * @return
    *    <code>true</code> if bold text should be used throughout the user
    *    interface.
    */
   public boolean isBoldText() {
      return false;
   }

   /**
    * Indicates whether the components in the split pane should redraw
    * continuously as the divider changes position.
    *
    * @return
    *    <code>true</code> if the components should redraw continuously as the
    *    divider changes position, or <code>false</code> if they should not
    *    redraw until the divider position stops changing.
    */
   public boolean isContinuouslyRedrawSplitPane() {
      return true;
   }

   // TODO: Document
   public String getDefaultProjectName() {
      return "MyProject";
   }

   // TODO: Document
   public String getDefaultProjectDomain() {
      return "com.mycompany";
   }

   // TODO: Document
   public String getDefaultAPIName() {
      return "MyAPI";
   }

   // TODO: Document
   public String getDefaultAPIDescription() {
      return "Test API. For various test purposes.";
   }

   // TODO: Document
   public String getDefaultFunctionName() {
      return "MyFunction";
   }

   // TODO: Document
   public String getDefaultFunctionDescription() {
      return "Test function. For various test purposes.";
   }

   // TODO: Document
   public String getDefaultProjectDirectory() {
      return System.getProperty("user.home");
   }

   // TODO: Document
   public double getProjectTreeResizeWeight() {
      return 0.3;
   }

   // TODO: Document
   public int getHorizontalBorder() {
      return 6;
   }

   // TODO: Document
   public int getHorizontalGap() {
      return 6;
   }

   // TODO: Document
   public int getVerticalBorder() {
      return 6;
   }

   // TODO: Document
   public int getVerticalGap() {
      return 6;
   }
}
