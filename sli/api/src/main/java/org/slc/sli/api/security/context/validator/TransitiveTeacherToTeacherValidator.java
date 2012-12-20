package org.slc.sli.api.security.context.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slc.sli.api.constants.EntityNames;
import org.slc.sli.api.constants.ParameterConstants;
import org.slc.sli.api.util.SecurityUtil;
import org.slc.sli.domain.Entity;
import org.slc.sli.domain.NeutralCriteria;
import org.slc.sli.domain.NeutralQuery;
import org.springframework.stereotype.Component;

/**
 * Validates teacher's access to given teachers
 * 
 * @author dkornishev
 * 
 */
@Component
public class TransitiveTeacherToTeacherValidator extends AbstractContextValidator {

	@Override
	public boolean canValidate(String entityType, boolean isTransitive) {
		return EntityNames.TEACHER.equals(entityType) && isTeacher() && isTransitive;
	}

	@Override
	public boolean validate(String entityType, Set<String> ids) {
		if (!this.canValidate(entityType, true)) {
			throw new IllegalArgumentException(String.format("Asked to validate %s->%s[%s]", SecurityUtil.getSLIPrincipal().getEntity().getType(), entityType, false));
		}

		if (ids == null || ids.size() == 0) {
			throw new IllegalArgumentException("Incoming list of ids cannot be null");
		}

        NeutralQuery nq = new NeutralQuery(new NeutralCriteria(ParameterConstants.STAFF_REFERENCE, "=", SecurityUtil
                .getSLIPrincipal().getEntity().getEntityId()));
        Iterable<Entity> tsa = getRepo().findAll(EntityNames.STAFF_ED_ORG_ASSOCIATION, nq);

		List<String> schools = new ArrayList<String>();
		for (Entity e : tsa) {
            if (!isFieldExpired(e.getBody(), ParameterConstants.END_DATE, false)) {
                schools.add((String) e.getBody().get(ParameterConstants.EDUCATION_ORGANIZATION_REFERENCE));
            }
		}

        nq = new NeutralQuery(new NeutralCriteria(ParameterConstants.EDUCATION_ORGANIZATION_REFERENCE, "in", schools));
        nq.addCriteria(new NeutralCriteria(ParameterConstants.STAFF_REFERENCE, "in", ids));

        tsa = getRepo().findAll(EntityNames.STAFF_ED_ORG_ASSOCIATION, nq);

		Set<String> fin = new HashSet<String>(ids);
		for (Entity e : tsa) {
            if (!isFieldExpired(e.getBody(), ParameterConstants.END_DATE, false)) {
                fin.remove(e.getBody().get(ParameterConstants.STAFF_REFERENCE));
            }
		}

		fin.remove(SecurityUtil.getSLIPrincipal().getEntity().getEntityId());

		return fin.isEmpty();
	}

}
