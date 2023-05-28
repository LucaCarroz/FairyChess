package com.example.demo.pieces;

import com.example.demo.CreatePiece;
import com.example.demo.pieces.original.pawns.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import com.example.demo.Game;
import com.example.demo.board.Move;
import com.example.demo.board.Square;
import com.example.demo.pieces.basic.*;
import com.example.demo.pieces.fusion.*;
import com.example.demo.pieces.original.pieces.*;
import com.example.demo.player.Player;

import java.util.List;

import static com.example.demo.pieces.Piece.Type.*;

abstract public class Piece extends ImageView {

    // All types of pieces. We can freely add new pieces
    public enum Type{


        /**
         * Basic pieces
         */
        PAWN(1),
        ROOK(5),
        KNIGHT(3),
        BISHOP(3),
        QUEEN(10),
        KING(0),
        /**
         * Fusion pieces
         */
        NAZGUL(9), // [Fou + Cavalier]
        TROJANHORSE(10), // [Tour + Cavalier]
        QUEENIGHT(15), // cavareine/pégase [Cavalier + Reine]
        CHAOS(30), // chaos [Reine + Reine]
        BUCEPHALE(9), // bucéphale [Cavalier + Cavalier]
        CARDINAL(9), // cardinal [Fou + Fou]
        LIGHTHOUSE(15), // phare [Tour + Tour]


        /**
         * Original pawns
         */
        MINION(2), // larbin //TODO change cost
        SOLDAT(3), // soldat //TODO change cost
        WALL(1), // muraille //TODO change cost
        INFANTRYMAN(3), // fantassin //TODO change cost
        CHAMPIFACE(0), // champiface //TODO change cost
        CURSEDGRAVE(-1), // tombe maudite, //TODO
        /**
         * Original pieces
         */
        ARACHNE(1), // arachne
        WALLABY(1), // wallaby
        BIRD(2), // piaf
        PHOENIX(4), // phénix TODO: add respawn
        DRAGON(3), // dragon
        GRYFFON(7), // griffon
        TWHOMP(5), // twhomp
        EXCALIBUR(-1), // excalibur //TODO
        SAURON(10), // anciennement "arbre", //TODO
        BOAT(9), // navire //TODO
        /**
         * Not implemented yet (tour + tour, etc)
         */
        JOKER(-1), //TODO
        MONARCH(-1), /* monarque, */ //TODO
        BALLIST(-1); // balliste (déplacements pas décrit sur le gdoc!!!); //TODO
        public final int price;

        Type(int price){
            this.price = price;
        }

    }

    protected Type type;
    protected final Player player;
    protected int x, y;
    protected List<Move> possibleMoves;

