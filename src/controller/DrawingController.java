package controller;

import dao.DrawingDAO;
import factory.ShapeFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logger.LoggerStrategy;
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
        drawingDAO.saveShapes(projectId, shapes);
        logger.log("Dessin sauvegardé pour le projet ID : " + projectId);
    }

    public void loadDrawing(int projectId) {
        shapes = drawingDAO.loadShapes(projectId);
        logger.log("Dessin chargé pour le projet ID : " + projectId);
        redraw();
    }

    public void clearDrawing() {
        shapes.clear();
        redraw();
        logger.log("Canvas vidé !");
    }


}
