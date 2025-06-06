package factory;

import javafx.scene.paint.Color;
import model.CercleShape;
import model.LineShape;
import model.RectangeShape;
import model.Shape;

public class ShapeFactory {

    public enum ShapeType {
        RECTANGLE,
        CIRCLE,
        LINE
    }

    public static Shape createShape(ShapeType type, double x, double y, double widthOrRadius, double heightOrEndY, Color color) {
        switch (type) {
            case RECTANGLE:
                return new RectangeShape(x, y, widthOrRadius, heightOrEndY, color);
            case CIRCLE:
                return new CercleShape(x, y, widthOrRadius, color);
            case LINE:
                return new LineShape(x, y, widthOrRadius, heightOrEndY, color);
            default:
                throw new IllegalArgumentException("Type de forme inconnu : " + type);
        }
    }
}