    public Piece(Player player, int x, int y){
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public Type getType() {
        return type;
    }

    public Player getPlayer(){
        return this.player;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImage(){
        System.out.println("com/example/demo/images" + this + ".png");
        super.setImage(new Image(CreatePiece.class.getResourceAsStream("images/" + this + ".png")));
    }

    abstract public List<Move> getAllPossibleMoves();

    public void showAllPossibleMoves(boolean val){

        possibleMoves = getAllPossibleMoves();

        if(val){
            Glow glow = new Glow();
            glow.setLevel(0.7);
            for(Move move : possibleMoves){
                Square square = getSquareByMove(move);
                square.setEffect(glow);

                Piece piece = getPieceByMove(move);
                if(piece == null) continue;
                if(piece.type == KING){
                    square.setBorder(new Border(new BorderStroke(Color.DARKRED,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.5))));
                }
                else{
                    square.setBorder(new Border(new BorderStroke(Color.BLUE,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.5))));
                }
            }
        }
        else{
            for(Square square : Game.cb.squares){
                //Square square = getSquareByName(move);
                square.setEffect(null);
                square.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
    }

    public static Square getSquareByName(String name){
        for(Square square : Game.cb.squares){
            if(square.getName().equals(name)){
                return square;
            }
        }

        return null;
    }

    public static Square getSquare(int x, int y){
        return getSquareByName("Square" + (x) + (y));
    }

    public static Square getSquareByMove(Move move){
        return getSquare(move.x, move.y);
    }

    public static Piece getPieceByName(String name){
        for(Square square : Game.cb.squares){
            if(square.getChildren().size() == 0) continue;

            if(square.getName().equals(name))
                return (Piece) square.getChildren().get(0);

        }
        return null;
    }

    public static Piece getPiece(int x, int y){
        return getPieceByMove(new Move(x, y));
    }

    public static Piece getPieceByMove(Move move){
        return getPieceByName("Square" + (move.x) + (move.y));
    }

    public boolean isInvincible(){
        return false;
    }

    @Override
    public String toString() {
        return this.player.toString().toLowerCase() + this.type.toString().substring(0,1).toUpperCase() + this.type.toString().substring(1).toLowerCase();
    }

    public static Piece piece(Type type, int x, int y, Player p){
        return switch (type){
            // basic
            case BISHOP -> new Bishop(p, x, y);
            case KNIGHT -> new Knight(p, x, y);
            case QUEEN -> new Queen(p, x, y);
            case ROOK -> new Rook(p, x, y);
            case PAWN -> new Pawn(p, x, y);
            case KING -> new King(p, x, y);
            // fusion
            case NAZGUL -> new Nazgul(p, x, y);
            case TROJANHORSE -> new Trojan_Horse(p, x, y);
            case QUEENIGHT -> new Queenight(p, x, y);
            case BUCEPHALE -> new Bucephale(p, x, y);
            case CARDINAL -> new Cardinal(p, x, y);
            case CHAOS -> new Chaos(p, x, y);
            case LIGHTHOUSE -> new Lighthouse(p, x, y);
            // custom pieces
            case ARACHNE -> new Arachne(p, x, y);
            case WALLABY -> new Wallaby(p, x, y);
            case BIRD -> new Bird(p, x, y);
            case PHOENIX -> new Phoenix(p, x, y);
            case DRAGON -> new Dragon(p, x, y);
            case GRYFFON -> new Gryffon(p, x, y);
            case TWHOMP -> new Twhomp(p, x, y);
            // custom pawns
            case WALL -> new Wall(p, x, y);
            case MINION -> new Minion(p, x, y);
            case SOLDAT -> new Soldat(p, x, y);
            case INFANTRYMAN -> new Infantryman(p, x, y);
            case CHAMPIFACE -> new Champiface(p, x, y);
            /*
                    CURSED_GRAVE(-1), // tombe maudite,

                    EXCALIBUR(-1), // excalibur
                    SAURON(10), // anciennement "arbre",
                    BOAT(9), // navire*/
                    /**
                     * Not implemented yet (pas sur le gdoc)
                     *//*
                    JOKER(-1),
                    MONARCH(-1), // monarque,
                    BALLIST(-1); // balliste (déplacements pas décrit sur le gdoc!!!);*/
            default -> throw new IllegalArgumentException();
        };
    }

    /*
    public static Piece fusionPiece(Type t1, Type t2, int x, int y, Player p){
        return switch (t1){
            case ROOK -> switch(t2){
                case ROOK -> new Lighthouse(p, x, y);
                case KNIGHT -> new Trojan_Horse(p, x, y);
                //case BISHOP -> new Bishop(p, x, y);
                //case QUEEN -> new Queen(p, x, y);
                default -> throw new IllegalArgumentException();
            };
            case KNIGHT -> switch(t2){
                case ROOK -> new Trojan_Horse(p, x, y);
                case KNIGHT -> new Bucephale(p, x, y);
                case BISHOP -> new Nazgul(p, x, y);
                case QUEEN -> new Queenight(p, x, y);
                default -> throw new IllegalArgumentException();
            };
            case BISHOP -> switch(t2){
                //case ROOK -> new Rook(p, x, y);
                case KNIGHT -> new Nazgul(p, x, y);
                case BISHOP -> new Cardinal(p, x, y);
                //case QUEEN -> new Queen(p, x, y);
                default -> throw new IllegalArgumentException();
            };
            case QUEEN -> switch(t2){
                //case ROOK -> new Rook(p, x, y);
                case KNIGHT -> new Queenight(p, x, y);
                //case BISHOP -> new Bishop(p, x, y);
                case QUEEN -> new Chaos(p, x, y);
                default -> throw new IllegalArgumentException();
            };
            default -> throw new IllegalArgumentException();
        };
    }

    public static int fusionPieceCost(Type t1, Type t2){
        return switch (t1){
            case ROOK -> switch(t2){
                case ROOK -> LIGHTHOUSE.price;
                case KNIGHT -> TROJANHORSE.price;
                //case BISHOP -> new Bishop(p, x, y);
                //case QUEEN -> new Queen(p, x, y);
                default -> throw new IllegalArgumentException();
            };
            case KNIGHT -> switch(t2){
                case ROOK -> TROJANHORSE.price;
                case KNIGHT -> BUCEPHALE.price;
                case BISHOP -> NAZGUL.price;
                case QUEEN -> QUEENIGHT.price;
                default -> throw new IllegalArgumentException();
            };
            case BISHOP -> switch(t2){
                //case ROOK -> new Rook(p, x, y);
                case KNIGHT -> NAZGUL.price;
                case BISHOP -> CARDINAL.price;
                //case QUEEN -> new Queen(p, x, y);
                default -> throw new IllegalArgumentException();
            };
            case QUEEN -> switch(t2){
                //case ROOK -> new Rook(p, x, y);
                case KNIGHT -> QUEENIGHT.price;
                //case BISHOP -> new Bishop(p, x, y);
                case QUEEN -> CHAOS.price;
                default -> throw new IllegalArgumentException();
            };
            default -> throw new IllegalArgumentException();
        };
    }*/
    public int price(){
        return type.price;
    }

    public boolean playsTwice(){
        return false;
    }

    /**
     * Returns true if this piece just once
     * @return true if this piece just once
     */
    public boolean justPlayed() {
        return false;
    }
    public void setJustPlayed(boolean justPlayed) {}
}
