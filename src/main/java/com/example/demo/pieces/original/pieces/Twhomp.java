package com.example.demo.pieces.original.pieces;

import com.example.demo.Game;
import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.TWHOMP;

public class Twhomp extends Piece {

    public Twhomp(Player player, int x, int y) {
        super(player, x, y);
        this.type = TWHOMP;
        setImage();
    }

    @Override
    public List<Move> getAllPossibleMoves() {
        return possibleMoves(x, y);
    }

    public static List<Move> getAllPossibleMovesFromPos(int x, int y){
        return possibleMoves(x, y);
    }

    private static List<Move> possibleMoves(int x, int y) {

        Move move;

        List<Move> possMoves = new ArrayList<>();

        for(int j=y-1; j>=0; j--){
            move = new Move(x, j);
            if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;

            possMoves.add(move);

            //if(getSquareByMove(move).isOccupied() && !getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;
        }

        for(int j=y+1; j<8; j++){
            move = new Move(x, j);
            if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;

            possMoves.add(move);

            //if(getSquareByMove(move).isOccupied() && !getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;
        }

        if(x>0 && (!getSquare(x-1, y).isOccupied() || !getPiece(x-1, y).getPlayer().equals(Game.currentPlayer)))
            possMoves.add(new Move(x-1, y));

        if(x<7 && (!getSquare(x+1, y).isOccupied() || !getPiece(x+1, y).getPlayer().equals(Game.currentPlayer)))
            possMoves.add(new Move(x+1, y));

        return possMoves.stream()
                .filter(m -> !(getSquareByMove(m).isOccupied() && getPieceByMove(m).getType() == Type.WALL))
                .toList();
    }
}
