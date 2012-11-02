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

package org.slc.sli.api.selectors.model.elem;

import org.slc.sli.api.selectors.doc.SelectorQueryPlan;
import org.slc.sli.api.selectors.doc.SelectorQueryVisitor;
import org.slc.sli.modeling.uml.ClassType;
import org.slc.sli.modeling.uml.ModelElement;

/**
 * @author jstokes
 */
public class IncludeXSDSelectorElement extends AbstractSelectorElement implements SelectorElement {

    public IncludeXSDSelectorElement(final ModelElement modelElement) {
        super.setElement(modelElement);
        super.setTyped(modelElement instanceof  ClassType);
    }

    @Override
    public Object getRHS() {
        return SelectorElement.INCLUDE_XSD;
    }

    @Override
    public String toString() {
        return "$";
    }

    @Override
    public SelectorQueryPlan accept(final SelectorQueryVisitor selectorQueryVisitor) {
        return selectorQueryVisitor.visit(this);
    }
}
