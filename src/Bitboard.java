/* Bitboard position
   RANK
   0   0  1  2  3  4  5  6  7
   1   8  9  10 11 12 13 14 15
   2   16 17 18 19 20 21 22 23
   3   24 25 26 27 28 29 30 31
   4   32 33 34 35 36 37 38 39
   5   40 41 42 43 44 45 46 47
   6   48 49 50 51 52 53 54 55
   7   56 57 58 59 60 61 62 63

       A  B  C  D  E  F  G  H  <- FILE
*/
public class Bitboard {
    long white;
    long black;
    long pawns;
    long knights;
    long bishops;
    long rooks;
    long queens;
    long kings;

    String activeColor;
    String castlingAvailability;
    String enPassantSquare;
    String halfmoveClock;
    String fullmoveClock;

    final long SQUARES = 64;

    public Bitboard(){
        this.clear();
    }

    public void clear() {
        this.white = 0L;
        this.black = 0L;
        this.pawns = 0L;
        this.knights = 0L;
        this.bishops = 0L;
        this.rooks = 0L;
        this.queens = 0L;
        this.kings = 0L;
    }

    public void loadFEN(String fen) {
        this.clear();
        String[] fenComponents = fen.split(" ");
        String[] ranks = fenComponents[0].split("/");
        this.castlingAvailability = fenComponents[2];
        this.activeColor = fenComponents[1];
        this.enPassantSquare = fenComponents[3];
        this.halfmoveClock = fenComponents[4];
        this.fullmoveClock = fenComponents[5];

        long curColumn;
        long bitmaskOffset;
        for (int i = 0; i < ranks.length; i++) {
            curColumn = 0L;
            for (int j = 0; j < ranks[i].length(); j++) {
                bitmaskOffset = (8L * i) + curColumn;
                // Empty squares
                if (Character.isDigit(ranks[i].charAt(j))) {
                    curColumn += Character.getNumericValue(ranks[i].charAt(j));

                // White piece
                } else if (Character.isUpperCase(ranks[i].charAt(j))) {
                    this.white |= 1L << bitmaskOffset;
                    switch (ranks[i].charAt(j)) {
                        case 'P':
                            this.pawns |= 1L << bitmaskOffset;
                            break;
                        case 'B':
                            this.bishops |= 1L << bitmaskOffset;
                            break;
                        case 'N':
                            this.knights |= 1L << bitmaskOffset;
                            break;
                        case 'R':
                            this.rooks |= 1L << bitmaskOffset;
                            break;
                        case 'Q':
                            this.queens |= 1L << bitmaskOffset;
                            break;
                        case 'K':
                            this.kings |= 1L << bitmaskOffset;
                            break;
                        default:
                            System.err.println("ERROR: Invalid character in FEN: " + ranks[i].charAt(j));
                            break;
                    }
                    curColumn++;

                // Black piece
                } else {
                    this.black |= 1L << bitmaskOffset;
                    switch (ranks[i].charAt(j)) {
                        case 'p':
                            this.pawns |= 1L << bitmaskOffset;
                            break;
                        case 'b':
                            this.bishops |= 1L << bitmaskOffset;
                            break;
                        case 'n':
                            this.knights |= 1L << bitmaskOffset;
                            break;
                        case 'r':
                            this.rooks |= 1L << bitmaskOffset;
                            break;
                        case 'q':
                            this.queens |= 1L << bitmaskOffset;
                            break;
                        case 'k':
                            this.kings |= 1L << bitmaskOffset;
                            break;
                        default:
                            System.err.println("ERROR: Invalid character in FEN: " + ranks[i].charAt(j));
                            break;
                    }
                    curColumn++;

                }
            }
        }
    }

    public void print() {
        for (long pos = 0L; pos < SQUARES; pos++) {
            if (pos % 8 == 0) {
                System.out.println();
            }
            this.print_square(pos);
        }
    }

    /* Private methods */
    private void print_square(long pos) {
        if (this.bit_set(pos, this.queens)) {
            if (this.bit_set(pos, this.white)) {
                System.out.print("♕");
            } else {
                System.out.print("♛");
            }

        } else if (this.bit_set(pos, this.kings)) {
            if (this.bit_set(pos, this.white)) {
                System.out.print("♔");
            } else {
                System.out.print("♚");
            }

        } else if (this.bit_set(pos, this.rooks)) {
            if (this.bit_set(pos, this.white)) {
                System.out.print("♖");
            } else {
                System.out.print("♜");
            }

        } else if (this.bit_set(pos, this.bishops)) {
            if (this.bit_set(pos, this.white)) {
                System.out.print("♗");
            } else {
                System.out.print("♝");
            }

        } else if (this.bit_set(pos, this.knights)) {
            if (this.bit_set(pos, this.white)) {
                System.out.print("♘");
            } else {
                System.out.print("♞");
            }

        } else if (this.bit_set(pos, this.pawns)) {
            if (this.bit_set(pos, this.white)) {
                System.out.print("♙");
            } else {
                System.out.print("♟");
            }

        } else {
            System.out.print(".");
        }
    }
    private boolean bit_set(long bit_pos, long board) {
        return ((board >> bit_pos) & 1L) == 1;
    }
}