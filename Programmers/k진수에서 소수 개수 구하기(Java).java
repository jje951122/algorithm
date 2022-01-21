class Solution {
    public int solution(int n, int k) {
        int answer = 0;
        String convertNum = convertKbinary(n, k);

        StringBuilder num = new StringBuilder();
        int j = 0;
        for (int i = 0; i < convertNum.length() - 1; i = j) {
            for (j = i + 1; j < convertNum.length() && convertNum.charAt(j) != '0'; j++) ;
            if (isPrime(Long.parseLong(convertNum.substring(i, j))))
                answer++;
        }
        return answer;
    }

    private static boolean isPrime(long num) {
        if (num <= 1) return false;

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    private static String convertKbinary(int n, int k) {
        StringBuilder str = new StringBuilder();

        while (n > 0) {
            str.append(n % k);
            n /= k;
        }

        str = str.reverse();
        return str.toString();
    }

}