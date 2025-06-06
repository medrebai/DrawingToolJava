package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineShape extends Shape {
    private double endX;
    private double endY;

    public LineShape(double startX, double startY, double endX, double endY, Color color) {
        super(startX, startY, color);
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeLine(x, y, endX, endY);
    }

    // Getters et setters
    public double getEndX() { return endX; }
    public double getEndY() { return endY; }
    public void setEndX(double endX) { this.endX = endX; }
    public void setEndY(double endY) { this.endY = endY; }
}
