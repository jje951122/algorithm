import java.util.*;

public class Main {
    static long MAX = 987654321;
    static List<Long> numList = new ArrayList<>();
    static List<Character> optList = new ArrayList<>();
    static List<Character> optKind = new ArrayList<>();
    static boolean[] visit;

    public static long solution(String expression) {
        splitExp(expression);

        visit = new boolean[optKind.size()];
        List<Character> order = new ArrayList<>();
        priorityOrder(order);
        return MAX;
    }

    private static void priorityOrder(List<Character> order) {
        if (order.size() == optKind.size()) {
            Long now = calc(order);
            if(now>MAX)MAX=now;
            return;
        }
        for (int i = 0; i < optKind.size(); i++) {
            if (visit[i]) continue;
            visit[i] = true;
            order.add(optKind.get(i));
            priorityOrder(order);
            visit[i] = false;
            order.remove(order.size() - 1);
        }
    }

    private static Long calcOpt(long a, long b, char opt) {
        long res = 0;
        if (opt == '+') {
            res = a + b;
        } else if (opt == '-') {
            res = a - b;
        } else {
            res = a * b;
        }
        return res;
    }

    private static Long calc(List<Character> order) {
        List<Long> copyNumList = new ArrayList<>(numList);
        List<Character> copyOptList = new ArrayList<>(optList);

        for (char opt : order) {
            for (int i = 0; i < copyOptList.size(); i++) {
                if (opt == copyOptList.get(i)) {
                    copyNumList.set(i, calcOpt(copyNumList.get(i), copyNumList.get(i + 1), opt));
                    copyNumList.remove(i + 1);
                    copyOptList.remove(i);
                    i--;
                }
            }
        }

        return Math.abs(copyNumList.get(0));
    }

    private static void splitExp(String exp) {
        char[] arr = exp.toCharArray();
        Set<Character> optSet = new HashSet<>();
        StringBuilder numberStr = new StringBuilder();
        for (char c : arr) {
            if (c == '+' || c == '-' || c == '*') {
                optSet.add(c);
                optList.add(c);
                numList.add(Long.valueOf(numberStr.toString()));
                numberStr = new StringBuilder();
            } else {
                numberStr.append(c);
            }
        }
        numList.add(Long.valueOf(numberStr.toString()));
        optKind = new ArrayList<>(optSet);

    }

}
