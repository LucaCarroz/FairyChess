package com.example.demo.pieces.original.pawns;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.pieces.basic.Pawn;
import com.example.demo.player.Player;

import java.util.List;

import static com.example.demo.pieces.Piece.Type.CHAMPIFACE;

public class Champiface extends Piece {
    public Champiface(Player player, int x, int y) {
        super(player, x, y);
        this.type = CHAMPIFACE;
        setImage();
    }
    @Override
    public List<Move> getAllPossibleMoves() {
        switch (player){
            case BLACK -> {
                Move move = new Move(x, y + 1);
                if (y < 7 && (!getSquareByMove(move).isOccupied() || (getPieceByMove(move).getPlayer() != player && !getPieceByMove(move).isInvincible())))
                    return List.of(move);
            }
            case WHITE -> {
                Move move = new Move(x, y - 1);
                if (y > 0 && (!getSquareByMove(move).isOccupied() || (getPieceByMove(move).getPlayer() != player && !getPieceByMove(move).isInvincible())))
                    return List.of(move);
            }
        }
        return List.of();
    }
}
