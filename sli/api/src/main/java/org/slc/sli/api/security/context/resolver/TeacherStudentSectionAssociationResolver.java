package org.slc.sli.api.security.context.resolver;

import org.slc.sli.api.config.AssociationDefinition;
import org.slc.sli.api.config.EntityDefinitionStore;
import org.slc.sli.api.config.EntityNames;
import org.slc.sli.api.config.ResourceNames;
import org.slc.sli.api.security.context.AssociativeContextHelper;
import org.slc.sli.domain.Entity;
import org.slc.sli.domain.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Resolves which StudentSectionAssociation a given teacher is allowed to see.
 *
 */
@Component
public class TeacherStudentSectionAssociationResolver implements EntityContextResolver {
    
    @Autowired
    private AssociativeContextHelper helper;
    
    @Autowired
    private EntityRepository repository;
    
    @Autowired
    private EntityDefinitionStore definitionStore;
    
    @Override
    public boolean canResolve(String fromEntityType, String toEntityType) {
        return EntityNames.TEACHER.equals(fromEntityType) && EntityNames.STUDENT_SECTION_ASSOCIATION.equals(toEntityType);
    }
    
    @Override
    public List<String> findAccessible(Entity principal) {
        //return helper.findAccessible(principal, Arrays.asList(ResourceNames.TEACHER_SECTION_ASSOCIATIONS, ResourceNames.STUDENT_SECTION_ASSOCIATIONS));
        
        List<String> ids = new ArrayList<String>(Arrays.asList(principal.getEntityId()));

        AssociationDefinition teacherSectionDef = (AssociationDefinition) definitionStore
                .lookupByResourceName(ResourceNames.TEACHER_SECTION_ASSOCIATIONS);
        AssociationDefinition sectionStudentDef = (AssociationDefinition) definitionStore
                .lookupByResourceName(ResourceNames.STUDENT_SECTION_ASSOCIATIONS);

        ids = findIdsFromAssociation(ids, EntityNames.TEACHER, EntityNames.SECTION, teacherSectionDef);
        ids = findIdsFromAssociation(ids, EntityNames.SECTION, EntityNames.STUDENT, sectionStudentDef);
                
        Iterable<Entity> entities = this.repository.findByQuery(EntityNames.STUDENT_SECTION_ASSOCIATION, new Query(Criteria.where("body.studentId").in(ids)), 0, 9999);        
        
        List<String> studentSectionAssociationIds = new ArrayList<String>();
        
        for (Entity e : entities) {
            studentSectionAssociationIds.add((String) e.getEntityId());
        }
        
        return studentSectionAssociationIds;
    }    
    
    private List<String> findIdsFromAssociation(List<String> ids, String sourceType, String targetType, AssociationDefinition definition) {
        List<String> associationIds = new ArrayList<String>();
        
        List<String> keys = getAssocKeys(sourceType, definition);
        String sourceKey = keys.get(0);
        String targetKey = keys.get(1);
        
        Iterable<Entity> entities = this.repository.findByQuery(definition.getStoredCollectionName(), new Query(Criteria.where("body." + sourceKey).in(ids)), 0, 9999);
                
        for (Entity e : entities) {
            associationIds.add((String) e.getBody().get(targetKey));
        }
        
        return associationIds;
    }
    
    private List<String> getAssocKeys(String entityType, AssociationDefinition ad) {
        
        if (ad.getSourceEntity().getType().equals(entityType)) {
            return Arrays.asList(ad.getSourceKey(), ad.getTargetKey());
        } else if (ad.getTargetEntity().getType().equals(entityType)) {
            return Arrays.asList(ad.getTargetKey(), ad.getSourceKey());
        } else {
            throw new IllegalArgumentException("Entity is not a member of association " + entityType + " " + ad.getType());
        }
    }
    
}
