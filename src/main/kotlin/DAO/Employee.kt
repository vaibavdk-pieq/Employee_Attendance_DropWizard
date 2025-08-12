package DAO

import DAO.ENUM.*
data class Employee(
    val employeeId: String,
    val firstName: String,
    val lastName: String,
    val role: Roles,
    val department: Departments,
    val reportingTo: String
) {
    companion object {
        private var counter = 1
        fun generateId(firstName: String, lastName: String): String {
            val initials = "${firstName.firstOrNull() ?: 'X'}${lastName.firstOrNull() ?: 'X'}"
            return "${initials.uppercase()}${counter++}"
        }
    }
}