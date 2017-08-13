import java.io.*;
import java.util.*;

public class split_bm {
    FastScanner in;
    PrintWriter out;

    ArrayList<Integer> gSum;
    ArrayList<Integer> g1, g2;
    boolean[] used;

    boolean go2(int v) {
        if (g2.size() == g1.size())
            return true;
        for (int i = n + 1; i < used.length; i++) {
            if (!used[i]) {
                int an = i + g1.get(v);
                if (an < used.length && !used[an]) {
                    used[i] = true;
                    g2.add(i);
                    gSum.add(an);
                    used[an] = true;
                    if (go2(v + 1))
                        return true;
                    g2.remove(g2.size() - 1);
                    gSum.remove(gSum.size() - 1);
                    used[i] = false;
                    used[an] = false;
                }
            }

        }
        return false;
    }

    int n;

    void precalc() {
        out.print("{");
        for (n = 1; n <= 23; n++) {
            int max = n * 3;
            int sum = (1 + max) * max / 2;
            if (sum % 2 != 0) {
                out.println("{-1},");
            } else {
                gSum = new ArrayList<Integer>();
                g1 = new ArrayList<Integer>();
                g2 = new ArrayList<Integer>();
                used = new boolean[max + 1];
                for (int i = 1; i <= n; i++) {
                    g1.add(i);
                }
                if (!go2(0))
                    throw new AssertionError();
                out.print("{");
                for (int j = 0; j < n; j++) {
                    out.print(g1.get(j) + ", ");
                }
                for (int j = 0; j < n; j++) {
                    out.print(g2.get(j) + ", ");
                }
                for (int j = 0; j < n; j++) {
                    if (j == n - 1)
                        out.print(gSum.get(j));
                    else
                        out.print(gSum.get(j) + ", ");
                }
                out.println("},");
            }
        }
    }

    int[][] answ = {
            { 1, 2, 3 },
            { -1 },
            { -1 },
            { 1, 2, 3, 4, 5, 8, 9, 7, 6, 10, 12, 11 },
            { 1, 2, 3, 4, 5, 6, 9, 12, 10, 8, 7, 11, 15, 14, 13 },
            { -1 },
            { -1 },
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 14, 18, 19, 15, 16, 12, 10, 13,
                    17, 22, 24, 21, 23, 20 },
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 15, 19, 22, 20, 17, 13, 16,
                    11, 14, 18, 23, 27, 26, 24, 21, 25 },
            { -1 },
            { -1 },
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 20, 23, 26,
                    29, 27, 25, 21, 22, 18, 14, 17, 19, 24, 28, 32, 36, 35, 34,
                    31, 33, 30 },
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 25,
                    26, 28, 31, 29, 27, 22, 24, 21, 15, 18, 20, 23, 30, 32, 35,
                    39, 38, 37, 33, 36, 34 },
            { -1 },
            { -1 },
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19,
                    20, 22, 24, 30, 32, 33, 37, 38, 34, 35, 31, 28, 25, 27, 18,
                    21, 23, 26, 29, 36, 39, 41, 46, 48, 45, 47, 44, 42, 40, 43 },
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
                    20, 21, 23, 25, 29, 34, 36, 37, 39, 40, 38, 32, 28, 33, 31,
                    26, 19, 22, 24, 27, 30, 35, 41, 44, 46, 49, 51, 50, 45, 42,
                    48, 47, 43 },
            { -1 },
            { -1 },
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
                    19, 20, 21, 23, 24, 26, 28, 29, 34, 40, 42, 39, 45, 46, 47,
                    43, 44, 37, 38, 36, 31, 32, 22, 25, 27, 30, 33, 35, 41, 48,
                    51, 49, 56, 58, 60, 57, 59, 53, 55, 54, 50, 52 },
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
                    19, 20, 21, 22, 24, 25, 27, 29, 30, 32, 42, 43, 41, 44, 47,
                    48, 49, 45, 46, 37, 40, 38, 33, 35, 23, 26, 28, 31, 34, 36,
                    39, 50, 52, 51, 55, 59, 61, 63, 60, 62, 54, 58, 57, 53, 56 },
            { -1 }, { -1 } };

    void solve() {
        int n = in.nextInt();
        if (answ[n - 1].length == 1) {
            out.println(-1);
        } else {
            for (int i = 0; i < answ[n - 1].length; i++) {
                out.print(answ[n - 1][i] + " ");
                if (i % n == n - 1)
                    out.println();
            }
        }
    }

    void run() {
        try {
            in = new FastScanner(new File("object.in"));
            out = new PrintWriter(new File("object.out"));

            solve();

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void runIO() {

        in = new FastScanner(System.in);
        out = new PrintWriter(System.out);

        solve();

        out.close();
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public FastScanner(InputStream f) {
            br = new BufferedReader(new InputStreamReader(f));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                String s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s == null)
                    return null;
                st = new StringTokenizer(s);
            }
            return st.nextToken();
        }

        boolean hasMoreTokens() {
            while (st == null || !st.hasMoreTokens()) {
                String s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s == null)
                    return false;
                st = new StringTokenizer(s);
            }
            return true;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }

    public static void main(String[] args) {
        new split_bm().runIO();
    }
}