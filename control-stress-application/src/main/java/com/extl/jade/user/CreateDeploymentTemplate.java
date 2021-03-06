
package com.extl.jade.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Input parameters for method createDeploymentTemplate
 * 
 * <p>Java class for createDeploymentTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createDeploymentTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="skeletonDeploymentTemplate" type="{http://extility.flexiant.net}deploymentTemplate" minOccurs="0"/>
 *         &lt;element name="when" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createDeploymentTemplate", propOrder = {
    "skeletonDeploymentTemplate",
    "when"
})
public class CreateDeploymentTemplate {

    protected DeploymentTemplate skeletonDeploymentTemplate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar when;

    /**
     * Gets the value of the skeletonDeploymentTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link DeploymentTemplate }
     *     
     */
    public DeploymentTemplate getSkeletonDeploymentTemplate() {
        return skeletonDeploymentTemplate;
    }

    /**
     * Sets the value of the skeletonDeploymentTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeploymentTemplate }
     *     
     */
    public void setSkeletonDeploymentTemplate(DeploymentTemplate value) {
        this.skeletonDeploymentTemplate = value;
    }

    /**
     * Gets the value of the when property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getWhen() {
        return when;
    }

    /**
     * Sets the value of the when property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setWhen(XMLGregorianCalendar value) {
        this.when = value;
    }

}
