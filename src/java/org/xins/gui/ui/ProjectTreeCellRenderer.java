/*
 * $Id: ProjectTreeCellRenderer.java,v 1.6 2005/06/06 11:33:49 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.model.ProjectNode;
import org.xins.gui.util.Utils;

/**
 * Tree cell renderer for <code>ProjectNode</code>s.
 *
 * @version $Revision: 1.6 $ $Date: 2005/06/06 11:33:49 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class ProjectTreeCellRenderer
extends DefaultTreeCellRenderer {

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
    * Constructs a new <code>ProjectTreeCellRenderer</code>.
    */
   public ProjectTreeCellRenderer() {
      // empty
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   public Component getTreeCellRendererComponent(JTree   tree,
                                                 Object  value,
                                                 boolean selected,
                                                 boolean expanded,
                                                 boolean leaf,
                                                 int     row,
                                                 boolean hasFocus) {

      // Allow the superclass to update the display depending on the context
      super.getTreeCellRendererComponent(tree, value, selected, expanded,
                                         leaf, row, hasFocus);

      // Determine what project node is to be rendered
      ProjectNode node = (ProjectNode) value;

      // Apply the appropriate text and icon
      setText(node.getName());
      setIcon(node.getSmallIcon());

      return this;
   }
}
