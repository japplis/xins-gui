/*
 * $Id: PatternInputVerifier.java,v 1.2 2006/03/01 12:47:41 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.*;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.xins.common.MandatoryArgumentChecker;

/**
 * Input verifier based on a regular expression.
 *
 * @version $Revision: 1.2 $ $Date: 2006/03/01 12:47:41 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public class PatternInputVerifier
extends InputVerifier{

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
    * Constructs a new <code>InputVerifier</code> for the specified
    * regular expression.
    *
    * @param pattern
    *    the regular expression, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>pattern == null</code>.
    */
   public PatternInputVerifier(String pattern)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("pattern", pattern);

      // Store
      _pattern = pattern;
      _exceptionSet = null;
   }

   /**
    * Constructs a new <code>InputVerifier</code> for the specified
    * regular expression.
    *
    * @param pattern
    *    the regular expression, cannot be <code>null</code>.
    *
    * @param exceptionSet
    *    the HashSet containing all fields that are not allowed. This can be <code>null</code>.
    *    
    * @throws IllegalArgumentException
    *    if <code>pattern == null</code>.
    */
   public PatternInputVerifier(String pattern, HashSet exceptionSet)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("pattern", pattern);

      // Store
      _pattern = pattern;
      _exceptionSet = exceptionSet;
   }

   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The regular expression. Never <code>null</code>.
    */
   private final String _pattern;
   
   /**
    * The HashSet containing all fields that are not allowed. This can be <code>null</code>.
    */
   private final HashSet _exceptionSet;
   
   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Checks whether the input in the specified <code>JComponent</code> is
    * valid.
    *
    * @param input
    *    the component to check, cannot be <code>null</code>.
    *
    * @return
    *    <code>true</code> if the input is considered valid,
    *    <code>false</code> otherwise.
    *
    * @throws IllegalArgumentException
    *    if <code>input == null</code>.
    *
    * @throws ClassCastException
    *    if the specified component is not a {@link JTextComponent}.
    */
   public boolean verify(JComponent input)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("input", input);

      // Convert to a JTextComponent
      JTextComponent comp = (JTextComponent) input;

      // Get text
      String text = comp.getText();

      // Verify

      boolean match = text.matches(_pattern);

      System.err.println(" pattern=\""  + _pattern
                       + "\"; text=\"" + text
                       + "\"; match="  + match);

      if ((match) && (_exceptionSet != null)) {
          boolean mayNotMatch = false;
          for( Iterator iter = _exceptionSet.iterator(); iter.hasNext(); ) {
              String element = (String)iter.next();
              mayNotMatch = text.matches(element);
              System.err.println(" not allowed pattern=\""  + element
                   + "\"; text=\"" + text
                   + "\"; match="  + mayNotMatch);
              if (mayNotMatch) 
                  match = false;
          }
      }
      
      return match;
   }
}
