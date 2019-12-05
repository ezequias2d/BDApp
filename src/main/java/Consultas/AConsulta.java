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
package Consultas;

import com.grr.bdapp.Repositorios.Tools.BDPostgreSQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ezequias
 */
public abstract class AConsulta {
    
    public AConsulta[] Execute(){
        try {
            Connection con = BDPostgreSQL.getConnection();
            Statement statement;
            for(String temp : getTemp()){
                statement = con.createStatement();
                statement.executeUpdate(temp);
            }
            statement = con.createStatement();
            ResultSet result =  statement.executeQuery(toString());
            LinkedList<AConsulta> list = new LinkedList<>();
            while (result.next()){
                list.add(getResult(result));
            }
            con.close();
            AConsulta[] output = list.toArray(new AConsulta[list.size()]);
            return output;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AConsulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public abstract AConsulta getResult(ResultSet result) throws SQLException;
    public abstract void show(AConsulta[] consulta);
    public abstract String[] getTemp();
}
