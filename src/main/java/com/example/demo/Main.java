package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import com.example.demo.board.ChessBoard;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.*;

public class Main extends Application {

    public static final int BOARD_DIM = ChessBoard.SQUARE_DIM * 8;
    public static final int SCENE_DIM = BOARD_DIM;
    public static final int N_COINS = 60;

    public static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Fairy Chess");

        // Create a button to start a new game
        Button newGameButton = new Button("Nouvelle partie");
        newGameButton.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        newGameButton.setStyle("-fx-background-color: #111111; -fx-text-fill: white; -fx-border-color: white;");
        newGameButton.setPrefSize(250, 100);
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Start a new game here
                try {
                    new CreatePiece(primaryStage, new ArrayList<>(List.of(ROOK, KNIGHT, BISHOP, QUEEN)), N_COINS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Create a button to display rules
        Button rulesButton = new Button("Règles");
        rulesButton.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        rulesButton.setStyle("-fx-background-color: #111111; -fx-text-fill: white; -fx-border-color: white;");
        rulesButton.setPrefSize(150, 100);
        rulesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Display rules
                try {
                    displayRules();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Create a label with a welcome message
        Label welcomeLabel = new Label("Bienvenue dans Fairy Chess!");
        welcomeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 38));
        welcomeLabel.setTextFill(Color.DARKRED);

        // Create a background image for the greeting screen
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("background.png")));
        background.fitWidthProperty().bind(primaryStage.widthProperty());
        background.fitHeightProperty().bind(primaryStage.heightProperty());

        // Create a stack pane to hold the greeting screen components
        StackPane greetingPane = new StackPane();
        greetingPane.getChildren().addAll(background, welcomeLabel, newGameButton, rulesButton);
        greetingPane.setAlignment(Pos.CENTER);
        greetingPane.setPrefSize(SCENE_DIM, SCENE_DIM);

        // Set the position of the button and the label
        rulesButton.setTranslateY(255);
        newGameButton.setTranslateY(150);
        welcomeLabel.setTranslateY(-200);

        // Set the greeting screen as the primary scene
        primaryStage.setScene(new Scene(greetingPane));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void displayRules() {


        Stage rulesStage = new Stage();
        rulesStage.setTitle("Règles");

        String rulesText = """
                •Chaque joueur commence par choisir ses pièces et les place sur l'échiquier à tour de rôle, en commençant par les blancs.
                
                •Le premier joueur choisit d'abord jusqu'à 3 pièces de fusion. Les pièces de fusion résultent de la combinaison de 2 pièces de base (Tour, Cavalier, Fou et Reine). Lorsque deux pièces sont fusionnées, la nouvelle pièce obtenue peut se déplacer comme les deux pièces fusionnées. Fusionner deux pièces identiques (par exemple, deux fous) crée un fou classique capable de jouer deux fois lors de chaque tour.
                
                •Ensuite, le joueur choisit jusqu'à 4 pièces originales qui ont des déplacements différents des autres pièces de base et qui peuvent avoir des effets spéciaux, tels que l'invincibilité (la pièce ne peut pas être capturée).
                
                •Enfin, le joueur sélectionne 8 types de pions, y compris les pions de base ainsi que des pions originaux.
                
                •Ensuite, le deuxième joueur choisit ses pièces et une fois que le deuxième joueur a terminé, la partie commence.
                
                •Chaque joueur dispose d'une quantité de monnaie limitée, et chaque pièce coûte de l'argent. Il vous faudra donc choisir vos pièces judicieusement !
                
                •Le but du jeu est identique à celui des échecs classiques : capturer le roi de l'adversaire !""";

        Text rulesLabel = new Text(rulesText);
        rulesLabel.setFont(Font.font(25));
        rulesLabel.setWrappingWidth(700); // Adjust the width as per your preference

        ScrollPane scrollPane = new ScrollPane(rulesLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox rulesLayout = new VBox(scrollPane);
        Scene rulesScene = new Scene(rulesLayout, 700, 800);

        rulesStage.setMinWidth(735); // Set the minimum width
        rulesStage.setMaxWidth(735); // Set the maximum width

        rulesStage.setScene(rulesScene);
        rulesStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

