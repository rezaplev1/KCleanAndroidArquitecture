package com.cristianmg.data.mapper

import com.cristianmg.data.entity.NCharacterEntity
import com.cristianmg.data.ext.DateFormat
import com.cristianmg.data.model.CharacterModel

class NCharacterMapper : IMapper<NCharacterEntity, CharacterModel> {

    override fun mapToEntity(model: CharacterModel): NCharacterEntity =
        NCharacterEntity(model.id,model.name,DateFormat.toNetworkFormat(model.modified),model.resourceUri)

    override fun mapToModel(entity: NCharacterEntity): CharacterModel
            = CharacterModel(entity.id,entity.name,DateFormat.fromDatabaseFormat(entity.modified),entity.resourceURI)


}