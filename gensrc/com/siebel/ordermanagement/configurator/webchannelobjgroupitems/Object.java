
package com.siebel.ordermanagement.configurator.webchannelobjgroupitems;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Object complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Object">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParentClassId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObjectType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UIOption" type="{http://siebel.com/OrderManagement/Configurator/WebChannelObjGroupItems}UIOption" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ObjectDecoration" type="{http://siebel.com/OrderManagement/Configurator/WebChannelObjGroupItems}ObjectDecoration" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Object", propOrder = {
    "parentClassId",
    "objectType",
    "name",
    "id",
    "displayName",
    "description",
    "uiOption",
    "objectDecoration"
})
public class Object {

    @XmlElement(name = "ParentClassId")
    protected String parentClassId;
    @XmlElement(name = "ObjectType")
    protected String objectType;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(name = "DisplayName")
    protected String displayName;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "UIOption")
    protected List<UIOption> uiOption;
    @XmlElement(name = "ObjectDecoration")
    protected List<ObjectDecoration> objectDecoration;

    /**
     * Gets the value of the parentClassId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentClassId() {
        return parentClassId;
    }

    /**
     * Sets the value of the parentClassId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentClassId(String value) {
        this.parentClassId = value;
    }

    /**
     * Gets the value of the objectType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * Sets the value of the objectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectType(String value) {
        this.objectType = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the uiOption property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uiOption property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUIOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UIOption }
     * 
     * 
     */
    public List<UIOption> getUIOption() {
        if (uiOption == null) {
            uiOption = new ArrayList<UIOption>();
        }
        return this.uiOption;
    }

    /**
     * Gets the value of the objectDecoration property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objectDecoration property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjectDecoration().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectDecoration }
     * 
     * 
     */
    public List<ObjectDecoration> getObjectDecoration() {
        if (objectDecoration == null) {
            objectDecoration = new ArrayList<ObjectDecoration>();
        }
        return this.objectDecoration;
    }

}
