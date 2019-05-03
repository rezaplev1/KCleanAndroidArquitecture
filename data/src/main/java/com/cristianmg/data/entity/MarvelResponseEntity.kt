package com.cristianmg.data.entity

open class MarvelResponseEntity<T>(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val etag: String,
    val data: T
) {


    fun getDataOrError(): T {
        return data
    }
}
