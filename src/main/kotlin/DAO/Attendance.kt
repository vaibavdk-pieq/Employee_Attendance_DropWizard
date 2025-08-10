package DAO



import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Duration
import java.time.LocalDateTime

class Attendance(
    val employeeId: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val checkInDateTime: LocalDateTime
) {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    var checkOutDateTime: LocalDateTime? = null

    var workingHours: Double? = null

    fun checkOut(checkOutDateTime: LocalDateTime): Boolean {
        if (checkOutDateTime.isAfter(this.checkInDateTime)) {
            this.checkOutDateTime = checkOutDateTime
            val workedHours = Duration.between(checkInDateTime, checkOutDateTime).toMinutes().toDouble() / 60.0
            this.workingHours = String.format("%.2f", workedHours).toDouble()
            return true
        }
        return false
    }

    override fun toString(): String {
        val checkOut = checkOutDateTime?.toString() ?: "N/A"
        val hours = workingHours?.let { String.format("%.2f", it) } ?: "N/A"
        return "Employee ID: $employeeId | Check-In: $checkInDateTime | Check-Out: $checkOut | Hours Worked: $hours"
    }
}
