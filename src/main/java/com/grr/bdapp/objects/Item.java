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

import javafx.util.Pair;

/**
 *
 * @author Ezequias
 */
public class Item {
    private int _idproduto;
    private int _idpedido;
    private int _quantidade;
    private String precoVenda;
    private String precoTotal;
    
    public Item(int idProduto, int idPedido){
        setIDProduto(idProduto);
        setIDPedido(idPedido);
    }
    
    public void setIDProduto(int id){
        _idproduto = id;
    }
    
    public int getIDProduto(){
        return _idproduto;
    }
    
    public void setIDPedido(int id) {
       _idpedido = id;
    }
    
    public int getIDPedido(){
        return _idpedido;
    }

    public int getQuantidade() {
        return _quantidade;
    }

    public void setQuantidade(int _quantidade) {
        this._quantidade = _quantidade;
    }

    public String getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(String precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(String precoTotal) {
        this.precoTotal = precoTotal;
    }
    
    public boolean equals(Object obj){
        if (obj instanceof Item){
            Item objI = (Item)obj;
            return objI.getIDPedido() == _idpedido  && objI.getIDProduto() == _idproduto;
        }
        return false;
    }
    
    
    /**
     * @return (IDProduto, IDPedido)
     */
    public Pair<Integer, Integer> getID(){
        return new Pair<Integer, Integer>(getIDProduto(), getIDPedido());
    }
}
