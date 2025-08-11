import DAO.AttendanceList
import com.example.AttendanceResource
import configuration.*
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import com.fasterxml.jackson.module.kotlin.kotlinModule
class EmployeeApplication: Application<EmployeeConfiguration>() {
    //override fun initialize(bootstrap: Bootstrap<HelloWorldConfiguration>) {}

    override fun run(configuration: EmployeeConfiguration, environment: Environment) {
        // Create attendance list storage
        val attendanceList = AttendanceList()

        // For now, hardcode employee IDs
        val employeeIds = listOf("EMP001", "EMP002", "EMP003")
        val resource= AttendanceResource(attendanceList, employeeIds)
        //val resource = HelloWorldResource(configuration.template)
        environment.objectMapper.registerModule(kotlinModule())//what is this
        environment.jersey().register(resource)
    }
}

fun main(args: Array<String>) {
    EmployeeApplication().run(*args)
}
