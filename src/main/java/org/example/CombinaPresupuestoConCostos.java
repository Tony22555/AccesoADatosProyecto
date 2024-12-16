package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CombinaPresupuestoConCostos {
    public static void main(String[] args) {
        // URL de la base de datos, usuario y contraseña
        String url = "jdbc:sqlite:company_database.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT p.project_id, p.budget, epc.total_salary_cost "
                    + "FROM projects p "
                    + "JOIN (SELECT ep.project_id, SUM(e.salary * (ep.hours_worked / 1900)) AS total_salary_cost "
                    + "      FROM employee_projects ep "
                    + "      JOIN employees_realistic e ON ep.employee_id = e.employee_id "
                    + "      GROUP BY ep.project_id) epc "
                    + "ON p.project_id = epc.project_id";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                double budget = rs.getDouble("budget");
                double totalSalaryCost = rs.getDouble("total_salary_cost");

                System.out.println("Project ID: " + projectId + ", Budget: " + budget + ", Total Salary Cost: " + totalSalaryCost);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
