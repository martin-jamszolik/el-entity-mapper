/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.viablespark.mapper;

/**
 *
 * @author martin
 */
public class GroupEntity {

    private String groupName;
    private Integer rank;

    public GroupEntity(String groupName, Integer rank) {
        this.groupName = groupName;
        this.rank = rank;
    }

    public GroupEntity() {
    }

    

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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    

}
