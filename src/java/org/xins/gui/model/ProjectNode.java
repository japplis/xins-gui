/*
 * $Id: ProjectNode.java,v 1.9 2005/06/06 13:51:40 znerd Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import javax.swing.Icon;
import javax.swing.tree.TreePath;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;
import org.xins.gui.util.Utils;

/**
 * Part of a XINS project. Project nodes include the project self, an API, a
 * function, a type and an error code.
 *
 * @version $Revision: 1.9 $ $Date: 2005/06/06 13:51:40 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public abstract class ProjectNode
extends Object {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * Cache for the small icons. Keys are unqualified class names, such as
    * <code>"Project"</code> or <code>"API"</code>. Values are {@link Icon}
    * instances.
    */
   private static final HashMap SMALL_ICONS = new HashMap();

   /**
    * Cache for the small icons. Keys are unqualified class names, such as
    * <code>"Project"</code> or <code>"API"</code>. Values are {@link Icon}
    * instances.
    */
   private static final HashMap LARGE_ICONS = new HashMap();

   /**
    * Name for the <em>parent</em> property for this bean class.
    */
   private static final String PARENT_PROPERTY = "parent";

   /**
    * Name for the <em>name</em> property for this bean class.
    */
   private static final String NAME_PROPERTY = "name";

   /**
    * Name for the <em>xmlString</em> property for this bean class.
    */
   private static final String XML_STRING_PROPERTY = "xmlString";

   /**
    * Name for the <em>path</em> property for this bean class.
    */
   private static final String PATH_PROPERTY = "path";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>ProjectNode</code> instance.
    *
    * @param name
    *    the name for this project node, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>name == null</code>.
    */
   public ProjectNode(String name)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("name", name);

      // Determine unqualified class name
      String uqcn = Utils.uqcn(this);

      // Get small icon
      Icon icon = (Icon) SMALL_ICONS.get(uqcn);
      if (icon == null) {
         icon = AppCenter.get().getIcon(uqcn + "Node");
         SMALL_ICONS.put(uqcn, icon);
      }
      _smallIcon = icon;

      // Get large icon
      icon = (Icon) LARGE_ICONS.get(uqcn);
      if (icon == null) {
         icon = AppCenter.get().getIcon(uqcn + "Node.Large");
         LARGE_ICONS.put(uqcn, icon);
      }
      _largeIcon = icon;

      // Initialize XML string so it is never null
      _xmlString = "";

      // Initialize the XML outputter (converts XML to a String)
      _xmlOutputter = new XMLOutputter(Format.getPrettyFormat());

      // Initialize the tree path
      _treePath = new TreePath(this);

      // Initialize fields
      _name = name;
      _propertyChangeSupport = new PropertyChangeSupport(this);
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The parent for this project node. Initially <code>null</code>.
    */
   private ProjectNode _parent;

   /**
    * The name of this project node. Never <code>null</code>.
    */
   private String _name;

   /**
    * The small icon for this node. Never <code>null</code>.
    */
   private Icon _smallIcon;

   /**
    * The large icon for this node. Never <code>null</code>.
    */
   private Icon _largeIcon;

   /**
    * The specification for this project node, in XML. Can be
    * <code>null</code>.
    */
   private Document _xml;

   /**
    * Converter for XML to produce a basic character string. Never
    * <code>null</code>.
    */
   private final XMLOutputter _xmlOutputter;

   /**
    * Equivalent of <code>_xml</code>, as a <code>String</code>. Never
    * <code>null</code>.
    */
   private String _xmlString;

   /**
    * Path to the specification file, relative to the project directory.
    */
   private String _path;

   /**
    * The tree path for this node. Never <code>null</code>.
    */
   private TreePath _treePath;

   /**
    * Property change support object. Never <code>null</code>.
    */
   private PropertyChangeSupport _propertyChangeSupport;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Updates the parent for this node.
    *
    * @param newParent
    *    the new parent for this project node, can be <code>null</code>.
    */
   public final void setParent(ProjectNode newParent) {

      // Check preconditions
      MandatoryArgumentChecker.check("newParent", newParent);

      // Make sure the new parent is allowable as a parent
      if (! isValidParent(newParent)) {
         String s = (newParent == null)
                  ? "(null)"
                  : newParent.toString();
         throw new RuntimeException("The specified node (" + s + ") is not considered a valid parent for this node (" + toString() + '.');
      }

      // Remember the old parent
      ProjectNode oldParent = _parent;

      // Store the parent
      _parent = newParent;

      // Update the tree path
      if (newParent == null) {
         _treePath = new TreePath(this);
      } else {
         _treePath = newParent.getTreePath().pathByAddingChild(this);
      }

      // Notify the listeners
      _propertyChangeSupport.firePropertyChange(PARENT_PROPERTY,
                                                oldParent,
                                                newParent);

      // Notify subclass
      parentChanged(newParent);
   }

   // TODO: Document
   protected void parentChanged(ProjectNode newParent) {
      // empty
   }

   /**
    * Checks if the specified node is a valid parent for this node.
    *
    * <p>The implementation of this method in class {@link ProjectNode} always
    * returns <code>false</code>. Subclasses should override the
    * implementation if appropriate.
    *
    * @return
    *    <code>true</code> if the specified node is a valid parent for this
    *    node, otherwise <code>false</code>.
    */
   public boolean isValidParent(ProjectNode node) {
      return false;
   }

   /**
    * Returns the parent for this project node. The parent for a root node (a
    * project) is <code>null</code>.
    *
    * @return
    *    the parent, can be <code>null</code>.
    */
   public final ProjectNode getParent() {
      return _parent;
   }

   /**
    * Updates the name of this project node.
    *
    * @param newName
    *    the new name for this project node, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>newName == null</code>.
    */
   public final void setName(String newName)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("newName", newName);

      // Remember the old name
      String oldName = _name;

      // TODO: Validate name?

      // Update
      _name = newName;

      // Notify the listeners
      _propertyChangeSupport.firePropertyChange(NAME_PROPERTY,
                                                oldName,
                                                newName);
   }

   /**
    * Retrieves the name of this project node.
    *
    * @return
    *    the name of this project node, never <code>null</code>.
    */
   public final String getName() {
      return _name;
   }

   /**
    * Returns the number of children for this node.
    *
    * @return
    *    the number of child nodes, always &gt;= 0.
    */
   public final int getChildCount() {
      List children = getChildren();
      return (children == null) ? 0 : getChildren().size();
   }

   /**
    * Returns the child node at the specified index.
    *
    * @param index
    *    the index, must be &gt;= 0 and &lt; {@link #getChildCount()}.
    *
    * @return
    *    the child at the specified index, never <code>null</code>.
    *
    * @throws IndexOutOfBoundsException
    *    if <code>index &lt; 0 || index &gt;= </code>{@link #getChildCount()}.
    */
   public final ProjectNode getChild(int index) throws IndexOutOfBoundsException {
      List children = getChildren();
      if (children == null) {
         throw new IndexOutOfBoundsException();
      }
      return (ProjectNode) children.get(index);
   }

   /**
    * Returns the list of all children.
    *
    * <p>The implementation of this method in class {@link ProjectNode}
    * returns <code>null</code>. Subclasses should override this method if
    * appropriate.
    *
    * @return
    *    the list of all children, can be <code>null</code>.
    */
   protected List getChildren() {
      return null;
   }

   /**
    * Retrieves the small icon for this node.
    *
    * @return
    *    the small icon for this project node, never <code>null</code>.
    */
   public final Icon getSmallIcon() {
      return _smallIcon;
   }

   /**
    * Retrieves the large icon for this node.
    *
    * @return
    *    the large icon for this project node, never <code>null</code>.
    */
   public final Icon getLargeIcon() {
      return _largeIcon;
   }

   /**
    * Returns a <code>TreePath</code> for this project node. The returned
    * {@link TreePath} will have the project as the first node in the path,
    * and this node as the last one.
    *
    * @return
    *    a {@link TreePath} for this node, never <code>null</code>.
    */
   public final TreePath getTreePath() {
      return _treePath;
   }

   /**
    * Sets the XML for this component. This will update the <em>xmlString</em>
    * property, see {@link #getXMLString()}.
    *
    * @param xml
    *    the new XML specification document for this project node, can be
    *    <code>null</code>.
    */
   protected final void setXML(Document xml) {

      // Remember old XML string
      String oldXMLString = _xmlString;

      // Store XML document
      _xml = xml;

      // If it is null, then the string is empty
      if (xml == null) {
         _xmlString = "";

      // Convert the document to a String
      } else {
         _xmlString = _xmlOutputter.outputString(_xml);
      }

      // Notify listeners
      _propertyChangeSupport.firePropertyChange(XML_STRING_PROPERTY,
                                                oldXMLString,
                                                _xmlString);
   }

   /**
    * Returns the specification document, as an XML character string.
    *
    * @return
    *    the specification document for this project node, never
    *    <code>null</code>.
    */
   public final String getXMLString() {
      return _xmlString;
   }

   /**
    * Updates the path to the specification document, relative to the
    * project directory.
    *
    * @param newPath
    *    the new path, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>newPath == null</code>.
    */
   protected final void setPath(String newPath)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("newPath", newPath);

      // Remember the old value
      String oldPath = _path;

      // Notify listeners
      if (! newPath.equals(oldPath)) {
         _path = newPath;
         _propertyChangeSupport.firePropertyChange(PATH_PROPERTY,
                                                   oldPath,
                                                   newPath);
      }
   }

   /**
    * Returns the path to the specification document, relative to the
    * project directory.
    *
    * @return
    *    the path, never <code>null</code>.
    */
   public final String getPath() {
      return _path;
   }

   // TODO: Document
   protected final void fireChildAdded(ProjectNode child, int index) {
      ProjectTreeModel model = AppCenter.get().getTreeModel();
      model.fireChildAdded(this, child, index);
   }

   /**
    * Returns a textual representation for object.
    *
    * @return
    *    the name of this project node, never <code>null</code>.
    */
   public final String toString() {
      return Utils.uqcn(this) + "(name='" + _name + "')";
   }
}
