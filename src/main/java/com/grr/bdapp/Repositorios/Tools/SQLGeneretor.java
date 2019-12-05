/* 
 * Copyright 2019 Ezequias Moises dos Santos Silva <ezequiasmoises@gmail.com>.
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
package com.grr.bdapp.Repositorios.Tools;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author ezequ
 */
public class SQLGeneretor {

    private StringBuilder builder;
    private Mode mode;
    private String table;
    
    private Queue<StringBuilder> values;
    
    private StringBuilder currentValue;
    
    public SQLGeneretor(Mode mode, String table){
        this.mode = mode;
        this.table = table;
        values = new LinkedList<>();
        
        builder = new StringBuilder();
        
        switch (mode){
            case INSERT:
                builder.append("INSERT INTO ");
                builder.append(table);
                builder.append(" VALUES ");
                break;
            case DELETE:
                builder.append("DELETE FROM ");
                builder.append(table);
                break;
            case UPDATE:
                builder.append("UPDATE ");
                builder.append(table);
                builder.append(" SET ");
                break;
            case SELECT:
                builder.append("SELECT * FROM ");
                builder.append(table);
        }
        push();
    }
    
    private String toInsert(){
        StringBuilder output = new StringBuilder(builder);
        for(StringBuilder value : values){
            output.append(value.toString());
            output.append(",");
        }
        output.setLength(output.length() - 1);
        output.append(";");
        return output.toString();
    }
    
    private String toRemove(){
        StringBuilder output = new StringBuilder(builder);
        if (values.size() > 0){
            output.append(" WHERE ");
        }
        int count = 0;
        for(StringBuilder value : values){
            count++;
            
            output.append(value.substring(1, value.length() - 1));
            if (count < values.size()){
                output.append(" AND ");
            }
            break;
        }
        output.append(";");
        return output.toString();
    }
    
    private String toUpdate(){
        StringBuilder output = new StringBuilder(builder);
        Iterator<StringBuilder> iterator = values.iterator();
        int count = 0;
        while (iterator.hasNext()){
            count++;
            StringBuilder value = iterator.next();
            if (count == values.size()){
                output.setLength(output.length() - 1);
                output.append(" WHERE ");
            }
            output.append(value.substring(1));
            output.setLength(output.length() - 1);
            output.append(",");
        }
        output.setLength(output.length() - 1);
        output.append(";");
        return output.toString();
    }
    
    private String toSelect(){
        StringBuilder output = new StringBuilder(builder);
        if (values.size() > 0){
            output.append(" WHERE ");
        }
        for(StringBuilder value : values){
            output.append(value.substring(1, value.length() - 1));
            break;
        }
        output.append(";");
        return output.toString();
    }
    

    @Override
    public String toString(){
        switch (mode){
            case INSERT:
                return toInsert();
            case DELETE:
                return toRemove();
            case UPDATE:
                return toUpdate();
            case SELECT:
                return toSelect();
        }
        return "";
    }
    
    public void push(){
        if (currentValue != null && !values.contains(currentValue)){
            currentValue.setLength(currentValue.length() - 1);
            currentValue.append(")");
            values.add(currentValue);    
        }
        currentValue = new StringBuilder();
        currentValue.append("(");
    }
    
    public void add(String value){
        currentValue.append("'");
        currentValue.append(value);
        currentValue.append("',");
    }
    public void addVal(String value){
        currentValue.append(value);
        currentValue.append(",");
    }
    
    public void add(int value){
        currentValue.append(Integer.toString(value));
        currentValue.append(",");
    }
    
    public void add(String column, String value){
        currentValue.append(column);
        currentValue.append(" = '");
        currentValue.append(value);
        currentValue.append("',");
        push();
    }
    
    public void add(String column, int value){
        currentValue.append(column);
        currentValue.append(" = ");
        currentValue.append(Integer.toString(value));
        currentValue.append(",");
        push();
    }
            
    
}
