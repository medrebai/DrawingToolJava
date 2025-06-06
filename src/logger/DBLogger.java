package logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBLogger implements LoggerStrategy {

    private Connection connection;

    public DBLogger(String dbUrl) {
        try {
            // Exemple de URL : "jdbc:sqlite:logs.db"
            connection = DriverManager.getConnection(dbUrl);
            initTable();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    private void initTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS logs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "timestamp TEXT, " +
                "message TEXT)";
        try (PreparedStatement stmt = connection.prepareStatement(createTableSQL)) {
            stmt.execute();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table de logs : " + e.getMessage());
        }
    }

    @Override
    public void log(String message) {
        String insertSQL = "INSERT INTO logs (timestamp, message) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, LocalDateTime.now().toString());
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du log dans la base de données : " + e.getMessage());
        }
    }
}
