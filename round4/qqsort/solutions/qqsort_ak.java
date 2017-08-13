import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class qqsort_ak {
    static int count(int[] a) {
        int n = a.length;
        int[] where = new int[n];
        for (int i = 0; i < n; i++) {
            where[a[i]] = i;
        }
        ArrayList<Integer> bounds = new ArrayList<Integer>();
        bounds.add(0);
        for (int i = 0; i < n - 1; i++) {
            if (where[i] > where[i + 1]) {
                bounds.add(i + 1);
            }
        }
        bounds.add(n);
        ArrayList<Integer> lengths = new ArrayList<Integer>();
        for (int i = 0; i < bounds.size() - 1; i++) {
            lengths.add(bounds.get(i + 1) - bounds.get(i));
        }
        int[] dp = new int[lengths.size() + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = i;
        }
        dp[1] = 0;
        for (int i = 1; i < lengths.size() - 1; i++) {
            dp[i + 1] = Math.min(dp[i + 1], dp[i] + 1);
            if (lengths.get(i) == 1) {
                dp[i + 2] = Math.min(dp[i + 2], dp[i] + 1);
            }
        }
        dp[lengths.size()] = Math.min(dp[lengths.size()], dp[lengths.size() - 1] + 1);
        return dp[lengths.size()];
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
