package DAO

class Employee(
    firstName: String,
    lastName: String,
    role: String,
    department: String,
    reportingTo: String
) {
    val id: String
    val firstName: String
    val lastName: String
    val role: String
    val department: String
    val reportingTo: String

    companion object {
        private var idCounter = 0
    }

    init {
        require(firstName.isNotBlank()) { "First name cannot be blank" }
        require(lastName.isNotBlank()) { "Last name cannot be blank" }
        require(role.isNotBlank()) { "Role cannot be blank" }
        require(department.isNotBlank()) { "Department cannot be blank" }
        require(reportingTo.isNotBlank()) { "ReportingTo cannot be blank" }

        this.firstName = firstName.trim().lowercase()
        this.lastName = lastName.trim().lowercase()
        this.role = role.trim().lowercase()
        this.department = department.trim().lowercase()
        this.reportingTo = reportingTo.trim().lowercase()

        this.id = "${this.firstName.first()}${this.lastName.first()}${idCounter++}".lowercase()
    }

    override fun toString(): String {
        return "| ID: $id | Name: $firstName $lastName | Role: $role | Department: $department | Reporting To: $reportingTo |"
    }
}
