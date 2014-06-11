
package com.siebel.selfservice.common.contact.id;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactCommunicationAddressId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactCommunicationAddressId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CommunicationAddressId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactCommunicationAddressId", propOrder = {
    "id",
    "modId",
    "communicationAddressId"
})
public class ContactCommunicationAddressId {

    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(name = "ModId")
    protected String modId;
    @XmlElement(name = "CommunicationAddressId")
    protected String communicationAddressId;

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
     * Gets the value of the modId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModId() {
        return modId;
    }

    /**
     * Sets the value of the modId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModId(String value) {
        this.modId = value;
    }

    /**
     * Gets the value of the communicationAddressId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommunicationAddressId() {
        return communicationAddressId;
    }

    /**
     * Sets the value of the communicationAddressId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommunicationAddressId(String value) {
        this.communicationAddressId = value;
    }

}
