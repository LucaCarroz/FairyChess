package com.example.demo.pieces.basic;

import com.example.demo.Game;
import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.pieces.Piece.Type.BISHOP;

public class Bishop extends Piece {
    public Bishop(Player player, int posX, int posY) {
        super(player, posX, posY);
        this.type = BISHOP;
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
        Move move;

        List<Move> possMoves = new ArrayList<>();

        for(int i=x-1, j=y+1; i>=0 && j<8; i--, j++){

            move = new Move(i, j);
            if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;

            possMoves.add(move);

            if(getSquareByMove(move).isOccupied() && !getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;
        }

        for(int i=x+1, j=y+1; i<8 && j<8; i++, j++){
            move = new Move(i, j);
            if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;

            possMoves.add(move);

            if(getSquareByMove(move).isOccupied() && !getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;
        }

        for(int i=x+1, j=y-1; i<8 && j>=0; i++, j--){
            move = new Move(i, j);
            if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;

            possMoves.add(move);

            if(getSquareByMove(move).isOccupied() && !getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;
        }

        for(int i=x-1, j=y-1; i>=0 && j>=0; i--, j--){
            move = new Move(i, j);
            if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;

            possMoves.add(move);

            if(getSquareByMove(move).isOccupied() && !getPieceByMove(move).getPlayer().equals(Game.currentPlayer)) break;
        }

        return possMoves.stream()
                .filter(m -> !(getSquareByMove(m).isOccupied() && getPieceByMove(m).getType() == Type.WALL)).toList();
    }
}
