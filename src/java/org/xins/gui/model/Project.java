/*
 * $Id: Project.java,v 1.12 2006/03/08 11:01:12 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.model;

import java.lang.IllegalArgumentException;
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
 * Representation of a XINS project.
 *
 * @version $Revision: 1.12 $ $Date: 2006/03/08 11:01:12 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class Project extends ProjectNode {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   /**
    * The name of the root element in the specification for a project.
    */
   private final static String ROOT_ELEMENT = "project";

   /**
    * The public ID for the project specification document type.
    */
   private final static String PUBLIC_ID =
      "-//XINS//DTD XINS Project 1.3//EN";

   /**
    * The system ID for the project specification document type.
    */
   private final static String SYSTEM_ID =
      "http://xins.sourceforge.net/dtd/xins-project_1_3.dtd";

   /**
    * The name of the <em>name</em> attribute in the root element.
    */
   private final static String ROOT_NAME_ATTRIBUTE = "name";

   /**
    * The name of the <em>domain</em> attribute in the root element.
    */
   private final static String ROOT_DOMAIN_ATTRIBUTE = "domain";

   /**
    * The name of the <em>API</em> element within the root element.
    */
   private final static String API_ELEMENT = "api";

   /**
    * The name of the <em>name</em> attribute in the <em>API</em> element.
    */
   private final static String API_NAME_ATTRIBUTE = "name";

   
   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>Project</code> instance.
    *
    * @param name
    *    the name for the project, cannot be <code>null</code>.
    *
    * @param domain
    *    the domain for the project, cannot be <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>name == null || domain == null</code>.
    */
   public Project(String name, String domain)
   throws IllegalArgumentException {

      super(name);

      // Store domain
      _domain = domain;

      // Initialize collection of APIs
      _apis = new ArrayList();

      // Initialize the XML document
      updateXML();

      // Set the specification file path
      setPath("xins-project.xml");
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   /**
    * The domain. Never <code>null</code>.
    */
   private String _domain;

   /**
    * The list of contained APIs. Never <code>null</code>.
    */
   private ArrayList _apis;

   /**
    * The set of existing API names.
    * Build method for getting this one
    */   
   public HashSet _apiNames = new HashSet();

   /**
    * The map of existing API names pointing to the matching APIs.
    */   
   public Map _apiByNames = new HashMap();   

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
      return Collections.unmodifiableList(_apis); // TODO: Get from field
   }

   /**
    * Adds the specified API to this project. The name of the API should be
    * unique in the context of this project. Also, the API cannot be part of a
    * different project.
    *
    * @param api
    *    the API to add, cannot be <code>null</code> and must have a unique
    *    name.
    *
    * @throws IllegalArgumentException
    *    if <code>api == null
    *          || @link #getAPI(api.getName()) != null)
    *          || api.getProject() != null</code>.
    */
   public void add(API api) throws IllegalArgumentException {
       
      // Check pre-conditions
      if (api == null) {
          throw new IllegalArgumentException("api == null");
      } else if (getAPI(api.getName()) != null) {
          throw new IllegalArgumentException("getAPI(api.getName()) != null");
      } else if (api.getParent() != null) {
          throw new IllegalArgumentException("api.getParent() != null");
      }
      
      // Add the child to the internal collections
      int index = _apis.size();
      _apis.add(api);
      _apiNames.add(api.getName());
      _apiByNames.put(api.getName(), api);
      
      // Update the parent on the child
      api.setParent(this);

      // Notify the listeners
      fireChildAdded(api, index);

      // Update the specification XML
      updateXML();
   }

   /**
    * Returns the API with the given name.
    *
    * @param apiName
    *    The apiName cannot be <code>null</code>
    */
   public API getAPI(String apiName) {
       return (API) _apiByNames.get(apiName);
   }
   
   /**
    * Gets the list of current API names
    *
    * @return
    *    The objects current value of _apiNames.
    */
   public HashSet getAPINames() {
       return _apiNames;
   }
   
   // TODO: setDomain(String)

   // TODO: Document
   public String getDomain() {
      return _domain;
   }

   /**
    * Updates the XML. This method should be called whenever something (may
    * have) changed in the XML.
    */
   private void updateXML() {

      // Build the XML element tree
      Element rootElement = new Element(ROOT_ELEMENT);
      rootElement.setAttribute(ROOT_NAME_ATTRIBUTE,   getName());
      rootElement.setAttribute(ROOT_DOMAIN_ATTRIBUTE, _domain);

      // Add XML for each API
      for (int i = 0; i < _apis.size(); i++) {
         API api = (API) _apis.get(i);
         Element apiElement = new Element(API_ELEMENT);
         apiElement.setAttribute(API_NAME_ATTRIBUTE, api.getName());
         rootElement.addContent(apiElement);
      }

      // Define the document type
      DocType docType = new DocType(ROOT_ELEMENT,
                                    PUBLIC_ID,
                                    SYSTEM_ID);

      // Construct the document
      Document document = new Document(rootElement, docType);

      // Store the XML document
      setXML(document);
   }
}
