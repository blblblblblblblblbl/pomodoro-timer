package blblblbl.simplelife.timer.ui.util

import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue
import kotlin.math.ceil

fun String.fillWithZeros() = this.padStart(MAX_LENGTH_TIMER, ZERO_STRING.first())
fun String.removeLast() = if (isNotEmpty()) this.take(this.length - 1) else this
fun String.firstInputIsZero(input: String) = this.isEmpty() && input == ZERO_STRING
fun Long.isNotZero(): Boolean = this != ZERO_LONG
fun Long?.getPositiveValue(): Long = this?.let { if (this < 0) ZERO_LONG else this } ?: ZERO_LONG
fun Long.isZero(): Boolean = this == ZERO_LONG
fun Int.isZero(): Boolean = this == ZERO_INT



fun Int.toStringOrEmpty(): String = if (this.isZero()) EMPTY else this.toString()
fun Int.toFormattedString(): String {
    return if (absoluteValue in 9 downTo 0) "$ZERO_STRING$absoluteValue" else absoluteValue.toStringOrEmpty()
}

fun Int.minuteToString(hasHour: Boolean): String =
    if (hasHour) this.toFormattedString() else this.toStringOrEmpty()

fun Int.secondToString(hasMinute: Boolean): String =
    if (hasMinute) this.toFormattedString() else this.toString()

fun String.removeExtraColon(): String =
    if (this.first().toString() == COLON) takeLast(length - 1) else this


fun Long.toHhMmSs(): String {
    val hours = ((this / (1000 * 60 * 60) % 24)).toInt().toStringOrEmpty()
    val minutes = ((this / (1000 * 60) % 60)).toInt().minuteToString(hours.isNotEmpty())
    val seconds = ceil(((this.toDouble() / 1000) % 60)).toInt().secondToString(minutes.isNotEmpty())
    var formattedTime = "$hours$COLON$minutes$COLON$seconds"
    while (formattedTime.isNotEmpty() && formattedTime.first().toString() == COLON) {
        formattedTime = formattedTime.removeExtraColon()
    }
    return formattedTime
}