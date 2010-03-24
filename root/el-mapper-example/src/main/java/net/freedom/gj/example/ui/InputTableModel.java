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
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Martin Jamszolik
 */
public class InputTableModel extends AbstractTableModel {

    static String[] names = {"property", "Input"};
    Object data;
    int rowcount;
    List<Method> getters;

    public InputTableModel() {
    }

    public InputTableModel(Object input) {
        setData(input);
    }

    public void setData(Object input) {
        data = input;
        getters = ReflectionUtil.getGetterMethods(input.getClass());
        rowcount = getters.size();
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
        return columnIndex == 1;
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
            Logger.getLogger(InputTableModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            Method getter = getters.get(rowIndex);
            Class gPar = getter.getReturnType();
            Method setter = data.getClass().getMethod(getter.getName().replaceFirst("get", "set"), gPar);

            Class[] par = setter.getParameterTypes();

            if (par[0].isPrimitive() && par[0].getName().equals("int")) {
                aValue = Integer.parseInt((String) aValue);
            }

            if (par[0].equals(Integer.class)) {
                aValue = Integer.parseInt((String) aValue);
            }

            if (par[0].equals(BigDecimal.class)) {
                aValue = new BigDecimal((String) aValue);
            }

            if (par[0].equals(Boolean.class)) {
                aValue = Boolean.valueOf((String) aValue);
            }

            ReflectionUtil.invokeSetter(data, setter, aValue);

        } catch (NumberFormatException ex) {
            Logger.getLogger(InputTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(InputTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InputTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
