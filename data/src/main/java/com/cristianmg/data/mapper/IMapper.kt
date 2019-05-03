package com.cristianmg.data.mapper


interface IMapper<Entity, Model> {

    fun mapToModel(entity: Entity): Model
    fun mapToEntity(model: Model): Entity

    fun mapListToEntity(entities: List<Model>): List<Entity> {
        val list = mutableListOf<Entity>()
        entities.mapTo(list) { mapToEntity(it) }
        return list
    }


    fun mapListToModel(entities: List<Entity>): List<Model> {
        val list = mutableListOf<Model>()
        entities.mapTo(list) { mapToModel(it) }
        return list
    }

}
