public class Main {
    public static void main(String[] args) {
        Bitboard board = new Bitboard();
        String startingPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        board.loadFEN(startingPosition);
        board.print();

        MoveGenerator moveGenerator = new MoveGenerator(board);
    }
}