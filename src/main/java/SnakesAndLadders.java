/**
 * Problem Description: https://leetcode.com/problems/snakes-and-ladders/
 */

import java.util.Queue;

public class SnakesAndLadders {
    public int snakesAndLadders(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int maxSq = rows*cols;

        boolean visited[] = new boolean[maxSq];
        int[] pred = new int[maxSq+1]; // Allows to print the path as well. Alternatively, a dist[] incrementing at each time can be used too.
        Queue<Integer> squaresQ = new LinkedList<>();
        squaresQ.add(1);
        visited[1] = true;
        pred[1] = 0;

        outer:
        while(!squaresQ.isEmpty()) {
            int sqOut = squaresQ.remove();

            for (int i=1; i<=6; i++) {
                int neighbourSq = sqOut + i;
                if (neighbourSq > maxSq)
                    break;

                int effRow = getEffRow(neighbourSq, rows, cols);
                int effCol = getEffCol(neighbourSq, rows, cols);
                if (board[effRow][effCol] != -1) {
                    neighbourSq = board[effRow][effCol];
                    effRow = getEffRow(neighbourSq, rows, cols);
                    effCol = getEffCol(neighbourSq, rows, cols);
                }
                if (neighbourSq == maxSq) {
                    pred[neighbourSq] = sqOut;
                    break outer;
                }

                if (!visited[neighbourSq]) {
                    visited[neighbourSq] = true;
                    pred[neighbourSq] = sqOut; // Alternatively, can do: dist[neighbourSq] = dist[sqOut]++;
                    squaresQ.add(neighbourSq);
                }

            }
        }

        int cnt = 0;
        int predSq = maxSq;
        if (pred[predSq] == 0)
            return -1;
        while(predSq != 0) {
            cnt++;
            predSq = pred[predSq];
        }

        return --cnt;
    }

    private int getEffRow(int sq, int rows, int cols) {
        return (rows - 1 - (sq-1)/cols);
    }

    private int getEffCol(int sq, int rows, int cols) {
        int effCol = (sq -1) % cols; // 0 based

        if (((sq - 1)/cols + 1) % 2 == 0) // even row from bottom:1 based
            effCol = cols - 1 - effCol;

        return effCol;
    }
}