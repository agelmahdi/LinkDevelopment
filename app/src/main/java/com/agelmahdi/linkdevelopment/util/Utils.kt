package com.agelmahdi.linkdevelopment.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatDate(string: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
        return formatter.format(parser.parse(string) as Date).toString()
    }
}