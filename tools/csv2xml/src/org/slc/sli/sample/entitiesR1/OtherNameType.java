//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.12.05 at 01:12:38 PM EST 
//


package org.slc.sli.sample.entitiesR1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OtherNameType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OtherNameType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Alias"/>
 *     &lt;enumeration value="Nickname"/>
 *     &lt;enumeration value="Other Name"/>
 *     &lt;enumeration value="Previous Legal Name"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OtherNameType")
@XmlEnum
public enum OtherNameType {

    @XmlEnumValue("Alias")
    ALIAS("Alias"),
    @XmlEnumValue("Nickname")
    NICKNAME("Nickname"),
    @XmlEnumValue("Other Name")
    OTHER_NAME("Other Name"),
    @XmlEnumValue("Previous Legal Name")
    PREVIOUS_LEGAL_NAME("Previous Legal Name");
    private final String value;

    OtherNameType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OtherNameType fromValue(String v) {
        for (OtherNameType c: OtherNameType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
