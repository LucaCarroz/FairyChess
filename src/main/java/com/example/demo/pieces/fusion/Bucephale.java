package com.example.demo.pieces.fusion;

import com.example.demo.board.Move;
import com.example.demo.pieces.DoubleTurnPiece;
import com.example.demo.pieces.basic.Knight;
import com.example.demo.player.Player;

import java.util.List;

import static com.example.demo.pieces.Piece.Type.BUCEPHALE;

/**
 * Double knight (plays twice)
 */
public class Bucephale extends DoubleTurnPiece {
    public Bucephale(Player player, int x, int y) {
        super(player, x, y);
        this.type = BUCEPHALE;
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
        return Knight.getAllPossibleMovesFromPos(x, y);
    }
}
