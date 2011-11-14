/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.freedom.gj.beans.factory;

import java.util.List;
import net.freedom.gj.beans.criteria.BeanCriteria;
import net.freedom.gj.beans.criteria.CriteriaBuilder;
import net.freedom.gj.beans.criteria.PropertyBuilder;
import net.freedom.gj.beans.criteria.PropertyCriteria;
import net.freedom.gj.beans.matcher.PropertyValueEqualsMatcher;

/**
 *
 * @author martin
 */
public class MyServiceC implements MyService,BeanCriteria{

    public void execute() {
        System.out.println("executed Service C");
    }

    public List<PropertyCriteria> getCriteria() {
        return new CriteriaBuilder().build( new PropertyBuilder()
                                        .build("client", new PropertyValueEqualsMatcher("ABC"))
                                        .build("category", new PropertyValueEqualsMatcher("furniture")))
                                    .getCriteria();
    }
    
}
