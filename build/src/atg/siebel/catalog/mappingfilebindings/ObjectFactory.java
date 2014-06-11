/*<ORACLECOPYRIGHT>
 * Copyright (C) 1994-2012 Oracle and/or its affiliates. All rights reserved.
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 * UNIX is a registered trademark of The Open Group.
 *
 * This software and related documentation are provided under a license agreement
 * containing restrictions on use and disclosure and are protected by intellectual property laws.
 * Except as expressly permitted in your license agreement or allowed by law, you may not use, copy,
 * reproduce, translate, broadcast, modify, license, transmit, distribute, exhibit, perform, publish,
 * or display any part, in any form, or by any means. Reverse engineering, disassembly,
 * or decompilation of this software, unless required by law for interoperability, is prohibited.
 *
 * The information contained herein is subject to change without notice and is not warranted to be error-free.
 * If you find any errors, please report them to us in writing.
 *
 * U.S. GOVERNMENT RIGHTS Programs, software, databases, and related documentation and technical data delivered to U.S.
 * Government customers are "commercial computer software" or "commercial technical data" pursuant to the applicable
 * Federal Acquisition Regulation and agency-specific supplemental regulations.
 * As such, the use, duplication, disclosure, modification, and adaptation shall be subject to the restrictions and
 * license terms set forth in the applicable Government contract, and, to the extent applicable by the terms of the
 * Government contract, the additional rights set forth in FAR 52.227-19, Commercial Computer Software License
 * (December 2007). Oracle America, Inc., 500 Oracle Parkway, Redwood City, CA 94065.
 *
 * This software or hardware is developed for general use in a variety of information management applications.
 * It is not developed or intended for use in any inherently dangerous applications, including applications that
 * may create a risk of personal injury. If you use this software or hardware in dangerous applications,
 * then you shall be responsible to take all appropriate fail-safe, backup, redundancy,
 * and other measures to ensure its safe use. Oracle Corporation and its affiliates disclaim any liability for any
 * damages caused by use of this software or hardware in dangerous applications.
 *
 * This software or hardware and documentation may provide access to or information on content,
 * products, and services from third parties. Oracle Corporation and its affiliates are not responsible for and
 * expressly disclaim all warranties of any kind with respect to third-party content, products, and services.
 * Oracle Corporation and its affiliates will not be responsible for any loss, costs,
 * or damages incurred due to your access to or use of third-party content, products, or services.
 </ORACLECOPYRIGHT>*/

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.30 at 10:15:38 AM BST 
//


package atg.siebel.catalog.mappingfilebindings;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the atg.siebel.catalog.mappingfilebindings package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SiebelField_QNAME = new QName("", "siebel-field");
    private final static QName _MappingComponent_QNAME = new QName("", "mapping-component");
    private final static QName _AtgEntity_QNAME = new QName("", "atg-entity");
    private final static QName _AtgProperty_QNAME = new QName("", "atg-property");
    private final static QName _AtgRepository_QNAME = new QName("", "atg-repository");
    private final static QName _SiebelEntity_QNAME = new QName("", "siebel-entity");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: atg.siebel.catalog.mappingfilebindings
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SiebelAtgMappings }
     * 
     */
    public SiebelAtgMappings createSiebelAtgMappings() {
        return new SiebelAtgMappings();
    }

    /**
     * Create an instance of {@link ListOfFieldMappings }
     * 
     */
    public ListOfFieldMappings createListOfFieldMappings() {
        return new ListOfFieldMappings();
    }

    /**
     * Create an instance of {@link ListOfEntityMappings }
     * 
     */
    public ListOfEntityMappings createListOfEntityMappings() {
        return new ListOfEntityMappings();
    }

    /**
     * Create an instance of {@link FieldMapping }
     * 
     */
    public FieldMapping createFieldMapping() {
        return new FieldMapping();
    }

    /**
     * Create an instance of {@link EntityMapping }
     * 
     */
    public EntityMapping createEntityMapping() {
        return new EntityMapping();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "siebel-field")
    public JAXBElement<String> createSiebelField(String value) {
        return new JAXBElement<String>(_SiebelField_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mapping-component")
    public JAXBElement<String> createMappingComponent(String value) {
        return new JAXBElement<String>(_MappingComponent_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "atg-entity")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createAtgEntity(String value) {
        return new JAXBElement<String>(_AtgEntity_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "atg-property")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createAtgProperty(String value) {
        return new JAXBElement<String>(_AtgProperty_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "atg-repository")
    public JAXBElement<String> createAtgRepository(String value) {
        return new JAXBElement<String>(_AtgRepository_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "siebel-entity")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createSiebelEntity(String value) {
        return new JAXBElement<String>(_SiebelEntity_QNAME, String.class, null, value);
    }

}
