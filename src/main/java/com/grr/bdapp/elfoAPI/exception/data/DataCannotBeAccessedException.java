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
package com.grr.bdapp.elfoAPI.exception.data;

import com.grr.bdapp.elfoAPI.exception.ElfoException;


/**
 * Representa exceçao lansada quando 'Serializer' nao consegue acessar um arquivo requisitado
 * por Serializer.save ou Serializer.open.
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class DataCannotBeAccessedException extends ElfoException {
    public DataCannotBeAccessedException(String ioMenseger) {
        super(ioMenseger);
    }
}
