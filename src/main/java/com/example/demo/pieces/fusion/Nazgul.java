package com.example.demo.pieces.fusion;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.pieces.basic.Bishop;
import com.example.demo.pieces.basic.Knight;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.NAZGUL;

// Bishop + Knight
public class Nazgul extends Piece {

    public Nazgul(Player player, int x, int y) {
        super(player, x, y);
        this.type = NAZGUL;
        setImage();
    }

    @Override
    public List<Move> getAllPossibleMoves() {

        ArrayList<Move> allMoves = new ArrayList<>(Bishop.getAllPossibleMovesFromPos(x, y));
        allMoves.addAll(Knight.getAllPossibleMovesFromPos(x,y ));

        return allMoves;
    }
}
