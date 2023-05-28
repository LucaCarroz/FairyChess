package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.example.demo.pieces.Piece;
import com.example.demo.player.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static com.example.demo.pieces.Piece.Type.*;

public class CreatePiece {

    private static final int NFUSIONPIECES = 3;
    private static final int NMAXCUSTOMPIECES = 4;
    private static final int NMAXPAWNS = 8;
    private static final int MINCOSTROOKFUSION = Math.min(TROJANHORSE.price, LIGHTHOUSE.price);
    private static final int MINCOSTKNIGHTFUSION = Math.min(Math.min(NAZGUL.price, TROJANHORSE.price), Math.min(QUEENIGHT.price, BUCEPHALE.price));
    private static final int MINCOSTBISHOPFUSION = Math.min(NAZGUL.price, CARDINAL.price);
    private static final int MINCOSTQUEENFUSION = Math.min(QUEENIGHT.price, CHAOS.price);
    private static final int MAXCOSTROOKFUSION = Math.max(TROJANHORSE.price, LIGHTHOUSE.price);
    private static final int MAXCOSTKNIGHTFUSION = Math.max(Math.max(NAZGUL.price, TROJANHORSE.price), Math.max(QUEENIGHT.price, BUCEPHALE.price));
    private static final int MAXCOSTBISHOPFUSION = Math.max(NAZGUL.price, CARDINAL.price);
    private static final int MAXCOSTQUEENFUSION = Math.max(QUEENIGHT.price, CHAOS.price);


    public static int nFusion = 0;
    public static int nCustom = 0;
    public static int nPawns = 0;
    public static Player player = Player.WHITE;
    public static boolean fusionning = false;
    public static boolean fusion = true;
    public static boolean customPiece = true;
    public static Piece.Type fusionPiece = null;
    public static ArrayList<Piece> whitePieces = new ArrayList<>();
    public static ArrayList<String> whiteCases = new ArrayList<>(List.of("a", "b", "c", "d", "f", "g", "h"));
    public static ArrayList<Piece> blackPieces = new ArrayList<>();
    public static ArrayList<String> blackCases = new ArrayList<>(List.of("a", "b", "c", "d", "f", "g", "h"));

    public static ArrayList<Piece> blackAndWhitePieces = new ArrayList<>();
    private static final List<Piece.Type> basicPieces = List.of(ROOK, KNIGHT, BISHOP, QUEEN);
    private static final List<Piece.Type> customPieces = List.of(ARACHNE, WALLABY, BIRD, PHOENIX, DRAGON, GRYFFON,
                    TWHOMP/*, CHAOS, EXCALIBUR, SAURON, BOAT*/);
    private static final List<Piece.Type> pawns = List.of(PAWN, MINION, WALL, SOLDAT, INFANTRYMAN);

    public CreatePiece(Stage stage, ArrayList<Piece.Type> pieces, int coins) {
        chosePieces(stage,pieces,coins);
    }

