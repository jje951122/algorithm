import java.io.*;

class Solution {
    static int[] change = {0, -1, 1};
    static int[][] dp;
    static int N, M;

    public static int solution(int[][] board, int[][] skill) {
        N = board.length;
        M = board[0].length;

        dp = new int[N + 1][M + 1];
        for (int[] s : skill) {
            int y1 = s[1], x1 = s[2];
            int y2 = s[3], x2 = s[4];
            int degree = (s[5] * change[s[0]]);

            dp[y1][x1] += degree;
            dp[y1][x2 + 1] += (degree * -1);
            dp[y2 + 1][x1] += (degree * -1);
            dp[y2 + 1][x2 + 1] += degree;
        }
        operate();

        // 살아남은 건물 확인
        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] + dp[i][j] > 0) answer++;
            }
        }
        return answer;
    }

    // 누적합 계산
    private static void operate() {
        // 상하
        for (int y = 1; y < N; y++) {
            for (int x = 0; x < M; x++) {
                dp[y][x] += dp[y - 1][x];
            }
        }
        // 좌우
        for (int x = 1; x < M; x++) {
            for (int y = 0; y < N; y++) {
                dp[y][x] += dp[y][x - 1];
            }
        }
    }

}

