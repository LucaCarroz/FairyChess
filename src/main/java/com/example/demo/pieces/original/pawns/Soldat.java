package com.example.demo.pieces.original.pawns;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.pieces.basic.King;
import com.example.demo.pieces.basic.Pawn;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.SOLDAT;

public class Soldat extends Piece {
    public Soldat(Player player, int x, int y) {
        super(player, x, y);
        this.type = SOLDAT;
        setImage();
    }

    @Override
    public List<Move> getAllPossibleMoves() {
        return King.getAllPossibleMovesFromPos(x, y);
    }
}
