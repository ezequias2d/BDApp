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
package com.grr.bdapp.objects;

import com.grr.bdapp.elfoAPI.data.IIdentificable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Ezequias
 */
public class Pedido implements IIdentificable {
    private int _ID;
    private Timestamp _dataPedido;
    private String _modoEncomenda;
    private String _statusPedido;
    private Timestamp _dataPrazo;
    private String _valor;
    private int _idcliente;
    
    private LinkedList<Item> _items;
    
    public Pedido(int id, int idCliente){
        setID(id);
        setIDCliente(idCliente);
        Date date = new Date();
        _dataPedido =  new Timestamp(date.getTime());
        _dataPrazo =  new Timestamp(date.getTime());
        _modoEncomenda = "";
        _statusPedido = "";
        _valor = "0,00";
    }
    
    private void setID(int id){
        _ID = id;
        _items = new LinkedList<>();
    }
    
    public void add(Item item){
        for (Item i : _items){
            if (i.getIDProduto() == item.getIDProduto()){
                _items.remove(i);
                break;
            }
        }
        _items.add(item);
    }
    
    public void remove(Item item){
        _items.remove(item);
    }
    
    public Item[] getItems(){
        return _items.toArray(new Item[_items.size()]);
    }
    
    public void setIDCliente(int id){
        _idcliente = id;
    }
    
    public int getIDCliente(){
        return _idcliente;
    }
    
    public void setDataPedido(Timestamp dataPedido){
        _dataPedido = dataPedido;
    }
    
    public Timestamp getDataPedido(){
        return _dataPedido;
    }
    
    public void setModoEncomenda(String modoEncomenda){
        _modoEncomenda = modoEncomenda;
    }
    
    public String getModoEncomenda(){
        return _modoEncomenda;
    }
    
    public void setStatusPedido(String statusPedido){
        _statusPedido = statusPedido;
    }
    
    public String getStatusPedido(){
        return _statusPedido;
    }
    
    public void setDataPrazo(Timestamp dataPrazo){
        _dataPrazo = dataPrazo;
    }
    
    public Timestamp getDataPrazo(){
        return _dataPrazo;
    }
    
    public void setValor(String valor){
        _valor = valor;
    }
    
    public String getValor(){
        return _valor;
    }

    @Override
    public int getIdentity() {
        return _ID;
    }
}
