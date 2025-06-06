# DrawingToolJava

Cette application JavaFX permet de dessiner sur un canevas et de sauvegarder les formes dans une base SQLite.
Elle propose plusieurs stratégies de journalisation (console, fichier `logs.txt` ou base `logs.db`).

## Fonctionnalités

- Dessin de rectangles, cercles et lignes.
- Sélection de la couleur, annulation et rétablissement des formes.
- Enregistrement et chargement des dessins grâce à `DrawingDAO` (base SQLite).
- Choix du mode de journalisation (console, fichier ou base de données).

## Structure du projet

```
src/
 ├─ controller/      → logique métier (`DrawingController`)
 ├─ dao/             → accès à la base (`DrawingDAO`)
 ├─ logger/          → stratégies de logs
 ├─ model/           → classes de formes (Rectangle, Cercle, Ligne...)
 └─ test/Main.java   → point d’entrée de l’application JavaFX
```

## Pré-requis

- JDK 19 ou supérieur.
- JavaFX (librairies présentes dans `PATH_TO_JAVAFX/lib`).
- Driver SQLite (`sqlite-jdbc`).

## Compilation et exécution

```bash
# Compilation
javac --module-path PATH_TO_JAVAFX/lib \
      --add-modules javafx.controls,javafx.fxml \
      -cp PATH_TO_SQLITE_JDBC \
      -d bin $(find src -name "*.java")

# Exécution
java --module-path PATH_TO_JAVAFX/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp "bin:PATH_TO_SQLITE_JDBC" test.Main
```

On peut également importer le projet dans un IDE (Eclipse, IntelliJ) en configurant les dépendances JavaFX et SQLite.
