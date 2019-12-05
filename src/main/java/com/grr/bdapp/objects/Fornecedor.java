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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ezequias
 */
public final class Fornecedor implements IIdentificable {
    private int _ID;
    private String _nome;
    private String _localidade;
    private String _tipo;
    private String _cpf;
    private String _cnpj;
    
    private Map<Integer, Produto> produtos;
    
    public Fornecedor(int ID){
        setID(ID);
        setNome("");
        setLocalidade("");
        setTipo("");
        setCPF("");
        setCNPJ("");
        produtos = new HashMap<>();
    }
    
    public Fornecedor(int ID, String nome, String localidade, String tipo, String cpf, String cnpj){
        setID(ID);
        setNome(nome);
        setLocalidade(localidade);
        setTipo(tipo);
        setCPF(cpf);
        setCNPJ(cnpj);
        produtos = new HashMap<>();
    }
    
    public boolean Add(Produto produto)
    {
        if (produto.getIDFornecedor() == getIdentity()){
            produtos.put(produto.getIdentity(), produto);
            return true;
        }
        return false;
    }
    
    public void Remove(int id){
        produtos.remove(id);
    }
    
    public Produto get(int id){
        return produtos.get(id);
    }
    
    private void setID(int ID){
        _ID = ID;
    }
    
    public String getNome(){
        return _nome;
    }
    
    public void setNome(String nome){
        _nome = nome;
    }
    
    public String getLocalidade(){
     return _localidade;   
    }
    
    public void setLocalidade(String localidade){
        _localidade = localidade;
    }
    
    public String getTipo(){
        return _tipo;
    }
    
    public void setTipo(String tipo){
        _tipo = tipo;
    }
    
    public String getCPF(){
        return _cpf;
    }
    
    public void setCPF(String cpf){
        _cpf = cpf;
    }
    
    public String getCNPJ(){
        return _cnpj;
    }
    
    public void setCNPJ(String cnpj){
        _cnpj = cnpj;
    }

    @Override
    public int getIdentity() {
        return _ID;
    }
    
}
