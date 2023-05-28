package com.example.demo.pieces.original.pawns;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.pieces.basic.Pawn;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.MINION;


public class Minion extends Piece {

    public Minion(Player player, int x, int y) {
        super(player, x, y);
        this.type = MINION;
        setImage();
    }

    @Override
    public List<Move> getAllPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<>(Pawn.getAllPossibleMovesFromPos(x, y, player));
        switch (player){
            case BLACK -> {
                if (y < 7){
                    Move move = new Move(x, y + 1);
                    if (getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer() != player)
                        moves.add(move);
                }

            }
            case WHITE -> {
                if (y > 0){
                    Move move = new Move(x, y - 1);
                    if (getSquareByMove(move).isOccupied() && getPieceByMove(move).getPlayer() != player)
                        moves.add(move);
                }

            }
        }
        return moves.stream()
                .filter(m -> !(getSquareByMove(m).isOccupied() && getPieceByMove(m).isInvincible())).toList();
    }
}
