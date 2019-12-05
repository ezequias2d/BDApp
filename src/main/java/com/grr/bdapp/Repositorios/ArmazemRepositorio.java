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
import com.grr.bdapp.objects.Armazem;
import com.grr.bdapp.objects.Estoque;
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
public class ArmazemRepositorio implements IRepositorio<Armazem> {

    @Override
    public Armazem add(Armazem object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.INSERT, "Armazem");
            
            gen.addVal("DEFAULT");
            gen.add(object.getNome());
            gen.add(object.getTamanho());
            gen.add(object.getEndereco());
            gen.push();
            
            statement.executeUpdate(gen.toString());
            
            gen = new SQLGeneretor(Mode.SELECT, "currval(pg_get_serial_sequence('armazem', 'idarmazem'))");
            statement = con.createStatement();
            
            ResultSet result = statement.executeQuery(gen.toString());
            result.next();
            long id = result.getLong(1);
            Armazem output = new Armazem((int)id);
            
            gen = new SQLGeneretor(Mode.DELETE, "estoque");
            gen.add("idArmazem", (int)id);
            statement = con.createStatement();
            statement.executeUpdate(gen.toString());
            
            if (object.getEstoque().length > 0){
                gen = new SQLGeneretor(Mode.INSERT, "estoque");
            
                for(Estoque estoque : object.getEstoque()){
                    gen.add(estoque.getIdentity());
                    gen.add(estoque.getCodigo());
                    gen.add(estoque.getQuantidade());
                    gen.add(estoque.getProduto().getIdentity());
                    gen.add(estoque.getIDArmazem());
                    gen.push();
                    output.add(estoque);
                }
                statement = con.createStatement();
                statement.executeUpdate(gen.toString());
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
    public void remove(Armazem object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.DELETE, "Armazem");
            
            gen.add("idArmazem",object.getID());
            
            statement.executeUpdate(gen.toString());
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public Armazem get(int indent) throws DataCannotBeAccessedException {
        Connection con;
        SQLGeneretor gen = null;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            gen  = new SQLGeneretor(Mode.SELECT, "armazem");
            
            gen.add("idArmazem", indent);
            gen.push();
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            if (result.next()){
                String nome = result.getString("nome");
                int tamanho = result.getInt("tamanho");
                String endereco = result.getString("endereco");
            
                Armazem armazem = new Armazem(indent);
            
                armazem.setNome(nome);
                armazem.setTamanho(tamanho);
                armazem.setEndereco(endereco);
            
                statement = con.createStatement();
            
                gen = new SQLGeneretor(Mode.SELECT, "estoque");
                gen.add("idArmazem", indent);
            
                result = statement.executeQuery(gen.toString());
                
                //mantem o result aberto
                statement.getMoreResults(Statement.KEEP_CURRENT_RESULT);
            
                while(result.next()){
                    int idEstoque = result.getInt("idEstoque");
                    int codigo = result.getInt("codigo");
                    int quantidade = result.getInt("quantidade");
                    int idProduto = result.getInt("idProduto");
                
                    Statement statementProduto = con.createStatement();
                    gen = new SQLGeneretor(Mode.SELECT, "produto");
                    gen.add("idproduto", idProduto);
                
                    ResultSet resultProduto = statementProduto.executeQuery(gen.toString());
                    
                    resultProduto.next();
                
                    String status = resultProduto.getString("status");
                    String preco = resultProduto.getString("preco");
                    String precoMinimo = resultProduto.getString("precominimo");
                    Timestamp garantia = resultProduto.getTimestamp("garantia");
                    int idFornecedor = resultProduto.getInt("idFornecedor");
                
                    Produto produto = new Produto(indent, idFornecedor);
                    produto.setStatus(status);
                    produto.setPreco(preco);
                    produto.setPrecoMinimo(precoMinimo);
                    produto.setGarantia(garantia);
                
                    Estoque estoque = new Estoque(idEstoque, codigo, quantidade, produto, indent);
                    armazem.add(estoque);
                }
            
                statement.close();
                return armazem;
            }

        } catch (SQLException ex) {
            if(gen != null)
                throw new DataCannotBeAccessedException("SQL:" + gen.toString() + "\n" + ex.getLocalizedMessage());
            else
                throw new DataCannotBeAccessedException("SQL: \n" + ex.getLocalizedMessage());
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        }
        return null;
    }

