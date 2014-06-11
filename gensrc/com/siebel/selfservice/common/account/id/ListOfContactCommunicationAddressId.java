
package com.siebel.selfservice.common.account.id;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfContactCommunicationAddressId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfContactCommunicationAddressId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContactCommunicationAddress" type="{http://www.siebel.com/SelfService/Common/Account/Id}ContactCommunicationAddressId" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfContactCommunicationAddressId", propOrder = {
    "contactCommunicationAddress"
})
public class ListOfContactCommunicationAddressId {

    @XmlElement(name = "ContactCommunicationAddress")
    protected List<ContactCommunicationAddressId> contactCommunicationAddress;

    /**
     * Gets the value of the contactCommunicationAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactCommunicationAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactCommunicationAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactCommunicationAddressId }
     * 
     * 
     */
    public List<ContactCommunicationAddressId> getContactCommunicationAddress() {
        if (contactCommunicationAddress == null) {
            contactCommunicationAddress = new ArrayList<ContactCommunicationAddressId>();
        }
        return this.contactCommunicationAddress;
    }

}
