package com.example.demo.board;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import com.example.demo.CreatePiece;
import com.example.demo.Game;

import static com.example.demo.board.Theme.*;

public class Controller {

    @FXML
    GridPane chessBoard;

    public void initialize(){

        // Themes are Coral, Dusk, Wheat, Marine, Emerald, Sandcastle

        Game game = new Game(chessBoard, CORAL, CreatePiece.blackAndWhitePieces);

    }
}
