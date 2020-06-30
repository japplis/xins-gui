/*
 * $Id: OkayCancelDialog.java,v 1.17 2006/02/21 15:38:05 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import info.clearthought.layout.TableLayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout; // TODO: Use TableLayout
import java.awt.FocusTraversalPolicy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.action.ActionBase;
import org.xins.gui.resources.Settings;
import org.xins.gui.util.Utils;

/**
 * Abstract base class for action-owned dialogs that show an <em>Okay</em> and
 * a <em>Cancel</em> option.
 *
 * @version $Revision: 1.17 $ $Date: 2006/02/21 15:38:05 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public abstract class OkayCancelDialog extends ActionDialog {

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
    * Constructs a new <code>OkayCancelDialog</code> for the specified
    * action, with both <em>Okay</em> and <em>Cancel</em> options.
    *
    * @param action
    *    the action that owns this dialog, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>action == null</code>.
    */
   protected OkayCancelDialog(ActionBase action)
   throws IllegalArgumentException {
      this(action, true);
   }

   /**
    * Constructs a new <code>OkayCancelDialog</code> for the specified
    * action, with at least an <em>Okay</em> option.
    *
    * @param action
    *    the action that owns this dialog, never <code>null</code>.
    *
    * @param withCancel
    *    flag that indicates whether the dialog should show a <em>Cancel</em>
    *    option as well.
    *
    * @throws IllegalArgumentException
    *    if <code>action == null</code>.
    */
   protected OkayCancelDialog(ActionBase action, boolean withCancel)
   throws IllegalArgumentException {
      super(action);

      // Create icon label
      AppCenter appCenter = AppCenter.get();
      String    iconID    = action.getID() + ".Large"; // TODO
      JLabel    icon      = new JLabel(appCenter.getIcon(iconID));
      icon.setVerticalAlignment(JLabel.TOP);

      // Create contents panel
      _dialogContent = new JPanel();

      // Create button panel
      JPanel buttonPanel = new JPanel(new FlowLayout());

      // Create 'Okay' button
      _okayButton = new OkayButton();
      _okayButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            AppCenter.LOG.debug("Dialog::Okay");
            okayAction();
         }
      });
      buttonPanel.add(_okayButton);

      // Create 'Cancel' button
      if (withCancel) {
         JButton cancelButton = createJButton("Cancel");
         cancelButton.setVerifyInputWhenFocusTarget(false);
         cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               AppCenter.LOG.debug("Dialog::Cancel");
               cancelAction();
            }
         });
         buttonPanel.add(cancelButton);
      }

      // Create separator
      JSeparator separator = new JSeparator();

      // Define column widths
      Settings settings = appCenter.getSettings();
      double p  = TableLayout.PREFERRED;
      double hb = settings.getHorizontalBorder();
      double hg = settings.getHorizontalGap();
      double[] cols = { hb, p, hg, p, hb };

      // Define row heights
      double vb = settings.getVerticalBorder();
      double vg = settings.getVerticalGap();
      double[] rows = { vb, p, vg, p, vg, p, vb };

      // Set the layout on the content pane
      Container contentPane = getContentPane();
      TableLayout layout = new TableLayout(cols, rows);
      contentPane.setLayout(layout);

      // Add all components
      contentPane.add(icon,           "1, 1");
      contentPane.add(_dialogContent, "3, 1");
      contentPane.add(separator,      "1, 3, 3, 1");
      contentPane.add(buttonPanel,    "1, 5, 3, 1");

      // Make the Okay button the default
      getRootPane().setDefaultButton(_okayButton);

      // Create a FocusHandler
      _focusHandler = new FocusHandler();
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The <em>Okay</em> button. Never <code>null</code>.
    */
   private final OkayButton _okayButton;

   /**
    * The content pane as seen outside this class. Never <code>null</code>.
    */
   private final JPanel _dialogContent;

   /**
    * Field that indicates whether the <code>Okay</code> option has been
    * triggered on this dialog.
    */
   private boolean _okay;

   /**
    * Focus listener.
    */
   private final FocusHandler _focusHandler;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Returns the container in which the dialog content should be put.
    *
    * @return
    *    the container to put the actual content in, never <code>null</code>.
    */
   public final JPanel getDialogContent() {
      return _dialogContent;
   }

   /**
    * Callback method, called when the <em>Okay</em> action is performed.
    */
   private void okayAction() {
      _okay = true;
      setVisible(false);
   }

   /**
    * Callback method, called when the <em>Cancel</em> action is performed.
    *
    * <p>The implementation of this method in class {@link OkayCancelDialog}
    * is empty.
    */
   private void cancelAction() {
      _okay = false;
      setVisible(false);
   }

   /**
    * Shows this dialog. It will be positioned centered relative to the main
    * frame.
    *
    * @return
    *    <code>false</code> if the dialog was cancelled, otherwise
    *    <code>true</code>.
    *
    * @throws IllegalStateException
    *    if this dialog is already visible.
    */
   public final boolean showDialog() throws IllegalStateException {

      // Check preconditions
      if (isVisible()) {
         throw new IllegalStateException("show() was called on visible dialog (" + getClass().getName() + ").");
      }

      // Display in center of main frame
      setLocationRelativeTo(AppCenter.get().getMainFrame());

      // Listen to focus events on all Swing text fields
      List textFields = new ArrayList();
      Utils.findComponents(_dialogContent, JTextField.class, textFields);
      for (int i = 0; i < textFields.size(); i++) {
         JTextField field = (JTextField) textFields.get(i);
         field.addFocusListener(_focusHandler);
      }

      // Notify the 'Okay' button
      _okayButton.beforeShowDialog();

      // Display (will not return until dialog is closed)
      setVisible(true);

      // Notify the 'Okay' button again
      _okayButton.afterShowDialog();

      // Remove the focus listener from the Swing text fields
      for (int i = 0; i < textFields.size(); i++) {
         JTextField field = (JTextField) textFields.get(i);
         field.removeFocusListener(_focusHandler);
      }

      return _okay;
   }


   //------------------------------------------------------------------------
   // Inner classes
   //------------------------------------------------------------------------

   /**
    * Focus handler.
    *
    * @version $Revision: 1.17 $ $Date: 2006/02/21 15:38:05 $
    * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
    */
   private class FocusHandler
   extends Object
   implements FocusListener {

      //---------------------------------------------------------------------
      // Constructors
      //---------------------------------------------------------------------

      /**
       * Constructs a new <code>FocusHandler</code>.
       */
      private FocusHandler() {
         // empty
      }


      //---------------------------------------------------------------------
      // Fields
      //---------------------------------------------------------------------

      //---------------------------------------------------------------------
      // Methods
      //---------------------------------------------------------------------

      public void focusGained(FocusEvent event) {
         Component source = (Component) event.getSource();
         if (source instanceof JTextField) {
            JTextField field = (JTextField) source;
            field.setCaretPosition(0);
            field.moveCaretPosition(field.getText().length());
         }
      }

      public void focusLost(FocusEvent event) {
         Component source = (Component) event.getSource();
         if (source instanceof JTextField) {
            JTextField field = (JTextField) source;
            field.setCaretPosition(field.getCaretPosition());
         }
      }
   }

   /**
    * The <em>Okay</em> button.
    *
    * @version $Revision: 1.17 $ $Date: 2006/02/21 15:38:05 $
    * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
    */
   private class OkayButton
   extends DialogButton
   implements DocumentListener {

      //---------------------------------------------------------------------
      // Constructors
      //---------------------------------------------------------------------

      /**
       * Constructs a new <code>OkayButton</code>.
       */
      private OkayButton() {

         // Pass button ID up
         super("Ok");

         // Verify input on previous component before focussing this one
         setVerifyInputWhenFocusTarget(true);
      }


      //---------------------------------------------------------------------
      // Fields
      //---------------------------------------------------------------------

      /**
       * The list of event sources that this button has registered with as a
       * listener. Will be initialized to a non-<code>null</code> value by
       * {@link #beforeShowDialog()}.
       */
      private ArrayList _eventSources;


      //---------------------------------------------------------------------
      // Methods
      //---------------------------------------------------------------------

      /**
       * Updates the <em>enabled</em> state for this button.
       *
       * @return
       *    the list of {@link JComponent} instances found, never
       *    <code>null</code>.
       */
      private ArrayList updateEnabled() {
         ArrayList found = new ArrayList();
         Utils.findComponents(_dialogContent, JTextComponent.class, found);
         int count = found.size();
         boolean enabled = true;
         for (int i = 0; i < count; i++) {
            JComponent comp = (JComponent) found.get(i);
            System.err.println("JComponent[" + i + "]: " + comp.getClass().getName());
            if (enabled) {
               // Determine if component input is considered valid
               InputVerifier verifier = comp.getInputVerifier();
               if (verifier != null) {
                  enabled = verifier.verify(comp);
               }
            }
         }

         // Update the 'enabled' state of this component
         setEnabled(enabled);

         return found;
      }

      /**
       * Callback method that is called just before the dialog is displayed.
       */
      private void beforeShowDialog() {

         // Update the 'enabled' state
         ArrayList list = updateEnabled();

         // Listen to key events for each component
         int count = list.size();
         for (int i = 0; i < count; i++) {
            JComponent component = (JComponent) list.get(i);
            if (component instanceof JTextComponent) {
               ((JTextComponent)component).getDocument().addDocumentListener(this);
            }
         }

         // Store the list of event sources
         _eventSources = list;
      }

      /**
       * Callback method that is called right after the dialog was displayed.
       */
      private void afterShowDialog() {

         // Unregister as a listener
         int count = _eventSources.size();
         for (int i = 0; i < count; i++) {
            JComponent component = (JComponent) _eventSources.get(i);
            if (component instanceof JTextComponent) {
               ((JTextComponent)component).getDocument().removeDocumentListener(this);
            }
         }

      }

    /**
     * Gives notification that a portion of the document has been 
     * removed.  The range is given in terms of what the view last
     * saw (that is, before updating sticky positions).
     * 
     * 
     * @param e the document event
     */
    public void removeUpdate(DocumentEvent e) {
        updateEnabled();
    }

    /**
     * Gives notification that there was an insert into the document.  The 
     * range given by the DocumentEvent bounds the freshly inserted region.
     * 
     * 
     * @param e the document event
     */
    public void insertUpdate(DocumentEvent e) {
        updateEnabled();
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     * 
     * 
     * @param e the document event
     */
    public void changedUpdate(DocumentEvent e) {
        updateEnabled();
    }

   }
}
