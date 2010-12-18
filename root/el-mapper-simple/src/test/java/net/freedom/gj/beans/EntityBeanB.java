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

package net.freedom.gj.beans;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Martin Jamszolik
 */
public class EntityBeanB {

    Map extension;

    private List<Group> entityGroups;
    private AddressEntity myAddress;

    public Map getExtension() {
        return extension;
    }

    public void setExtension(Map extension) {
        this.extension = extension;
    }

    public List<Group> getEntityGroups() {
        return entityGroups;
    }

    public void setEntityGroups(List<Group> entityGroups) {
        this.entityGroups = entityGroups;
    }

    public AddressEntity getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(AddressEntity myAddress) {
        this.myAddress = myAddress;
    }

    


}
