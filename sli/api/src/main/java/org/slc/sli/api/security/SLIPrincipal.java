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

package org.slc.sli.api.security;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slc.sli.domain.Entity;
import org.slc.sli.domain.NeutralQuery;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Attribute holder for SLI Principal
 * 
 * @author dkornishev
 * @author shalka
 */
@Component
public class SLIPrincipal implements Principal, Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String realm;
    private String externalId;
    private String adminRealm;
    private String edOrg;
    private String tenantId;
    private String sessionId;
    private List<String> roles;
    private Map<String, List<String>> edOrgRoles;
    private String edOrgId;
    private boolean adminUser;
    private String firstName;
    private String lastName;
    private String vendor;
    private Set<String> subEdOrgHierarchy;
    private String sandboxTenant;
    private boolean adminRealmAuthenticated;
    private Entity entity;
    private String userType;
    private Collection<GrantedAuthority> selfRights;
    private Set<String> authorizingEdOrgs;
    private String email;
    private Map<String, List<NeutralQuery>> obligations;
    private boolean studentAccessFlag = true;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public SLIPrincipal() {
        // Empty default constructor is used in various places.
        authorizingEdOrgs = new HashSet<String>();
        obligations = new HashMap<String, List<NeutralQuery>>();
    }

    public SLIPrincipal(String id) {
        this();
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getId() {
        return id;
    }

    /**
     * Gets the fully-qualified LDAP string generated by OpenAM.
     * 
     * @return String containing Realm information.
     */
    public String getRealm() {
        return realm;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Map<String, List<String>> getEdOrgRoles() {
        return edOrgRoles;
    }

    public void setEdOrgRoles(Map<String, List<String>> edOrgRoles) {
        this.edOrgRoles = edOrgRoles;
    }

    public String getAdminRealm() {
        return adminRealm;
    }

    public void setAdminRealm(String adminRealm) {
        this.adminRealm = adminRealm;
    }

    /**
     * LDAP Attribute "edorg" is set to "X-DistrictY" which is the "stateOrganizationId" for the District in the edorg
     * hierarchy for the data that will be ingested
     * 
     * @return
     */
    public String getEdOrg() {
        return edOrg;
    }

    public void setEdOrg(String edOrg) {
        this.edOrg = edOrg;
    }

    @Override
    public String toString() {
        return this.externalId + "@" + this.realm;
    }

    /**
     * Sets the tenant id of the sli principal.
     * 
     * @param newTenantId new tenant id.
     */
    public void setTenantId(String newTenantId) {
        this.tenantId = newTenantId;
    }

    /**
     * Gets the tenant id of the sli principal.
     * 
     * @return tenant id.
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Gets the entity ID of the edOrg the user is associated with
     * 
     * @return
     */
    public String getEdOrgId() {
        return edOrgId;
    }

    /**
     * @param edOrgId the entityId of the edOrg the user is associated with
     */
    public void setEdOrgId(String edOrgId) {
        this.edOrgId = edOrgId;
    }

    public Map<String, Object> toMap() throws Exception {
        Field[] fields = this.getClass().getFields();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field f : fields) {
            map.put(f.getName(), f.get(this));
        }

        return map;
    }

    public void setAdminUser(boolean isAdminUser) {
        this.adminUser = isAdminUser;
    }

    public boolean isAdminUser() {
        return adminUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Collection<String> getSubEdOrgHierarchy() {
        if (subEdOrgHierarchy != null) {
            return subEdOrgHierarchy;
        } else {
            return Collections.emptySet();
        }
    }

    public void setSubEdOrgHierarchy(Collection<String> subEdOrgHierarchy) {
        this.subEdOrgHierarchy = new TreeSet<String>(subEdOrgHierarchy);
    }

    public String getSandboxTenant() {
        return sandboxTenant;
    }

    public void setSandboxTenant(String sandboxTenant) {
        this.sandboxTenant = sandboxTenant;
    }

    public boolean isAdminRealmAuthenticated() {
        return adminRealmAuthenticated;
    }

    public void setAdminRealmAuthenticated(boolean adminRealmAuthenticated) {
        this.adminRealmAuthenticated = adminRealmAuthenticated;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setSelfRights(Collection<GrantedAuthority> auths) {
        this.selfRights = auths;
    }

    public Collection<GrantedAuthority> getSelfRights() {
        return this.selfRights;
    }

    /**
     * These are edorgs that have authorized the app that the user is currently logged into.
     * 
     * The set contains ids of both the authorizing LEA and any sub-LEAs or schools within the LEA.
     * 
     * @return
     */
    public Set<String> getAuthorizingEdOrgs() {
        return authorizingEdOrgs;
    }

    public void setAuthorizingEdOrgs(Set<String> authorizingEdOrgs) {
        this.authorizingEdOrgs = authorizingEdOrgs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<NeutralQuery> getObligation(String key) {
        List<NeutralQuery> result = obligations.get(key);
        
        if(result == null) {
            result = Collections.emptyList();
        }
        
        return result;
    }

    public void addObligation(String collection, List<NeutralQuery> obligations) {
        this.obligations.put(collection, obligations);
    }
    
    public void clearObligations() {
    	this.obligations.clear();
    }

    public boolean isStudentAccessFlag() {
        return studentAccessFlag;
    }

    public void setStudentAccessFlag(boolean studentAccessFlag) {
        this.studentAccessFlag = studentAccessFlag;
    }

}
