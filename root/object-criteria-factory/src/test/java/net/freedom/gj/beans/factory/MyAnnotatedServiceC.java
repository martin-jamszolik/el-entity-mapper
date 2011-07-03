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

package net.freedom.gj.beans.factory;

import net.freedom.gj.beans.annotation.Criteria;
import net.freedom.gj.beans.annotation.Matcher;
import net.freedom.gj.beans.matcher.PropertyValueEqualsMatcher;
import net.freedom.gj.beans.matcher.ToStringValueMatcher;

/**
 *
 * @author Modest Syla
 */

@Criteria({
    @Matcher(property = "client",matcher = PropertyValueEqualsMatcher.class,stringValue = "ABC"),
    @Matcher(property = "category",matcher = ToStringValueMatcher.class,stringValue = "furniture")
})
public class MyAnnotatedServiceC implements MyService{

    public void execute() {
        System.out.println("executed annotated Service C");
    }

}
