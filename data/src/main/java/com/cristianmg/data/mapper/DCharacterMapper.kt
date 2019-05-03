package com.cristianmg.data.mapper

import com.cristianmg.data.DCharacterEntity
import com.cristianmg.data.ext.DateFormat
import com.cristianmg.data.model.CharacterModel


class DCharacterMapper : IMapperSqlDelight<DCharacterEntity, CharacterModel> {

    override fun mapToModel(entity: DCharacterEntity): CharacterModel
            = CharacterModel(entity.id,entity.name,DateFormat.fromDatabaseFormat(entity.modified),entity.resourceURI)


}