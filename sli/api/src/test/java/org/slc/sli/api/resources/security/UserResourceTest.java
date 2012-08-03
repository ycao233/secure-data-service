package org.slc.sli.api.resources.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;

import org.slc.sli.api.init.RoleInitializer;
import org.slc.sli.api.ldap.LdapService;
import org.slc.sli.api.ldap.User;
import org.slc.sli.api.resources.security.UserResource.RightToGroupMapper;
import org.slc.sli.api.resources.security.UserResource.RoleToGroupMapper;
import org.slc.sli.api.service.SuperAdminService;
import org.slc.sli.api.util.SecurityUtil.SecurityUtilProxy;
import org.slc.sli.domain.enums.Right;

/**
 * Unit tests for user resource.
 *
 * @author joechung
 *
 */
@RunWith(JUnit4.class)
public class UserResourceTest {

    @InjectMocks
    UserResource resource = new UserResource();

    @Mock
    LdapService ldap;

    @Mock
    SecurityUtilProxy secUtil;

    @Mock
    SuperAdminService adminService;

    private static final GrantedAuthority[] EMPTY_RIGHT = {};
    private static final GrantedAuthority[] NO_ADMIN_RIGHT = { Right.INGEST_DATA, Right.ANONYMOUS_ACCESS };
    private static final GrantedAuthority[] ONE_ADMIN_RIGHT_ONLY = { Right.CRUD_SEA_ADMIN };
    private static final GrantedAuthority[] ONE_ADMIN_RIGHT_WITH_OTHERS = { Right.CRUD_SLC_OPERATOR,
            Right.AGGREGATE_READ };
    private static final GrantedAuthority[] TWO_ADMIN_RIGHTS_ONLY = { Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN };
    private static final GrantedAuthority[] TWO_ADMIN_RIGHTS_WITH_OTHERS = { Right.EDORG_APP_AUTHZ,
            Right.CRUD_LEA_ADMIN, Right.CRUD_SANDBOX_ADMIN };
    private static final String REALM = "REALM";
    private static final String TENANT = "TENANT";
    private static final String EDORG1 = "EDORG1";
    private static final String EDORG2 = "EDORG2";
    private static final String UUID1 = "UUID1";
    private static final String UUID2 = "UUID2";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource.setRealm(REALM);
        resource.setEnableSamt(true);
        Mockito.when(adminService.getAllowedEdOrgs(TENANT, EDORG1)).thenReturn(
                new HashSet<String>(Arrays.asList(EDORG1, EDORG2)));
        Mockito.when(secUtil.getTenantId()).thenReturn(TENANT); // need a tenant without
                                                                // CRUD_SLC_OPERATOR.
    }

    @Test
    public void testCreate() {
        // SLC Op
        Collection<GrantedAuthority> rights = new HashSet<GrantedAuthority>();
        rights.addAll(Arrays.asList(Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN));
        Mockito.when(secUtil.getAllRights()).thenReturn(rights);
        User newUser = new User();
        newUser.setGroups(Arrays.asList(RoleInitializer.SLC_OPERATOR));
        newUser.setFullName("Eddard", "Stark");
        newUser.setEmail("nedstark@winterfell.gov");
        newUser.setUid("nedstark");
        Response res = resource.create(newUser);
        Mockito.verify(ldap).createUser(REALM, newUser);
        Assert.assertNotNull(res);
        Assert.assertEquals(201, res.getStatus());

        // cannot create without CRUD_SLC_OPERATOR
        rights.remove(Right.CRUD_SLC_OPERATOR);
        res = resource.create(newUser);
        Assert.assertNotNull(res);
        Assert.assertEquals(403, res.getStatus());

        // SEA
        Mockito.when(secUtil.getEdOrg()).thenReturn(EDORG1);
        newUser.setGroups(Arrays.asList(RoleInitializer.SEA_ADMINISTRATOR));
        newUser.setEdorg(EDORG1);
        newUser.setTenant(TENANT);
        res = resource.create(newUser);
        Mockito.verify(ldap, Mockito.times(2)).createUser(REALM, newUser);
        Assert.assertNotNull(res);
        Assert.assertEquals(201, res.getStatus());

        // cannot create without CRUD_SEA_ADMIN
        rights.remove(Right.CRUD_SEA_ADMIN);
        res = resource.create(newUser);
        Assert.assertNotNull(res);
        Assert.assertEquals(403, res.getStatus());

        // LEA
        Mockito.when(secUtil.hasRole(RoleInitializer.LEA_ADMINISTRATOR)).thenReturn(true);
        newUser.setGroups(Arrays.asList(RoleInitializer.LEA_ADMINISTRATOR));
        newUser.setEdorg(EDORG2);
        res = resource.create(newUser);
        Assert.assertNotNull(res);
        Assert.assertEquals(201, res.getStatus());
        Mockito.verify(ldap, Mockito.times(3)).createUser(REALM, newUser);

        // cannot create without CRUD_LEA_ADMIN
        rights.remove(Right.CRUD_LEA_ADMIN);
        res = resource.create(newUser);
        Assert.assertNotNull(res);
        Assert.assertEquals(403, res.getStatus());
    }

    @Test
    public void testMailValidation() {
        Collection<GrantedAuthority> rights = new HashSet<GrantedAuthority>();
        rights.addAll(Arrays.asList(Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN));
        Mockito.when(secUtil.getAllRights()).thenReturn(rights);
        User userWithNoMail = new User();
        userWithNoMail.setGroups(Arrays.asList(RoleInitializer.SEA_ADMINISTRATOR));
        userWithNoMail.setEdorg(EDORG1);
        userWithNoMail.setTenant(TENANT);
        userWithNoMail.setFullName("John", "Snow");
        userWithNoMail.setUid("jsnow");
        userWithNoMail.setPassword("jsn0w");
        Response response = resource.create(userWithNoMail);
        assertEquals(400, response.getStatus());
        assertEquals("No email address", response.getEntity());
    }

    @Test
    public void testNameValidation() {
        Collection<GrantedAuthority> rights = new HashSet<GrantedAuthority>();
        rights.addAll(Arrays.asList(Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN));
        Mockito.when(secUtil.getAllRights()).thenReturn(rights);
        User userWithNoName = new User();
        userWithNoName.setGroups(Arrays.asList(RoleInitializer.SEA_ADMINISTRATOR));
        userWithNoName.setEdorg(EDORG1);
        userWithNoName.setTenant(TENANT);
        userWithNoName.setUid("kindlyman");
        userWithNoName.setPassword("k1ndlyman");
        userWithNoName.setEmail("kindlyman@bravos.org");
        assertEquals(400, resource.create(userWithNoName).getStatus());
    }

    @Test
    public void testUidValidation() {
        Collection<GrantedAuthority> rights = new HashSet<GrantedAuthority>();
        rights.addAll(Arrays.asList(Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN));
        Mockito.when(secUtil.getAllRights()).thenReturn(rights);
        User userWithNoId = new User();
        userWithNoId.setGroups(Arrays.asList(RoleInitializer.SEA_ADMINISTRATOR));
        userWithNoId.setEdorg(EDORG1);
        userWithNoId.setTenant(TENANT);
        userWithNoId.setFullName("Arya", "Stark");
        userWithNoId.setPassword("@ry@");
        userWithNoId.setEmail("arya@winterfell.org");
        assertEquals(400, resource.create(userWithNoId).getStatus());
    }

    @Test
    public void testUpdate() {
        Collection<GrantedAuthority> rights = new HashSet<GrantedAuthority>();
        rights.addAll(Arrays.asList(Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN));
        Mockito.when(secUtil.getAllRights()).thenReturn(rights);
        Mockito.when(secUtil.getUid()).thenReturn(UUID1);
        User newUser = new User();
        newUser.setGroups(Arrays.asList(RoleInitializer.SLC_OPERATOR));
        newUser.setUid(UUID2);
        newUser.setFullName("Robb", "Stark");
        newUser.setEmail("robbstark@winterfell.gov");
        newUser.setUid("robbstark");
        Response res = resource.update(newUser);
        Mockito.verify(ldap).updateUser(REALM, newUser);
        Assert.assertNotNull(res);
        Assert.assertEquals(204, res.getStatus());
    }

    @Test
    public void testDelete() {
        Collection<GrantedAuthority> rights = new HashSet<GrantedAuthority>();
        rights.addAll(Arrays.asList(Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN));
        Mockito.when(secUtil.getAllRights()).thenReturn(rights);
        Mockito.when(secUtil.getUid()).thenReturn(UUID1);
        User newUser = new User();
        newUser.setGroups(Arrays.asList(RoleInitializer.SLC_OPERATOR));
        newUser.setUid(UUID2);
        Mockito.when(ldap.getUser(REALM, UUID2)).thenReturn(newUser);
        Response res = resource.delete(newUser.getUid());
        Mockito.verify(ldap).getUser(REALM, newUser.getUid());
        Mockito.verify(ldap).removeUser(REALM, newUser.getUid());
        Assert.assertNotNull(res);
        Assert.assertEquals(204, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReadAll() {
        Collection<GrantedAuthority> rights = new HashSet<GrantedAuthority>();
        rights.addAll(Arrays.asList(Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN));
        Mockito.when(secUtil.getAllRights()).thenReturn(rights);
        Mockito.when(secUtil.getUid()).thenReturn("myUid");
        Mockito.when(secUtil.hasRole(RoleInitializer.LEA_ADMINISTRATOR)).thenReturn(false);

        List<User> users = Arrays.asList(new User(), new User());
        Mockito.when(
                ldap.findUsersByGroups(Mockito.eq(REALM), Mockito.anyCollectionOf(String.class), Mockito.anyString(),
                        (Collection<String>) Mockito.isNull())).thenReturn(users);

        Response res = resource.readAll();
        Assert.assertEquals(200, res.getStatus());
        Object entity = res.getEntity();
        Assert.assertEquals(users, entity);
    }

    @Test
    public void testGetEdOrgs() {
        // SLC OP
        Collection<GrantedAuthority> rights = new HashSet<GrantedAuthority>();
        rights.addAll(Arrays.asList(Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN));
        Mockito.when(secUtil.getAllRights()).thenReturn(rights);
        Mockito.when(secUtil.hasRole(RoleInitializer.LEA_ADMINISTRATOR)).thenReturn(false);

        Response res = resource.getEdOrgs();
        Assert.assertEquals(200, res.getStatus());
        Assert.assertEquals(new LinkedList<String>(), res.getEntity());

        // SEA
        Mockito.when(secUtil.getTenantId()).thenReturn(TENANT);
        rights.remove(Right.CRUD_SLC_OPERATOR);
        Mockito.when(adminService.getAllowedEdOrgs(TENANT, null)).thenReturn(
                new HashSet<String>(Arrays.asList(EDORG1, EDORG2)));
        res = resource.getEdOrgs();
        Assert.assertEquals(200, res.getStatus());
        Assert.assertEquals(Arrays.asList(EDORG1, EDORG2), res.getEntity());

        // LEA
        rights.remove(Right.CRUD_SEA_ADMIN);
        Mockito.when(secUtil.getEdOrg()).thenReturn(EDORG1);
        Mockito.when(adminService.getAllowedEdOrgs(TENANT, EDORG1)).thenReturn(
                new HashSet<String>(Arrays.asList(EDORG1, EDORG2)));
        res = resource.getEdOrgs();
        Assert.assertEquals(200, res.getStatus());
        Assert.assertEquals(Arrays.asList(EDORG1, EDORG2), res.getEntity());
    }

    @Test
    public void testBadData() {
        Collection<GrantedAuthority> rights = new HashSet<GrantedAuthority>();
        rights.addAll(Arrays.asList(Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN));
        Mockito.when(secUtil.getAllRights()).thenReturn(rights);
        User newUser = new User();
        // no tenant
        newUser.setGroups(Arrays.asList(RoleInitializer.SEA_ADMINISTRATOR));
        Response res = resource.create(newUser);
        Assert.assertEquals(400, res.getStatus());
        newUser.setGroups(Arrays.asList(RoleInitializer.LEA_ADMINISTRATOR));
        res = resource.create(newUser);
        Assert.assertEquals(400, res.getStatus());

        // no edorg
        newUser.setTenant(TENANT);
        newUser.setGroups(Arrays.asList(RoleInitializer.SEA_ADMINISTRATOR));
        res = resource.create(newUser);
        Assert.assertEquals(400, res.getStatus());

        newUser.setGroups(Arrays.asList(RoleInitializer.LEA_ADMINISTRATOR));
        res = resource.create(newUser);
        Assert.assertEquals(400, res.getStatus());
    }

    @Test
    public void testValidateAdminRights() {
        assertNotNull(resource.validateAdminRights(Arrays.asList(EMPTY_RIGHT), "tenant"));
        assertNotNull(resource.validateAdminRights(Arrays.asList(NO_ADMIN_RIGHT), "tenant"));
        assertNull(resource.validateAdminRights(Arrays.asList(ONE_ADMIN_RIGHT_ONLY), "tenant"));
        assertNull(resource.validateAdminRights(Arrays.asList(ONE_ADMIN_RIGHT_WITH_OTHERS), "tenant"));
        assertNull(resource.validateAdminRights(Arrays.asList(TWO_ADMIN_RIGHTS_ONLY), "tenant"));
        assertNull(resource.validateAdminRights(Arrays.asList(TWO_ADMIN_RIGHTS_WITH_OTHERS), "tenant"));
        try {
            assertNull(resource.validateAdminRights(Arrays.asList(ONE_ADMIN_RIGHT_ONLY), null));
            Assert.fail("expected exception");
        } catch (RuntimeException e) {
            Assert.assertTrue("sweet", true);
        }
    }

    @Test
    public void testRightToGroupMapper() {
        RightToGroupMapper rightToGroupMapper = RightToGroupMapper.getInstance();
        assertEquals(0, rightToGroupMapper.getGroups(Arrays.asList(new GrantedAuthority[] {})).size());

        Collection<String> leaAdminGroups = rightToGroupMapper.getGroups(Arrays
                .asList(new GrantedAuthority[] { Right.CRUD_LEA_ADMIN }));
        assertCollectionEquals(
                RoleToGroupMapper.getInstance().mapRoleToGroups(
                        Arrays.asList(new String[] { RoleInitializer.LEA_ADMINISTRATOR,
                                RoleInitializer.REALM_ADMINISTRATOR, RoleInitializer.INGESTION_USER })), leaAdminGroups);

        Collection<String> seaAdminGroups = rightToGroupMapper.getGroups(Arrays.asList(new GrantedAuthority[] {
                Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN }));
        assertCollectionEquals(
                RoleToGroupMapper.getInstance().mapRoleToGroups(
                        Arrays.asList(new String[] { RoleInitializer.SEA_ADMINISTRATOR,
                                RoleInitializer.LEA_ADMINISTRATOR, RoleInitializer.REALM_ADMINISTRATOR,
                                RoleInitializer.INGESTION_USER })), seaAdminGroups);

        Collection<String> slcOperatorGroups = rightToGroupMapper.getGroups(Arrays.asList(new GrantedAuthority[] {
                Right.CRUD_SLC_OPERATOR, Right.CRUD_SEA_ADMIN, Right.CRUD_LEA_ADMIN }));
        assertCollectionEquals(
                RoleToGroupMapper.getInstance().mapRoleToGroups(
                        Arrays.asList(new String[] { RoleInitializer.SLC_OPERATOR, RoleInitializer.SEA_ADMINISTRATOR,
                                RoleInitializer.LEA_ADMINISTRATOR, RoleInitializer.REALM_ADMINISTRATOR,
                                RoleInitializer.INGESTION_USER })), slcOperatorGroups);

        Collection<String> sandboxAdminGroups = rightToGroupMapper.getGroups(Arrays
                .asList(new GrantedAuthority[] { Right.CRUD_SANDBOX_ADMIN }));
        assertCollectionEquals(
                RoleToGroupMapper.getInstance().mapRoleToGroups(
                        Arrays.asList(new String[] { RoleInitializer.SANDBOX_ADMINISTRATOR,
                                RoleInitializer.APP_DEVELOPER, RoleInitializer.INGESTION_USER })), sandboxAdminGroups);

        Collection<String> slcOperatorGroups2 = rightToGroupMapper.getGroups(Arrays
                .asList(new GrantedAuthority[] { Right.CRUD_SLC_OPERATOR }));
        assertCollectionEquals(
                RoleToGroupMapper.getInstance().mapRoleToGroups(
                        Arrays.asList(new String[] { RoleInitializer.SLC_OPERATOR, RoleInitializer.REALM_ADMINISTRATOR,
                                RoleInitializer.INGESTION_USER })), slcOperatorGroups2);
    }

    @Test
    public void testValidateAtMostOneAdminRole() {
        assertNull(UserResource.validateAtMostOneAdminRole(Arrays.asList(new String[] {})));
        assertNull(UserResource.validateAtMostOneAdminRole(Arrays
                .asList(new String[] { RoleInitializer.INGESTION_USER })));
        assertNull(UserResource
                .validateAtMostOneAdminRole(Arrays.asList(new String[] { RoleInitializer.SLC_OPERATOR })));
        assertNotNull(UserResource.validateAtMostOneAdminRole(Arrays.asList(new String[] {
                RoleInitializer.SLC_OPERATOR, RoleInitializer.LEA_ADMINISTRATOR })));
        assertNull(UserResource.validateAtMostOneAdminRole(Arrays.asList(new String[] {
                RoleInitializer.LEA_ADMINISTRATOR, RoleInitializer.LEA_ADMINISTRATOR })));
    }

    private void assertCollectionEquals(final Collection<String> expected, final Collection<String> actual) {
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}
