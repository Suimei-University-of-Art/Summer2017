import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class qqsort_ak_n3 {
    static int count(int[] a) {
        int n = a.length;
        int[] where = new int[n];
        for (int i = 0; i < n; i++) {
            where[a[i]] = i;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int[] xs : dp) {
            Arrays.fill(xs, n);
        }
        for (int i = 0; i < n; i++) {
            dp[i][i] = dp[i][i + 1] = 0;
            for (int j = i + 1; j < n && where[j - 1] < where[j]; j++) {
                dp[i][j + 1] = 0;
            }
        }
        dp[n][n] = 0;
        for (int len = 2; len <= n; len++) {
            for (int j = 0; j + len <= n; j++) {
                for (int k = j; k <= j + len - 1; k++) {
                    dp[j][j + len] = Math.min(dp[j][j + len], dp[j][k] + 1 + dp[k + 1][j + len]);
                }
            }
        }
        return dp[0][n];
    }

    public static void main(String[] args) throws IOException {
        MyScanner in = new MyScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = in.nextInt() - 1;
            }
            out.println(count(a));
        }

        out.close();
    }

    static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        MyScanner(Reader r) {
            br = new BufferedReader(r);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }


}
