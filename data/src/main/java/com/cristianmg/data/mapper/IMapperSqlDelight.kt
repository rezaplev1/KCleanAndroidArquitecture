package com.cristianmg.data.mapper

import java.lang.IllegalStateException


interface IMapperSqlDelight<Entity, Model>:IMapper<Entity,Model> {


    /**
     * Not is a good practices create a entity of sqldelight
     * **/
    override fun mapListToEntity(entities: List<Model>): List<Entity> {
        throw IllegalStateException("Not is a good practices create a entity of sqldelight")
    }

    /**
     * Not is a good practices create a entity of sqldelight
     * **/
    override fun mapToEntity(model: Model): Entity{
        throw IllegalStateException("Not is a good practices create a entity of sqldelight")
    }


}
