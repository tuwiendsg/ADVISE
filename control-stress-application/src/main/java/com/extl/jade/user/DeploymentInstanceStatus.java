
package com.extl.jade.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deploymentInstanceStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="deploymentInstanceStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="RUNNING"/>
 *     &lt;enumeration value="STARTING"/>
 *     &lt;enumeration value="STOPPING"/>
 *     &lt;enumeration value="STOPPED"/>
 *     &lt;enumeration value="REBOOTING"/>
 *     &lt;enumeration value="INSTALLING"/>
 *     &lt;enumeration value="MIGRATING"/>
 *     &lt;enumeration value="RECOVERY"/>
 *     &lt;enumeration value="BUILDING"/>
 *     &lt;enumeration value="DELETING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "deploymentInstanceStatus")
@XmlEnum
public enum DeploymentInstanceStatus {


    /**
     * Internal error state
     * 
     */
    ERROR,

    /**
     * Running
     * 
     */
    RUNNING,

    /**
     * Starting
     * 
     */
    STARTING,

    /**
     * Stopping
     * 
     */
    STOPPING,

    /**
     * Stopped
     * 
     */
    STOPPED,

    /**
     * Rebooting
     * 
     */
    REBOOTING,

    /**
     * Installing
     * 
     */
    INSTALLING,

    /**
     * Migrating
     * 
     */
    MIGRATING,

    /**
     * Undergoing live recovery
     * 
     */
    RECOVERY,

    /**
     * Building
     * 
     */
    BUILDING,

    /**
     * Deleting
     * 
     */
    DELETING;

    public String value() {
        return name();
    }

    public static DeploymentInstanceStatus fromValue(String v) {
        return valueOf(v);
    }

}
