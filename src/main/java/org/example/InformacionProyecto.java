
package org.example;

/*
 * b) Consulte la información del proyecto y los ID de los empleados de employee_projects
 *  y los ID de los empleados y los salarios de employee_realistic. Únelo en el ID de empleado.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InformacionProyecto {
    public static void main(String[] args) {
    	 // URL de la base de datos, usuario y contraseña
    	String url = "jdbc:sqlite:company_database.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Consulta SQL actualizada con el nuevo nombre de la tabla
            String query = "SELECT ep.project_id, ep.employee_id, e.salary "
                         + "FROM employee_projects ep "
                         + "JOIN employees_realistic e ON ep.employee_id = e.employee_id";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                int employeeId = rs.getInt("employee_id");
                double salary = rs.getDouble("salary");

                // Imprimir los resultados de la consulta
                System.out.println("Project ID: " + projectId + ", Employee ID: " + employeeId + ", Salary: " + salary);
            }

        } catch (SQLException e) {
            // Manejo de excepciones SQL
            System.out.println(e.getMessage());
        }
    }
}
