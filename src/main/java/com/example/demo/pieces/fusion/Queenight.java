package com.example.demo.pieces.fusion;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.pieces.basic.Knight;
import com.example.demo.pieces.basic.Queen;
import com.example.demo.pieces.basic.Rook;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.QUEENIGHT;

// Queen + Knight
public class Queenight extends Piece {

    public Queenight(Player player, int x, int y) {
        super(player, x, y);
        this.type = QUEENIGHT;
        setImage();
    }

    @Override
    public List<Move> getAllPossibleMoves() {
        ArrayList<Move> allMoves = new ArrayList<>(Knight.getAllPossibleMovesFromPos(x, y));
        allMoves.addAll(Queen.getAllPossibleMovesFromPos(x,y ));

        return allMoves;
    }


}
