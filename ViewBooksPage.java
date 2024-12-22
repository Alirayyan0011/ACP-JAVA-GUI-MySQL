import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewBooksPage extends JFrame {
    public ViewBooksPage() {
        setTitle("View Books");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] columns = { "ID", "Title", "Author", "Year" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");

                tableModel.addRow(new Object[] { id, title, author, year });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton editButton = new JButton("Edit Book");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String title = (String) tableModel.getValueAt(selectedRow, 1);
                String author = (String) tableModel.getValueAt(selectedRow, 2);
                int year = (int) tableModel.getValueAt(selectedRow, 3);

                new EditBookPage(id, title, author, year);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to edit.");
            }
        });

        add(editButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
