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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ezequ
 */
public final class BDPostgreSQL {
    
    private static final String driver = "org.postgresql.Driver";;
    private static final String user = "postgres";
    private static final String senha = "admin";
    private static final String url = "jdbc:postgresql://localhost:5432/db";
    
    private static Connection lastCon;

    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        if (lastCon != null && !lastCon.isClosed()){
            lastCon.close();
        }
        if(lastCon == null || lastCon.isClosed()){
            
            Class.forName(driver);
            lastCon = (Connection) DriverManager.getConnection(url, user, senha);
        }
        
        return lastCon;
    }
    
    
}
