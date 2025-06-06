package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangeShape extends Shape {
    private double width;
    private double height;

    public RectangeShape(double x, double y, double width, double height, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);
    }

    // Getters et setters
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }
}
