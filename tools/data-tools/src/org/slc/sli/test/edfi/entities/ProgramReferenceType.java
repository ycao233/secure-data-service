//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2012.02.12 at 04:54:37 PM EST
//


package org.slc.sli.test.edfi.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides alternative references for programs. Use XML IDREF to reference a program record that is included in the interchange
 *
 * <p>Java class for ProgramReferenceType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ProgramReferenceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}ReferenceType">
 *       &lt;sequence>
 *         &lt;element name="ProgramIdentity" type="{http://ed-fi.org/0100}ProgramIdentityType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgramReferenceType", propOrder = {
    "programIdentity"
})
public class ProgramReferenceType
    extends ReferenceType
{

    @XmlElement(name = "ProgramIdentity")
    protected ProgramIdentityType programIdentity;

    /**
     * Gets the value of the programIdentity property.
     *
     * @return
     *     possible object is
     *     {@link ProgramIdentityType }
     *
     */
    public ProgramIdentityType getProgramIdentity() {
        return programIdentity;
    }

    /**
     * Sets the value of the programIdentity property.
     *
     * @param value
     *     allowed object is
     *     {@link ProgramIdentityType }
     *
     */
    public void setProgramIdentity(ProgramIdentityType value) {
        this.programIdentity = value;
    }

}
