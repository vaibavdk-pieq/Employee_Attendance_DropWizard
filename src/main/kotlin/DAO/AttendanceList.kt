package DAO

import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AttendanceList : ArrayList<Attendance>() {

    // Check if employee already checked in for a date
    fun hasAlreadyCheckedIn(empId: String, givenDate: LocalDate): Boolean {
        return this.any {
            it.employeeId == empId && it.checkInDateTime.toLocalDate() == givenDate
        }
    }

    // Check if employee already checked out for a date
    fun hasAlreadyCheckedOut(empId: String, checkOutDate: LocalDate): Boolean {
        return this.any {
            it.employeeId == empId &&
                    it.checkInDateTime.toLocalDate() == checkOutDate &&
                    it.checkOutDateTime != null
        }
    }

    // Perform checkout
    fun checkOut(empId: String, checkOutDateTime: LocalDateTime): String {
        val date = checkOutDateTime.toLocalDate()

        if (!hasAlreadyCheckedIn(empId, date)) {
            return "No CheckIn has been made on $date"
        }
        if (hasAlreadyCheckedOut(empId, date)) {
            return "Already Checked Out!!"
        }



        val attendanceRecord = this.find {
            it.employeeId == empId && it.checkInDateTime.toLocalDate() == date
        }
        if(checkOutDateTime.isAfter(LocalDateTime.now())||checkOutDateTime.isBefore(attendanceRecord?.checkInDateTime)){
            return "Check OUT Time cannot be future or Cannot be before the Check In Date Time"
        }

        val success: Boolean = attendanceRecord?.checkOut(checkOutDateTime) ?: false

        return if (success) {
            "Checked out successfully at $checkOutDateTime. \n Working hours: ${attendanceRecord.workingHours}"
        } else {
            "Check-Out failed. Check-Out time must be after Check-In time."
        }
    }

    // Add attendance record, avoiding duplicate check-ins
    override fun add(attendance: Attendance): Boolean {
        if (hasAlreadyCheckedIn(attendance.employeeId, attendance.checkInDateTime.toLocalDate())) {
            return false
        }
        return super.add(attendance)
    }

    override fun toString(): String {
        if (isEmpty()) {
            return "No attendance records found."
        }
        return joinToString(separator = "\n") { it.toString() }
    }

    // Summary of total working hours per employee in date range
    // Summary of total working hours per employee in date range
    fun summaryOfWorkingHours(fromDate: String, toDate: String): List<Map<String, Any>> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val fromDateTime = LocalDateTime.parse("$fromDate 00:00", formatter)
        val toDateTime = LocalDateTime.parse("$toDate 23:59", formatter)

        if (isEmpty()) return emptyList()

        val sortedList = sortedWith(compareBy({ it.employeeId }, { it.checkInDateTime }))
        val summaryList = mutableListOf<Map<String, Any>>()

        var currentEmpId: String? = null
        var totalHours = 0.0

        for (record in sortedList) {
            val empId = record.employeeId
            val checkIn = record.checkInDateTime

            // Skip records outside range
            if (checkIn.isBefore(fromDateTime) || checkIn.isAfter(toDateTime)) continue

            // New employee → save previous total
            if (currentEmpId != null && currentEmpId != empId) {
                summaryList.add(
                    mapOf(
                        "employeeId" to currentEmpId,
                        "totalWorkingHours" to "%.2f".format(totalHours).toDouble()
                    )
                )
                totalHours = 0.0
            }

            currentEmpId = empId
            totalHours += record.workingHours ?: 0.0
        }

        // Add the last employee's data
        if (currentEmpId != null) {
            summaryList.add(
                mapOf(
                    "employeeId" to currentEmpId,
                    "totalWorkingHours" to "%.2f".format(totalHours).toDouble()
                )
            )
        }

        return summaryList
    }

}
