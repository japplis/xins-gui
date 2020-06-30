/*
 * $Id: ProjectTree.java,v 1.11 2005/06/06 15:11:01 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.ui;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.model.Project;
import org.xins.gui.model.ProjectNode;
import org.xins.gui.model.ProjectTreeModel;
import org.xins.gui.resources.Settings;

/**
 * Visual project tree component.
 *
 * @version $Revision: 1.11 $ $Date: 2005/06/06 15:11:01 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class ProjectTree
extends JPanel {

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
    * Constructs a new <code>ProjectTree</code> instance.
    */
   public ProjectTree() {

      // Listen to AppCenter property changes
      AppCenter appCenter = AppCenter.get();
      Settings  settings  = appCenter.getSettings();
      appCenter.addPropertyChangeListener(new PropertyChangeHandler());

      // Use border layout
      setLayout(new BorderLayout());

      // Construct a JTree with model
      ProjectTreeModel treeModel = appCenter.getTreeModel();
      _tree = new JTree(treeModel);
      _tree.setRootVisible(true);
      _tree.setShowsRootHandles(false);
      _tree.setCellRenderer(new ProjectTreeCellRenderer());

      // Define column widths
      double hb = settings.getHorizontalBorder();
      double f  = TableLayout.FILL;
      double[] cols = { hb, f, hb };

      // Define row heights
      double vb = settings.getVerticalBorder();
      double[] rows = { vb, f, vb };

      // Stick the JTree inside a panel with some margins
      TableLayout layout = new TableLayout(rows, cols);
      JPanel treePanel = new JPanel(layout);
      treePanel.setBackground(_tree.getBackground());
      treePanel.add(_tree, "1, 1");

      // Stick the tree panel inside a scroll pane
      _scrollPane = new JScrollPane(
         treePanel,
         JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
         JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      // Listen to tree selection events
      _tree.addTreeSelectionListener(new TreeSelectionHandler());

      // Veto tree root node expansion
      _tree.addTreeWillExpandListener(new TreeExpansionHandler());

      // Add the scroll pane
      add(_scrollPane, BorderLayout.CENTER);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The scroll pane that contains the <code>JTree</code> component. Never
    * <code>null</code>. 
    */
   private JScrollPane _scrollPane;

   /**
    * The <code>JTree</code> component. Never <code>null</code>. 
    */
   private JTree _tree;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Callback method, called when the current project node changed on the
    * <code>AppCenter</code>.
    *
    * @param node
    *    the new project node, can be <code>null</code>.
    */
   private void projectNodeChanged(ProjectNode node) {

      // If the current node is null, then deselect
      if (node == null) {
         // TODO

      // If the current node is not null, select and expand it
      } else {
         TreePath path = node.getTreePath();
         _tree.setSelectionPath(path);
         _tree.expandPath(path);
      }
   }


   //------------------------------------------------------------------------
   // Inner classes
   //------------------------------------------------------------------------

   /**
    * Listener for <code>AppCenter</code> property change events. If the
    * current project or the current project node changes, then it updates the
    * selection in the tree.
    *
    * @version $Revision: 1.11 $ $Date: 2005/06/06 15:11:01 $
    * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
    */
   private class PropertyChangeHandler
   extends Object
   implements PropertyChangeListener {

      //---------------------------------------------------------------------
      // Constructors
      //---------------------------------------------------------------------

      /**
       * Constructs a new <code>PropertyChangeHandler</code>.
       */
      private PropertyChangeHandler() {
         // empty
      }


      //---------------------------------------------------------------------
      // Fields
      //---------------------------------------------------------------------

      //---------------------------------------------------------------------
      // Methods
      //---------------------------------------------------------------------

      public void propertyChange(PropertyChangeEvent event) {

         // Get the source of the event
         Object eventSource = event.getSource();

         // Ignore all events that are not from the AppCenter
         if (! (eventSource instanceof AppCenter)) {
            return;
         }

         // Determine which property changed
         AppCenter appCenter = (AppCenter) eventSource;
         String    property  = event.getPropertyName();

         // Current project node changed (perhaps to null)
         if (AppCenter.PROJECT_NODE_PROPERTY.equals(property)) {
            projectNodeChanged((ProjectNode) event.getNewValue());
         }
      }
   }


   /**
    * Listener for tree selection events. It updates the
    * <code>projectNode</code> property in the <code>AppCenter</code> as
    * appropriate.
    *
    * @version $Revision: 1.11 $ $Date: 2005/06/06 15:11:01 $
    * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
    */
   private class TreeSelectionHandler
   extends Object
   implements TreeSelectionListener {

      //---------------------------------------------------------------------
      // Constructors
      //---------------------------------------------------------------------

      /**
       * Constructs a new <code>TreeSelectionHandler</code>.
       */
      private TreeSelectionHandler() {
         // empty
      }

      //---------------------------------------------------------------------
      // Fields
      //---------------------------------------------------------------------

      //---------------------------------------------------------------------
      // Methods
      //---------------------------------------------------------------------

      public void valueChanged(TreeSelectionEvent event) {

         // Get the selection path
         JTree tree = (JTree) event.getSource();
         TreePath path = tree.getSelectionPath();

         // Determine which project node was selected
         ProjectNode node = (path == null)
                          ? null
                          : (ProjectNode) (path.getLastPathComponent());

         // Notify the AppCenter
         AppCenter.get().setProjectNode(node);
      }
   }

   /**
    * Listener for tree expansion events. If the event pertains to the
    * collapsing of the root node, then this is vetoed.
    *
    * @version $Revision: 1.11 $ $Date: 2005/06/06 15:11:01 $
    * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
    */
   private class TreeExpansionHandler
   extends Object
   implements TreeWillExpandListener {

      //---------------------------------------------------------------------
      // Constructors
      //---------------------------------------------------------------------

      /**
       * Constructs a new <code>TreeExpansionHandler</code>.
       */
      private TreeExpansionHandler() {
         // empty
      }

      //---------------------------------------------------------------------
      // Fields
      //---------------------------------------------------------------------

      //---------------------------------------------------------------------
      // Methods
      //---------------------------------------------------------------------

      public void treeWillExpand(TreeExpansionEvent event) {
         // empty
      }

      public void treeWillCollapse(TreeExpansionEvent event)
      throws ExpandVetoException {
         TreePath path = event.getPath();
         if (path.getPathCount() == 1) {
            throw new ExpandVetoException(event);
         }
      }
   }
}
