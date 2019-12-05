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

/**
 *
 * @author Ezequias
 */
public class Cliente implements  IIdentificable{

    private int _id;
    private String _nome;
    private String _cpf;
    private String _pais;
    private String _estado;
    private String _cidade;
    private Timestamp _garantia;
    private Timestamp  _dataCadastro;
    private String _limiteCredito;
    
    public Cliente(int id){
        _id = id;
        _nome = "";
        _cpf = "";
        _estado = "";
        _cidade = "";
        Date date = new Date();
        _garantia =  new Timestamp(date.getTime());
        _dataCadastro = new Timestamp(date.getTime());
        _limiteCredito = "0";
    }
    
    @Override
    public int getIdentity() {
        return _id;
    }
    
    public String getNome() {
        return _nome;
    }

    public void setNome(String _nome) {
        this._nome = _nome;
    }

    public String getCpf() {
        return _cpf;
    }

    public void setCpf(String _cpf) {
        this._cpf = _cpf;
    }

    public String getPais() {
        return _pais;
    }

    public void setPais(String _pais) {
        this._pais = _pais;
    }

    public String getEstado() {
        return _estado;
    }

    public void setEstado(String _estado) {
        this._estado = _estado;
    }

    public String getCidade() {
        return _cidade;
    }

    public void setCidade(String _cidade) {
        this._cidade = _cidade;
    }

    public Timestamp getGarantia() {
        return _garantia;
    }

    public void setGarantia(Timestamp _garantia) {
        this._garantia = _garantia;
    }

    public Timestamp getDataCadastro() {
        return _dataCadastro;
    }

    public void setDataCadastro(Timestamp _dataCadastro) {
        this._dataCadastro = _dataCadastro;
    }

    public String getLimiteCredito() {
        return _limiteCredito;
    }

    public void setLimiteCredito(String _limiteCredito) {
        this._limiteCredito = _limiteCredito;
    }
}
