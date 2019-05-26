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

package com.cristianmg.sqldelight.domain.model


data class ThumbnailModel(
    val path: String,
    val extension: String
) {


    /* Portrait mode size 50 x 75*/
    fun getPortraitSmall(): String = getImage("portrait_small")

    /* Portrait mode size 100 x 150*/
    fun getPortraitMedium(): String = getImage("portrait_medium")

    /* Portrait mode size 150 x 225*/
    fun getPortraitXLarge(): String = getImage("portrait_xlarge")


    /* Portrait mode size 168 x 252*/
    fun getPortraitFantastic(): String = getImage("portrait_fantastic")

    /* Portrait mode size 300 x450 */
    fun getPortraitUncanny(): String = getImage("portrait_uncanny")


    /* Portrait mode size 216 x 324*/
    fun getPortraitIncredible(): String = getImage("portrait_incredible")


    private fun getImage(size: String): String {
        return path.plus("/").plus(size).plus(".").plus(extension)
    }

}