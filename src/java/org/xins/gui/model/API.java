/*
 * $Id: API.java,v 1.11 2006/03/14 14:37:18 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;

import org.xins.common.MandatoryArgumentChecker;

import org.xins.gui.AppCenter;

/**
 * Representation of an API within a XINS project.
 *
 * @version $Revision: 1.11 $ $Date: 2006/03/14 14:37:18 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class API extends ProjectNode {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * The name of the root element in the specification for an API.
    */
   private final static String ROOT_ELEMENT = "api";

   /**
    * The public ID for the API specification document type.
    */
   private final static String PUBLIC_ID = "-//XINS//DTD XINS API 1.3//EN";

   /**
    * The system ID for the API specification document type.
    */
   private final static String SYSTEM_ID =
      "http://xins.sourceforge.net/dtd/api_1_3.dtd";

   /**
    * The name of the <em>name</em> attribute in the root element.
    */
   private final static String ROOT_NAME_ATTRIBUTE = "name";

   /**
    * The name of the element for a description, within the root element.
    */
   private final static String DESCRIPTION_ELEMENT_NAME = "description";

   /**
    * The name of the <em>function</em> element within the root element.
    */
   private final static String FUNCTION_ELEMENT = "function";

   /**
    * The name of the <em>name</em> attribute in the <em>function</em> element.
    */
   private final static String FUNCTION_NAME_ATTRIBUTE = "name";


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>API</code> instance.
    *
    * @param name
    *    the name for the API, cannot be <code>null</code>.
    *
    * @param description
    *    the description for the API, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>name == null || description == null</code>.
    */
   public API(String name, String description)
   throws IllegalArgumentException {

      super(name);

      // Check remaining preconditions
      MandatoryArgumentChecker.check("description", description);

      // Store description
      _description = description;

      // Initialize collection of functions
      _functions = new ArrayList();

      // Update the XML
      updateXML();

      // Set the specification file path
      setPath("apis/" + name + "/spec/api.xml");
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The list of contained functions. Never <code>null</code>.
    */
   private ArrayList _functions;

   /**
    * The description for this API. Never <code>null</code>.
    */
   private String _description;
   
   /**
    * The set of existing API names.
    * Build method for getting this one
    */   
   public HashSet _functionNames = new HashSet();

   /**
    * The map of existing API names pointing to the matching APIs.
    */   
   public Map _functionByNames = new HashMap(); 

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Returns the list of all children.
    *
    * <p>The implementation of this method in class {@link ProjectNode}
    * returns <code>null</code>.
    *
    * @return
    *    the list of all children, can be <code>null</code>.
    */
   protected List getChildren() {
      return Collections.unmodifiableList(_functions); // TODO: Get from field
   }

   /**
    * Checks if the specified node is a valid parent for this node.
    *
    * <p>The implementation of this method in class <code>API</code> returns
    * <code>true</code> if and only if
    * <code>node instanceof </code>{@link Project}.
    *
    * @return
    *    <code>true</code> if the specified node is a valid parent for this
    *    node, otherwise <code>false</code>.
    */
   public boolean isValidParent(ProjectNode node) {
      return (node instanceof Project);
   }

   // TODO: Document
   public void add(Function function) {

      // Check pre-conditions
      if (function == null) {
          throw new IllegalArgumentException("function == null");
      } else if (getFunction(function.getName()) != null) {
          throw new IllegalArgumentException("getAPI(function.getName()) != null");
      } else if (function.getParent() != null) {
          throw new IllegalArgumentException("function.getParent() != null");
      }
      

      // Add the child to the internal collection
      int index = _functions.size();
      _functions.add(function);
      _functionNames.add(function.getName());
      _functionByNames.put(function.getName(), function);

      // Update the parent on the child
      function.setParent(this);

      // Notify the listeners
      fireChildAdded(function, index);

      // Update the specification XML
      updateXML();
   }
   
   /**
    * Returns the function with the given name.
    *
    * @param functionName
    *    The functionName cannot be <code>null</code>
    */
   public Function getFunction(String functionName) {
       return (Function) _functionByNames.get(functionName);
   }
   
   /**
    * Gets the list of current funtcion names
    *
    * @return
    *    The objects current value of _functionNames.
    */
   public HashSet getFunctionNames() {
       return _functionNames;
   }
   
   /**
    * Updates the description of this API.
    *
    * @param newDescription
    *    the new description for this API, cannot be <code>null</code>.
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
    *    the description of this API, never <code>null</code>.
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
      Element rootElement = new Element(ROOT_ELEMENT);
      rootElement.setAttribute(ROOT_NAME_ATTRIBUTE, getName());
      Element descElement = new Element(DESCRIPTION_ELEMENT_NAME);
      descElement.addContent(_description);
      rootElement.addContent(descElement);

      // Add XML for each function
      for (int i = 0; i < _functions.size(); i++) {
         Function function = (Function) _functions.get(i);
         Element functionElement = new Element(FUNCTION_ELEMENT);
         functionElement.setAttribute(FUNCTION_NAME_ATTRIBUTE,
                                      function.getName());
         rootElement.addContent(functionElement);
      }

      // Define the document type
      DocType docType = new DocType(ROOT_ELEMENT,
                                    PUBLIC_ID,
                                    SYSTEM_ID);

      // Construct the document
      Document document = new Document(rootElement, docType);

      // Store the document
      setXML(document);
   }
}
