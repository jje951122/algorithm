import java.io.*;
import java.util.*;

public class Main {
    static int R, C;
    static List<Integer>[][] map;
    static int[] dy = {0, 1, 1, 1, 0, 0, 0, -1, -1, -1};
    static int[] dx = {0, -1, 0, 1, -1, 0, 1, -1, 0, 1};
    static List<Pos> crazyArduinos = new ArrayList<>();
    static Pos player;


    static class Pos {
        int y, x, num;

        Pos(int y, int x) {
            this.y = y;
            this.x = x;
            this.num = -1;
        }

        Pos(int y, int x, int num) {
            this.y = y;
            this.x = x;
            this.num = num;
        }
    }

    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader("a.txt"));

        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new ArrayList[R][C];
        int arduinoIdx = 1;

        for (int i = 0; i < R; i++) {
            char[] temp = br.readLine().toCharArray();
            for (int j = 0; j < temp.length; j++) {
                map[i][j] = new ArrayList<Integer>();
                if (temp[j] == 'I') {
                    player = new Pos(i, j);
                } else if (temp[j] == 'R') {
                    crazyArduinos.add(new Pos(i, j, arduinoIdx));
                    map[i][j].add(arduinoIdx++);
                }
            }
        }

        char[] commands = br.readLine().toCharArray();
        int cnt = 0;
        for (char c : commands) {
            playerGo(c - '0');
            cnt++;
            if (isPlayerDead()) break;

            CrazyGo();
            if (isPlayerDead()) break;

            bombCrazy();

        }
        if (cnt < commands.length) {
            System.out.println("kraj " + cnt);
        } else {
            printMap();
        }
    }

    private static void printMap() {
        char arr[][] = new char[R][C];
        for (int i = 0; i < R; i++) {
            Arrays.fill(arr[i], '.');
        }
        arr[player.y][player.x] = 'I';
        for (Pos p : crazyArduinos) {
            arr[p.y][p.x] = 'R';
        }

        for (int i = 0; i < R; i++) {
            System.out.println(arr[i]);
        }
    }

    private static void bombCrazy() {
        crazyArduinos.clear();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j].size() == 1) crazyArduinos.add(new Pos(i, j, map[i][j].get(0)));
                else if (map[i][j].size() >= 2) map[i][j].clear();
            }
        }
    }

    private static void CrazyGo() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                map[i][j].clear();
            }
        }

        for (int i = 0; i < crazyArduinos.size(); i++) {
            int y = crazyArduinos.get(i).y;
            int x = crazyArduinos.get(i).x;
            int dir = closestChoice(y, x);

            int ny = y + dy[dir];
            int nx = x + dx[dir];
            crazyArduinos.get(i).y = ny;
            crazyArduinos.get(i).x = nx;
            map[ny][nx].add(crazyArduinos.get(i).num);
        }
    }

    private static int closestChoice(int y, int x) {
        int minDist = 987654321;
        int minDir = 0;
        for (int dir = 1; dir <= 9; dir++) {
            if (dir == 5) continue;
            int ny = y + dy[dir];
            int nx = x + dx[dir];
            int dist = Math.abs(ny - player.y) + Math.abs(nx - player.x);
            if (minDist > dist) {
                minDist = dist;
                minDir = dir;
            }
        }
        return minDir;
    }

    private static void playerGo(int command) {
        if (command == 5)
            return;
        player.y = player.y + dy[command];
        player.x = player.x + dx[command];
    }

    private static boolean isPlayerDead() {
        if (map[player.y][player.x].size() == 0) return false;
        else return true;
    }
}