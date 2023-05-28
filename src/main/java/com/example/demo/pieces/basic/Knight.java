package com.example.demo.pieces.basic;

import com.example.demo.Game;
import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.pieces.Piece.Type.KNIGHT;

public class Knight extends Piece {
    public Knight(Player player, int posX, int posY) {
        super(player, posX, posY);
        this.type = KNIGHT;
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

        moves.add(new Move(x+2, y+1));
        moves.add(new Move(x+2, y-1));
        moves.add(new Move(x+1, y+2));
        moves.add(new Move(x-1, y+2));
        moves.add(new Move(x-2, y+1));
        moves.add(new Move(x-2, y-1));
        moves.add(new Move(x+1, y-2));
        moves.add(new Move(x-1, y-2));

        for(Move move : moves){
            if(getSquareByMove(move) != null){
                if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) continue;

                possMoves.add(move);

            }
        }

        return possMoves.stream()
                .filter(m -> !(getSquareByMove(m).isOccupied() && getPieceByMove(m).isInvincible()))
                .toList();
    }

}
