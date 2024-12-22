import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddBookPage extends JFrame {
    private JTextField titleField, authorField, yearField;

    public AddBookPage() {
        setTitle("Add Book");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        JLabel titleLabel = new JLabel("Title:");
        JLabel authorLabel = new JLabel("Author:");
        JLabel yearLabel = new JLabel("Year:");

        titleField = new JTextField();
        authorField = new JTextField();
        yearField = new JTextField();

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> addBook());

        add(titleLabel);
        add(titleField);
        add(authorLabel);
        add(authorField);
        add(yearLabel);
        add(yearField);
        add(new JLabel());
        add(addButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String yearText = yearField.getText();

        if (title.isEmpty() || author.isEmpty() || yearText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
            return;
        }

        try {
            int year = Integer.parseInt(yearText);

            try (Connection conn = DBConnection.getConnection()) {
                String query = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setInt(3, year);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book added successfully!");
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Year must be a number.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add book. Please try again.");
        }
    }
}
