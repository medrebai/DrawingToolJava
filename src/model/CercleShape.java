package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CercleShape extends Shape {
    private double radius;

    public CercleShape(double x, double y, double radius, Color color) {
        super(x, y, color);
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x, y, radius * 2, radius * 2);
    }

    // Getters et setters
    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }
}
