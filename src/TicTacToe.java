import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicTacToe
{

    enum TTTSpace {X, O}

    private static Scanner inputScanner;
    private static TTTSpace[][] board = new TTTSpace[3][3];
    private static boolean running = true;

    public static void main(String[] args)
    {
        inputScanner = new Scanner(System.in);

        printBoard();

        while(running)
        {
            humanTurn();
            computerTurn();
            printBoard();
        }
    }

    private static void computerTurn()
    {

    }

    private static void humanTurn()
    {
        String s = inputScanner.nextLine();

        int row = Integer.parseInt(String.valueOf(s.charAt(0)));
        int col = Integer.parseInt(String.valueOf(s.charAt(1)));

        board[row][col] = TTTSpace.X;

        checkWinCondition(TTTSpace.X, row, col);
    }

    private static void checkWinCondition(TTTSpace team, int row, int col)
    {
        boolean UpLeft = checkWinCondition0(team, row, col,-1, -1);
        boolean Left = checkWinCondition0(team, row, col, 0, -1);
        boolean Up = checkWinCondition0(team, row, col, -1, 0);
        boolean UpRight = checkWinCondition0(team, row, col, -1, 1);
        boolean DownLeft = checkWinCondition0(team, row, col, 1, -1);
        boolean Down = checkWinCondition0(team, row, col, -1, 0);
        boolean Right = checkWinCondition0(team, row, col, 0, 1);
        boolean DownRight = checkWinCondition0(team, row, col, 1, 1);

        if (row == col && UpLeft && DownRight)
        {
            notifyWin(team);
        }

        if (row == 2-col && UpRight && DownLeft)
        {
            notifyWin(team);
        }

        if (Left && Right || Up && Down)
        {
            notifyWin(team);
        }
    }

    private static void notifyWin(TTTSpace team)
    {
        System.out.println(stringRep(team) + " WINS");
        running = false;
    }

    private static boolean checkWinCondition0(TTTSpace team, int row, int col, int rd, int cd)
    {
        row = row+rd;
        col = col+cd;

        if (row < 0 || row > 2)
        {
            return true;
        }

        if (col < 0 || col > 2)
        {
            return true;
        }

        return board[row][col] == team && checkWinCondition0(team, row + rd, col + cd, rd, cd);
    }

    private static void printBoard()
    {
        String line = String.join("", Collections.nCopies(board.length*3, "-"));

        String boardAsString = Arrays.stream(board)
                .map(row -> Arrays.stream(row)
                        .map(TicTacToe::stringRep)
                        .collect(Collectors.joining(" | ")))
                .collect(Collectors.joining("\n" + line + "\n"));

        System.out.println(boardAsString);
    }

    private static String stringRep(TTTSpace space)
    {
        if(space == null)
        {
            return " ";
        }
        else if (space == TTTSpace.X)
        {
            return "X";
        }
        else
        {
            return "O";
        }
    }
}
