
package com.extl.jade.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Input parameters for method updateMetadata
 * 
 * <p>Java class for updateMetadata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateMetadata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceUUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resourceMetadata" type="{http://extility.flexiant.net}resourceMetadata" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateMetadata", propOrder = {
    "resourceUUID",
    "resourceMetadata"
})
public class UpdateMetadata {

    protected String resourceUUID;
    protected ResourceMetadata resourceMetadata;

    /**
     * Gets the value of the resourceUUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceUUID() {
        return resourceUUID;
    }

    /**
     * Sets the value of the resourceUUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceUUID(String value) {
        this.resourceUUID = value;
    }

    /**
     * Gets the value of the resourceMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceMetadata }
     *     
     */
    public ResourceMetadata getResourceMetadata() {
        return resourceMetadata;
    }

    /**
     * Sets the value of the resourceMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceMetadata }
     *     
     */
    public void setResourceMetadata(ResourceMetadata value) {
        this.resourceMetadata = value;
    }

}
