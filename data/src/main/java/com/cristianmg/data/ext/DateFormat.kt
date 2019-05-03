package com.cristianmg.data.ext

import java.text.SimpleDateFormat
import java.util.*

class DateFormat {

    companion object {

        const val DATABASE_PATTER = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        fun toDatabaseFormat(calendar: Calendar):String {
            val dateFormat = SimpleDateFormat(DATABASE_PATTER, Locale.getDefault())
            return dateFormat.format(calendar.time)
        }

        fun toNetworkFormat(calendar: Calendar):String {
            val dateFormat = SimpleDateFormat(DATABASE_PATTER, Locale.getDefault())
            return dateFormat.format(calendar.time)
        }

        fun fromDatabaseFormat(date: String):Calendar {
            val dateFormat = SimpleDateFormat(DATABASE_PATTER, Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(date)
            return calendar
        }

        fun fromNetworkFormat(date: String):Calendar {
            val dateFormat = SimpleDateFormat(DATABASE_PATTER, Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(date)
            return calendar
        }
    }

}