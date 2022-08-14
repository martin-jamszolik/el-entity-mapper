/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.viablespark.mapper.converter;

/**
 *
 * @author martin
 */
public class ToUpperCaseConverter implements Converter{

    public Object convert(Object object) {

        if( object == null )
            return null;

        return ((String)object).toUpperCase();


    }

}
