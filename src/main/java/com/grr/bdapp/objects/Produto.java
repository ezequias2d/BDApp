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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Ezequias
 */
public class Produto implements Cloneable, IIdentificable {
    private int _ID;
    
    private String _status;
    private String _preco;
    private String _precoMinimo;
    private Timestamp _garantia;
    private int _idFornecedor;
    private Collection<String> _nomes;
    private Collection<String> _descricoes;
    
    public Produto(int ID, int IDFornecedor){
        setID(ID);
        setIDFornecedor(IDFornecedor);
        _nomes = new ArrayList<>();
        _descricoes = new ArrayList<>();
        _status = "";
        _preco = "0,00";
        _precoMinimo = "0,00";
        Date date = new Date();
        _garantia =  new Timestamp(date.getTime());
    }
    
    public void setNomes(Collection<String> nomes){
        _nomes = nomes;
    }
    
    public void setDescricoes(Collection<String> descricoes){
        _descricoes = descricoes;
    }
    
    public void addNome(String nome){
        _nomes.add(nome);
    }
    
    public void addDescricao(String descricao){
        _descricoes.add(descricao);
    }
    
    public void removeNome(String nome){
        _nomes.remove(nome);
    }
    
    public String[] getNomes(){
        return _nomes.toArray(new String[_nomes.size()]);
    }
    
    public String[] getDescricoes(){
        return _descricoes.toArray(new String[_descricoes.size()]);
    }
    
    public void removeDescricao(String descricao){
        _descricoes.remove(descricao);
    }
    
    private void setID(int ID){
        _ID = ID;
    }
    
    public void setIDFornecedor(int id){
        _idFornecedor = id;
    }
    
    public int getIDFornecedor(){
        return _idFornecedor;
    }
    
    public String getStatus(){
        return _status;
    }
    
    public void setStatus(String status){
        _status = status;
    }
    
    public String getPreco(){
        return _preco;
    }
    
    public void setPreco(String preco){
        _preco = preco;
    }
    
    public String getPrecoMinimo(){
        return _precoMinimo;
    }
    
    public void setPrecoMinimo(String precoMinimo){
        _precoMinimo = precoMinimo;
    }
    
    public Timestamp getGarantia(){
        return _garantia;
    }
    
    public void setGarantia(Timestamp garantia){
        _garantia = garantia;
    }

    @Override
    public int getIdentity() {
        return _ID;
    }
}
