package org.example;

import java.sql.*;

public class CalcularCostosalarialesProyecto {
    public static void main(String[] args) {
        // URL de la base de datos, usuario y contraseña
        String url = "jdbc:sqlite:company_database.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Consulta SQL actualizada con el nuevo nombre de la tabla
            String query = """
                    SELECT ep.project_id,SUM(er.salary / 1900) AS project_salary_costs
                    FROM employee_projects AS ep
                    JOIN employees_realistic AS er
                    ON ep.employee_id = er.employee_id
                    GROUP BY ep.project_id;
                    """;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                double salary = rs.getDouble("project_salary_costs");

                // Imprimir los resultados de la consulta
                System.out.println("Project ID: " + projectId + ", Salary Costs: " + salary);
            }

        } catch (SQLException e) {
            // Manejo de excepciones SQL
            System.out.println(e.getMessage());
        }
    }
}
