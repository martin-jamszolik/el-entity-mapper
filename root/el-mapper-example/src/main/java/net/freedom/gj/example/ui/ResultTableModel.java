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

package net.freedom.gj.example.ui;


import java.lang.reflect.Method;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Martin Jamszolik
 */
public class ResultTableModel  extends AbstractTableModel {

    static String[] names = {"property","Result"};
    Object data;
    int rowcount;
    List<Method> getters;   

   public ResultTableModel(){}

   public ResultTableModel(Object val ){
       setData( val );
   }

    public void setData(Object input ){
        data = input;
        getters = ReflectionUtil.getGetterMethods(input.getClass());       
        rowcount =  getters.size();
        fireTableDataChanged();
    }

    public int getRowCount() {
       return rowcount;
    }

    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }




    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            switch (columnIndex) {
                case 0:
                    return getters.get(rowIndex).getName().substring(3);
                case 1:
                    return ReflectionUtil.invokeGetter(data, getters.get(rowIndex));
                default:
                    return null;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
