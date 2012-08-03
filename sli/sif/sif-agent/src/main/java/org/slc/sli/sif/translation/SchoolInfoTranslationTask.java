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

package org.slc.sli.sif.translation;

import java.util.ArrayList;
import java.util.List;

import openadk.library.SIFDataObject;
import openadk.library.student.SchoolInfo;

import org.slc.sli.sif.domain.slientity.GenericEntity;
import org.slc.sli.sif.domain.slientity.SchoolEntity;
import org.springframework.stereotype.Component;

@Component
public class SchoolInfoTranslationTask<T extends SchoolInfo> implements TranslationTask
{

    @Override
    public List<GenericEntity> translate(SIFDataObject sifData)
    {
        //Hey, I translate only SchoolInfo
        if (!(sifData instanceof SchoolInfo)) 
            return new ArrayList<GenericEntity>();
        
        SchoolEntity e = new SchoolEntity();
        //covert properties
        
        List<GenericEntity> list = new ArrayList<GenericEntity>(1);
        list.add(e);        
        return list;
    }

}
