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
package com.grr.bdapp.Repositorios;

import com.grr.bdapp.objects.Fornecedor;
import com.grr.bdapp.Repositorios.Tools.BDPostgreSQL;
import com.grr.bdapp.Repositorios.Tools.SQLGeneretor;
import com.grr.bdapp.Repositorios.Tools.Mode;
import com.grr.bdapp.elfoAPI.data.IRepositorio;
import com.grr.bdapp.elfoAPI.exception.data.DataCannotBeAccessedException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author ezequ
 */
public class FornecedorRepositorio implements IRepositorio<Fornecedor> {

    @Override
    public Fornecedor add(Fornecedor object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.INSERT, "Fornecedor");
            
            gen.addVal("DEFAULT");
            gen.add(object.getNome());
            gen.add(object.getLocalidade());
            gen.add(object.getTipo());
            gen.add(object.getCPF());
            gen.add(object.getCNPJ());
            
            gen.push();
            
            statement.executeUpdate(gen.toString());
            
            gen = new SQLGeneretor(Mode.SELECT, "currval(pg_get_serial_sequence('Fornecedor', 'idfornecedor'))");
            ResultSet result = statement.executeQuery(gen.toString());
            result.next();
            
            int id = (int)result.getLong(1);
            Fornecedor output = new Fornecedor(id);
            output.setNome(object.getNome());
            output.setLocalidade(object.getLocalidade());
            output.setTipo(object.getTipo());
            output.setCPF(object.getCPF());
            output.setCNPJ(object.getCNPJ());
            return output;
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public void remove(Fornecedor object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.DELETE, "Fornecedor");
            
            gen.add("idFornecedor",object.getIdentity());
            
            statement.executeUpdate(gen.toString());
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public Fornecedor get(int indent) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.SELECT, "Fornecedor");
            
            gen.add("idFornecedor", indent);
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            if (result.next()){
                String nome = result.getString("nome");
                String localidade = result.getString("localidade");
                String tipo = result.getString("tipo");
                String cpf = result.getString("cpf");
                String cnpj = result.getString("cnpj");
            
                return new Fornecedor(indent, nome, localidade, tipo, cpf, cnpj);
            }
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Fornecedor[] toArray() throws DataCannotBeAccessedException{
        LinkedList<Fornecedor> output = new LinkedList<>();
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.SELECT, "Fornecedor");
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            while (result.next()){
                int id = result.getInt("idFornecedor");
                String nome = result.getString("nome");
                String localidade = result.getString("localidade");
                String tipo = result.getString("tipo");
                String cpf = result.getString("cpf");
                String cnpj = result.getString("cnpj");
            
                output.add(new Fornecedor(id, nome, localidade, tipo, cpf, cnpj));
            }
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
        return (Fornecedor[])output.toArray(new Fornecedor[output.size()]);
    }

    @Override
    public void update(Fornecedor object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.UPDATE, "Fornecedor");
            
            
            gen.add("nome", object.getNome());
            gen.add("localidade", object.getLocalidade());
            gen.add("tipo", object.getTipo());
            gen.add("cpf", object.getCPF());
            gen.add("cnpj", object.getCNPJ());
            
            gen.add("idFornecedor", object.getIdentity());
            
            statement.executeUpdate(gen.toString());
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }
    
}
