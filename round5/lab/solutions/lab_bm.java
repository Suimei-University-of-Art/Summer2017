import java.io.*;
import java.util.*;

public class lab_bm {
    FastScanner in;
    PrintWriter out;

    long gcd(long x, long y) {
        return x == 0 ? y : gcd(y % x, x);
    }

    void solve() {
        int n = in.nextInt();
        int m = in.nextInt();
        int C = in.nextInt();
        int H = in.nextInt();
        int k = in.nextInt();
        final int MAX = (int) 1e5 + 10;
        BitSet[][] ans = new BitSet[2][MAX];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < MAX; j++)
                ans[i][j] = new BitSet();
        for (int part = 0; part < 2; part++) {
            int max = part == 0 ? n : m;
            for (int i = 0; i < max; i++) {
                int u = in.nextInt();
                for (int it = 1; it * 1L * it <= u; it++) {
                    if (u % it == 0) {
                        ans[part][it].set(u / it);
                        ans[part][u / it].set(it);
                    }
                }
            }
        }
        for (int i = 0; i < k; i++) {
            int p = in.nextInt();
            int q = in.nextInt();
            int g = (int) gcd(p, q);
            p /= g;
            q /= g;
            if (p < C * q || p > H * q) {
                out.println("NO");
            } else {
                long A = q * 1L * H - p;
                long B = p - q * 1L * C;
                long G = gcd(Math.abs(A), Math.abs(B));
                A /= G;
                B /= G;
                if (A < 0) {
                    A *= -1;
                    B *= -1;
                }
                if (A < 1 || A >= MAX || B < 1 || B >= MAX) {
                    out.println("NO");
                } else {
                    BitSet tmp = (BitSet) ans[0][(int) A].clone();
                    tmp.and(ans[1][(int) B]);
                    out.println(tmp.cardinality() > 0 ? "YES" : "NO");
                }
            }
        }
    }

    void run() {
        try {
            in = new FastScanner(new File("lab.in"));
            out = new PrintWriter(new File("lab.out"));

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
        new lab_bm().runIO();
    }
}