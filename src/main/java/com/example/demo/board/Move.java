package com.example.demo.board;

public class Move {
    public final int x,y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Move(Square square){
        this.x = square.getX();
        this.y = square.getY();
    }


    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Move move)) {
            return false;
        }

        // Compare the data members and return accordingly
        return move.x == x && move.y == y;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x) + Integer.hashCode(y);
    }


}
