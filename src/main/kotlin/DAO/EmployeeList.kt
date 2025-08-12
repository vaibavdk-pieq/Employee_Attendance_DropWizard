package DAO

import DAO.ENUM.Departments
import DAO.ENUM.Roles

class EmployeeList : ArrayList<Employee>() {

    init {
        // Predefined manager-level employees
        val predefined = listOf(
            Employee(employeeId = Employee.generateId("Anita", "Sharma"), firstName = "Anita", lastName = "Sharma", department = Departments.HR, role = Roles.TEAM_LEAD, reportingTo = "cto"),
            Employee(employeeId = Employee.generateId("Rahul", "Verma"), firstName = "Rahul", lastName = "Verma", department = Departments.IT, role = Roles.TEAM_LEAD, reportingTo = "cto"),
            Employee(employeeId = Employee.generateId("Sanya", "Patel"), firstName = "Sanya", lastName = "Patel", department = Departments.MARKETING, role = Roles.TEAM_LEAD, reportingTo = "cto"),
            Employee(employeeId = Employee.generateId("Deepak", "Kumar"), firstName = "Deepak", lastName = "Kumar", department = Departments.IT, role = Roles.TEAM_LEAD, reportingTo = "cto"),
            Employee(employeeId = Employee.generateId("Priya", "Nair"), firstName = "Priya", lastName = "Nair", department = Departments.IT, role = Roles.TEAM_LEAD, reportingTo = "cto"),
            Employee(employeeId = Employee.generateId("Karan", "Singh"), firstName = "Karan", lastName = "Singh", department = Departments.IT, role = Roles.TEAM_LEAD, reportingTo = "cto"),
            Employee(employeeId = Employee.generateId("Meera", "Raj"), firstName = "Meera", lastName = "Raj", department = Departments.FINANCE, role = Roles.TEAM_LEAD, reportingTo = "cto"),
            Employee(employeeId = Employee.generateId("Arjun", "Das"), firstName = "Arjun", lastName = "Das", department = Departments.SALES, role = Roles.TEAM_LEAD, reportingTo = "cto"),
            Employee(employeeId = Employee.generateId("Riya", "Menon"), firstName = "Riya", lastName = "Menon", department = Departments.HR, role = Roles.TEAM_LEAD, reportingTo = "cto"),
            Employee(employeeId = Employee.generateId("Vikram", "Iyer"), firstName = "Vikram", lastName = "Iyer", department = Departments.IT, role = Roles.TEAM_LEAD, reportingTo = "cto")
        )
        super.addAll(predefined)
    }

    // Add new employee, prevent duplicates by ID
    override fun add(employee: Employee): Boolean {
        if (isEmployeeExist(employee.employeeId)) {
            return false
        }
        return super.add(employee)
    }

    // Delete employee by empId
    fun deleteEmployee(empId: String): Boolean {
        val employeeRecord = this.find { it.employeeId == empId }
        return if (employeeRecord != null) this.remove(employeeRecord) else false
    }

    // Check if employee exists by empId
    fun isEmployeeExist(empId: String): Boolean {
        return this.any { it.employeeId == empId }
    }

    // Get employee by ID
    fun getEmployeeById(empId: String): Employee? {
        return this.find { it.employeeId == empId }
    }

    override fun toString(): String {
        if (this.isEmpty()) return "No employees found."
        return this.joinToString(separator = "\n")
    }
}
