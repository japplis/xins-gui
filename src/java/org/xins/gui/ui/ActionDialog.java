/*
 * $Id: ActionDialog.java,v 1.11 2006/03/01 13:40:40 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import java.net.URL;
import java.util.HashSet;
import javax.swing.*;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.action.ActionBase;
import org.xins.gui.util.Utils;

/**
 * Abstract base class for action-owned dialogs.
 *
 * @version $Revision: 1.11 $ $Date: 2006/03/01 13:40:40 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public abstract class ActionDialog extends DialogBase {

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
    * Constructs a new <code>ActionDialog</code> for the specified
    * action.
    *
    * @param action
    *    the action that owns this dialog, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>action == null</code>.
    */
   protected ActionDialog(ActionBase action)
   throws IllegalArgumentException {

      super("dialogs.actions." + action.getID() + ".title");
      // TODO: We throw NPE iso IAE

      _action            = action;
      _translationPrefix = "dialogs.actions." + action.getID() + '.';

      setModal(true);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The prefix for translations. Never <code>null</code>.
    */
   private final String _translationPrefix;

   /**
    * The action that owns this dialog. Never <code>null</code>.
    */
   private final ActionBase _action;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>JLabel</code>.
    *
    * @param id
    *    the label ID, cannot be <code>null</code>.
    *
    * @return
    *    the {@link JLabel} instance, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>id == null</code>.
    */
   protected final JLabel createJLabel(String id)
   throws IllegalArgumentException {
      return Utils.createJLabel(_translationPrefix, id);
   }

   /**
    * Constructs a new <code>JButton</code>.
    *
    * @param id
    *    the button ID, cannot be <code>null</code>.
    *
    * @return
    *    the {@link JButton} instance, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>id == null</code>.
    */
   protected final JButton createJButton(String id) {
      return Utils.createJButton(_translationPrefix, id);
   }

   /**
    * Constructs a new <code>JTextField</code>.
    *
    * @param label
    *    the associated {@link JLabel}, can be <code>null</code>.
    *
    * @param pattern
    *    a regular expression which the content of the text field should
    *    match, or <code>null</code> if there is no restriction.
    *
    * @return
    *    the {@link JTextField} instance, never <code>null</code>.
    */
   protected final JTextField createJTextField(JLabel label, String pattern) {
      return Utils.createJTextField(label, pattern);
   }
   
   /**
    * Constructs a new <code>JTextField</code>.
    *
    * @param label
    *    the associated {@link JLabel}, can be <code>null</code>.
    *
    * @param pattern
    *    a regular expression which the content of the text field should
    *    match, or <code>null</code> if there is no restriction.
    *
    * @param exceptionSet
    *    A set of names that may not be used in this text field.
    *
    * @return
    *    the {@link JTextField} instance, never <code>null</code>.
    */
   protected final JTextField createJTextField(JLabel label, String pattern, HashSet exceptionSet) {
      return Utils.createJTextField(label, pattern, exceptionSet);
   }
   
   /**
    * Constructs a new <code>JTextArea</code>. The text area will be wrapped
    * inside a {@link JScrollPane}. Use
    *
    *    <blockquote><code>({@link JTextArea}) </code> {@link JScrollPane#getViewport() getViewport()}.{@link JViewport#getView() getView()}</blockquote>
    *
    * to get the contained {@link JTextArea}.
    *
    * @param label
    *    the associated {@link JLabel}, can be <code>null</code>.
    *
    * @param pattern
    *    a regular expression which the content of the text area should
    *    match, or <code>null</code> if there is no restriction.
    *
    * @return
    *    the {@link JTextArea}, wrapped inside a {@link JScrollPane}, never
    *    <code>null</code>.
    */
   protected final JScrollPane createJTextArea(JLabel label, String pattern) {
      return Utils.createJTextArea(label, pattern);
   }

   /**
    * Retrieves the action that owns this dialog.
    *
    * @return
    *    the action that owns this dialog, never <code>null</code>.
    */
   public final ActionBase getAction() {
      return _action;
   }

   /**
    * Resets the contents for this dialog.
    */
   public abstract void resetContents();


   //------------------------------------------------------------------------
   // Inner classes
   //------------------------------------------------------------------------

   /**
    * Dialog button.
    *
    * @version $Revision: 1.11 $ $Date: 2006/03/01 13:40:40 $
    * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
    */
   protected class DialogButton extends StandardButton {
      //TODO: Remove this class

      //---------------------------------------------------------------------
      // Constructors
      //---------------------------------------------------------------------

      /**
       * Constructs a new <code>DialogButton</code>.
       *
       * @param id
       *    the button ID, cannot be <code>null</code>.
       *
       * @throws IllegalArgumentException
       *    if <code>id == null</code>.
       */
      public DialogButton(String id) {
         super(_translationPrefix, id);
      }

      //---------------------------------------------------------------------
      // Fields
      //---------------------------------------------------------------------

      //---------------------------------------------------------------------
      // Methods
      //---------------------------------------------------------------------
   }

}
