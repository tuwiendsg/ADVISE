
package com.extl.jade.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for firewallProtocol.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="firewallProtocol">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ANY"/>
 *     &lt;enumeration value="ICMP"/>
 *     &lt;enumeration value="TCP"/>
 *     &lt;enumeration value="UDP"/>
 *     &lt;enumeration value="GRE"/>
 *     &lt;enumeration value="IPSEC_ESP"/>
 *     &lt;enumeration value="IPSEC_AH"/>
 *     &lt;enumeration value="ICMP6"/>
 *     &lt;enumeration value="L2TP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "firewallProtocol")
@XmlEnum
public enum FirewallProtocol {


    /**
     * Any protocol
     * 
     */
    ANY("ANY"),

    /**
     * ICMP
     * 
     */
    ICMP("ICMP"),

    /**
     * TCP
     * 
     */
    TCP("TCP"),

    /**
     * UDP
     * 
     */
    UDP("UDP"),

    /**
     * GRE
     * 
     */
    GRE("GRE"),

    /**
     * IPSEC ESP
     * 
     */
    IPSEC_ESP("IPSEC_ESP"),

    /**
     * IPSEC AH
     * 
     */
    IPSEC_AH("IPSEC_AH"),

    /**
     * ICMP6
     * 
     */
    @XmlEnumValue("ICMP6")
    ICMP_6("ICMP6"),

    /**
     * L2TP
     * 
     */
    @XmlEnumValue("L2TP")
    L_2_TP("L2TP");
    private final String value;

    FirewallProtocol(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FirewallProtocol fromValue(String v) {
        for (FirewallProtocol c: FirewallProtocol.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
