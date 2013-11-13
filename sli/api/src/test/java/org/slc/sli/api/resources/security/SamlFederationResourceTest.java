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


package org.slc.sli.api.resources.security;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.ElementFilter;
import org.jdom.input.DOMBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.util.LinkedMultiValueMap;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.slc.sli.api.security.OauthMongoSessionManager;
import org.slc.sli.api.security.SLIPrincipal;
import org.slc.sli.api.security.SecurityEventBuilder;
import org.slc.sli.api.security.context.resolver.RealmHelper;
import org.slc.sli.api.security.resolve.UserLocator;
import org.slc.sli.api.security.roles.EdOrgContextualRoleBuilder;
import org.slc.sli.api.security.saml.SamlAttributeTransformer;
import org.slc.sli.api.security.saml.SamlHelper;
import org.slc.sli.api.test.WebContextTestExecutionListener;
import org.slc.sli.common.constants.EntityNames;
import org.slc.sli.common.util.logging.SecurityEvent;
import org.slc.sli.domain.Entity;
import org.slc.sli.domain.MongoEntity;
import org.slc.sli.domain.NeutralQuery;
import org.slc.sli.domain.Repository;

/**
 * Unit tests for the Saml Federation Resource class.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
@TestExecutionListeners({ WebContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class })
public class SamlFederationResourceTest {

    @InjectMocks
    @Autowired
    private SamlFederationResource resource;

    @Mock
    private Repository<Entity> repo;

    @Mock
    private UserLocator users;

    @Mock
    private RealmHelper realmHelper;

    @Mock
    private SamlHelper samlHelper;

    @Mock
    private OauthMongoSessionManager sessionManager;

    @Mock
    private SamlAttributeTransformer transformer;

    @Mock
    private EdOrgContextualRoleBuilder edOrgRoleBuilder;

    @Mock
    private HttpServletRequest httpServletRequest;

    private SecurityEventBuilder securityEventBuilder = Mockito.mock(SecurityEventBuilder.class);

    private Entity realm = Mockito.mock(Entity.class);
    private Entity edOrg = Mockito.mock(Entity.class);

    private Element rootElement = Mockito.mock(Element.class);
    private Element assertion = Mockito.mock(Element.class);
    private Element conditions = Mockito.mock(Element.class);
    private Element subject = Mockito.mock(Element.class);
    private Element subjConfirmation = Mockito.mock(Element.class);
    private Element subjConfirmationData = Mockito.mock(Element.class);
    private Element statement = Mockito.mock(Element.class);
    private List<Element> attributeNodes = new ArrayList<Element>();
    private Document doc = Mockito.mock(Document.class);

    private SLIPrincipal principal;

    private static final int STATUS_OK = 200;
    private static final int STATUS_FOUND = 302;

    @SuppressWarnings("unchecked")
    @Before
    public void init() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);

        Mockito.when(edOrg.getEntityId()).thenReturn("My School");
        Mockito.when(realm.getEntityId()).thenReturn("My Realm");
        Mockito.when(repo.findOne(eq("realm"), any(NeutralQuery.class))).thenReturn(realm);
        Mockito.when(repo.findOne(eq(EntityNames.EDUCATION_ORGANIZATION), any(NeutralQuery.class))).thenReturn(edOrg);

        attributeNodes.clear();
        Mockito.when(subjConfirmation.getChild("SubjectConfirmationData", SamlHelper.SAML_NS)).thenReturn(subjConfirmationData);
        Mockito.when(subject.getChild("SubjectConfirmation", SamlHelper.SAML_NS)).thenReturn(subjConfirmation);
        Mockito.when(statement.getChildren("Attribute", SamlHelper.SAML_NS)).thenReturn(attributeNodes);
        Mockito.when(assertion.getChild("Conditions", SamlHelper.SAML_NS)).thenReturn(conditions);
        Mockito.when(assertion.getChild("Subject", SamlHelper.SAML_NS)).thenReturn(subject);
        Mockito.when(assertion.getChild("AttributeStatement", SamlHelper.SAML_NS)).thenReturn(statement);
        Mockito.when(rootElement.getAttributeValue("InResponseTo")).thenReturn("My Request");
        Mockito.when(rootElement.getChildText("Issuer", SamlHelper.SAML_NS)).thenReturn("My Issuer");
        Mockito.when(rootElement.getChild("Assertion", SamlHelper.SAML_NS)).thenReturn(assertion);
        Mockito.when(doc.getRootElement()).thenReturn(rootElement);

        principal = new SLIPrincipal();
        Mockito.when(users.locate(anyString(), anyString(), anyString())).thenReturn(principal);

        setRealm(Boolean.FALSE);

        List<String> roles = new ArrayList<String>();
        roles.add("Educator");
        constructAttributes(null, false, "My User", null, roles);
        Entity entity = new MongoEntity("user", "My User", new HashMap<String, Object>(), new HashMap<String, Object>());
        principal.setEntity(entity);
        principal.setRoles(roles);

        Mockito.when(conditions.getAttributeValue("NotBefore")).thenReturn(DateTime.now(DateTimeZone.UTC).minusHours(1).toString());
        Mockito.when(conditions.getAttributeValue("NotOnOrAfter")).thenReturn(DateTime.now(DateTimeZone.UTC).plusHours(1).toString());

        Mockito.when(realmHelper.isUserAllowedLoginToRealm(any(Entity.class), eq(realm))).thenReturn(true);
        Mockito.when(subjConfirmationData.getAttributeValue(eq("Recipient"))).thenReturn("http://myapi.com");
        Mockito.when(subjConfirmationData.getAttributeValue(eq("NotBefore"))).thenReturn(DateTime.now(DateTimeZone.UTC).minusHours(1).toString());
        Mockito.when(subjConfirmationData.getAttributeValue(eq("NotOnOrAfter"))).thenReturn(DateTime.now(DateTimeZone.UTC).plusHours(1).toString());

        Mockito.when(samlHelper.decodeSamlPost(anyString())).thenReturn(doc);

        Mockito.when(sessionManager.getAppSession(anyString(), any(Entity.class))).thenCallRealMethod();

        Mockito.when(transformer.apply(eq(realm), any(LinkedMultiValueMap.class)))
            .thenAnswer(new Answer<LinkedMultiValueMap<String, String>>() {
                @Override
                public LinkedMultiValueMap<String, String> answer(InvocationOnMock invocation) throws Throwable {
                    return (LinkedMultiValueMap<String, String>) invocation.getArguments()[1];
                }
            });

        Map<String, List<String>> sliEdOrgRoleMap = new HashMap<String, List<String>>();
        sliEdOrgRoleMap.put("My EdOrg", roles);
        Mockito.when(edOrgRoleBuilder.buildValidStaffRoles(anyString(), anyString(), anyString(), eq(roles))).thenReturn(sliEdOrgRoleMap);

        Mockito.when(httpServletRequest.getRemoteHost()).thenReturn("My Host");
        Mockito.when(httpServletRequest.getRemotePort()).thenReturn(10101);
        Mockito.when(httpServletRequest.getHeader(eq("User-Agent"))).thenReturn("My User-Agent");
        Mockito.when(httpServletRequest.getRemoteUser()).thenReturn("My User");
        Mockito.when(httpServletRequest.getHeader(eq("Origin"))).thenReturn("My Origin");

        SecurityEvent event = new SecurityEvent();
        Mockito.when(securityEventBuilder.createSecurityEvent(any(String.class), any(URI.class), any(String.class), anyBoolean())).thenReturn(event);
        Mockito.when(securityEventBuilder.createSecurityEvent(any(String.class), any(URI.class), any(String.class), any(SLIPrincipal.class),
                any(String.class), any(Entity.class), any(Set.class), anyBoolean())).thenReturn(event);
        resource.setSecurityEventBuilder(securityEventBuilder);

        Field sandboxEnabled = SamlFederationResource.class.getDeclaredField("sandboxEnabled");
        sandboxEnabled.setAccessible(true);
        sandboxEnabled.set(resource, false);
    }

    @Test
    public void getMetadataTest() {
        Response response = resource.getMetadata();

        Assert.assertNotNull(response);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
        Assert.assertNotNull(response.getEntity());

        Exception exception = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader((String) response.getEntity()));
            org.w3c.dom.Document doc = db.parse(is);
            DOMBuilder builder = new DOMBuilder();
            org.jdom.Document jdomDocument = builder.build(doc);
            Iterator<Element> itr = jdomDocument.getDescendants(new ElementFilter());

            while (itr.hasNext()) {
                Element el = itr.next();
                if(el.getName().equals("X509Certificate")) {
                    Assert.assertNotNull(el.getText());
                }
            }
        } catch (ParserConfigurationException e) {
            exception = e;
        } catch (SAXException e) {
            exception = e;
        } catch (IOException e) {
            exception = e;
        }
        Assert.assertNull(exception);
    }


    @SuppressWarnings("unchecked")
    @Test
    public void consumeNonAdminNonDevRealmInstalledTest() throws URISyntaxException {
        String postData = "nonAdminNonDevRealmInstalled";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        Response loginResponse = resource.consume(postData, uriInfo);

        Assert.assertEquals(STATUS_OK, loginResponse.getStatus());
        Mockito.verify(securityEventBuilder, times(1)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), any(SLIPrincipal.class),
                any(String.class), any(Entity.class), any(Set.class), eq(true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void consumeNonAdminNonDevRealmNotInstalledTest() throws URISyntaxException {
        String postData = "nonAdminNonDevRealmNotInstalled";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.FALSE);

        Response loginResponse = resource.consume(postData, uriInfo);

        Assert.assertEquals(STATUS_FOUND, loginResponse.getStatus());
        Mockito.verify(securityEventBuilder, times(1)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), any(SLIPrincipal.class),
                any(String.class), any(Entity.class), any(Set.class), eq(true));
    }

    @SuppressWarnings("unused")
    @Test
    public void consumeAdminRealmAndSandboxEnabledWithSamlTenantTest() throws URISyntaxException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String postData = "adminRealmAndSandboxEnabledWithSamlTenant";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        setRealm(Boolean.TRUE);

        Field sandboxEnabled = SamlFederationResource.class.getDeclaredField("sandboxEnabled");
        sandboxEnabled.setAccessible(true);
        sandboxEnabled.set(resource, true);

        List<String> roles = new ArrayList<String>();
        roles.add("Educator");
        constructAttributes("My Tenant", false, "My User", null, roles);

        Response loginResponse = resource.consume(postData, uriInfo);

        Assert.assertEquals(STATUS_OK, loginResponse.getStatus());
        Mockito.verify(securityEventBuilder, times(1)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), any(SLIPrincipal.class),
                any(String.class), any(Entity.class), any(Set.class), eq(true));
    }

    @SuppressWarnings("unused")
    @Test
    public void consumeisAdminRealmAndSandboxEnabledNoSamlTenantTest() throws URISyntaxException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String postData = "adminRealmAndSandboxEnabledNoSamlTenant";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        setRealm(Boolean.TRUE);

        Field sandboxEnabled = SamlFederationResource.class.getDeclaredField("sandboxEnabled");
        sandboxEnabled.setAccessible(true);
        sandboxEnabled.set(resource, true);

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Invalid user configuration.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void consumeUserNotAllowedToLogInTest() throws URISyntaxException {
        String postData = "userNotAllowedToLogIn";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        Mockito.when(realmHelper.isUserAllowedLoginToRealm(any(Entity.class), eq(realm))).thenReturn(false);

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("User is not associated with realm.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void consumeNoRealmTest() throws URISyntaxException {
        String postData = "noRealm";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        Mockito.when(repo.findOne(eq("realm"), any(NeutralQuery.class))).thenReturn(null);

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Authorization could not be verified.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void consumeNoSubjectTest() throws URISyntaxException {
        String postData = "noSubject";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        Mockito.when(assertion.getChild("Subject", SamlHelper.SAML_NS)).thenReturn(null);

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Authorization could not be verified.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void consumeInvalidRecipientTest() throws URISyntaxException {
        String postData = "invalidRecipient";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        Mockito.when(subjConfirmationData.getAttributeValue(eq("Recipient"))).thenReturn("http://notmyapi.com");

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Authorization could not be verified.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void consumeNoRolesTest() throws URISyntaxException {
        String postData = "noRoles";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        constructAttributes(null, false, "My User", null, new ArrayList<String>());

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Invalid user. No roles specified for user.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void consumeNoMatchingRolesTest() throws URISyntaxException {
        String postData = "noMatchingRoles";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        List<String> samlRoles = new ArrayList<String>();
        samlRoles.add("IT Administrator");
        constructAttributes(null, false, "My User", null, samlRoles);
        Entity entity = new MongoEntity("user", "My User", new HashMap<String, Object>(), new HashMap<String, Object>());
        principal.setEntity(entity);
        principal.setRoles(samlRoles);

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Invalid user.  No valid role mappings exist for the roles specified in the SAML Assertion.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test
    public void consumeNoEdOrgTest() throws URISyntaxException {
        String postData = "noEdOrg";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        Mockito.when(edOrgRoleBuilder.buildValidStaffRoles(anyString(), anyString(), anyString(), any(List.class))).thenReturn(new HashMap<String, List<String>>());

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("No Matching Roles", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings({ "unused" })
    @Test
    public void consumeInvalidUserTest() throws URISyntaxException {
        String postData = "invalidUser";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        List<String> roles = new ArrayList<String>();
        roles.add("Educator");
        constructAttributes(null, false, "Bad User", null, roles);

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Invalid user.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings({ "unused" })
    @Test
    public void consumeNoUserTest() throws URISyntaxException {
        String postData = "noUser";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        Entity entity = new MongoEntity("user", SLIPrincipal.NULL_ENTITY_ID, new HashMap<String, Object>(), new HashMap<String, Object>());
        principal.setEntity(entity);

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Invalid user.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void consumeBadConditionsTest() throws URISyntaxException {
        String postData = "badConditions";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        Mockito.when(conditions.getAttributeValue("NotBefore")).thenReturn(DateTime.now(DateTimeZone.UTC).minusHours(1).toString());
        Mockito.when(conditions.getAttributeValue("NotOnOrAfter")).thenReturn(DateTime.now(DateTimeZone.UTC).toString());

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Authorization could not be verified.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(0)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }

    @SuppressWarnings("unused")
    @Test
    public void consumeBadSAMLPostDataTest() throws URISyntaxException {
        String postData = "badSAMLPostData";

        URI requestUri = new URI("http://myapi.com");
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(requestUri);

        setSession(Boolean.TRUE);

        Mockito.when(samlHelper.decodeSamlPost(anyString())).thenThrow(new IllegalArgumentException("Invalid SAML message"));

        try {
            Response loginResponse = resource.consume(postData, uriInfo);
        } catch (AccessDeniedException ade) {
            Assert.assertEquals("Authorization could not be verified.", ade.getMessage());
        }

        Mockito.verify(securityEventBuilder, times(1)).createSecurityEvent(any(String.class), any(URI.class), any(String.class), eq(false));
    }


    private void constructAttributes(String samlTenant, boolean isAdmin, String userId, String userType, List<String> roles) {
        // Set SAML tenant.
        if (samlTenant != null) {
            Element samlTenantAttributeNode = Mockito.mock(Element.class);
            Element samlTenantValueNode = Mockito.mock(Element.class);
            Mockito.when(samlTenantValueNode.getText()).thenReturn(samlTenant);
            List<Element> samlTenantValueNodes = new ArrayList<Element>();
            samlTenantValueNodes.add(samlTenantValueNode);
            Mockito.when(samlTenantAttributeNode.getAttributeValue("Name")).thenReturn("tenant");
            Mockito.when(samlTenantAttributeNode.getChildren("AttributeValue", SamlHelper.SAML_NS)).thenReturn(samlTenantValueNodes);
            attributeNodes.add(samlTenantAttributeNode);
        }

        // Set isAdmin flag.
        {
            Element isAdminAttributeNode = Mockito.mock(Element.class);
            Element isAdminValueNode = Mockito.mock(Element.class);
            Mockito.when(isAdminValueNode.getText()).thenReturn(Boolean.toString(isAdmin));
            List<Element> isAdminValueNodes = new ArrayList<Element>();
            isAdminValueNodes.add(isAdminValueNode);
            Mockito.when(isAdminAttributeNode.getAttributeValue("Name")).thenReturn("isAdmin");
            Mockito.when(isAdminAttributeNode.getChildren("AttributeValue", SamlHelper.SAML_NS)).thenReturn(isAdminValueNodes);
            attributeNodes.add(isAdminAttributeNode);
        }

        // Set user ID.
        if (userId != null) {
            Element userIdAttributeNode = Mockito.mock(Element.class);
            Element userIdValueNode = Mockito.mock(Element.class);
            Mockito.when(userIdValueNode.getText()).thenReturn(userId);
            List<Element> userIdValueNodes = new ArrayList<Element>();
            userIdValueNodes.add(userIdValueNode);
            Mockito.when(userIdAttributeNode.getAttributeValue("Name")).thenReturn("userId");
            Mockito.when(userIdAttributeNode.getChildren("AttributeValue", SamlHelper.SAML_NS)).thenReturn(userIdValueNodes);
            attributeNodes.add(userIdAttributeNode);
        }

        // Set user type.
        if (userType != null) {
            Element userTypeAttributeNode = Mockito.mock(Element.class);
            Element userTypeValueNode = Mockito.mock(Element.class);
            Mockito.when(userTypeValueNode.getText()).thenReturn(userType);
            List<Element> userTypeValueNodes = new ArrayList<Element>();
            userTypeValueNodes.add(userTypeValueNode);
            Mockito.when(userTypeAttributeNode.getAttributeValue("Name")).thenReturn("userType");
            Mockito.when(userTypeAttributeNode.getChildren("AttributeValue", SamlHelper.SAML_NS)).thenReturn(userTypeValueNodes);
            attributeNodes.add(userTypeAttributeNode);
        }

        // Set user roles.
        if (roles != null) {
            Element rolesAttributeNode = Mockito.mock(Element.class);
            List<Element> rolesValueNodes = new ArrayList<Element>();
            for (String role : roles) {
                Element rolesValueNode = Mockito.mock(Element.class);
                Mockito.when(rolesValueNode.getText()).thenReturn(role);
                rolesValueNodes.add(rolesValueNode);
            }
            Mockito.when(rolesAttributeNode.getAttributeValue("Name")).thenReturn("roles");
            Mockito.when(rolesAttributeNode.getChildren("AttributeValue", SamlHelper.SAML_NS)).thenReturn(rolesValueNodes);
            attributeNodes.add(rolesAttributeNode);
        }
    }

    private void setSession(Boolean installed) {
        Map<String, Object> appSessionCode = new HashMap<String, Object>();
        appSessionCode.put("value", "My Code");
        Map<String, Object> appSession = new HashMap<String, Object>();
        appSession.put("samlId", "My Request");
        appSession.put("code", appSessionCode);
        appSession.put("installed", installed);
        appSession.put("redirectUri", "My.Redirect.URI");
        List<Map<String, Object>> appSessions = new ArrayList<Map<String, Object>>();
        appSessions.add(appSession);
        Map<String, Object> sessionBody = new HashMap<String, Object>();
        sessionBody.put("requestedRealmId", "My Realm");
        sessionBody.put("appSession", appSessions);
        Entity session = new MongoEntity("user", "My User", sessionBody, new HashMap<String, Object>());
        Mockito.when(sessionManager.getSessionForSamlId(eq("My Request"))).thenReturn(session);
    }

    private void setRealm(Boolean isAdminRealm) {
        Map<String, Object> realmBody = new HashMap<String, Object>();
        realmBody.put("edOrg", "My School");
        Map<String, List<Map<String, String>>> saml = new HashMap<String, List<Map<String, String>>>();
        saml.put("field", new ArrayList<Map<String, String>>());
        realmBody.put("saml", saml);
        realmBody.put("tenantId", "My Tenant");
        realmBody.put("admin", Boolean.FALSE);
        realmBody.put("developer", Boolean.FALSE);
        Mockito.when(realm.getBody()).thenReturn(realmBody);
    }

}