    @Override
    public Armazem[] toArray() throws DataCannotBeAccessedException {
        LinkedList<Armazem> output = new LinkedList<>();
        Connection con;
        SQLGeneretor gen = null;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            gen = new SQLGeneretor(Mode.SELECT, "armazem");
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            statement.getMoreResults(Statement.KEEP_CURRENT_RESULT);
            while(result.next()){
                int id = result.getInt("idArmazem");
                String nome = result.getString("nome");
                int tamanho = result.getInt("tamanho");
                String endereco = result.getString("endereco");
                
                Armazem armazem = new Armazem(id);
                armazem.setNome(nome);
                armazem.setTamanho(tamanho);
                armazem.setEndereco(endereco);
                
                Statement statementEstoque = con.createStatement();
                gen = new SQLGeneretor(Mode.SELECT, "estoque");
                gen.add("idArmazem", id);
                
                ResultSet resultEstoque = statementEstoque.executeQuery(gen.toString());
                statementEstoque.getMoreResults(Statement.KEEP_CURRENT_RESULT);
                
                while(resultEstoque.next()){
                    int idEstoque = resultEstoque.getInt("idestoque");
                    int codigo = resultEstoque.getInt("codigo");
                    int quantidade = resultEstoque.getInt("quantidade");
                    int idProduto = resultEstoque.getInt("idProduto");
                
                    Statement statementProduto = con.createStatement();
                    gen = new SQLGeneretor(Mode.SELECT, "produto");
                    gen.add("idproduto", idProduto);
                
                    ResultSet resultProduto = statementProduto.executeQuery(gen.toString());
                    resultProduto.next();
                
                    String status = resultProduto.getString("status");
                    String preco = resultProduto.getString("preco");
                    String precoMinimo = resultProduto.getString("precominimo");
                    Timestamp garantia = resultProduto.getTimestamp("garantia");
                    int idFornecedor = resultProduto.getInt("idFornecedor");
                
                    Produto produto = new Produto(idProduto, idFornecedor);
                    produto.setStatus(status);
                    produto.setPreco(preco);
                    produto.setPrecoMinimo(precoMinimo);
                    produto.setGarantia(garantia);
                
                    Estoque estoque = new Estoque(idEstoque, codigo, quantidade, produto, id);
                    armazem.add(estoque);
                }
                statementEstoque.close();
                output.add(armazem);
            }
            statement.close();
        } catch (SQLException ex) {
            if (gen != null)
                throw new DataCannotBeAccessedException("SQL:" + gen.toString() + "\n" + ex.getLocalizedMessage());
            else
                throw new DataCannotBeAccessedException("SQL: \n" + ex.getLocalizedMessage());
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        }
        return output.toArray(new Armazem[output.size()]);
    }

    @Override
    public void update(Armazem object) throws DataCannotBeAccessedException {
        Connection con;
        SQLGeneretor gen = null;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            gen = new SQLGeneretor(Mode.UPDATE, "Armazem");
            
            gen.add("idArmazem", object.getIdentity());
            gen.add("nome", object.getNome());
            gen.add("tamanho", object.getTamanho());
            gen.add("endereco", object.getEndereco());
            
            gen.add("idArmazem", object.getIdentity());
            
            statement.executeUpdate(gen.toString());
            
            gen = new SQLGeneretor(Mode.DELETE, "estoque");
            gen.add("idArmazem", object.getIdentity());
            statement = con.createStatement();
            statement.executeUpdate(gen.toString());
            
            if (object.getEstoque().length > 0){
                gen = new SQLGeneretor(Mode.INSERT, "estoque");
            
                for(Estoque estoque : object.getEstoque()){
                    if (estoque.getIdentity() == -1){
                        //não possui id gerado ainda
                        gen.addVal("DEFAULT");
                    } else {
                        //ja possui id gerado
                        gen.add(estoque.getIdentity());
                    }
                    gen.add(estoque.getCodigo());
                    gen.add(estoque.getQuantidade());
                    gen.add(estoque.getProduto().getIdentity());
                    gen.add(estoque.getIDArmazem());
                    gen.push();
                }
                statement = con.createStatement();
                statement.executeUpdate(gen.toString());
            }            
        } catch (ClassNotFoundException ex) {
            if (gen != null)
                throw new DataCannotBeAccessedException("SQL:" + gen.toString() + "\n" + ex.getLocalizedMessage());
            else
                throw new DataCannotBeAccessedException("SQL: \n" + ex.getLocalizedMessage());
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }
    
}