    public void chosePieces(Stage stage, List<Piece.Type> allPieces, int coins) {

        ArrayList<Piece.Type> pieces = new ArrayList<>(allPieces);

        Collections.shuffle(pieces);
        Random r = new Random();
        while (pieces.size() > 5)
            pieces.remove(r.nextInt(pieces.size()));

        ArrayList<StackPane> costs = new ArrayList<>();
        ArrayList<ImageView> images = new ArrayList<>();
        ArrayList<Button> buttons = new ArrayList<>();

        for (int i = 0; i < pieces.size(); i++){
            String path = "images/" + player.toString().toLowerCase() + pieces.get(i).toString().substring(0, 1).toUpperCase() + pieces.get(i).toString().substring(1).toLowerCase() + ".png";
            System.out.println(path);
            ImageView image  = new ImageView(new Image(getClass().getResourceAsStream(path)));
            // TODO: change the "pieces.get(i).price" when we are doing fusions to display the correct price
            String cost = "-1$";
            if (fusion && !fusionning){
                switch(pieces.get(i)) {
                    case ROOK -> cost = "$" + MINCOSTROOKFUSION + "-" + MAXCOSTROOKFUSION;
                    case KNIGHT -> cost = "$" + MINCOSTKNIGHTFUSION + "-" + MAXCOSTKNIGHTFUSION;
                    case BISHOP -> cost = "$" + MINCOSTBISHOPFUSION + "-" + MAXCOSTBISHOPFUSION;
                    case QUEEN -> cost = "$" + MINCOSTQUEENFUSION + "-" + MAXCOSTQUEENFUSION;
                }
            } else {
                cost = "$" + pieces.get(i).price;
            }



            Label costLabel = new Label(cost); // Replace 'pieces.get(i).getCost()' with the code that gets the cost of the piece
            costLabel.setTextFill(Color.GOLD);
            costLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
            VBox vBox = new VBox(image, costLabel);
            vBox.setAlignment(Pos.CENTER);
            StackPane stackPane = new StackPane(vBox);
            costs.add(stackPane);
            Button button = new Button(String.valueOf(i + 1));
            // add action listeners to each button to display the corresponding image
            int finalI = i;
            button.setOnAction(event -> {
                try {
                    if (fusion && !fusionning){
                        switch (pieces.get(finalI)){
                            case ROOK -> {if (MINCOSTROOKFUSION <= coins) displayImage(stage, pieces.get(finalI), coins);}
                            case KNIGHT -> {if (MINCOSTKNIGHTFUSION <= coins) displayImage(stage, pieces.get(finalI), coins);}
                            case BISHOP -> {if (MINCOSTBISHOPFUSION<= coins) displayImage(stage, pieces.get(finalI), coins);}
                            case QUEEN -> {if (MINCOSTQUEENFUSION <= coins) displayImage(stage, pieces.get(finalI), coins);}
                        }
                    } else {
                        if (pieces.get(finalI).price <= coins)
                            displayImage(stage, pieces.get(finalI), coins);
                    }
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            buttons.add(button);
        }

        // create HBox to hold buttons & images
        HBox imagesHB = null;
        HBox buttonsHB = null;
        switch (pieces.size()){
            case 1 -> {
                imagesHB = new HBox(10, costs.get(0));
                buttonsHB = new HBox(48, buttons.get(0));
            }
            case 2 -> {
                imagesHB = new HBox(10, costs.get(0), costs.get(1));
                buttonsHB = new HBox(48, buttons.get(0), buttons.get(1));
            }
            case 3 -> {
                imagesHB = new HBox(10, costs.get(0), costs.get(1), costs.get(2));
                buttonsHB = new HBox(48, buttons.get(0), buttons.get(1), buttons.get(2));
            }
            case 4 -> {
                imagesHB = new HBox(10, costs.get(0), costs.get(1), costs.get(2), costs.get(3));
                buttonsHB = new HBox(48, buttons.get(0), buttons.get(1), buttons.get(2), buttons.get(3));
            }
            case 5 -> {
                imagesHB = new HBox(10, costs.get(0), costs.get(1), costs.get(2), costs.get(3), costs.get(4));
                buttonsHB = new HBox(48, buttons.get(0), buttons.get(1), buttons.get(2), buttons.get(3), buttons.get(4));
            }
        }

        // Pass button
        // add pass button
        Button passButton = new Button("Passer");
        passButton.setOnAction(e -> {
            try {
                if (!(fusion && fusionning)) displayImage(stage, null, coins);
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        passButton.setAlignment(Pos.CENTER_RIGHT);

        // add gold coin image
        Image coinImage = new Image(CreatePiece.class.getResourceAsStream("goldcoin.png")); // replace "coin.png" with your image file name
        ImageView coinImageView = new ImageView(coinImage);
        Text coinsText = new Text(String.valueOf(coins)); // replace "10" with the actual number of coins
        coinsText.setFill(Color.GOLD);
        coinsText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        //coinsText.setPadding(new Insets(0, 0, 0, 10)); // add left padding
        HBox coinHB = new HBox(coinImageView, coinsText);
        coinHB.setAlignment(Pos.CENTER_LEFT);
        coinHB.setPadding(new Insets(0, 20, 0, 0)); // add left padding


        buttonsHB.setAlignment(Pos.CENTER);
        imagesHB.setAlignment(Pos.CENTER);

        // create VBox to hold image views and buttons
        VBox root = new VBox(20);
        root.getChildren().addAll(coinHB, imagesHB, buttonsHB, passButton);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // create scene and set stage
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Les " + ((player == Player.WHITE)? "blancs" : "noirs") + " choisissent");
        stage.show();
    }


    private void displayImage(Stage stage, Piece.Type chosenPiece, int coins) throws IOException, InterruptedException {

        if (player == Player.BLACK){
            System.out.println("Nfusion: " + nFusion);
            System.out.println("NCustom: " + nCustom);
            System.out.println("fusion: " + fusion);
            System.out.println("fusioning: " + fusionning);
        }
            if (fusion) {
                if (fusionning) {
                    nFusion++;
                    int x = choseSquare((player == Player.WHITE)? whiteCases : blackCases);
                    int y = (player == Player.WHITE)? 7 : 0;
                    System.out.println(chosenPiece);
                    Piece piece = Piece.piece(chosenPiece, x, y, player);
                    if (player == Player.WHITE)
                        whitePieces.add(piece);
                    else
                        blackPieces.add(piece);

                    fusionning = false;

                    if (nFusion == NFUSIONPIECES) {
                        nFusion = 0;
                        fusion = false;
                        chosePieces(stage, customPieces, coins - piece.getType().price);
                    }
                    else
                        chosePieces(stage, basicPieces, coins - piece.getType().price);
                } else {
                    if (chosenPiece == null){
                        nFusion++;
                        if (nFusion == NFUSIONPIECES) {
                            nFusion = 0;
                            fusion = false;
                            chosePieces(stage, customPieces, coins);
                        }
                        else
                            chosePieces(stage, basicPieces, coins);
                    } else {
                        fusionPiece = chosenPiece;
                        fusionning = true;
                        switch (fusionPiece) {
                            case ROOK -> chosePieces(stage, new ArrayList<>(List.of(TROJANHORSE, LIGHTHOUSE)), coins);
                            case KNIGHT -> chosePieces(stage, new ArrayList<>(List.of(TROJANHORSE, NAZGUL, QUEENIGHT, BUCEPHALE)), coins);
                            case BISHOP -> chosePieces(stage, new ArrayList<>(List.of(NAZGUL, CARDINAL)), coins);
                            case QUEEN -> chosePieces(stage, new ArrayList<>(List.of(QUEENIGHT, CHAOS)), coins);
                        }
                    }
                }
            } else {

                if (customPiece) {
                    // Custom piece choice
                    nCustom++;

                    // If player pressed "pass", pass
                    if (chosenPiece != null) {

                        int x = choseSquare((player == Player.WHITE) ? whiteCases : blackCases);
                        int y = (player == Player.WHITE) ? 7 : 0;
                        Piece piece = Piece.piece(chosenPiece, x, y, player);
                        if (player == Player.WHITE)
                            whitePieces.add(piece);
                        else
                            blackPieces.add(piece);
                        if (nCustom == NMAXCUSTOMPIECES) {
                            if (player == Player.BLACK)
                                blackCases = new ArrayList<>(List.of("a", "b", "c", "d", "e", "f", "g", "h"));
                            else
                                whiteCases = new ArrayList<>(List.of("a", "b", "c", "d", "e", "f", "g", "h"));
                            customPiece = false;
                            nCustom = 0;
                            chosePieces(stage, pawns, coins - piece.getType().price);

                            /*if (player == Player.BLACK) {
                                blackAndWhitePieces.addAll(whitePieces);
                                blackAndWhitePieces.addAll(blackPieces);
                                FXMLLoader fxmlLoader = new FXMLLoader(CreatePiece.class.getResource("sample.fxml"));
                                Parent root = fxmlLoader.load();
                                stage.setScene(new Scene(root, Main.BOARD_DIM, Main.BOARD_DIM));
                            } else {
                                nCustom = 0;
                                fusion = true;
                                player = Player.BLACK;
                                chosePieces(stage, basicPieces, Main.N_COINS);
                            }*/
                        } else
                            chosePieces(stage, customPieces, coins - piece.getType().price);

                    } else {
                        if (nCustom == NMAXCUSTOMPIECES) {
                            if (player == Player.BLACK)
                                blackCases = new ArrayList<>(List.of("a", "b", "c", "d", "e", "f", "g", "h"));
                             else
                                whiteCases = new ArrayList<>(List.of("a", "b", "c", "d", "e", "f", "g", "h"));

                            customPiece = false;
                            nCustom = 0;
                            chosePieces(stage, pawns, coins);
                            /*if (player == Player.BLACK) {
                                stage.setTitle("Fairy Chess");
                                blackAndWhitePieces.addAll(whitePieces);
                                blackAndWhitePieces.addAll(blackPieces);
                                FXMLLoader fxmlLoader = new FXMLLoader(CreatePiece.class.getResource("sample.fxml"));
                                Parent root = fxmlLoader.load();
                                stage.setScene(new Scene(root, Main.BOARD_DIM, Main.BOARD_DIM));
                            } else {
                                nCustom = 0;
                                fusion = true;
                                player = Player.BLACK;
                                chosePieces(stage, basicPieces, Main.N_COINS);
                            }*/
                        } else
                            chosePieces(stage, customPieces, coins);
                    }
                } else {
                    // Pawns choice
                    nPawns++;

                    if (chosenPiece != null){

                        int x = choseSquare((player == Player.WHITE) ? whiteCases : blackCases);
                        int y = (player == Player.WHITE) ? 6 : 1;
                        Piece piece = Piece.piece(chosenPiece, x, y, player);
                        if (player == Player.WHITE)
                            whitePieces.add(piece);
                        else
                            blackPieces.add(piece);

                        if (nPawns == NMAXPAWNS) {
                            if (player == Player.BLACK) {
                                blackAndWhitePieces.addAll(whitePieces);
                                blackAndWhitePieces.addAll(blackPieces);
                                FXMLLoader fxmlLoader = new FXMLLoader(CreatePiece.class.getResource("sample.fxml"));
                                Parent root = fxmlLoader.load();
                                stage.setScene(new Scene(root, Main.BOARD_DIM, Main.BOARD_DIM));
                            } else {
                                nCustom = 0;
                                nPawns = 0;
                                fusion = true;
                                customPiece = true;
                                player = Player.BLACK;
                                chosePieces(stage, basicPieces, Main.N_COINS);
                            }
                        } else
                            chosePieces(stage, pawns, coins - piece.getType().price);
                    } else {
                        if (nPawns == NMAXPAWNS) {
                            if (player == Player.BLACK) {
                                stage.setTitle("Fairy Chess");
                                blackAndWhitePieces.addAll(whitePieces);
                                blackAndWhitePieces.addAll(blackPieces);
                                FXMLLoader fxmlLoader = new FXMLLoader(CreatePiece.class.getResource("sample.fxml"));
                                Parent root = fxmlLoader.load();
                                stage.setScene(new Scene(root, Main.BOARD_DIM, Main.BOARD_DIM));
                            } else {
                                nCustom = 0;
                                nPawns = 0;
                                fusion = true;
                                customPiece = true;
                                player = Player.BLACK;
                                chosePieces(stage, basicPieces, Main.N_COINS);
                            }
                        } else
                            chosePieces(stage, pawns, coins);
                    }
                }
            }
        //}
    }

    // Returns the x value chosen by the user (0-7)
    private static int choseSquare(List<String> validSquares) {
        String input = "";

        while (!validSquares.contains(input)) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Entrez la case sur laquelle vous voulez poser la piece");
            dialog.setContentText(validSquares.toString());
            input = dialog.showAndWait().orElse("");
        }

        validSquares.remove(input);

        return switch (input) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            case "e" -> 4;
            case "f" -> 5;
            case "g" -> 6;
            case "h" -> 7;
            default -> throw new IllegalArgumentException();
        };
    }
}
