
package com.siebel.selfservice.common.contact.id;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfContactPersonalAddressId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfContactPersonalAddressId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContactPersonalAddress" type="{http://www.siebel.com/SelfService/Common/Contact/Id}ContactPersonalAddressId" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfContactPersonalAddressId", propOrder = {
    "contactPersonalAddress"
})
public class ListOfContactPersonalAddressId {

    @XmlElement(name = "ContactPersonalAddress")
    protected List<ContactPersonalAddressId> contactPersonalAddress;

    /**
     * Gets the value of the contactPersonalAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactPersonalAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactPersonalAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactPersonalAddressId }
     * 
     * 
     */
    public List<ContactPersonalAddressId> getContactPersonalAddress() {
        if (contactPersonalAddress == null) {
            contactPersonalAddress = new ArrayList<ContactPersonalAddressId>();
        }
        return this.contactPersonalAddress;
    }

}
