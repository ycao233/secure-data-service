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


package org.slc.sli.api.resources.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import org.slc.sli.api.constants.EntityNames;
import org.slc.sli.api.security.context.resolver.EdOrgToChildEdOrgNodeFilter;
import org.slc.sli.api.util.SecurityUtil;
import org.slc.sli.domain.Entity;
import org.slc.sli.domain.NeutralCriteria;
import org.slc.sli.domain.NeutralQuery;
import org.slc.sli.domain.Repository;

/**
 * Common methods for classes that deal with delegation
 *
 */
@Component
public class DelegationUtil {

    @Autowired
    private EdOrgToChildEdOrgNodeFilter edOrgNodeFilter;

    @Autowired
    @Qualifier("validationRepo")
    Repository<Entity> repo;

    public List<String> getAppApprovalDelegateEdOrgs() {
        List<String> delegateEdOrgs = getDelegateEdOrgs("appApprovalEnabled");
        debug("Ed orgs that have delegated ApplicationApproval are {}", delegateEdOrgs);
        return delegateEdOrgs;
    }

    public List<String> getSecurityEventDelegateStateIds() {
        List<String> delegateEdOrgs = getSecurityEventDelegateEdOrgs();
        NeutralQuery query = new NeutralQuery(new NeutralCriteria("_id", "in", delegateEdOrgs));
        List<String> stateIds = new ArrayList<String>();
        Iterable<Entity> edOrgs = repo.findAll(EntityNames.EDUCATION_ORGANIZATION, query);
        if (edOrgs != null) {
            for (Entity edOrg:edOrgs) {
                Map<String, Object> body = edOrg.getBody();
                if (body != null) {
                    String stateId = (String) body.get("stateOrganizationId");
                    if (stateId != null) {
                        stateIds.add(stateId);
                    }
                }
            }
        }
        return stateIds;
    }

    public List<String> getSecurityEventDelegateEdOrgs() {
        List<String> delegateEdOrgs = getDelegateEdOrgs("viewSecurityEventsEnabled");
        debug("Ed orgs that have delegated SecurityEventViewing are {}", delegateEdOrgs);
        return delegateEdOrgs;
    }

    private List<String> getDelegateEdOrgs(String delegateFeature) {
        String edOrgId = SecurityUtil.getEdOrgId();
        List<String> myEdOrgsIds = edOrgNodeFilter.getChildEducationOrganizations(edOrgId);
        List<String> delegateEdOrgs = new ArrayList<String>();
        for (String curEdOrg : myEdOrgsIds) {
            NeutralQuery delegateQuery = new NeutralQuery();
            delegateQuery.addCriteria(new NeutralCriteria(delegateFeature, "=", true));
            delegateQuery.addCriteria(new NeutralCriteria("localEdOrgId", "=", curEdOrg));
            if (repo.findOne(EntityNames.ADMIN_DELEGATION, delegateQuery) != null) {
                delegateEdOrgs.add(curEdOrg);
            }
        }
        return delegateEdOrgs;
    }
}
