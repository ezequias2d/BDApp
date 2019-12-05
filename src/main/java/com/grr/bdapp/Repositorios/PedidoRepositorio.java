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
import com.grr.bdapp.objects.Item;
import com.grr.bdapp.objects.Pedido;
import com.grr.bdapp.Repositorios.Tools.BDPostgreSQL;
import com.grr.bdapp.Repositorios.Tools.SQLGeneretor;
import com.grr.bdapp.Repositorios.Tools.Mode;
import com.grr.bdapp.elfoAPI.data.IRepositorio;
import com.grr.bdapp.elfoAPI.exception.data.DataCannotBeAccessedException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author Ezequias
 */
public class PedidoRepositorio implements IRepositorio<Pedido>{

    @Override
    public Pedido add(Pedido object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.INSERT, "pedido");
            
            gen.addVal("DEFAULT");
            gen.add(object.getDataPedido().toString());
            gen.add(object.getModoEncomenda());
            gen.add(object.getStatusPedido());
            gen.add(object.getDataPrazo().toString());
            gen.add(object.getValor());
            gen.add(object.getIDCliente());
            
            gen.push();
            
            statement.executeUpdate(gen.toString());
            
            statement = con.createStatement();
            
            gen = new SQLGeneretor(Mode.SELECT, "currval(pg_get_serial_sequence('pedido', 'idpedido'))");
            ResultSet result = statement.executeQuery(gen.toString());
            result.next();
            int id = (int)result.getLong(1);
            
            Pedido output = new Pedido(id, object.getIDCliente());
            output.setDataPedido(object.getDataPedido());
            output.setDataPrazo(object.getDataPrazo());
            output.setModoEncomenda(object.getModoEncomenda());
            output.setStatusPedido(object.getStatusPedido());
            output.setValor(object.getValor());
            
            gen = new SQLGeneretor(Mode.DELETE, "ItemPedido");
            gen.add("idPedido", id);
            statement = con.createStatement();
            statement.executeUpdate(gen.toString());
            
            gen = new SQLGeneretor(Mode.INSERT, "ItemPedido");
            for (Item item : object.getItems()){
                gen.add(item.getIDPedido());
                gen.add(item.getIDProduto());
                gen.add(item.getQuantidade());
                gen.add(item.getPrecoVenda());
                gen.add(item.getPrecoTotal());
                gen.push();
                output.add(item);
            }
            statement = con.createStatement();
            statement.executeUpdate(gen.toString());
            return output;
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public void remove(Pedido object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.DELETE, "pedido");
            
            gen.add("idpedido", object.getIdentity());
            
            statement.executeUpdate(gen.toString());
                        
            gen = new SQLGeneretor(Mode.DELETE, "ItemPedido");
            gen.add("idPedido", object.getIdentity());
            statement = con.createStatement();
            statement.executeUpdate(gen.toString());
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public Pedido get(int indent) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.SELECT, "pedido");
            
            gen.add("idpedido", indent);
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            if(result.next()){
                int id = result.getInt("idPedido");
                int idCliente = result.getInt("idCliente");
                Pedido output = new Pedido(id, idCliente);
                output.setDataPedido(result.getTimestamp("datapedido"));
                output.setDataPrazo(result.getTimestamp("dataprazo"));
                output.setModoEncomenda(result.getString("modoencomenda"));
                output.setStatusPedido(result.getString("statuspedido"));
                output.setValor(result.getString("valor"));
            
                gen = new SQLGeneretor(Mode.SELECT, "itemPedido");
                gen.add("idpedido", id);
                
                Statement statementItem = con.createStatement();
                ResultSet resultItem = statementItem.executeQuery(gen.toString());
                
                while(resultItem.next()){
                    Item item = new Item(id, resultItem.getInt("idproduto"));
                    item.setPrecoTotal(resultItem.getString("precototal"));
                    item.setPrecoVenda(resultItem.getString("precovenda"));
                    item.setQuantidade(resultItem.getInt("quantidade"));
                    output.add(item);
                }
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
    public Pedido[] toArray() throws DataCannotBeAccessedException {
        LinkedList<Pedido> output = new LinkedList<>();
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.SELECT, "pedido");
            
            ResultSet result = statement.executeQuery(gen.toString());
            
            statement.getMoreResults(Statement.KEEP_CURRENT_RESULT);
            
            while(result.next()){
                int id = result.getInt("idPedido");
                int idCliente = result.getInt("idCliente");
                
                Pedido pedido = new Pedido(id, idCliente);
                pedido.setDataPedido(result.getTimestamp("datapedido"));
                pedido.setDataPrazo(result.getTimestamp("dataprazo"));
                pedido.setModoEncomenda(result.getString("modoencomenda"));
                pedido.setStatusPedido(result.getString("statuspedido"));
                pedido.setValor(result.getString("valor"));
            
                gen = new SQLGeneretor(Mode.SELECT, "itemPedido");
                gen.add("idpedido", id);
                
                Statement statementItem = con.createStatement();
                ResultSet resultItem = statementItem.executeQuery(gen.toString());
                
                while(resultItem.next()){
                    if(id == 6){
                        System.out.println("Item");
                    }
                    Item item = new Item(id, resultItem.getInt("idproduto"));
                    item.setPrecoTotal(resultItem.getString("precototal"));
                    item.setPrecoVenda(resultItem.getString("precovenda"));
                    item.setQuantidade(resultItem.getInt("quantidade"));
                    pedido.add(item);
                }
                output.add(pedido);
            }
            statement.close();
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
        return output.toArray(new Pedido[output.size()]);
    }

    @Override
    public void update(Pedido object) throws DataCannotBeAccessedException {
        Connection con;
        try {
            con = BDPostgreSQL.getConnection();
            
            Statement statement = con.createStatement();
            
            SQLGeneretor gen = new SQLGeneretor(Mode.UPDATE, "pedido");
            
            gen.add("dataPedido", object.getDataPedido().toString());
            gen.add("modoEncomenda", object.getModoEncomenda());
            gen.add("statusPedido", object.getStatusPedido());
            gen.add("dataPrazo", object.getDataPrazo().toString());
            gen.add("valor", object.getValor());
            gen.add("idcliente", object.getIDCliente());
            
            gen.add("idpedido", object.getIdentity());
            
            statement.executeUpdate(gen.toString());
            
            statement = con.createStatement();
            
            Pedido output = new Pedido(object.getIdentity(), object.getIDCliente());
            output.setDataPedido(object.getDataPedido());
            output.setDataPrazo(object.getDataPrazo());
            output.setModoEncomenda(object.getModoEncomenda());
            output.setStatusPedido(object.getStatusPedido());
            output.setValor(object.getValor());
            
            gen = new SQLGeneretor(Mode.DELETE, "ItemPedido");
            gen.add("idPedido", object.getIdentity());
            statement = con.createStatement();
            statement.executeUpdate(gen.toString());
            
            gen = new SQLGeneretor(Mode.INSERT, "ItemPedido");
            for (Item item : object.getItems()){
                gen.add(item.getIDPedido());
                gen.add(item.getIDProduto());
                gen.add(item.getQuantidade());
                gen.add(item.getPrecoVenda());
                gen.add(item.getPrecoTotal());
                gen.push();
                output.add(item);
            }
            statement = con.createStatement();
            statement.executeUpdate(gen.toString());
            
        } catch (ClassNotFoundException ex) {
            throw new DataCannotBeAccessedException("Classe de conexão com o banco de dados não encontrada.");
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
            throw new DataCannotBeAccessedException("SQL:\n" + ex.getLocalizedMessage());
        }
    }

    
    
}
