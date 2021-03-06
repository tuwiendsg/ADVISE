
package com.extl.jade.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for aggregation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="aggregation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SUM"/>
 *     &lt;enumeration value="COUNT"/>
 *     &lt;enumeration value="MIN"/>
 *     &lt;enumeration value="MAX"/>
 *     &lt;enumeration value="AVERAGE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "aggregation")
@XmlEnum
public enum Aggregation {


    /**
     * Sum the aggregated values
     * 
     */
    SUM,

    /**
     * Count the aggregated values
     * 
     */
    COUNT,

    /**
     * Find the minimum of the aggregated values
     * 
     */
    MIN,

    /**
     * Find the maximum of the aggregated values
     * 
     */
    MAX,

    /**
     * Find the average of the aggregated values
     * 
     */
    AVERAGE;

    public String value() {
        return name();
    }

    public static Aggregation fromValue(String v) {
        return valueOf(v);
    }

}
