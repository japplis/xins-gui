/*
 * $Id: ProjectTreeModel.java,v 1.3 2005/06/06 11:33:49 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;

/**
 * Tree model for a XINS project.
 *
 * @version $Revision: 1.3 $ $Date: 2005/06/06 11:33:49 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class ProjectTreeModel
extends Object
implements TreeModel {

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
    * Constructs a new <code>ProjectTreeModel</code>.
    */
   public ProjectTreeModel() {
      _listeners = new ArrayList();

      AppCenter.get().addPropertyChangeListener(new PropertyChangeHandler());
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The list of event listeners. Never <code>null</code>.
    */
   private final ArrayList _listeners;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   public Object getRoot() {
      return AppCenter.get().getProject();
   }

   public Object getChild(Object parent, int index) {
      ProjectNode node = (ProjectNode) parent;
      return node.getChild(index);
   }

   public int getChildCount(Object parent) {
      ProjectNode node = (ProjectNode) parent;
      return node.getChildCount();
   }

   public boolean isLeaf(Object node) {
      return (node instanceof Function);
   }

   public void valueForPathChanged(TreePath path, Object newValue) {
      // TODO
   }

   public int getIndexOfChild(Object parent, Object child) {
      return -1;
   }

   public void addTreeModelListener(TreeModelListener listener) {
      AppCenter.LOG.debug("Adding TreeModelListener.");
      _listeners.add(listener);
   }

   public void removeTreeModelListener(TreeModelListener listener) {
      AppCenter.LOG.debug("Removing TreeModelListener.");
      _listeners.remove(listener);
   }

   public TreePath getPathToRoot(ProjectNode node) {
      return node.getTreePath();
   }

   /**
    * Callback method, called when the current project changed on the
    * <code>AppCenter</code>.
    *
    * @param project
    *    the new project, can be <code>null</code>.
    */
   private void projectChanged(Project project) {
      if (project == null) {
         fireTreeStructureChanged(null);
      } else {
         fireTreeStructureChanged(project);
      }
   }

   /**
    * Fires a <code>TreeModelEvent</code> indicating that the tree structure
    * changed drastically.
    *
    * @param node
    *    the root node for the subtree that changed drastically, can be
    *    <code>null</code>.
    */
   private void fireTreeStructureChanged(ProjectNode node) {

      AppCenter.LOG.debug("Tree structure changed.");

      // Get the list of listeners
      if (_listeners.size() > 0) {

         // Determine the path
         TreePath path = (node == null)
                       ? null
                       : node.getTreePath();

         // Construct the event (one for all listeners)
         TreeModelEvent event = new TreeModelEvent(this, path);

         // Notify the listeners (last to first)
         for (int i = (_listeners.size() - 1); i >= 0; i--) {
            TreeModelListener tml = (TreeModelListener) _listeners.get(i);
            if (tml != null) {
               try {
                  tml.treeStructureChanged(event);
               } catch (Throwable exception) {
                  exception.printStackTrace();
               }
            }
         }
      }
   }

   // TODO: Document
   public void fireChildAdded(ProjectNode parent, ProjectNode child, int index)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("parent", parent, "child", child);

      // Get the list of listeners
      if (_listeners.size() > 0) {

         // Determine the path
         TreePath path = parent.getTreePath();

         // Construct the event (one for all listeners)
         TreeModelEvent event = new TreeModelEvent(this,
                                                   path,
                                                   new int[]    { index },
                                                   new Object[] { child } );

         // Notify the listeners (last to first)
         for (int i = (_listeners.size() - 1); i >= 0; i--) {
            TreeModelListener tml = (TreeModelListener) _listeners.get(i);
            if (tml != null) {
               try {
                  tml.treeNodesInserted(event);
               } catch (Throwable exception) {
                  exception.printStackTrace();
               }
            }
         }
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
    * @version $Revision: 1.3 $ $Date: 2005/06/06 11:33:49 $
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

         // Current project changed (perhaps to null)
         if (AppCenter.PROJECT_PROPERTY.equals(property)) {
            projectChanged((Project) event.getNewValue());
         }
      }
   }
}
