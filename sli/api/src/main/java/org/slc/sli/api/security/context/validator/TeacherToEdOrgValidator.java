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

package org.slc.sli.api.security.context.validator;

import org.slc.sli.api.constants.EntityNames;
import org.slc.sli.api.security.context.resolver.EdOrgHelper;
import org.slc.sli.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
public class TeacherToEdOrgValidator extends AbstractContextValidator {
    
    @Autowired
    EdOrgHelper helper;

    @Override
    public boolean canValidate(String entityType, boolean isTransitive) {
        return !isTransitive
                && (EntityNames.SCHOOL.equals(entityType) || EntityNames.EDUCATION_ORGANIZATION.equals(entityType))
                && isTeacher();
    }

    @Override
    public boolean validate(String entityType, Set<String> ids) {
        List<String> schools = helper.getDirectEdOrgAssociations(SecurityUtil.getSLIPrincipal().getEntity());
        
        //TODO: currently adding districts so that teachers can update school entities without the
        //parent edorg ref causing problems.  Once ed-org write restrictions are in place,
        //we can use that for PUT reference checks instead of validators.
        schools.addAll(helper.getDistricts(SecurityUtil.getSLIPrincipal().getEntity()));
        return schools.containsAll(ids);
    }

}
