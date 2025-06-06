package test;

import controller.DrawingController;
import dao.DrawingDAO;
import factory.ShapeFactory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logger.ConsoleLogger;
import logger.DBLogger;
import logger.FileLogger;
import logger.LoggerStrategy;

public class Main extends Application {

    private DrawingController controller;

    @Override
    public void start(Stage primaryStage) {
        // Canvas
        Canvas canvas = new Canvas(600, 400);
        canvas.setStyle("-fx-border-color: #333; -fx-border-width: 2;");
        canvas.setWidth(600);
        canvas.setHeight(400);

        // DAO
        DrawingDAO drawingDAO = new DrawingDAO("jdbc:sqlite:drawing.db");

        // Default Logger
        LoggerStrategy defaultLogger = new ConsoleLogger();
        controller = new DrawingController(canvas, defaultLogger, drawingDAO);

        // Logger Selector ComboBox
        ComboBox<String> loggerSelector = new ComboBox<>();
        loggerSelector.getItems().addAll("Console", "File", "Database");
        loggerSelector.setValue("Console");
        


        loggerSelector.setOnAction(e -> {
            String selected = loggerSelector.getValue();
            switch (selected) {
                case "Console":
                    controller.setLogger(new ConsoleLogger());
                    break;
                case "File":
                    controller.setLogger(new FileLogger("logs.txt"));
                    break;
                case "Database":
                    controller.setLogger(new DBLogger("jdbc:sqlite:logs.db"));
                    break;
            }
        });

        // Shape Selector ComboBox
        ComboBox<String> shapeSelector = new ComboBox<>();
        shapeSelector.getItems().addAll("Rectangle", "Circle", "Line");
        shapeSelector.setValue("Rectangle");

        shapeSelector.setOnAction(e -> {
            String selected = shapeSelector.getValue();
            switch (selected) {
                case "Rectangle":
                    controller.setCurrentShapeType(ShapeFactory.ShapeType.RECTANGLE);
                    break;
                case "Circle":
                    controller.setCurrentShapeType(ShapeFactory.ShapeType.CIRCLE);
                    break;
                case "Line":
                    controller.setCurrentShapeType(ShapeFactory.ShapeType.LINE);
                    break;
            }
        });

        // Color Picker
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setOnAction(e -> {
            Color selectedColor = colorPicker.getValue();
            controller.setCurrentColor(selectedColor);
        });

        // Project ID Selector ComboBox
        ComboBox<Integer> projectSelector = new ComboBox<>();
        projectSelector.getItems().addAll(1, 2, 3, 4, 5); // exemple de projets
        projectSelector.setValue(1);

        // Buttons for Save, Load, and Clear
        Button redoButton = new Button("↪️ Redo");
        redoButton.setStyle("-fx-background-color: #009688; -fx-text-fill: white;");
        redoButton.setOnAction(e -> controller.redoLastShape());
        Button undoButton = new Button("↩️ Undo");
        undoButton.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white;");
        undoButton.setOnAction(e -> controller.undoLastShape());
        Button saveButton = new Button("💾 Save Drawing");
        Button loadButton = new Button("📂 Load Drawing");
        Button clearButton = new Button("🗑️ Clear Drawing");
        clearButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        clearButton.setOnAction(e -> controller.clearDrawing());

        saveButton.setOnAction(e -> controller.saveDrawing(projectSelector.getValue()));
        loadButton.setOnAction(e -> controller.loadDrawing(projectSelector.getValue()));

        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loadButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        // Handle mouse clicks on canvas
        canvas.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            switch (controller.getCurrentShapeType()) {
                case RECTANGLE:
                    controller.addShape(x, y, 100, 50);
                    break;
                case CIRCLE:
                    controller.addShape(x, y, 30, 0);
                    break;
                case LINE:
                    controller.addShape(x, y, x + 100, y + 100);
                    break;
            }
        });

        // Layout controls
        VBox controls = new VBox(10,
                new Label("Select Logger:"), loggerSelector,
                new Label("Select Shape:"), shapeSelector,
                new Label("Select Color:"), colorPicker,
                new Label("Select Project ID:"), projectSelector,
                saveButton, loadButton, clearButton, undoButton,redoButton);
        controls.setPadding(new Insets(15));
        controls.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-border-width: 1;");
        controls.setPrefWidth(200);

        // Main layout
        HBox mainLayout = new HBox(20, controls, canvas);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));

        // Scene
        Scene scene = new Scene(mainLayout, 850, 500);

        primaryStage.setTitle("🎨 Drawing App with Logger");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
