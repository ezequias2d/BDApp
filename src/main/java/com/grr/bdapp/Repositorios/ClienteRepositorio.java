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

import com.grr.bdapp.objects.Cliente;
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
 * @author Ezequias
 */
public class ClienteRepositorio implements IRepositorio<Cliente>{

    @Override
    public Cliente add(Cliente object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.INSERT, "Cliente");
            
            gen.addVal("DEFAULT");
            gen.add(object.getNome());
            gen.add(object.getCpf());
            gen.add(object.getPais());
            gen.add(object.getEstado());
            gen.add(object.getCidade());
            gen.add(object.getGarantia().toString());
            gen.add(object.getDataCadastro().toString());
            gen.add(object.getLimiteCredito());
            gen.push();
            
            statement.executeUpdate(gen.toString());
            
            statement = con.createStatement();
            
            gen = new SQLGeneretor(Mode.SELECT, "currval(pg_get_serial_sequence('cliente', 'idcliente'))");
            ResultSet result = statement.executeQuery(gen.toString());
            result.next();
            int id = (int)result.getLong(1);
            
            Cliente output = new Cliente(id);
            output.setCidade(object.getCidade());
            output.setCpf(object.getCpf());
            output.setDataCadastro(object.getDataCadastro());
            output.setEstado(object.getEstado());
            output.setGarantia(object.getGarantia());
            output.setLimiteCredito(object.getLimiteCredito());
            output.setNome(object.getNome());
            output.setPais(object.getPais());
            
            return output;
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public void remove(Cliente object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.DELETE, "Cliente");
            
            gen.add("idCliente",object.getIdentity());
            
            statement.executeUpdate(gen.toString());
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public Cliente get(int indent) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.SELECT, "Cliente");
            
            gen.add("idCliente", indent);
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            if (result.next()){
                Cliente output = new Cliente(indent);
                
                output.setCidade(result.getString("cidade"));
                output.setCpf(result.getString("cpf"));
                output.setDataCadastro(result.getTimestamp("datacadastro"));
                output.setEstado(result.getString("estado"));
                output.setGarantia(result.getTimestamp("garantia"));
                output.setLimiteCredito(result.getString("limitecredito"));
                output.setNome(result.getString("nome"));
                output.setPais(result.getString("pais"));
                return output;
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
    public Cliente[] toArray() throws DataCannotBeAccessedException {
        LinkedList<Cliente> output = new LinkedList<>();
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.SELECT, "Cliente");
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            while (result.next()){
                Cliente cliente = new Cliente(result.getInt("idcliente"));
                
                cliente.setCidade(result.getString("cidade"));
                cliente.setCpf(result.getString("cpf"));
                cliente.setDataCadastro(result.getTimestamp("datacadastro"));
                cliente.setEstado(result.getString("estado"));
                cliente.setGarantia(result.getTimestamp("garantia"));
                cliente.setLimiteCredito(result.getString("limitecredito"));
                cliente.setNome(result.getString("nome"));
                cliente.setPais(result.getString("pais"));
                output.add(cliente);
            }
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
        return output.toArray(new Cliente[output.size()]);
    }

    @Override
    public void update(Cliente object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.UPDATE, "Cliente");
            
            gen.add("nome", object.getNome());
            gen.add("cpf", object.getCpf());
            gen.add("pais", object.getPais());
            gen.add("estado", object.getEstado());
            gen.add("cidade", object.getCidade());
            gen.add("garantia", object.getGarantia().toString());
            gen.add("datacadastro", object.getDataCadastro().toString());
            gen.add("limitecredito", object.getLimiteCredito());
            
            gen.add("idcliente", object.getIdentity());
            
            statement.executeUpdate(gen.toString());
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }
    
    
}
