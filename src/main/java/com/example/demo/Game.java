package com.example.demo;

import javafx.event.EventTarget;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.example.demo.board.ChessBoard;
import com.example.demo.board.Move;
import com.example.demo.board.Square;
import com.example.demo.pieces.basic.Pawn;
import com.example.demo.pieces.Piece;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.Main.primaryStage;
import static com.example.demo.pieces.Piece.Type.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Game {

    public static Piece currentPiece;
    public static Player currentPlayer;
    public static ChessBoard cb;
    public static boolean isPlayingTwice;
    public static Piece playingTwice;
    private boolean game;

    private Player playerThatJustAdvancedTwoSquares;
    private Pawn pawnThatJustAdvancedTwoSquares;

    public Game(GridPane chessBoard, String theme, List<Piece> pieces){
        cb = new ChessBoard(chessBoard, theme, pieces);
        currentPiece = null;
        currentPlayer = Player.WHITE;
        isPlayingTwice = false;
        playingTwice = null;
        this.game = true;
        addEventHandlers(cb.getChessBoard());
        playerThatJustAdvancedTwoSquares = null;
    }

    private void addEventHandlers(GridPane chessBoard){
        chessBoard.setOnMouseClicked(event -> {

            EventTarget target = event.getTarget();

            // Clicked on square
            if(target instanceof Square square){
                if(square.isOccupied()){
                    Piece newPiece = (Piece) square.getChildren().get(0);

                    if (isPlayingTwice){
                        if (currentPiece == null){
                            currentPiece = newPiece;
                            if (currentPiece != playingTwice) {
                                currentPiece = null;
                                return;
                            }
                            selectPiece(game);
                        } else {
                            if (newPiece == playingTwice) {
                                deselectPiece(false);
                                currentPiece = newPiece;
                                selectPiece(game);
                            } else {
                                killPiece(square);
                            }
                        }
                    } else {
                        // Selecting a new piece
                        if (currentPiece == null) {
                            currentPiece = newPiece;
                            if (!(currentPiece.getPlayer() == currentPlayer)) {
                                currentPiece = null;
                                return;
                            }
                            selectPiece(game);
                        }
                        // Selecting other piece of same color || Killing a piece
                        else {
                            if (currentPiece.getPlayer() == newPiece.getPlayer()) {
                                deselectPiece(false);
                                currentPiece = newPiece;
                                selectPiece(game);
                            } else {
                                killPiece(square);
                            }
                        }
                    }

                }
                // Dropping a piece on blank square
                else{
                    if (currentPiece != null) dropPiece(square);
                }
            }
            // Clicked on piece
            else if (target instanceof Piece newPiece){
                Square square = (Square) newPiece.getParent();
                // Selecting a new piece
                /*if (isPlayingTwice && newPiece != playingTwice) {
                    if (currentPiece != null) deselectPiece(false);
                    currentPiece = null;
                    return;
                }*/
                if (isPlayingTwice){
                    if (currentPiece == null){
                        currentPiece = newPiece;
                        if (currentPiece != playingTwice) {
                            currentPiece = null;
                            return;
                        }
                        selectPiece(game);
                    } else {
                        if (newPiece == playingTwice) {
                            deselectPiece(false);
                            currentPiece = newPiece;
                            selectPiece(game);
                        } else {
                            killPiece(square);
                        }
                    }
                } else {
                    if (currentPiece == null) {
                        currentPiece = newPiece;
                        if (!(currentPiece.getPlayer() == currentPlayer)) {
                            currentPiece = null;
                            return;
                        }
                        selectPiece(game);
                    }
                    // Selecting other piece of same color || Killing a piece
                    else {
                        if (currentPiece.getPlayer() == newPiece.getPlayer()) {
                            deselectPiece(false);
                            currentPiece = newPiece;
                            selectPiece(game);
                        } else {
                            killPiece(square);
                        }
                    }
                }
            }
        });
    }

    private void selectPiece(boolean game){
        if(!game){
            currentPiece = null;
            return;
        }

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.BLACK);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        currentPiece.setEffect(borderGlow);
        currentPiece.showAllPossibleMoves(true);
    }

    private void deselectPiece(boolean changePlayer){
        currentPiece.setEffect(null);
        currentPiece.showAllPossibleMoves(false);
        currentPiece = null;
        if(changePlayer) {
            currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
            if (playerThatJustAdvancedTwoSquares != null && playerThatJustAdvancedTwoSquares == currentPlayer){
                playerThatJustAdvancedTwoSquares = null;
                pawnThatJustAdvancedTwoSquares.justAdvancedTwoSquares = false;
                pawnThatJustAdvancedTwoSquares = null;
            }
        }
    }

    private void dropPiece(Square square){
        if(!currentPiece.getAllPossibleMoves().contains(new Move(square))) return;

        if (currentPiece.getType() == PAWN && Math.abs(square.getY() - currentPiece.y()) == 2) {
            Pawn pawn = (Pawn) currentPiece;
            pawn.justAdvancedTwoSquares = true;
            pawnThatJustAdvancedTwoSquares = pawn;
            playerThatJustAdvancedTwoSquares = currentPlayer;
        }


        Square initialSquare = (Square) currentPiece.getParent();
        square.getChildren().add(currentPiece);
        square.setOccupancy(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupancy(false);
        currentPiece.setX(square.getX());
        currentPiece.setY(square.getY());

        // Check for "prise en passant"
        if (currentPiece.getType() == PAWN && square.getX() != initialSquare.getX()) {
            int dx = square.getX() - initialSquare.getX();
            Square killedPawn = cb.getSquare(initialSquare.getX() + dx, initialSquare.getY());
            killedPawn.getChildren().remove(0);
            killedPawn.setOccupancy(false);
        }

        if (currentPiece.getType() == TWHOMP){
            int startY = initialSquare.getY();
            int endY = square.getY();
            if (startY > endY){
                for (int i = endY + 1; i < startY; i++) {
                    Square killedPiece = cb.getSquare(initialSquare.getX(), i);
                    if (killedPiece.isOccupied()) {
                        killedPiece.getChildren().remove(0);
                        killedPiece.setOccupancy(false);
                    }
                }
            }
            if (startY < endY){
                for (int i = startY + 1; i < endY; i++) {
                    Square killedPiece = cb.getSquare(initialSquare.getX(), i);
                    if (killedPiece.isOccupied()) {
                        killedPiece.getChildren().remove(0);
                        killedPiece.setOccupancy(false);
                    }
                }
            }
        }

        if (currentPiece.playsTwice()){
            if (currentPiece.justPlayed()){
                isPlayingTwice = false;
                currentPiece.setJustPlayed(false);
                deselectPiece(true);
            } else {
                isPlayingTwice = true;
                playingTwice = currentPiece;
                currentPiece.setJustPlayed(true);
                deselectPiece(false);
            }
        } else {
            deselectPiece(true);
        }
    }

    private void killPiece(Square square){
        if(!currentPiece.getAllPossibleMoves().contains(new Move(square))) return;

        Square initialSquare = (Square) currentPiece.getParent();
        if (currentPiece.getType() == TWHOMP){
            int startY = initialSquare.getY();
            int endY = square.getY();

            if (startY > endY){
                for (int i = endY + 1; i < startY; i++) {
                    Square killedPiece = cb.getSquare(initialSquare.getX(), i);
                    if (killedPiece.isOccupied()) {
                        killedPiece.getChildren().remove(0);
                        killedPiece.setOccupancy(false);
                    }
                }
            }
            if (startY < endY){
                for (int i = startY + 1; i < endY; i++) {
                    Square killedPiece = cb.getSquare(initialSquare.getX(), i);
                    if (killedPiece.isOccupied()) {
                        killedPiece.getChildren().remove(0);
                        killedPiece.setOccupancy(false);
                    }
                }
            }
        }

        Piece killedPiece = (Piece) square.getChildren().get(0);
        if(killedPiece.getType() == KING) {
            this.game = false;
            Stage dialog = new Stage();
            dialog.initStyle(StageStyle.DECORATED); // Set the stage style to transparent

            Text text;
            if (killedPiece.getPlayer() == Player.WHITE){
                text = new Text("Les noirs ont gagné !");
            } else {
                text = new Text("Les blancs ont gagné !");
            }
            text.setFont(Font.font(24));
            text.setTextAlignment(TextAlignment.CENTER);

            Button newGameButton = new Button("Nouvelle partie");
            newGameButton.setOnAction(event -> {
                // Code to start a new game
                dialog.close(); // Close the dialog after starting a new game
                cleanUp();
                new CreatePiece(primaryStage, new ArrayList<>(List.of(ROOK, KNIGHT, BISHOP, QUEEN)), Main.N_COINS);
            });

            VBox vbox = new VBox(text, newGameButton);
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(20);

            Scene dialogScene = new Scene(vbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        }

        square.getChildren().remove(0);
        square.getChildren().add(currentPiece);
        square.setOccupancy(true);
        initialSquare.getChildren().removeAll();
        initialSquare.setOccupancy(false);
        currentPiece.setX(square.getX());
        currentPiece.setY(square.getY());

        if (currentPiece.playsTwice()){
            if (currentPiece.justPlayed()){
                isPlayingTwice = false;
                currentPiece.setJustPlayed(false);
                deselectPiece(true);
            } else {
                isPlayingTwice = true;
                playingTwice = currentPiece;
                currentPiece.setJustPlayed(true);
                deselectPiece(false);
            }
        } else {
            deselectPiece(true);
        }
    }

    private void cleanUp() {
        CreatePiece.nFusion = 0;
        CreatePiece.nCustom = 0;
        CreatePiece.nPawns = 0;
        CreatePiece.player = Player.WHITE;
        CreatePiece.fusionning = false;
        CreatePiece.fusion = true;
        CreatePiece.customPiece = true;
        CreatePiece.fusionPiece = null;
        CreatePiece.whitePieces = new ArrayList<>();
        CreatePiece.whiteCases = new ArrayList<>(List.of("a", "b", "c", "d", "f", "g", "h"));
        CreatePiece.blackPieces = new ArrayList<>();
        CreatePiece.blackCases = new ArrayList<>(List.of("a", "b", "c", "d", "f", "g", "h"));
        CreatePiece.blackAndWhitePieces = new ArrayList<>();
    }


}
