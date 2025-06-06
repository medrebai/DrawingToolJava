package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected double x;
    protected double y;
    protected Color color;

    public Shape(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public abstract void draw(GraphicsContext gc);

    // Getters et setters
    public double getX() { return x; }
    public double getY() { return y; }
    public Color getColor() { return color; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setColor(Color color) { this.color = color; }
}

