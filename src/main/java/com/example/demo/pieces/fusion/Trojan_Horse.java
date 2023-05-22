package com.example.demo.pieces.fusion;

import com.example.demo.board.Move;
import com.example.demo.pieces.Piece;
import com.example.demo.pieces.basic.Knight;
import com.example.demo.pieces.basic.Rook;
import com.example.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pieces.Piece.Type.TROJANHORSE;

// Rook + Knight
public class Trojan_Horse extends Piece {

    public Trojan_Horse(Player player, int posX, int posY){
        super(player, posX, posY);
        this.type = TROJANHORSE;
        setImage();
    }

    @Override
    public List<Move> getAllPossibleMoves() {
        ArrayList<Move> allMoves = new ArrayList<>(Rook.getAllPossibleMovesFromPos(x, y));
        allMoves.addAll(Knight.getAllPossibleMovesFromPos(x, y));

        return allMoves;
    }

}
