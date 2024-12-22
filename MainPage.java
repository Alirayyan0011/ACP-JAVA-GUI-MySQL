import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPage extends JFrame {
    public MainPage() {
        setTitle("Main Page");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton addBookButton = new JButton("Add Book");
        JButton viewBooksButton = new JButton("View Books");
        JButton logoutButton = new JButton("Logout");

        addBookButton.addActionListener(e -> new AddBookPage());
        viewBooksButton.addActionListener(e -> new ViewBooksPage());
        logoutButton.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(addBookButton);
        panel.add(viewBooksButton);
        panel.add(logoutButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
