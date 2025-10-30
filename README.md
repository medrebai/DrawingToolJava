Here’s your GitHub `README.md` in proper Markdown format, ready to copy-paste:

```markdown
# DrawingToolJava

A **JavaFX application** for drawing on canvas and saving shapes in an SQLite database. It offers multiple logging strategies (console, `logs.txt` file, or `logs.db` database).

---

## 🎯 Features

- **Shape Drawing**: Rectangles, circles, and lines with color selection
- **Project Management**: Save and load multiple drawings with project IDs
- **Action History**: Undo and redo shape operations
- **Data Persistence**: Save to SQLite database via DAO pattern
- **Modular Logging System**: Console, text file, or database logging
- **Intuitive Interface**: Shape, color, and project selection via ComboBox

---

## 🏗️ Architecture and Modules

```

src/
├─ controller/           → Business logic (DrawingController)
├─ dao/                  → Data access (DrawingDAO) - DAO Pattern
├─ factory/              → Shape creation (ShapeFactory) - Factory Pattern
├─ logger/               → Logging strategies - Strategy Pattern
│   ├─ LoggerStrategy.java
│   ├─ ConsoleLogger.java
│   ├─ FileLogger.java
│   └─ DBLogger.java
├─ model/                → Business classes
│   ├─ Shape.java (abstract class)
│   ├─ RectangeShape.java
│   ├─ CercleShape.java
│   └─ LineShape.java
└─ test/Main.java        → Entry point and JavaFX UI configuration

````

---

## 🔧 Implemented Design Patterns

### 1. Factory Pattern - Shape Creation
```java
// ShapeFactory.java
public static Shape createShape(ShapeType type, double x, double y, 
                               double param1, double param2, Color color)
````

**Advantage**: Decouples shape creation from usage, facilitates adding new shapes.

### 2. Strategy Pattern - Logging System

```java
// LoggerStrategy.java
public interface LoggerStrategy {
    void log(String message);
}
```

**Implementations**:

* `ConsoleLogger`: Logs to console
* `FileLogger`: Logs to text file
* `DBLogger`: Logs to database

**Advantage**: Allows dynamic switching of logging strategies without modifying client code.

### 3. DAO Pattern - Data Access

```java
// DrawingDAO.java
public void saveShapes(int projectId, List<Shape> shapes)
public List<Shape> loadShapes(int projectId)
```

**Advantage**: Abstracts data access, ensuring independence between business layer and persistence.

### 4. Command Pattern (Conceptual) - Undo/Redo

* Implemented simply with lists, following the **Command pattern** concept for action history management.

---

## 📐 Applied SOLID Principles

* **S** - Single Responsibility Principle
  Each class has one clear responsibility: `Shape` (drawing), `DrawingDAO` (persistence), `LoggerStrategy` (logging), `ShapeFactory` (object creation)

* **O** - Open/Closed Principle
  Add new shapes or loggers without modifying existing code

* **L** - Liskov Substitution Principle
  All Shape subclasses and loggers can replace their base class/interface safely

* **I** - Interface Segregation Principle
  LoggerStrategy has a single specific method

* **D** - Dependency Inversion Principle
  DrawingController depends on abstractions (`LoggerStrategy`), not concrete implementations

---

## 🗃️ Data Model

```sql
CREATE TABLE shapes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    project_id INTEGER,
    type TEXT,
    x REAL,
    y REAL,
    param1 REAL,
    param2 REAL,
    color TEXT
);
```

---

## ⚙️ Technical Requirements

* **JDK**: 19 or higher
* **JavaFX**: SDK 17+
* **Database**: SQLite (embedded)
* **Driver**: sqlite-jdbc

---

## 🚀 Compilation and Execution

### Command Line

```bash
# Compilation
javac --module-path PATH_TO_JAVAFX/lib \
      --add-modules javafx.controls,javafx.fxml \
      -cp PATH_TO_SQLITE_JDBC \
      -d bin $(find src -name "*.java")

# Execution
java --module-path PATH_TO_JAVAFX/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp "bin:PATH_TO_SQLITE_JDBC" test.Main
```

### IDE Configuration

* Configure JavaFX module path
* Add SQLite JDBC driver to classpath
* VM arguments:

  ```
  --module-path [javafx-sdk-path]/lib --add-modules javafx.controls,javafx.fxml
  ```

---

## 📊 Logging

Supports three modes:

* **Console**: Direct display in console
* **File**: Write to `logs.txt` with timestamp
* **Database**: Storage in `logs` table of `logs.db`

---

## 🎨 User Interface

* Drawing Canvas 600x400
* Shape selector (Rectangle, Circle, Line)
* Color picker (ColorPicker)
* Project management (ID ComboBox)
* Buttons: Save, Load, Clear, Undo, Redo
* Logging strategy selector

---

## 🔄 Typical Workflow

1. Select a shape and color
2. Click on canvas to draw
3. Use Undo/Redo to adjust
4. Choose a project ID
5. Save with "Save Drawing"
6. Reload later with "Load Drawing"

---

This modular architecture, together with SOLID principles and design patterns, makes the application **maintainable, extensible, and robust**.

```

If you want, I can also **add a slick GIF/demo section** for GitHub README to make it look like a professional open-source project. It’ll make the project pop instantly. Do you want me to do that?
```
