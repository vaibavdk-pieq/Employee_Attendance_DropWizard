package resource

import DAO.Employee
import DAO.EmployeeList
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class EmployeeResource(private val employeeList: EmployeeList) {

    // Get all employees
    @GET
    fun getAllEmployees(): Response {
        return Response.ok(employeeList).build()
    }

    // Get employee by ID
    @GET
    @Path("/{id}")
    fun getEmployeeById(@PathParam("id") id: String): Response {
        val employee = employeeList.getEmployeeById(id)
        return if (employee != null) {
            Response.ok(employee).build()
        } else {
            Response.status(Response.Status.NOT_FOUND)
                .entity(mapOf("error" to "Employee with ID $id not found"))
                .build()
        }
    }

    // Add a new employee
    @POST
    fun addEmployee(employee: Employee): Response {
        // Check if employee with same id exists
//        if (employeeList.isEmployeeExist(employee.id)) {
//            return Response.status(Response.Status.CONFLICT)
//                .entity(mapOf("error" to "Employee with ID ${employee.id} already exists"))
//                .build()
//        }

        return try {
            val added = employeeList.add(employee)
            if (added) {
                Response.status(Response.Status.CREATED).entity(employee).build()
            } else {
                Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(mapOf("error" to "Failed to add employee"))
                    .build()
            }
        } catch (ex: IllegalArgumentException) {
            Response.status(Response.Status.BAD_REQUEST)
                .entity(mapOf("error" to ex.message))
                .build()
        }
    }

    // Delete employee by ID
    @DELETE
    @Path("/{id}")
    fun deleteEmployee(@PathParam("id") id: String): Response {
        val deleted = employeeList.deleteEmployee(id)
        return if (deleted) {
            Response.ok(mapOf("message" to "Employee $id deleted successfully")).build()
        } else {
            Response.status(Response.Status.NOT_FOUND)
                .entity(mapOf("error" to "Employee with ID $id not found"))
                .build()
        }
    }
}
