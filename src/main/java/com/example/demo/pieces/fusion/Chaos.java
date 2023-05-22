package com.example.demo.pieces.fusion;

import com.example.demo.Game;
import com.example.demo.board.Move;
import com.example.demo.pieces.DoubleTurnPiece;
import com.example.demo.pieces.basic.Queen;
import com.example.demo.player.Player;

import java.util.List;

import static com.example.demo.pieces.Piece.Type.CHAOS;

/**
 * Double Queen (plays twice)
 */
public class Chaos extends DoubleTurnPiece {

    public Chaos(Player player, int x, int y) {
        super(player, x, y);
        this.type = CHAOS;
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
        return Queen.getAllPossibleMovesFromPos(x, y);
    }

}
