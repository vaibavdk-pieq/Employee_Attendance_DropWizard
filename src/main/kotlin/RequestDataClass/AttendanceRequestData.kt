package RequestDataClass

import java.time.LocalDateTime
import com.fasterxml.jackson.annotation.JsonFormat

data class AttendanceRequest(
    val employeeId: String?,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val dateTime: LocalDateTime? = null
)