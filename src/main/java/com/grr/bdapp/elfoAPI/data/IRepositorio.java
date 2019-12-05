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
package com.grr.bdapp.elfoAPI.data;

import com.grr.bdapp.elfoAPI.exception.data.DataCannotBeAccessedException;

/**
 * Interface de repositorio generico
 * @param <T> IIdentificable
 */
public interface IRepositorio<T extends IIdentificable> {

    public abstract T add(T object) throws DataCannotBeAccessedException;
    public abstract void remove(T object) throws DataCannotBeAccessedException;
    public abstract T get(int indent) throws DataCannotBeAccessedException;
    public abstract T[] toArray() throws DataCannotBeAccessedException; 
    public abstract void update(T object) throws DataCannotBeAccessedException;
}
