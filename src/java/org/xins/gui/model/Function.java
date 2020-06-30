/*
 * $Id: Function.java,v 1.6 2006/03/08 11:02:43 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.model;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;

/**
 * Representation of a function within an API.
 *
 * @version $Revision: 1.6 $ $Date: 2006/03/08 11:02:43 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class Function extends ProjectNode {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * The name of the root element in the specification for a function.
    */
   private final static String ROOT_ELEMENT_NAME = "function";

   /**
    * The public ID for the function specification document type.
    */
   private final static String PUBLIC_ID =
      "-//XINS//DTD XINS Function 1.3//EN";

   /**
    * The system ID for the function specification document type.
    */
   private final static String SYSTEM_ID =
      "http://xins.sourceforge.net/dtd/function_1_3.dtd";

   /**
    * The name of the <em>name</em> attribute in the root element.
    */
   private final static String ROOT_NAME_ATTRIBUTE = "name";

   /**
    * The name of the element for a description, within the root element.
    */
   private final static String DESCRIPTION_ELEMENT_NAME = "description";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>Function</code> instance.
    *
    * @param name
    *    the name for the function, cannot be <code>null</code>.
    *
    * @param description
    *    the description for the function, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>name == null || description == null</code>.
    */
   public Function(String name, String description)
   throws IllegalArgumentException {

      super(name);

      // Check remaining preconditions
      MandatoryArgumentChecker.check("description", description);

      // Store description
      _description = description;

      // Update the XML
      updateXML();
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The description for this function. Never <code>null</code>.
    */
   private String _description;


   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Checks if the specified node is a valid parent for this node.
    *
    * <p>The implementation of this method in class <code>Function</code>
    * returns <code>true</code> if and only if
    * <code>node instanceof </code>{@link API}.
    *
    * @return
    *    <code>true</code> if the specified node is a valid parent for this
    *    node, otherwise <code>false</code>.
    */
   public boolean isValidParent(ProjectNode node) {
      return (node instanceof API);
   }

   protected void parentChanged(ProjectNode newParent) {
      API api = (API) newParent;
      setPath("apis/" + api.getName() + "/spec/" + getName() + ".fnc");
   }

   /**
    * Updates the description of this function.
    *
    * @param newDescription
    *    the new description for this function, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>newDescription == null</code>.
    */
   public void setDescription(String newDescription)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("newDescription", newDescription);

      // TODO: Validate description?
      // TODO: Notify listeners?

      // Update
      _description = newDescription;
   }

   /**
    * Retrieves the description of this project node.
    *
    * @return
    *    the description of this function, never <code>null</code>.
    */
   public String getDescription() {
      return _description;
   }

   /**
    * Updates the XML. This method should be called whenever something (may
    * have) changed in the XML.
    */
   private void updateXML() {

      // Build the XML element tree
      Element rootElement = new Element(ROOT_ELEMENT_NAME);
      rootElement.setAttribute(ROOT_NAME_ATTRIBUTE, getName());
      Element descElement = new Element(DESCRIPTION_ELEMENT_NAME);
      descElement.addContent(_description);
      rootElement.addContent(descElement);

      // Define the document type
      DocType docType = new DocType(ROOT_ELEMENT_NAME,
                                    PUBLIC_ID,
                                    SYSTEM_ID);

      // Construct the document
      Document document = new Document(rootElement, docType);

      // Store the document
      setXML(document);
   }
}
