/*
 * Copyright 2012 Shared Learning Collaborative, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.31 at 09:35:49 AM EDT 
//


package org.slc.sli.test.edfi.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * This event entity represents the recording of whether a student is in attendance for a class or
 * in attendance to receive or participate in program services.
 * 
 * <p>
 * Java class for AttendanceEvent complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttendanceEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}ComplexObjectType">
 *       &lt;sequence>
 *         &lt;element name="EventDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="SchoolYear" type="{http://ed-fi.org/0100}SchoolYearType"/>
 *         &lt;element name="AttendanceEventType" type="{http://ed-fi.org/0100}AttendanceEventType" minOccurs="0"/>
 *         &lt;element name="AttendanceEventCategory" type="{http://ed-fi.org/0100}AttendanceEventCategoryType"/>
 *         &lt;element name="AttendanceEventReason" type="{http://ed-fi.org/0100}AttendanceEventReason" minOccurs="0"/>
 *         &lt;element name="EducationalEnvironment" type="{http://ed-fi.org/0100}EducationalEnvironmentType" minOccurs="0"/>
 *         &lt;element name="StudentReference" type="{http://ed-fi.org/0100}StudentReferenceType"/>
 *         &lt;element name="SectionReference" type="{http://ed-fi.org/0100}SectionReferenceType" minOccurs="0"/>
 *         &lt;element name="SchoolReference" type="{http://ed-fi.org/0100}EducationalOrgReferenceType" minOccurs="0"/>
 *         &lt;element name="SessionReference" type="{http://ed-fi.org/0100}SessionReferenceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttendanceEvent", propOrder = {
    "eventDate",
 "schoolYear",
    "attendanceEventType",
    "attendanceEventCategory",
    "attendanceEventReason",
    "educationalEnvironment",
    "studentReference",
    "sectionReference",
    "schoolReference",
    "sessionReference"
})
@XmlRootElement(name = "AttendanceEvent")
public class AttendanceEvent
    extends ComplexObjectType
{

    @XmlElement(name = "EventDate", required = true)
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "date")
    protected String eventDate;
    @XmlElement(name = "AttendanceEventType")
    protected AttendanceEventType attendanceEventType;
    @XmlElement(name = "AttendanceEventCategory", required = true)
    protected AttendanceEventCategoryType attendanceEventCategory;
    @XmlElement(name = "AttendanceEventReason")
    protected String attendanceEventReason;
    @XmlElement(name = "EducationalEnvironment")
    protected EducationalEnvironmentType educationalEnvironment;
    @XmlElement(name = "StudentReference", required = true)
    protected StudentReferenceType studentReference;
    @XmlElement(name = "SectionReference")
    protected SectionReferenceType sectionReference;
    @XmlElement(name = "SchoolReference")
    protected EducationalOrgReferenceType schoolReference;
    @XmlElement(name = "SessionReference")
    protected SessionReferenceType sessionReference;
    @XmlElement(name = "SchoolYear", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String schoolYear;

    /**
     * Gets the value of the eventDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventDate() {
        return eventDate;
    }

    /**
     * Sets the value of the eventDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventDate(String value) {
        this.eventDate = value;
    }

    /**
     * Gets the value of the attendanceEventType property.
     * 
     * @return
     *     possible object is
     *     {@link AttendanceEventType }
     *     
     */
    public AttendanceEventType getAttendanceEventType() {
        return attendanceEventType;
    }

    /**
     * Sets the value of the attendanceEventType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttendanceEventType }
     *     
     */
    public void setAttendanceEventType(AttendanceEventType value) {
        this.attendanceEventType = value;
    }

    /**
     * Gets the value of the attendanceEventCategory property.
     * 
     * @return
     *     possible object is
     *     {@link AttendanceEventCategoryType }
     *     
     */
    public AttendanceEventCategoryType getAttendanceEventCategory() {
        return attendanceEventCategory;
    }

    /**
     * Sets the value of the attendanceEventCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttendanceEventCategoryType }
     *     
     */
    public void setAttendanceEventCategory(AttendanceEventCategoryType value) {
        this.attendanceEventCategory = value;
    }

    /**
     * Gets the value of the attendanceEventReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttendanceEventReason() {
        return attendanceEventReason;
    }

    /**
     * Sets the value of the attendanceEventReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttendanceEventReason(String value) {
        this.attendanceEventReason = value;
    }

    /**
     * Gets the value of the educationalEnvironment property.
     * 
     * @return
     *     possible object is
     *     {@link EducationalEnvironmentType }
     *     
     */
    public EducationalEnvironmentType getEducationalEnvironment() {
        return educationalEnvironment;
    }

    /**
     * Sets the value of the educationalEnvironment property.
     * 
     * @param value
     *     allowed object is
     *     {@link EducationalEnvironmentType }
     *     
     */
    public void setEducationalEnvironment(EducationalEnvironmentType value) {
        this.educationalEnvironment = value;
    }

    /**
     * Gets the value of the studentReference property.
     * 
     * @return
     *     possible object is
     *     {@link StudentReferenceType }
     *     
     */
    public StudentReferenceType getStudentReference() {
        return studentReference;
    }

    /**
     * Sets the value of the studentReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentReferenceType }
     *     
     */
    public void setStudentReference(StudentReferenceType value) {
        this.studentReference = value;
    }

    /**
     * Gets the value of the sectionReference property.
     * 
     * @return
     *     possible object is
     *     {@link SectionReferenceType }
     *     
     */
    public SectionReferenceType getSectionReference() {
        return sectionReference;
    }

    /**
     * Sets the value of the sectionReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SectionReferenceType }
     *     
     */
    public void setSectionReference(SectionReferenceType value) {
        this.sectionReference = value;
    }

    /**
     * Gets the value of the schoolReference property.
     * 
     * @return
     *     possible object is
     *     {@link EducationalOrgReferenceType }
     *     
     */
    public EducationalOrgReferenceType getSchoolReference() {
        return schoolReference;
    }

    /**
     * Sets the value of the schoolReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link EducationalOrgReferenceType }
     *     
     */
    public void setSchoolReference(EducationalOrgReferenceType value) {
        this.schoolReference = value;
    }

    /**
     * Gets the value of the sessionReference property.
     * 
     * @return
     *     possible object is
     *     {@link SessionReferenceType }
     *     
     */
    public SessionReferenceType getSessionReference() {
        return sessionReference;
    }

    /**
     * Sets the value of the sessionReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionReferenceType }
     *     
     */
    public void setSessionReference(SessionReferenceType value) {
        this.sessionReference = value;
    }

    /**
     * Gets the value of the schoolYear property.
     * 
     * @return
     *         possible object is {@link String }
     * 
     */
    public String getSchoolYear() {
        return schoolYear;
    }

    /**
     * Sets the value of the schoolYear property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSchoolYear(String value) {
        this.schoolYear = value;
    }

}
