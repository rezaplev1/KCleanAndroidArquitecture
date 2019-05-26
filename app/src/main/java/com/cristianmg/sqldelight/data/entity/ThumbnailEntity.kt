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

package com.cristianmg.sqldelight.data.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName


/*@Entity(
    tableName = "thumbnail",
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["id"],
        childColumns = ["id"],
        onDelete = CASCADE
    )],
    indices = [Index("id")]
)*/
data class ThumbnailEntity(

    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String
)