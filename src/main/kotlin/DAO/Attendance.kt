package DAO

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Duration
import java.time.LocalDateTime

data class Attendance(
    val employeeId: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val checkInDateTime: LocalDateTime,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    var checkOutDateTime: LocalDateTime? = null,

    var workingHours: Double? = null
) {
    fun checkOut(checkOutDateTime: LocalDateTime): Boolean {
        if (checkOutDateTime.isAfter(this.checkInDateTime)) {
            this.checkOutDateTime = checkOutDateTime
            val workedHours = Duration.between(checkInDateTime, checkOutDateTime).toMinutes().toDouble() / 60.0
            this.workingHours = String.format("%.2f", workedHours).toDouble()
            return true
        }
        return false
    }
}
