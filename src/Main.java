public class Main {

    /**
     * "B-R" represents a Black Rook, "B-N" represents a Black Knight,
     * "B-B" represents a Black Bishop, "B-Q" represents a Black Queen,
     * "B-K" represents a Black King, and "B-P" represents Black Pawns.
     * <p>
     * In Java, an array is a list like structure where every element of the list has an index i.e. place in the list starting from 0
     * e.g. String[] myFirstArray = {"B-R", "B-N", "B-B", "B-Q", "B-K", "B-B", "B-N", "B-R"};
     * here "B-R" is in the first place as well as the last place and its index is 0 and 7
     * (computers are used to starting counting from 0, its natural for us to count from 1
     * but computers count from 0)
     * Quiz 1: what is the index of "B-Q" in myFirstArray?
     * <p>
     * An array can contain every element as array themselves and that is called 2-dimensional array
     * (see chessBoard below for example)
     * the index of a 2D array is then defined as (0,0) for "B-R" and (0,7) as that for last "B-R"
     * Quiz 2: what is the index of "W-K" in myFirstArray?
     * <p>
     * Consider the "W-P" at index (6,4), this is a white pawn.
     * The possible positions it can move to are
     * a) (5,4) - single step
     * b) (4,4) - double step
     * Quiz 3: for a "B-P" in (1,1), what are the possible moves?
     * Enumerate all the resulting index corresponding to each of the possible moves?
     * <p>
     * Can we in general say that if element is at (x,y),
     * they can move to (x+1,y) or (x+2,y) if chessBoard[x][y].startsWith("B")
     * they can move to (x-1,y) or (x-2,y) if chessBoard[x][y].startsWith("W")
     * <p>
     * Now consider, the chess board has progressed along,
     * 1) the pawn can also move diagonally to kill the opponent
     * i.e. (x+1,y+1) or (x+1,y-1) for black pawn
     * i.e. (x-1,y+1) or (x-1,y-1) for white pawn
     * How do we know if there's an opponent on nextX and nextY?
     * 2) the pawn cannot move to a position where there are other chess pieces
     * for every nextX and nextY from the above rules, this condition is applied
     * i.e. if(chessBoard[nextX][nextY].isEmpty()) addToPossibleMoves(new Integer{nextX, nextY})
     * <p>
     * Now go through the listPossibleDestinationIndexesForPawn function below
     *
     * Quiz 4: Write a new function listPossibleDestinationIndexesForRook yourself
     * @param args
     */
    public static void main(String[] args) {
        // Initialize the chessboard with starting positions of pieces
        String[][] chessBoard = {
                {"B-R", "B-N", "B-B", "B-Q", "B-K", "B-B", "B-N", "B-R"},
                {"B-P", "B-P", "B-P", "B-P", "B-P", "B-P", "B-P", "B-P"},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"W-P", "W-P", "W-P", "W-P", "W-P", "W-P", "W-P", "W-P"},
                {"W-R", "W-N", "W-B", "W-Q", "W-K", "W-B", "W-N", "W-R"}
        };

        // Example: Position of a white pawn at A2 (index {6, 0})
        Integer[][] currentPosition = {{6, 0}};

        // Get possible moves for the pawn at the given position
        Integer[][][] possibleMoves = listPossibleDestinationIndexesForPawn(chessBoard, currentPosition);

        // Print possible moves
        for (Integer[][] move : possibleMoves) {
            System.out.println("Move to: (" + move[0][0] + ", " + move[0][1] + ")");
        }
    }

    // Function to list possible destination indexes for a pawn
    private static Integer[][][] listPossibleDestinationIndexesForPawn(String[][] chessBoard, Integer[][] currentPosition) {
        int row = currentPosition[0][0]; // Current row of the pawn
        int col = currentPosition[0][1]; // Current column of the pawn
        String piece = chessBoard[row][col]; // Piece at the current position

        // Check if the piece is a pawn; return empty array if not
        if (!piece.equals("W-P") && !piece.equals("B-P")) {
            return new Integer[][][]{}; // Not a pawn
        }

        boolean isWhite = piece.equals("W-P"); // Check if the pawn is white
        int direction = isWhite ? -1 : 1; // White pawns move up (-1), black pawns move down (+1)
        int startRow = isWhite ? 6 : 1; // Starting row for white pawns is 6, for black pawns is 1

        // Array to hold possible moves; max 4 possible moves (one forward, two forward, two diagonal captures)
        Integer[][][] possibleMoves = new Integer[4][][];
        int index = 0;

        // One square forward move
        if (isInBounds(row + direction, col) && chessBoard[row + direction][col].isEmpty()) {
            possibleMoves[index++] = new Integer[][]{{row + direction, col}};
        }

        // Two squares forward move from starting position
        if (row == startRow && chessBoard[row + direction][col].isEmpty() && chessBoard[row + 2 * direction][col].isEmpty()) {
            possibleMoves[index++] = new Integer[][]{{row + 2 * direction, col}};
        }

        // Diagonal capture moves
        if (isInBounds(row + direction, col - 1) && !chessBoard[row + direction][col - 1].isEmpty()
                && isOpponentPiece(isWhite, chessBoard[row + direction][col - 1])) {
            possibleMoves[index++] = new Integer[][]{{row + direction, col - 1}};
        }
        if (isInBounds(row + direction, col + 1) && !chessBoard[row + direction][col + 1].isEmpty()
                && isOpponentPiece(isWhite, chessBoard[row + direction][col + 1])) {
            possibleMoves[index++] = new Integer[][]{{row + direction, col + 1}};
        }

        // Trim the possibleMoves array to the actual number of valid moves
        Integer[][][] trimmedMoves = new Integer[index][][];
        System.arraycopy(possibleMoves, 0, trimmedMoves, 0, index);

        return trimmedMoves;
    }

    // Helper function to check if a position is within the bounds of the chessboard
    private static boolean isInBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // Helper function to check if a piece is an opponent's piece
    private static boolean isOpponentPiece(boolean isWhite, String piece) {
        return isWhite ? piece.startsWith("B-") : piece.startsWith("W-");
    }


}
