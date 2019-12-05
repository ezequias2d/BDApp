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

import com.grr.bdapp.Repositorios.Tools.SQLGeneretor;
import com.grr.bdapp.Repositorios.Tools.Mode;
import com.grr.bdapp.Repositorios.Tools.BDPostgreSQL;
import com.grr.bdapp.objects.Produto;
import com.grr.bdapp.elfoAPI.data.IRepositorio;
import com.grr.bdapp.elfoAPI.exception.data.DataCannotBeAccessedException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;

/**
 *
 * @author ezequ
 */
public class ProdutoRepositorio implements IRepositorio<Produto>{
    
    @Override
    public Produto add(Produto object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.INSERT, "Produto");
            
            gen.addVal("DEFAULT");
            gen.add(object.getStatus());
            gen.add(object.getPreco());
            gen.add(object.getPrecoMinimo());
            gen.add(object.getGarantia().toString());
            gen.add(object.getIDFornecedor());
            
            gen.push();
            
            statement.executeUpdate(gen.toString());
            
            statement = con.createStatement();
            
            gen = new SQLGeneretor(Mode.SELECT, "currval(pg_get_serial_sequence('Produto', 'idproduto'))");
            ResultSet result = statement.executeQuery(gen.toString());
            result.next();
            int id = (int)result.getLong(1);
            
            Produto output = new Produto(id, object.getIDFornecedor());
            output.setStatus(object.getStatus());
            output.setPreco(object.getPreco());
            output.setPrecoMinimo(object.getPrecoMinimo());
            output.setGarantia(object.getGarantia());
            
            for (String nome : object.getNomes()){
                statement = con.createStatement();
                gen = new SQLGeneretor(Mode.INSERT, "nomeproduto");
                gen.add(id);
                gen.add(nome);    
                gen.push();
                
                statement.executeUpdate(gen.toString());
                output.addNome(nome);
            }
            
            for (String descricao : object.getDescricoes()){
                statement = con.createStatement();
                gen = new SQLGeneretor(Mode.INSERT, "descricaoproduto");
                gen.add(id);
                gen.add(descricao);    
                gen.push();
                
                statement.executeUpdate(gen.toString());
                output.addDescricao(descricao);
            }
            return output;
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public void remove(Produto object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.DELETE, "Produto");
            
            gen.add("idProduto", object.getIdentity());
            
            statement.executeUpdate(gen.toString());
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public Produto get(int indent) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.SELECT, "Produto");
            
            gen.add("idProduto", indent);
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            if(result.next()){
                String status = result.getString("status");
                String preco = result.getString("preco");
                String precoMinimo = result.getString("precominimo");
                Timestamp garantia = result.getTimestamp("garantia");
                int idFornecedor = result.getInt("idFornecedor");
                
                Produto produto = new Produto(indent, idFornecedor);
                produto.setStatus(status);
                produto.setPreco(preco);
                produto.setPrecoMinimo(precoMinimo);
                produto.setGarantia(garantia);
                
                statement = con.createStatement();
                gen = new SQLGeneretor(Mode.SELECT, "nomeproduto");
                gen.add("idproduto", indent);
                
                result = statement.executeQuery(gen.toString());
                
                while(result.next()){
                    produto.addNome(result.getString("nome"));
                }
                
                statement = con.createStatement();
                gen = new SQLGeneretor(Mode.SELECT, "descricaoproduto");
                gen.add("idproduto", indent);
                
                result = statement.executeQuery(gen.toString());
                
                while(result.next()){
                    produto.addNome(result.getString("descricao"));
                }
                
                return produto;
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
    public Produto[] toArray() throws DataCannotBeAccessedException {
        LinkedList<Produto> output = new LinkedList();
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.SELECT, "Produto");            
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            statement.getMoreResults(Statement.KEEP_CURRENT_RESULT);
            while(result.next()){
                int id = result.getInt("idProduto");
                String status = result.getString("status");
                String preco = result.getString("preco");
                String precoMinimo = result.getString("precominimo");
                Timestamp garantia = result.getTimestamp("garantia");
                int idFornecedor = result.getInt("idFornecedor");
                
                Produto produto = new Produto(id, idFornecedor);
                produto.setStatus(status);
                produto.setPreco(preco);
                produto.setPrecoMinimo(precoMinimo);
                produto.setGarantia(garantia);
                
                Statement statementNome = con.createStatement();
                gen = new SQLGeneretor(Mode.SELECT, "nomeproduto");
                gen.add("idproduto", id);
                
                ResultSet resultNome = statementNome.executeQuery(gen.toString());
                
                while(resultNome.next()){
                    produto.addNome(resultNome.getString("nome"));
                }
                
                Statement statementDescricao = con.createStatement();
                gen = new SQLGeneretor(Mode.SELECT, "descricaoproduto");
                gen.add("idproduto", id);
                
                ResultSet resultDescricao = statementDescricao.executeQuery(gen.toString());
                
                while(resultDescricao.next()){
                    produto.addDescricao(resultDescricao.getString("descricao"));
                }
                output.add(produto);
            }
            statement.close();
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
        return output.toArray(new Produto[output.size()]);
    }

    @Override
    public void update(Produto object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.UPDATE, "Produto");
            
            gen.add("status", object.getStatus());
            gen.add("preco", object.getPreco());
            gen.add("precominimo", object.getPrecoMinimo());
            gen.add("garantia", object.getGarantia().toString());
            gen.add("idFornecedor", object.getIDFornecedor());
            
            gen.add("idProduto", object.getIdentity());
            
            statement.executeUpdate(gen.toString());
            
            statement = con.createStatement();
            gen = new SQLGeneretor(Mode.DELETE, "nomeproduto");
            statement.executeUpdate(gen.toString());
            
            for (String nome : object.getNomes()){
                statement = con.createStatement();
                gen = new SQLGeneretor(Mode.INSERT, "nomeproduto");
                gen.add(object.getIdentity());
                gen.add(nome);    
                gen.push();
                
                statement.executeUpdate(gen.toString());
            }
            
            statement = con.createStatement();
            gen = new SQLGeneretor(Mode.DELETE, "descricaoproduto");
            statement.executeUpdate(gen.toString());
            
            for (String descricao : object.getDescricoes()){
                statement = con.createStatement();
                gen = new SQLGeneretor(Mode.INSERT, "descricaoproduto");
                gen.add(object.getIdentity());
                gen.add(descricao);    
                gen.push();
                statement.executeUpdate(gen.toString());
            }
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }
    
}
