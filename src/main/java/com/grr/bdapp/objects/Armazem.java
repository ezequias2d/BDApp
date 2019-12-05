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
public class Armazem implements IIdentificable{
    
    private int _ID;
    private String _nome;
    private int _tamanho;
    private String _endereco;
    
    private Map<Integer, Estoque> _estoques;
    
    public Armazem(int ID){
        setID(ID);
        _estoques = new HashMap<>();
    }
    
    public boolean add(Estoque estoque){
        if (estoque.getIDArmazem() == getIdentity()){
            _estoques.put(estoque.getIdentity(), estoque);
            return true;
        }
        return false;
    }
    
    public void remove(Estoque estoque){
        _estoques.remove(estoque.getIdentity());
    }
    
    public Estoque[] getEstoque(){
        return _estoques.values().toArray(new Estoque[_estoques.size()]);
    }
    
    private void setID(int id){
        _ID = id;
    }
    
    public String getNome(){
        return _nome;
    }
    
    public void setNome(String nome){
        _nome = nome;
    }
    
    public int getTamanho(){
        return _tamanho;
    }
    
    public void setTamanho(int tamanho){
        _tamanho = tamanho;
    }
    
    public String getEndereco(){
        return _endereco;
    }
    
    public void setEndereco(String endereco){
        _endereco = endereco;
    }
    
    public int getIdentity() {
        return _ID;
    }
    
    
}
