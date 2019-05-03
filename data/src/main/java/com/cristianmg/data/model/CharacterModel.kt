package com.cristianmg.data.model

import java.util.*

data class CharacterModel(
    val id: Long,
    val name: String,
    val modified: Calendar,
    val resourceUri:String
)