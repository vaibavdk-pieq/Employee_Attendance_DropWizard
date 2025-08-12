import DAO.AttendanceList
import DAO.EmployeeList
import Services.EmployeeService
import com.example.resources.EmployeeResource
import configuration.*
import resource.AttendanceResource
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import com.fasterxml.jackson.module.kotlin.kotlinModule


class EmployeeApplication: Application<EmployeeConfiguration>() {
    //override fun initialize(bootstrap: Bootstrap<HelloWorldConfiguration>) {}

    override fun run(configuration: EmployeeConfiguration, environment: Environment) {
        // Create attendance list storage
        val attendanceList = AttendanceList()
        val employeeList= EmployeeList()

        // For now, hardcode employee IDs
        //val employeeIds = listOf("EMP001", "EMP002", "EMP003")
        val EmployeeService= EmployeeService(employeeList)
        val AttendanceResource= AttendanceResource(attendanceList, employeeList)
        val EmployeeResource= EmployeeResource(EmployeeService)
        //val resource = HelloWorldResource(configuration.template)
        environment.objectMapper.registerModule(kotlinModule())//what is this
        environment.jersey().register(EmployeeResource)
        environment.jersey().register(AttendanceResource)

    }
}

fun main(args: Array<String>) {
    EmployeeApplication().run(*args)
}
