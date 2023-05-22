package com.example.demo.pieces;

import com.example.demo.player.Player;

public abstract class DoubleTurnPiece extends Piece{
    private boolean justPlayed = false;
    public DoubleTurnPiece(Player player, int x, int y) {
        super(player, x, y);
    }
    @Override
    public boolean playsTwice() {
        return true;
    }

    @Override
    public boolean justPlayed() {
        return justPlayed;
    }
    @Override
    public void setJustPlayed(boolean justPlayed) {
        this.justPlayed = justPlayed;
    }
}
