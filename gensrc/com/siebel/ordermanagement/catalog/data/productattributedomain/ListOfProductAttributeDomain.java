
package com.siebel.ordermanagement.catalog.data.productattributedomain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfProductAttributeDomain complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfProductAttributeDomain">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProductAttribute" type="{http://siebel.com/OrderManagement/Catalog/Data/ProductAttributeDomain}ProductAttribute" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfProductAttributeDomain", propOrder = {
    "productAttribute"
})
public class ListOfProductAttributeDomain {

    @XmlElement(name = "ProductAttribute")
    protected List<ProductAttribute> productAttribute;

    /**
     * Gets the value of the productAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductAttribute }
     * 
     * 
     */
    public List<ProductAttribute> getProductAttribute() {
        if (productAttribute == null) {
            productAttribute = new ArrayList<ProductAttribute>();
        }
        return this.productAttribute;
    }

}
