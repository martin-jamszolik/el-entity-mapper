/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.viablespark.criteria;

import java.util.List;
import org.viablespark.criteria.BeanCriteria;
import org.viablespark.criteria.CriteriaBuilder;
import org.viablespark.criteria.PropertyBuilder;
import org.viablespark.criteria.PropertyCriteria;
import org.viablespark.criteria.matcher.PropertyValueEqualsMatcher;

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
