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

package org.slc.sli.api.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import org.slc.sli.api.representation.EntityBody;
import org.slc.sli.validation.ValidationError;
import org.slc.sli.validation.strategy.AbstractBlacklistStrategy;

/**
 * Validates field names of a custom entity.
 *
 * @author tshewchuk
 *
 */
@Component
public class CustomEntityValidator {

    @Resource(name = "validationStrategyList")
    private List<AbstractBlacklistStrategy> validationStrategyList;

    /**
     * Validate a custom entity by checking its field names against the blacklists.
     *
     * @param entityId
     *            Custom Entity ID
     * @param entityType
     *            Entity type (must match PathConstants.CUSTOM_ENTITIES)
     * @param entityBody
     *            Custom Entity to be validated
     *
     */
    public List<ValidationError> validate(EntityBody entityBody) {
        List<ValidationError> errorList = new ArrayList<ValidationError>();
        if (!entityBody.isEmpty()) {
            validate(entityBody, errorList);
        }
        return errorList;
    }

    /**
     * Validate a custom entity by checking its field names against the blacklists.
     *
     * @param entityId
     *            Custom Entity ID
     * @param entityType
     *            Entity type (must match PathConstants.CUSTOM_ENTITIES)
     * @param entityBody
     *            Custom Entity to be validated
     * @param errorList
     *            List of errors encountered during validation
     *
     */
    @SuppressWarnings("unchecked")
    private void validate(Object value, List<ValidationError> errorList) {
        if (value instanceof Map<?, ?>) {  // Map.
            for (String fieldName : ((Map<? extends String, ? extends Object>) value).keySet()) {
                // Remove valid characters before checking.
                String fieldNameToCheck = fieldName.replace("<", "").replace(">", "");
                for (AbstractBlacklistStrategy abstractBlacklistStrategy : validationStrategyList) {
                    if (!abstractBlacklistStrategy.isValid("", fieldNameToCheck)) {
                        errorList.add(new ValidationError(ValidationError.ErrorType.INVALID_FIELD_NAME, fieldName,
                                fieldName, null));
                    }
                }

                // If field contains sub-fields, check them, also.
                validate(((Map<? extends String, ? extends Object>) value).get(fieldName), errorList);
            }
        } else if (value instanceof Object[]) {  // Array of Something.
            for (Object elem : (Object[]) value) {
                validate(elem, errorList);
            }
        } else if (value instanceof Collection<?>) {  // Collection of Something.
            for (Object elem : (Collection<Object>) value) {
                validate(elem, errorList);
            }
        }  // String or special type.  Search no more!
    }
}
