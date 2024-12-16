package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MostrarResultadosCostosSalariales extends JFrame {
    public MostrarResultadosCostosSalariales() {
        setTitle("Resultados de la Consulta SQL");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Project ID", "Salary Costs"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:company_database.db");
             Statement stmt = conn.createStatement()) {

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
                tableModel.addRow(new Object[]{projectId, salary});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ejecutar la consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MostrarResultadosCostosSalariales frame = new MostrarResultadosCostosSalariales();
            frame.setVisible(true);
        });
    }
}
