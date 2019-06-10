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

package com.cristianmg.sqldelight.data.mapper

import com.cristianmg.sqldelight.data.entity.CharacterEntity
import com.cristianmg.sqldelight.data.entity.ComicInfoEntity
import com.cristianmg.sqldelight.data.entity.ThumbnailEntity
import com.cristianmg.sqldelight.data.ext.DateFormat
import com.cristianmg.sqldelight.domain.model.CharacterModel
import com.cristianmg.sqldelight.domain.model.ThumbnailModel

class CharacterMapper : IMapper<CharacterEntity, CharacterModel> {

    override fun mapToEntity(model: CharacterModel): CharacterEntity =
        CharacterEntity(
            model.id,
            model.name,
            DateFormat.toGmtFormat(model.modified),
            ThumbnailEntity(model.thumbnail.path, model.thumbnail.extension),
            model.resourceURI,
            ComicInfoEntity(model.comicsAvailable)
        )

    override fun mapToModel(entity: CharacterEntity): CharacterModel =
        CharacterModel(
            entity.id,
            entity.name,
            DateFormat.toLocaleFormat(entity.modified),
            ThumbnailModel(entity.thumbnail.path, entity.thumbnail.extension),
            entity.resourceURI,
            entity.comics.comicsAvailable
        )


}