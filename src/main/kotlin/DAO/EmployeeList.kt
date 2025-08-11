package DAO

import DAO.ENUM.Departments
import DAO.ENUM.Roles

class EmployeeList : ArrayList<Employee>() {

    init {
        // Predefined manager-level employees
        val predefined = listOf(
            Employee("Anita", "Sharma", Departments.HR.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Rahul", "Verma", Departments.IT.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Sanya", "Patel", Departments.MARKETING.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Deepak", "Kumar", Departments.IT.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Priya", "Nair", Departments.IT.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Karan", "Singh", Departments.IT.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Meera", "Raj", Departments.FINANCE.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Arjun", "Das", Departments.SALES.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Riya", "Menon", Departments.HR.name, Roles.TEAM_LEAD.role, "cto"),
            Employee("Vikram", "Iyer", Departments.IT.name, Roles.TEAM_LEAD.role, "cto")
        )
        super.addAll(predefined)
    }

    // Add new employee, prevent duplicates by ID
    override fun add(employee: Employee): Boolean {
        if (isEmployeeExist(employee.id)) {
            return false
        }
        return super.add(employee)
    }

    // Delete employee by empId
    fun deleteEmployee(empId: String): Boolean {
        val employeeRecord = this.find { it.id == empId }
        return if (employeeRecord != null) this.remove(employeeRecord) else false
    }

    // Check if employee exists by empId
    fun isEmployeeExist(empId: String): Boolean {
        return this.any { it.id == empId }
    }

    // Optional: Get employee by ID
    fun getEmployeeById(empId: String): Employee? {
        return this.find { it.id == empId }
    }

    override fun toString(): String {
        if (this.isEmpty()) return "No employees found."
        return this.joinToString(separator = "\n")
    }
}
