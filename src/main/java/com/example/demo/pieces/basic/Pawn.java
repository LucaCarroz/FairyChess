package com.example.demo.pieces.basic;

import com.example.demo.Game;
import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.PAWN;

public class Pawn extends Piece {

    public boolean justAdvancedTwoSquares;

    public Pawn(Player player, int posX, int posY) {
        super(player, posX, posY);
        this.type = PAWN;
        setImage();
        justAdvancedTwoSquares = false;
    }

    @Override
    public List<Move> getAllPossibleMoves() {
        return possibleMoves(x, y, getPlayer());
    }

    public static List<Move> getAllPossibleMovesFromPos(int x, int y, Player player){
        return possibleMoves(x, y, player);
    }

    private static List<Move> possibleMoves(int x, int y, Player player){

        List<Move> moves = new ArrayList<>();
        List<Move> possMoves = new ArrayList<>();

        if(player == Player.BLACK){
            moves.add(new Move(x+1, y+1));
            moves.add(new Move(x, y+1));
            moves.add(new Move(x-1, y+1));
            if(y == 1 && !getSquare(x, 2).isOccupied()) moves.add(new Move(x, 3));
        }
        else{
            moves.add(new Move(x+1, y-1));
            moves.add(new Move(x, y-1));
            moves.add(new Move(x-1, y-1));
            if(y == 6 && !getSquare(x, 5).isOccupied()) moves.add(new Move(x, 4));
        }

        for(Move move : moves){
            if(getSquareByMove(move) != null){
                if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer() == Game.currentPlayer) continue;
                if(getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer() != Game.currentPlayer && getSquareByMove(move).getX() == x) continue;
                if(!getSquareByMove(move).isOccupied() && getSquareByMove(move).getX() != x) continue;

                possMoves.add(move);
            }
        }

        // check for "prise en passant"
        switch (player) {
            case BLACK -> {
                if (x > 0){
                    Move move = new Move(x-1, y);
                    if (getSquareByMove(move).isOccupied() &&
                            getPieceByMove(move).getPlayer() != player &&
                            getPieceByMove(move).getType() == PAWN &&
                            ((Pawn) getPieceByMove(move)).justAdvancedTwoSquares &&
                            !getSquareByMove(new Move(x-1, y+1)).isOccupied())
                        possMoves.add(new Move(x-1, y+1));
                }
                if (x < 7){
                    Move move = new Move(x+1, y);
                    if (getSquareByMove(move).isOccupied() &&
                            getPieceByMove(move).getPlayer() != player &&
                            getPieceByMove(move).getType() == PAWN &&
                            ((Pawn) getPieceByMove(move)).justAdvancedTwoSquares &&
                            !getSquareByMove(new Move(x+1, y+1)).isOccupied())
                        possMoves.add(new Move(x+1, y+1));
                }
            }
            case WHITE -> {
                if (x > 0){
                    Move move = new Move(x-1, y);
                    if (getSquareByMove(move).isOccupied() &&
                            getPieceByMove(move).getPlayer() != player &&
                            getPieceByMove(move).getType() == PAWN &&
                            ((Pawn) getPieceByMove(move)).justAdvancedTwoSquares &&
                            !getSquareByMove(new Move(x-1, y-1)).isOccupied())
                        possMoves.add(new Move(x-1, y-1));
                }
                if (x < 7){
                    Move move = new Move(x+1, y);
                    if (getSquareByMove(move).isOccupied() &&
                            getPieceByMove(move).getPlayer() != player &&
                            getPieceByMove(move).getType() == PAWN &&
                            ((Pawn) getPieceByMove(move)).justAdvancedTwoSquares &&
                            !getSquareByMove(new Move(x+1, y-1)).isOccupied())
                        possMoves.add(new Move(x+1, y-1));
                }
            }
        }

        return possMoves.stream()
                .filter(m -> !(getSquareByMove(m).isOccupied() && getPieceByMove(m).getType() == Type.WALL)).toList();
    }


}
