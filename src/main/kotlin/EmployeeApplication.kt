import DAO.AttendanceList
import DAO.EmployeeList
import configuration.*
import resource.AttendanceResource
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import com.fasterxml.jackson.module.kotlin.kotlinModule
import resource.EmployeeResource

class EmployeeApplication: Application<EmployeeConfiguration>() {
    //override fun initialize(bootstrap: Bootstrap<HelloWorldConfiguration>) {}

    override fun run(configuration: EmployeeConfiguration, environment: Environment) {
        // Create attendance list storage
        val attendanceList = AttendanceList()
        val employeeList= EmployeeList()

        // For now, hardcode employee IDs
        //val employeeIds = listOf("EMP001", "EMP002", "EMP003")
        val AttendanceResource= AttendanceResource(attendanceList, employeeList)
        val EmployeeResource= EmployeeResource(employeeList)
        //val resource = HelloWorldResource(configuration.template)
        environment.objectMapper.registerModule(kotlinModule())//what is this
        environment.jersey().register(EmployeeResource)
        environment.jersey().register(AttendanceResource)

    }
}

fun main(args: Array<String>) {
    EmployeeApplication().run(*args)
}
