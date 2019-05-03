package com.cristianmg.data.entity

data class MarvelPaginateData<T>(
    val offset:Int,
    val limit:Int,
    val total:Int,
    val count:Int,
    val results: T
)
