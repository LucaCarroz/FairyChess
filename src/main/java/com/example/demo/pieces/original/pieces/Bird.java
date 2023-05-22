package com.example.demo.pieces.original.pieces;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.BIRD;

public class Bird extends Piece {

    public Bird(Player player, int x, int y) {
        super(player, x, y);
        this.type = BIRD;
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
        ArrayList<Move> allMoves = new ArrayList<>(Wallaby.getAllPossibleMovesFromPos(x, y));
        allMoves.addAll(Arachne.getAllPossibleMovesFromPos(x, y));

        return allMoves;
    }
}
