//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2012.02.12 at 04:54:37 PM EST
//


package org.slc.sli.test.edfi.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Encapsulates the possible attributes that can be used to lookup the identity of staff members, including teachers
 *
 * <p>Java class for StaffIdentityType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="StaffIdentityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StaffUniqueStateId" type="{http://ed-fi.org/0100}UniqueStateIdentifier" minOccurs="0"/>
 *         &lt;element name="StaffIdentificationCode" type="{http://ed-fi.org/0100}StaffIdentificationCode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://ed-fi.org/0100}Name" minOccurs="0"/>
 *         &lt;element name="OtherName" type="{http://ed-fi.org/0100}OtherName" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Sex" type="{http://ed-fi.org/0100}SexType" minOccurs="0"/>
 *         &lt;element name="BirthDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="HispanicLatinoEthnicity" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Race" type="{http://ed-fi.org/0100}RaceType" minOccurs="0"/>
 *         &lt;element name="Telephone" type="{http://ed-fi.org/0100}Telephone" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ElectronicMail" type="{http://ed-fi.org/0100}ElectronicMail" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StaffIdentityType", propOrder = {
    "staffUniqueStateId",
    "staffIdentificationCode",
    "name",
    "otherName",
    "sex",
    "birthDate",
    "hispanicLatinoEthnicity",
    "race",
    "telephone",
    "electronicMail"
})
public class StaffIdentityType {

    @XmlElement(name = "StaffUniqueStateId")
    protected String staffUniqueStateId;
    @XmlElement(name = "StaffIdentificationCode")
    protected List<StaffIdentificationCode> staffIdentificationCode;
    @XmlElement(name = "Name")
    protected Name name;
    @XmlElement(name = "OtherName")
    protected List<OtherName> otherName;
    @XmlElement(name = "Sex")
    protected SexType sex;
    @XmlElement(name = "BirthDate", type = String.class)
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "date")
    protected Calendar birthDate;
    @XmlElementRef(name = "HispanicLatinoEthnicity", namespace = "http://ed-fi.org/0100", type = JAXBElement.class)
    protected JAXBElement<Boolean> hispanicLatinoEthnicity;
    @XmlElement(name = "Race")
    protected RaceType race;
    @XmlElement(name = "Telephone")
    protected List<Telephone> telephone;
    @XmlElement(name = "ElectronicMail")
    protected List<ElectronicMail> electronicMail;

    /**
     * Gets the value of the staffUniqueStateId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStaffUniqueStateId() {
        return staffUniqueStateId;
    }

    /**
     * Sets the value of the staffUniqueStateId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStaffUniqueStateId(String value) {
        this.staffUniqueStateId = value;
    }

    /**
     * Gets the value of the staffIdentificationCode property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the staffIdentificationCode property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStaffIdentificationCode().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StaffIdentificationCode }
     *
     *
     */
    public List<StaffIdentificationCode> getStaffIdentificationCode() {
        if (staffIdentificationCode == null) {
            staffIdentificationCode = new ArrayList<StaffIdentificationCode>();
        }
        return this.staffIdentificationCode;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link Name }
     *
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link Name }
     *
     */
    public void setName(Name value) {
        this.name = value;
    }

    /**
     * Gets the value of the otherName property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherName property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherName().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OtherName }
     *
     *
     */
    public List<OtherName> getOtherName() {
        if (otherName == null) {
            otherName = new ArrayList<OtherName>();
        }
        return this.otherName;
    }

    /**
     * Gets the value of the sex property.
     *
     * @return
     *     possible object is
     *     {@link SexType }
     *
     */
    public SexType getSex() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     *
     * @param value
     *     allowed object is
     *     {@link SexType }
     *
     */
    public void setSex(SexType value) {
        this.sex = value;
    }

    /**
     * Gets the value of the birthDate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBirthDate(Calendar value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the hispanicLatinoEthnicity property.
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     */
    public JAXBElement<Boolean> getHispanicLatinoEthnicity() {
        return hispanicLatinoEthnicity;
    }

    /**
     * Sets the value of the hispanicLatinoEthnicity property.
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *
     */
    public void setHispanicLatinoEthnicity(JAXBElement<Boolean> value) {
        this.hispanicLatinoEthnicity = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the race property.
     *
     * @return
     *     possible object is
     *     {@link RaceType }
     *
     */
    public RaceType getRace() {
        return race;
    }

    /**
     * Sets the value of the race property.
     *
     * @param value
     *     allowed object is
     *     {@link RaceType }
     *
     */
    public void setRace(RaceType value) {
        this.race = value;
    }

    /**
     * Gets the value of the telephone property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telephone property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelephone().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Telephone }
     *
     *
     */
    public List<Telephone> getTelephone() {
        if (telephone == null) {
            telephone = new ArrayList<Telephone>();
        }
        return this.telephone;
    }

    /**
     * Gets the value of the electronicMail property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the electronicMail property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElectronicMail().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElectronicMail }
     *
     *
     */
    public List<ElectronicMail> getElectronicMail() {
        if (electronicMail == null) {
            electronicMail = new ArrayList<ElectronicMail>();
        }
        return this.electronicMail;
    }

}
