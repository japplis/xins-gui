/*
 * $Id: Utils.java,v 1.6 2006/03/01 13:38:13 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.util;

import java.awt.Component;
import java.awt.Container;
import java.util.HashSet;
import java.util.List;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.ui.StandardButton;

/**
 * Collection of utility functions.
 *
 * @version $Revision: 1.6 $ $Date: 2006/03/01 13:38:13 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class Utils extends Object {
    
    //------------------------------------------------------------------------
    // Class fields
    //------------------------------------------------------------------------
    
    //------------------------------------------------------------------------
    // Class functions
    //------------------------------------------------------------------------
    
    /**
     * Gets all <code>Component</code> objects in the specified container.
     *
     * @param root
     *    the {@link Container} to find {@link Component} instances in, cannot
     *    be <code>null</code>.
     *
     * @param classFilter
     *    the {@link Class} that the found components must match, or
     *    <code>null</code> if anything will match.
     *
     * @param list
     *    the {@link List} to add all found {@link Component} instances to,
     *    cannot be <code>null</code>.
     *
     * @throws IllegalArgumentException
     *    if <code>root == null || list == null</code>.
     */
    public static void findComponents(Container root,
            Class     classFilter,
            List      list) {
        
        // Check preconditions
        MandatoryArgumentChecker.check("root", root, "list", list);
        
        if (classFilter == null) {
            classFilter = Component.class;
        }
        
        // Add the root if it is the right class
        if (classFilter.isAssignableFrom(root.getClass())) {
            list.add(root);
        }
        
        // Descend into the contained components
        int count = root.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component comp = root.getComponent(i);
            if (comp instanceof Container) {
                findComponents((Container) comp, classFilter, list);
            } else if (classFilter.isAssignableFrom(comp.getClass())) {
                list.add(comp);
            }
        }
    }
    
    /**
     * Determines the unqualified class name for an object.
     *
     * @param o
     *    the object to determine the unqualified class name for, cannot be
     *    <code>null</code>.
     *
     * @return
     *    the unqualified name of the class of the specified object, never
     *    <code>null</code> and never an empty string.
     *
     * @throws IllegalArgumentException
     *    if <code>o == null</code>.
     */
    public static String uqcn(Object o) {
        
        // Check preconditions
        MandatoryArgumentChecker.check("o", o);
        
        // Determine and return the unqualified class name
        String fqcn = o.getClass().getName();
        return fqcn.substring(fqcn.lastIndexOf('.') + 1);
    }
    
    /**
     * Constructs a new <code>JLabel</code>.
     *
     * @param translationPrefix
     *    the translation prefix, cannot be <code>null</code>.
     *
     * @param id
     *    the label ID, cannot be <code>null</code>.
     *
     * @return
     *    the {@link JLabel} instance, never <code>null</code>.
     *
     * @throws IllegalArgumentException
     *    if <code>translationPrefix == null || id == null</code>.
     */
    public static final JLabel createJLabel(String translationPrefix,
            String id)
            throws IllegalArgumentException {
        
        // Check preconditions
        MandatoryArgumentChecker.check("translationPrefix", translationPrefix,
                "id",                id);
        
        // Determine the caption
        AppCenter appCenter = AppCenter.get();
        String    base      = translationPrefix + "labels." + id + '.';
        String    caption   = appCenter.translate(base + "label");
        
        // Construct the JLabel
        JLabel label = new JLabel(caption);
        label.setVerticalAlignment(JLabel.TOP);
        
        // Assign a mnemonic, if defined
        String mnemonicStr = appCenter.translate2(base + "mnemonic");
        if (mnemonicStr != null && mnemonicStr.length() == 1) {
            label.setDisplayedMnemonic(mnemonicStr.charAt(0));
        }
        
        // Assign a tooltip, if defined
        String tooltip = appCenter.translate2(base + "comment");
        if (tooltip != null) {
            label.setToolTipText(tooltip);
        }
        
        return label;
    }
    
    /**
     * Constructs a new <code>JButton</code>.
     *
     * @param translationPrefix
     *    the translation prefix, cannot be <code>null</code>.
     *
     * @param id
     *    the button ID, cannot be <code>null</code>.
     *
     * @return
     *    the {@link JButton} instance, never <code>null</code>.
     *
     * @throws IllegalArgumentException
     *    if <code>translationPrefix == null || id == null</code>.
     */
    public static final JButton createJButton(String translationPrefix,
            String id) {
        return new StandardButton(translationPrefix, id);
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
    public static final JTextField createJTextField(JLabel label, String pattern) {
        
        return createJTextField(label, pattern, null);
        
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
    public static final JTextField createJTextField(JLabel label,
            String pattern,
            HashSet exceptionSet) {
        
        // Construct the component
        JTextField field = new JTextField(25); // TODO: Get from settings
        
        // Associate label with text field
        if (label != null) {
            label.setLabelFor(field);
            field.setToolTipText(label.getToolTipText());
        }
        
        // Set input verifier for the pattern
        if (pattern != null) {
            InputVerifier verifier = new PatternInputVerifier(pattern,exceptionSet);
            field.setInputVerifier(verifier);
        }
        
        return field;
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
    public static final JScrollPane createJTextArea(JLabel label,
            String pattern) {
        
        // Construct the component
        JTextArea area = new JTextArea(5, 25); // TODO: Get from settings
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        
        // Associate label with text field
        if (label != null) {
            label.setLabelFor(area);
            area.setToolTipText(label.getToolTipText());
        }
        
        // Set input verifier
        if (pattern != null) {
            InputVerifier verifier = new PatternInputVerifier(pattern);
            area.setInputVerifier(verifier);
        }
        
        // Wrap the text area in a scroll pane
        JScrollPane scroller =
                new JScrollPane(area,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        
        return scroller;
    }
    
    
    //------------------------------------------------------------------------
    // Constructors
    //------------------------------------------------------------------------
    
    /**
     * Constructs a new <code>Utils</code>.
     */
    private Utils() {
        // empty
    }
    
    
    //------------------------------------------------------------------------
    // Fields
    //------------------------------------------------------------------------
    
    //------------------------------------------------------------------------
    // Methods
    //------------------------------------------------------------------------
}
