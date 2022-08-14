/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.viablespark.example.jpa;

/**
 *
 * @author martin
 */
public class GroupEntity {

    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString(){
        return groupName;
    }

}
