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

/**
 *
 * @author Ezequias
 */
public final class Estoque implements IIdentificable {
    private int _ID;
    private int _codigo;
    private int _quantidade;
    private Produto _produto;
    private int _IDArmazem;
    
    public Estoque(){
        setID(-1);
        setCodigo(0);
        setQuantidade(0);
        setProduto(new Produto(0, 0));
        setIDArmazem(0);
    }
    
    public Estoque(int ID, int codigo, int quantidade, Produto produto, int IDArmazem){
        setID(ID);
        setCodigo(codigo);
        setQuantidade(quantidade);
        setProduto(produto);
        setIDArmazem(IDArmazem);
    }
    
    private void setID(int ID){
        _ID = ID;
    }
   
    public void setCodigo(int codigo){
        _codigo = codigo;
    }
    
    public int getCodigo(){
        return _codigo;
    }
    
    public void setQuantidade(int quantidade){
        _quantidade = quantidade;
    }
    
    public int getQuantidade(){
        return _quantidade;
    }
    
    public void setProduto(Produto produto){
        _produto = produto;
    }
    
    public Produto getProduto(){
        return _produto;
    }
    
    public void setIDArmazem(int idarmazem){
        _IDArmazem = idarmazem;
    }
    
    public int getIDArmazem(){
        return _IDArmazem;
    }

    @Override
    public int getIdentity() {
        return _ID;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Estoque){
            Estoque objE = (Estoque)obj;
            return _ID == objE.getIdentity();
        }
        return false;
    }
}
