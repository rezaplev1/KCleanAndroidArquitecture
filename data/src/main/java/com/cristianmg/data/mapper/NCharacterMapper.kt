/*
 * Copyright 2019 Cristian Menárguez González
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cristianmg.data.mapper

import com.cristianmg.data.entity.NCharacterEntity
import com.cristianmg.data.ext.DateFormat
import com.cristianmg.data.model.CharacterModel

class NCharacterMapper : IMapper<NCharacterEntity, CharacterModel> {

    override fun mapToEntity(model: CharacterModel): NCharacterEntity =
        NCharacterEntity(model.id,model.name,DateFormat.toNetworkFormat(model.modified),model.resourceUri)

    override fun mapToModel(entity: NCharacterEntity): CharacterModel
            = CharacterModel(entity.id,entity.name,DateFormat.fromNetworkFormat(entity.modified),entity.resourceURI)


}