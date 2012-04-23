//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.17 at 01:12:00 PM EDT 
//


package org.slc.sli.test.edfi.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides references for behavior descriptors during interchange. Use XML IDREF to reference a course record that is included in the interchange.  To lookup where already loaded specify either CodeValue OR Description.
 * 
 * <p>Java class for BehaviorDescriptorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BehaviorDescriptorType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}ReferenceType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="CodeValue" type="{http://ed-fi.org/0100}CodeValue"/>
 *         &lt;element name="ShortDescription" type="{http://ed-fi.org/0100}ShortDescription"/>
 *         &lt;element name="Description" type="{http://ed-fi.org/0100}Description"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BehaviorDescriptorType", propOrder = {
    "codeValueOrShortDescriptionOrDescription"
})
public class BehaviorDescriptorType
    extends ReferenceType
{

    @XmlElementRefs({
        @XmlElementRef(name = "ShortDescription", namespace = "http://ed-fi.org/0100", type = JAXBElement.class),
        @XmlElementRef(name = "Description", namespace = "http://ed-fi.org/0100", type = JAXBElement.class),
        @XmlElementRef(name = "CodeValue", namespace = "http://ed-fi.org/0100", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> codeValueOrShortDescriptionOrDescription;

    /**
     * Gets the value of the codeValueOrShortDescriptionOrDescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codeValueOrShortDescriptionOrDescription property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodeValueOrShortDescriptionOrDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getCodeValueOrShortDescriptionOrDescription() {
        if (codeValueOrShortDescriptionOrDescription == null) {
            codeValueOrShortDescriptionOrDescription = new ArrayList<JAXBElement<String>>();
        }
        return this.codeValueOrShortDescriptionOrDescription;
    }

}
