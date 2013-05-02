/*
 * Copyright 2012-2013 inBloom, Inc. and its affiliates.
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
package org.slc.sli.bulk.extract.context.resolver.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import org.slc.sli.domain.Entity;
import org.slc.sli.domain.NeutralCriteria;
import org.slc.sli.domain.NeutralQuery;
import org.slc.sli.domain.Repository;

/**
 * Unit test for the student context resolver
 * 
 * @author nbrown
 * 
 */
public class StudentContextResolverTest {
    private final StudentContextResolver underTest = new StudentContextResolver();
    
    @Before
    public void setup() {
        underTest.getStudentEdOrgCache().clear();
    }
    
    @Test
    public void testFindGoverningLEA() {
        assertEquals(new HashSet<String>(Arrays.asList("edOrg1", "edOrg2", "edOrg3", "futureEdOrg1")),
                underTest.findGoverningLEA(buildTestStudent()));
    }
    
    @Test
    public void testGetLEAsForStudentId() {
        @SuppressWarnings("unchecked")
        Repository<Entity> repo = mock(Repository.class);
        Entity testStudent = buildTestStudent();
        when(repo.findOne("student", buildQuery("42"))).thenReturn(testStudent);
        underTest.setRepo(repo);
        assertEquals(new HashSet<String>(Arrays.asList("edOrg1", "edOrg2", "edOrg3", "futureEdOrg1")),
                underTest.getLEAsForStudentId("42"));
    }
    
    @SuppressWarnings("unchecked")
    private Entity buildTestStudent() {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("studentUniqueStateId", "42");
        DateTime today = DateTime.now();
        List<String> badEdorgs = Arrays.asList("badEdOrg");
        DateTimeFormatter format = ISODateTimeFormat.date();
        Map<String, Object> oldSchool = new HashMap<String, Object>();
        oldSchool.put("_id", "oldSchool");
        oldSchool.put("entryDate", today.minusMonths(15).toString(format));
        oldSchool.put("exitWithdrawDate", today.minusMonths(3).toString(format));
        oldSchool.put("edOrgs", badEdorgs);
        Map<String, Object> currentSchool = new HashMap<String, Object>();
        currentSchool.put("_id", "currentSchool");
        currentSchool.put("entryDate", today.minusMonths(3).toString(format));
        currentSchool.put("exitWithdrawDate", today.plusMonths(9).toString(format));
        currentSchool.put("edOrgs", Arrays.asList("edOrg1", "edOrg2"));
        Map<String, Object> futureSchool = new HashMap<String, Object>();
        futureSchool.put("_id", "futureSchool");
        futureSchool.put("entryDate", today.plusMonths(9).toString(format));
        futureSchool.put("exitWithdrawDate", today.plusMonths(21).toString(format));
        futureSchool.put("edOrgs", Arrays.asList("futureEdOrg1"));
        Map<String, Object> unboundedSchool = new HashMap<String, Object>();
        unboundedSchool.put("_id", "unboundedSchool");
        unboundedSchool.put("entryDate", today.minusMonths(15).toString(format));
        unboundedSchool.put("edOrgs", badEdorgs);
        unboundedSchool.put("edOrgs", Arrays.asList("edOrg1", "edOrg3"));
        Map<String, List<Map<String, Object>>> denormalized = new HashMap<String, List<Map<String, Object>>>();
        denormalized.put("schools", Arrays.asList(oldSchool, currentSchool, futureSchool, unboundedSchool));
        Entity testStudent = mock(Entity.class);
        when(testStudent.getEntityId()).thenReturn("testStudent");
        when(testStudent.getBody()).thenReturn(body);
        when(testStudent.getDenormalizedData()).thenReturn(denormalized);
        return testStudent;
    }
    
    private NeutralQuery buildQuery(String id) {
        return new NeutralQuery(new NeutralCriteria("_id", NeutralCriteria.OPERATOR_EQUAL, id)).setEmbeddedFieldString("schools");
    }

    @Test
    public void testCache() {
        Entity testEntity = buildTestStudent();
        Set<String> fromEntity1 = underTest.findGoverningLEA(testEntity);
        Set<String> fromId = underTest.getLEAsForStudentId("testStudent");
        Set<String> fromEntity2 = underTest.findGoverningLEA(testEntity);
        assertEquals(fromEntity1, fromId);
        assertEquals(fromEntity2, fromId);
    }
}