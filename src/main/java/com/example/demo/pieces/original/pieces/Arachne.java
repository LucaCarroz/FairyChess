package com.example.demo.pieces.original.pieces;

import com.example.demo.Game;
import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.ARACHNE;

public class Arachne extends Piece {
    public Arachne(Player player, int x, int y) {
        super(player, x, y);
        this.type = ARACHNE;
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

        List<Move> moves = new ArrayList<>();
        List<Move> possMoves = new ArrayList<>();

        moves.add(new Move(x+2, y+2));
        moves.add(new Move(x+2, y-2));
        moves.add(new Move(x-2, y+2));
        moves.add(new Move(x-2, y-2));

        for(Move move : moves){
            if(getSquareByMove(move) != null){
                if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) continue;

                possMoves.add(move);

            }
        }

        return possMoves.stream()
                .filter(m -> !(getSquareByMove(m).isOccupied() && getPieceByMove(m).getType() == Type.WALL))
                .toList();
    }
}
