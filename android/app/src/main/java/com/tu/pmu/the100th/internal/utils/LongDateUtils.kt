package com.tu.pmu.the100th.internal.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


class LongDateUtils {
    companion object {

        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("dd/MM/yyyy")
            return format.format(date)
        }

        fun currentTimeToLong(): Long {
            return System.currentTimeMillis()
        }

        fun convertDateToLong(date: String): Long {
            val df = SimpleDateFormat("dd/MM/yyyy")
            return df.parse(date).time
        }

        fun convertLongToLocalDate(time: Long): LocalDate {
            return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()
        }

        fun isLongDateBeforeToday(dt: Long): Boolean {
            val date = Instant.ofEpochMilli(dt).atZone(ZoneId.systemDefault()).toLocalDate()
            return date.isBefore(LocalDate.now())
        }
    }
}