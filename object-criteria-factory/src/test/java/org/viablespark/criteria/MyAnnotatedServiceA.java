/*
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.viablespark.criteria;

import java.util.Date;
import org.viablespark.criteria.annotation.Criteria;
import org.viablespark.criteria.annotation.CriteriaList;
import org.viablespark.criteria.annotation.Matcher;

import org.viablespark.criteria.matcher.InstanceOfMatcher;
import org.viablespark.criteria.matcher.ToStringValueMatcher;

/**
 *
 * @author Modest Syla
 */
@CriteriaList({
    @Criteria({
        @Matcher(property = "payload",matcher = InstanceOfMatcher.class,classValue = String.class)
    }),
    @Criteria({
        @Matcher(property = "payload",matcher = InstanceOfMatcher.class,classValue = Date.class),
        @Matcher(property = "other",matcher = ToStringValueMatcher.class,stringValue = "extra")
    })  
})
public class MyAnnotatedServiceA implements MyService {

    public void execute() {
        System.out.println("executed annotated Service A");
    }

}
