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
package com.grr.bdapp.elfoAPI.exception;

/**
 * Representa exceçao geral da ELFO API.
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class ElfoException extends Exception{
    protected String message;

    /**
     * Contrutor de ElfoException
     * @param message Mensagem
     */
    public ElfoException(String message){
        super();
        this.message = message;
    }

    /**
     *  Pega mensagem
     * @return Message
     */
    public String getMessage(){
        return message;
    }

    /**
     * Seta mensagem
     * @param message Messege
     */
    public void setMessage(String message){
        this.message = message;
    }
}
