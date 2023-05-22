package com.example.demo.pieces.fusion;

import com.example.demo.board.Move;
import com.example.demo.pieces.DoubleTurnPiece;
import com.example.demo.pieces.basic.Bishop;
import com.example.demo.player.Player;

import java.util.List;

import static com.example.demo.pieces.Piece.Type.CARDINAL;

/**
 * Double bishop (plays twice)
 */
public class Cardinal extends DoubleTurnPiece {
    public Cardinal(Player player, int x, int y) {
        super(player, x, y);
        this.type = CARDINAL;
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
        return Bishop.getAllPossibleMovesFromPos(x, y);
    }
}
