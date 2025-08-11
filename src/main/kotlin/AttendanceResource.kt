package com.example

import DAO.*
import RequestDataClass.AttendanceRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import com.fasterxml.jackson.annotation.JsonFormat

// Request DTO using LocalDateTime with format
//data class AttendanceRequest(
//    val empId: String?,
//
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
//    val dateTime: LocalDateTime? = null
//)

@Path("/attendance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AttendanceResource(
    private val attendanceList: AttendanceList,
    private val employeeIds: List<String>
) {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    private fun orNow(dateTime: LocalDateTime?): LocalDateTime {
        return dateTime ?: LocalDateTime.now()
    }

    @POST
    @Path("/checkin")
    fun checkIn(request: AttendanceRequest): Response {
        val empId = request.empId?.trim().orEmpty()
        if (empId.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(mapOf("error" to "empId is required"))
                .build()
        }
        if (!employeeIds.contains(empId)) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(mapOf("error" to "Employee not found"))
                .build()
        }

        if(request.dateTime?.isAfter(LocalDateTime.now()) ?: false){
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity(mapOf("error" to "Check-In date cannot be future"))
                .build()
        }

        val checkInTime = orNow(request.dateTime)

        val attendance = Attendance(empId, checkInTime)
        val added = attendanceList.add(attendance)
        return if (added) {
            Response.status(Response.Status.CREATED)
                .entity(
                    mapOf(
                        "message" to "Check-in successful",
                        "empId" to empId,
                        "checkIn" to checkInTime.format(formatter)
                    )
                )
                .build()
        } else {
            Response.status(Response.Status.CONFLICT)
                .entity(mapOf("error" to "Already checked in for this date"))
                .build()
        }
    }

    @PUT
    @Path("/checkout")
    fun checkOut(request: AttendanceRequest): Response {
        val empId = request.empId?.trim().orEmpty()
        if (empId.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(mapOf("error" to "empId is required"))
                .build()
        }
        if (!employeeIds.contains(empId)) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(mapOf("error" to "Employee not found"))
                .build()
        }

        val checkOutTime = orNow(request.dateTime)

        val message = attendanceList.checkOut(empId, checkOutTime)
        return if (message.startsWith("Checked out successfully")) {
            Response.ok(mapOf("message" to message)).build()
        } else {
            Response.status(Response.Status.BAD_REQUEST)
                .entity(mapOf("error" to message))
                .build()
        }
    }

    @GET
    @Path("/records")
    fun getRecords(): List<Attendance> = attendanceList

    @GET
    @Path("/summary")
    fun getSummary(
        @QueryParam("fromDate") fromDate: String?,
        @QueryParam("toDate") toDate: String?
    ): Response {
        if (fromDate.isNullOrBlank() || toDate.isNullOrBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(mapOf("error" to "fromDate and toDate required in yyyy-MM-dd format"))
                .build()
        }
        val summary = attendanceList.summaryOfWorkingHours(fromDate, toDate)
        return Response.ok(mapOf("summary" to summary)).build()
    }
}
