package bncc.net.bom.utils

import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object StringHelper {
    var pattern2 = "###,###"

    fun convertFormatPrice(priceValue: Long, pattern: String?): String? {
        return try {
            val decimalFormat = DecimalFormat(pattern)
            decimalFormat.applyPattern(pattern)
            "Rp " + decimalFormat.format(priceValue)
        } catch (e: Exception) {
            "Rp 0"
        }
    }

    fun convertFormatPrice(priceValue: String): String? {
        return convertFormatPrice(priceValue.toLong(), pattern2)
    }

    fun convertFormatPriceWithoutRp(priceValue: Long, pattern: String?): String? {
        return try {
            val decimalFormat = DecimalFormat(pattern)
            decimalFormat.applyPattern(pattern)
            "" + decimalFormat.format(priceValue)
        } catch (e: Exception) {
            "0"
        }
    }

    fun formatDate(value: String?): String? {
        return formatingDateFromString("yyyy-MM-dd", " dd MMM yyyy", value)
    }

    fun formatingDateFromString(
        fromFormat: String?, toFormat: String?,
        stringDate: String?
    ): String? {
        //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        var format = SimpleDateFormat(fromFormat, Locale.getDefault())
        try {
            val newDate = format.parse(stringDate)
            format = SimpleDateFormat(toFormat, Locale.getDefault())
            return format.format(newDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return stringDate
    }
}