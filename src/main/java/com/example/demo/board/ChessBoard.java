package com.example.demo.board;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import com.example.demo.pieces.*;
import com.example.demo.pieces.basic.*;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {

    public static final int SQUARE_DIM = 80;
    private final GridPane chessBoard;
    String theme;
    public ArrayList<Square> squares = new ArrayList<>();

    public ChessBoard(GridPane chessBoard, String theme, List<Piece> pieces){
        this.chessBoard = chessBoard;
        this.theme = theme;

        makeBoard(this.chessBoard, theme, pieces);
    }

    public Square getSquare(int x, int y) {
        for (Square square: squares){
            if (square.getX() == x && square.getY() == y)
                return square;
        }
        throw new IllegalArgumentException("Invalid x and y: x = " + x + ",y = " + y);
    }

    private void makeBoard(GridPane chessBoard, String theme, List<Piece> pieces){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                Square square = new Square(i,j, "Square" + i + j);
                square.setPrefHeight(SQUARE_DIM);
                square.setPrefWidth(SQUARE_DIM);
                square.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                setTheme(square, theme, i, j);
                chessBoard.add(square, i, j, 1, 1);
                squares.add(square);
            }
        }
        addPieces(pieces);
    }

    private void setTheme(Square square, String theme, int i, int j){
        Color color1 = Color.web("#ffffff00");
        Color color2 = Color.web("#ffffff00");

        switch (theme) {
            case "Coral" -> {
                color1 = Color.web("#b1e4b9");
                color2 = Color.web("#70a2a3");
            }
            case "Dusk" -> {
                color1 = Color.web("#cbb7ae");
                color2 = Color.web("#716677");
            }
            case "Wheat" -> {
                color1 = Color.web("#eaefce");
                color2 = Color.web("#bbbe65");
            }
            case "Marine" -> {
                color1 = Color.web("#9dacff");
                color2 = Color.web("#6f74d2");
            }
            case "Emerald" -> {
                color1 = Color.web("#adbd90");
                color2 = Color.web("#6e8f72");
            }
            case "Sandcastle" -> {
                color1 = Color.web("#e4c16f");
                color2 = Color.web("#b88b4a");
            }
        }

        if((i+j)%2==0){
            square.setBackground(new Background(new BackgroundFill(color1, CornerRadii.EMPTY, Insets.EMPTY)));
        }else{
            square.setBackground(new Background(new BackgroundFill(color2, CornerRadii.EMPTY, Insets.EMPTY)));
        }

    }

    private void addPiece(Square square, Piece piece){
        square.getChildren().add(piece);
        square.setOccupancy(true);
    }

    private void addPieces(List<Piece> pieces){
        // Default layout (normal game)
        if (pieces.isEmpty()){
            for(Square square : squares) {
                if (square.isOccupied()) continue;
                if (square.getY() == 1) {
                    addPiece(square, new Pawn(Player.BLACK, square.getX(), square.getY()));
                } else if (square.getY() == 6) {
                    addPiece(square, new Pawn(Player.WHITE, square.getX(), square.getY()));
                } else if (square.getY() == 0) {
                    if (square.getX() == 4) {
                        addPiece(square, new King(Player.BLACK, square.getX(), square.getY()));
                    }
                    if (square.getX() == 3) {
                        addPiece(square, new Queen(Player.BLACK, square.getX(), square.getY()));
                    }
                    if (square.getX() == 2 || square.getX() == 5) {
                        addPiece(square, new Bishop(Player.BLACK, square.getX(), square.getY()));
                    }
                    if (square.getX() == 1 || square.getX() == 6) {
                        addPiece(square, new Knight(Player.BLACK, square.getX(), square.getY()));
                    }
                    if (square.getX() == 0 || square.getX() == 7) {
                        addPiece(square, new Rook(Player.BLACK, square.getX(), square.getY()));
                    }
                } else if (square.getY() == 7) {
                    if (square.getX() == 4) {
                        addPiece(square, new King(Player.WHITE, square.getX(), square.getY()));
                    }
                    if (square.getX() == 3) {
                        addPiece(square, new Queen(Player.WHITE, square.getX(), square.getY()));
                    }
                    if (square.getX() == 2 || square.getX() == 5) {
                        addPiece(square, new Bishop(Player.WHITE, square.getX(), square.getY()));
                    }
                    if (square.getX() == 1 || square.getX() == 6) {
                        addPiece(square, new Knight(Player.WHITE, square.getX(), square.getY()));
                    }
                    if (square.getX() == 0 || square.getX() == 7) {
                        addPiece(square, new Rook(Player.WHITE, square.getX(), square.getY()));
                    }
                }
            }
        } else {
            System.out.println(pieces);
            // Custom layout
            for(Square square : squares) {
                for (Piece piece: pieces){
                    if (square.getX() == piece.x() && square.getY() == piece.y())
                        addPiece(square, piece);
                }
                // TODO: remove this when new pawns implemented
                if (square.getY() == 1)
                    addPiece(square, new Pawn(Player.BLACK, square.getX(), square.getY()));
                if (square.getY() == 6)
                    addPiece(square, new Pawn(Player.WHITE, square.getX(), square.getY()));
                if (square.getX() == 4 && square.getY() == 0)
                    addPiece(square, new King(Player.BLACK, square.getX(), square.getY()));
                if (square.getX() == 4 && square.getY() == 7)
                    addPiece(square, new King(Player.WHITE, square.getX(), square.getY()));
            }
        }
    }

    public GridPane getChessBoard() {
        return chessBoard;
    }
}
