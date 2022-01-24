import java.util.*;

class Solution {
    // 해당 IDX의 자식은 누가 있는지
    static ArrayList<Integer>[] childs;
    static int[] Info;
    static int maxSheepCnt = 0;
    static boolean[] visit;

    public static int solution(int[] info, int[][] edges) {
        Info = info;
        childs = new ArrayList[info.length];
        for (int[] l : edges) {
            int parent = l[0];
            int child = l[1];
            if (childs[parent] == null) {
                childs[parent] = new ArrayList<>();
            }
            childs[parent].add(child);
        }

        visit = new boolean[info.length];
        List<Integer> list = new ArrayList<>();
        list.add(0);
        dfs(0, 0, 0, list);
        return maxSheepCnt;
    }

    private static void dfs(int idx, int sheepCnt, int wolfCnt, List<Integer> nextPos) {
        // 이전에 방문한 적이 없다면 늑대/양 수, 양의 최대값 최신화
        if (!visit[idx]) {
            if (Info[idx] == 0) sheepCnt++;
            else wolfCnt++;

            if (wolfCnt >= sheepCnt) return;
            visit[idx] = true;
            maxSheepCnt = Math.max(sheepCnt, maxSheepCnt);
        }
        // 다음 탐색 위치 갱신
        List<Integer> list = new ArrayList<>();
        list.addAll(nextPos);
        // 다음 탐색 목록중 현재 위치제외
        list.remove(Integer.valueOf(idx));
        if (childs[idx] != null) {
            for (int child : childs[idx]) {
                list.add(child);
            }
        }
        // 갈수 있는 모든 Node Dfs
        for (int next : list) {
            dfs(next, sheepCnt, wolfCnt, list);
        }
        visit[idx] = false;
    }

}