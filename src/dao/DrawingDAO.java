package dao;

import model.Shape;
import model.RectangeShape;
import model.CercleShape;
import model.LineShape;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingDAO {

    private Connection connection;

    public DrawingDAO(String dbUrl) {
        try {
            connection = DriverManager.getConnection(dbUrl);
            initTables();
        } catch (SQLException e) {
            System.err.println("Erreur connexion DB : " + e.getMessage());
        }
    }

    private void initTables() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS shapes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "project_id INTEGER," +  // 🆕 Ajouté
                "type TEXT," +
                "x REAL," +
                "y REAL," +
                "param1 REAL," +
                "param2 REAL," +
                "color TEXT)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        }
    }
    public void saveShapes(int projectId, List<Shape> shapes) {
        String sql = "INSERT INTO shapes (project_id, type, x, y, param1, param2, color) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Shape shape : shapes) {
                stmt.setInt(1, projectId);
                stmt.setString(2, shape.getClass().getSimpleName());
                stmt.setDouble(3, shape.getX());
                stmt.setDouble(4, shape.getY());

                if (shape instanceof RectangeShape) {
                    stmt.setDouble(5, ((RectangeShape) shape).getWidth());
                    stmt.setDouble(6, ((RectangeShape) shape).getHeight());
                } else if (shape instanceof CercleShape) {
                    stmt.setDouble(5, ((CercleShape) shape).getRadius());
                    stmt.setDouble(6, 0);
                } else if (shape instanceof LineShape) {
                    stmt.setDouble(5, ((LineShape) shape).getEndX());
                    stmt.setDouble(6, ((LineShape) shape).getEndY());
                }

                stmt.setString(7, shape.getColor().toString());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }


    public List<Shape> loadShapes(int projectId) {
        List<Shape> shapes = new ArrayList<>();
        String sql = "SELECT * FROM shapes WHERE project_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double param1 = rs.getDouble("param1");
                double param2 = rs.getDouble("param2");
                Color color = Color.web(rs.getString("color"));

                switch (type) {
                    case "RectangeShape":
                        shapes.add(new RectangeShape(x, y, param1, param2, color));
                        break;
                    case "CercleShape":
                        shapes.add(new CercleShape(x, y, param1, color));
                        break;
                    case "LineShape":
                        shapes.add(new LineShape(x, y, param1, param2, color));
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
        }
        return shapes;
    }
    public void deleteShapesForProject(int projectId) {
        String sql = "DELETE FROM shapes WHERE project_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }



}
