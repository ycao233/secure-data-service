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
package org.slc.sli.bulk.extract.date;

import org.slc.sli.common.constants.EntityNames;
import org.slc.sli.common.constants.ParameterConstants;

import java.util.*;

/**
 * @author ablum
 */
public class EntityDates {
    public static final Map<String, String> ENTITY_DATE_FIELDS = new HashMap<String, String>();

    public static final Map<String, List<String>> ENTITY_PATH_FIELDS = new HashMap<String, List<String>>();

    public static final int COLLECTION = 0;
    public static final int FIELD = 1;

    static {
        ENTITY_DATE_FIELDS.put(EntityNames.STUDENT_PROGRAM_ASSOCIATION, ParameterConstants.BEGIN_DATE);
        ENTITY_DATE_FIELDS.put(EntityNames.STUDENT_COHORT_ASSOCIATION, ParameterConstants.BEGIN_DATE);

        ENTITY_PATH_FIELDS.put(EntityNames.STUDENT_DISCIPLINE_INCIDENT_ASSOCIATION,
                new ArrayList<String>(Arrays.asList(EntityNames.DISCIPLINE_INCIDENT, ParameterConstants.DISCIPLINE_INCIDENT_ID)));
    }
}
