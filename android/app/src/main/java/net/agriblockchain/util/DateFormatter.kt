package net.agriblockchain.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {

    fun getFormattedDate(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy/mm/dd", Locale.getDefault())

        return dateFormat.format(date)
    }

    fun getFormattedTime(time: Date): String {
        val dateFormat = SimpleDateFormat("kk:mm", Locale.getDefault())

        return dateFormat.format(time)
    }

    fun getFormattedDateTime(datetime: Date): String {
        val dateFormat = SimpleDateFormat("EEE, MMM d, ''yy", Locale.getDefault())

        return dateFormat.format(dateFormat).plus(getFormattedTime(datetime))
    }
}
