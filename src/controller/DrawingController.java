package controller;

import dao.DrawingDAO;
import factory.ShapeFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logger.LoggerStrategy;
import model.Edge;
import model.Node;
import model.Shape;

import java.util.ArrayList;
import java.util.List;

public class DrawingController {
    private ShapeFactory.ShapeType currentShapeType;
    private Color currentColor;
    private LoggerStrategy logger;
    private Canvas canvas;
    private List<Shape> shapes;
    private DrawingDAO drawingDAO;
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private int nodeCounter = 0;
    private List<Shape> undoneShapes = new ArrayList<>();



    public DrawingController(Canvas canvas, LoggerStrategy logger, DrawingDAO drawingDAO) {
        this.canvas = canvas;
        this.logger = logger;
        this.drawingDAO = drawingDAO;
        this.shapes = new ArrayList<>();
        this.currentShapeType = ShapeFactory.ShapeType.RECTANGLE;
        this.currentColor = Color.BLACK;
    }

    public void setLogger(LoggerStrategy logger) {
        this.logger = logger;
        logger.log("Logger changé pour : " + logger.getClass().getSimpleName());
    }

    public ShapeFactory.ShapeType getCurrentShapeType() {
        return currentShapeType;
    }

    public void setCurrentShapeType(ShapeFactory.ShapeType type) {
        this.currentShapeType = type;
        logger.log("Forme sélectionnée : " + type);
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
        logger.log("Couleur sélectionnée : " + color.toString());
    }

    public void addShape(double x, double y, double param1, double param2) {
        Shape shape = ShapeFactory.createShape(currentShapeType, x, y, param1, param2, currentColor);
        shapes.add(shape);
        logger.log("Forme ajoutée : " + currentShapeType + " en (" + x + ", " + y + ")");
        redraw();
    }

    public void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }

    public void saveDrawing(int projectId) {
        drawingDAO.deleteShapesForProject(projectId); // 🗑️ Supprime les formes existantes
        drawingDAO.saveShapes(projectId, shapes);     // 💾 Sauvegarde les nouvelles formes
        logger.log("Dessin sauvegardé pour le projet ID : " + projectId);
    }


    public void loadDrawing(int projectId) {
        shapes = drawingDAO.loadShapes(projectId);
        logger.log("Dessin chargé pour le projet ID : " + projectId);
        redraw();
    }

    public void clearDrawing() {
        int nbShapes = shapes.size();
        shapes.clear();
        redraw();
        logger.log("Canvas vidé et " + nbShapes + " formes supprimées du tableau !");
    }
    

    public void addNode(double x, double y) {
        Node node = new Node(nodeCounter++, x, y);
        nodes.add(node);
        redraw();
        logger.log("Nœud ajouté : " + node.getId() + " à (" + x + ", " + y + ")");
    }
    public void undoLastShape() {
        if (!shapes.isEmpty()) {
            Shape removedShape = shapes.remove(shapes.size() - 1);
            undoneShapes.add(removedShape); // On sauvegarde la forme annulée
            redraw();
            logger.log("Forme annulée : " + removedShape.getClass().getSimpleName());
        } else {
            logger.log("Aucune forme à annuler !");
        }
    }
    public void redoLastShape() {
        if (!undoneShapes.isEmpty()) {
            Shape restoredShape = undoneShapes.remove(undoneShapes.size() - 1);
            shapes.add(restoredShape);
            redraw();
            logger.log("Forme restaurée : " + restoredShape.getClass().getSimpleName());
        } else {
            logger.log("Aucune forme à restaurer !");
        }
    }




}
