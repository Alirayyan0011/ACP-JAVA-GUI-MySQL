import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EditBookPage extends JFrame {
    private JTextField titleField, authorField, yearField;
    private int bookId;

    public EditBookPage(int id, String title, String author, int year) {
        this.bookId = id;

        setTitle("Edit Book");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        JLabel titleLabel = new JLabel("Title:");
        JLabel authorLabel = new JLabel("Author:");
        JLabel yearLabel = new JLabel("Year:");

        titleField = new JTextField(title);
        authorField = new JTextField(author);
        yearField = new JTextField(String.valueOf(year));

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> updateBook());

        add(titleLabel);
        add(titleField);
        add(authorLabel);
        add(authorField);
        add(yearLabel);
        add(yearField);
        add(new JLabel());
        add(saveButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateBook() {
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
                String query = "UPDATE books SET title = ?, author = ?, year = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setInt(3, year);
                stmt.setInt(4, bookId);

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book updated successfully!");
                new ViewBooksPage();
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Year must be a number.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
