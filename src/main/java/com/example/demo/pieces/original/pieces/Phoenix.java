package com.example.demo.pieces.original.pieces;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.pieces.basic.Knight;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.PHOENIX;

public class Phoenix extends Piece {

    public Phoenix(Player player, int x, int y) {
        super(player, x, y);
        this.type = PHOENIX;
        setImage();
    }

    @Override
    public List<Move> getAllPossibleMoves() {
        return possibleMoves(x, y);
    }

    public static List<Move> getAllPossibleMovesFromPos(int x, int y){
        return possibleMoves(x, y);
    }

    private static List<Move> possibleMoves(int x, int y){
        ArrayList<Move> allMoves = new ArrayList<>(Knight.getAllPossibleMovesFromPos(x, y));
        allMoves.addAll(Bird.getAllPossibleMovesFromPos(x, y));

        return allMoves;
    }


}
