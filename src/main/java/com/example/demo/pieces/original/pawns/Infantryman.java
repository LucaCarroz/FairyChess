package com.example.demo.pieces.original.pawns;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.pieces.basic.King;
import com.example.demo.pieces.basic.Pawn;
import com.example.demo.player.Player;

import java.util.List;

import static com.example.demo.pieces.Piece.Type.INFANTRYMAN;

public class Infantryman extends Piece {

    public Infantryman(Player player, int x, int y) {
        super(player, x, y);
        this.type = INFANTRYMAN;
        setImage();
    }
    @Override
    public List<Move> getAllPossibleMoves() {
        return Pawn.getAllPossibleMovesFromPos(x, y, player);
    }

    @Override
    public boolean isInvincible() {
        return true;
    }
}
