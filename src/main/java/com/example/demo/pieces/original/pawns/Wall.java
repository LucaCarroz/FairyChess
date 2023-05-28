package com.example.demo.pieces.original.pawns;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.player.Player;

import java.util.List;

import static com.example.demo.pieces.Piece.Type.WALL;

public class Wall extends Piece {

    public Wall(Player player, int x, int y) {
        super(player, x, y);
        this.type = WALL;
        setImage();
    }

    @Override
    public List<Move> getAllPossibleMoves() {
        return List.of();
    }
}
