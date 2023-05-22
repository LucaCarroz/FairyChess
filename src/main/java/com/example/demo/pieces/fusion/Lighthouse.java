package com.example.demo.pieces.fusion;

import com.example.demo.board.Move;
import com.example.demo.pieces.DoubleTurnPiece;
import com.example.demo.pieces.basic.Rook;
import com.example.demo.player.Player;

import java.util.List;

import static com.example.demo.pieces.Piece.Type.LIGHTHOUSE;

/**
 * Double rook (plays twice)
 */
public class Lighthouse extends DoubleTurnPiece {

    public Lighthouse(Player player, int x, int y) {
        super(player, x, y);
        this.type = LIGHTHOUSE;
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
        return Rook.getAllPossibleMovesFromPos(x, y);
    }

}
