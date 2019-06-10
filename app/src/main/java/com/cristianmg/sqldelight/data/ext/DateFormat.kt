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

package com.cristianmg.sqldelight.data.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateFormat {

    companion object {

        private const val DATABASE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ"

        @SuppressLint("SimpleDateFormat")
        fun toGmtFormat(calendar: Calendar): String {
            val dateFormat = SimpleDateFormat(DATABASE_PATTERN)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return dateFormat.format(calendar.time)
        }

        @SuppressLint("SimpleDateFormat")
        fun toLocaleFormat(date: String): Calendar {
            val dateFormat = SimpleDateFormat(DATABASE_PATTERN)
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(date)
            return calendar
        }
    }

}