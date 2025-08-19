package com.example.resources

import Services.EmployeeService
import RequestDataClass.EmployeeRequest
import DAO.Employee
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class EmployeeResource(private val employeeService: EmployeeService) {

    @POST
    @Path("/addemployee")
    fun addEmployee(request: EmployeeRequest): Response {
        return try {
            val employee: Employee = employeeService.addEmployee(request)
            Response.status(Response.Status.CREATED).entity(employee).build()
        } catch (e: IllegalArgumentException) {
            Response.status(Response.Status.BAD_REQUEST)
                .entity(mapOf("error" to e.message))
                .build()
        } catch (e: Exception) {
            Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(mapOf("error" to "Unexpected error: ${e.message}"))
                .build()
        }
    }

    @GET
    fun getAllEmployees(): Response {
        val employees = employeeService.getAllEmployees()
        return Response.ok(employees).build()
    }

    @DELETE
    @Path("/deleteemployee/{id}")
    fun deleteEmployee(@PathParam("id") id: String): Response {
        return try {
            val deletedEmployee = employeeService.deleteEmployee(id)
            if (deletedEmployee) {
                Response.ok(mapOf("message" to "Employee deleted successfully")).build()
            } else {
                Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(mapOf("error" to "Unknown error occurred")).build()
            }
        } catch (e: IllegalArgumentException) {
            Response.status(Response.Status.BAD_REQUEST)
                .entity(mapOf("error" to e.message)).build()
        } catch (e: NoSuchElementException) {
            Response.status(Response.Status.NOT_FOUND)
                .entity(mapOf("error" to e.message)).build()
        }
    }


}
