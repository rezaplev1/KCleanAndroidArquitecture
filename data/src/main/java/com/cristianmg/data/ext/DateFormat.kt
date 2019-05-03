package com.cristianmg.data.ext

import java.text.SimpleDateFormat
import java.util.*

class DateFormat {

    companion object {

        private const val DATABASE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ"

        fun toDatabaseFormat(calendar: Calendar):String {
            val dateFormat = SimpleDateFormat(DATABASE_PATTERN, Locale.getDefault())
            return dateFormat.format(calendar.time)
        }

        fun toNetworkFormat(calendar: Calendar):String {
            val dateFormat = SimpleDateFormat(DATABASE_PATTERN, Locale.getDefault())
            return dateFormat.format(calendar.time)
        }

        fun fromDatabaseFormat(date: String):Calendar {
            val dateFormat = SimpleDateFormat(DATABASE_PATTERN, Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(date)
            return calendar
        }

        fun fromNetworkFormat(date: String):Calendar {
            val dateFormat = SimpleDateFormat(DATABASE_PATTERN, Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(date)
            return calendar
        }
    }

}