package Services

import DAO.ENUM.Departments
import DAO.ENUM.Roles
import RequestDataClass.EmployeeRequest



import DAO.*
import kotlin.collections.find

class EmployeeService(private val employeeList: EmployeeList) {
    //private val employees = arrayListOf<Employee>()

    fun addEmployee(request: EmployeeRequest): Employee {
        // Validation 1: Non-empty names
        if (request.firstName.isBlank()) {
            throw IllegalArgumentException("First name cannot be empty")
        }
        if (request.lastName.isBlank()) {
            throw IllegalArgumentException("Last name cannot be empty")
        }

        // Validation 2: Role must exist in enum
        val roleEnum = try {
            Roles.valueOf(request.role.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid role: ${request.role}")
        }

        // Validation 3: Department must exist in enum
        val deptEnum = try {
            Departments.valueOf(request.department.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid department: ${request.department}")
        }

        // Validation 4: ReportingTo must be an employee who reports to "cto"
        val manager = employeeList.find { it.employeeId == request.reportingTo.uppercase() }
            ?: throw IllegalArgumentException("ReportingTo employee not found: ${request.reportingTo}")

        if (manager.reportingTo.lowercase() != "cto") {
            throw IllegalArgumentException("ReportingTo must be someone who reports to CTO")
        }

        // Create Employee
        val employee = Employee(
            employeeId = Employee.generateId(request.firstName, request.lastName),
            firstName = request.firstName,
            lastName = request.lastName,
            role = roleEnum,
            department = deptEnum,
            reportingTo = request.reportingTo
        )

        employeeList.add(employee)
        return employee
    }
    fun deleteEmployee(employeeId: String?): Boolean {
        try {
            if (employeeId.isNullOrBlank()) {
                throw IllegalArgumentException("Employee ID is null or empty")
            }

            val employeeRecord = employeeList.find { it.employeeId == employeeId.uppercase() }
                ?: throw NoSuchElementException("Employee with ID $employeeId not found")

            employeeList.remove(employeeRecord)
            return true

        } catch (e: Exception) {
            println("Error deleting employee: ${e.message}")
            throw e
        }
    }

    fun getAllEmployees(): List<Employee> = employeeList
}
