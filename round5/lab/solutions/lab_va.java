import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class lab_va implements Runnable {
    public static void main(String[] args) {
        new Thread(new lab_va()).run();
    }

    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public String nextToken() throws IOException {
        while (in == null || !in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }

        return in.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    int MAX = 100000;
    int D = 300;

    public long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }//
    public void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        int C = nextInt();
        int H = nextInt();
        int k = nextInt();

        boolean[] cold = new boolean[MAX + 1];

        for (int i = 0; i < n; i++) {
            cold[nextInt()] = true;
        }

        boolean[] hot = new boolean[MAX + 1];
        for (int i = 0; i < m; i++) {
            hot[nextInt()] = true;
        }

        int[][] ans = new int[D][D];
        for (int i = 0; i < D; i++) {
            Arrays.fill(ans[i], -1);
        }

        for (int t = 0; t < k; t++) {
            int p = nextInt(), q = nextInt();

            if (1L * C * q < p && p < 1L * H * q) {
                long A = q * 1L * H - p;
                long B = p - q * 1L * C;

                long gcd = gcd(A, B);
                A /= gcd;
                B /= gcd;

                boolean can = false;
                if (Math.max(A, B) >= D || ans[(int) A][(int) B] == -1) {
                    for (long i = A, j = B; i <= MAX && j <= MAX && !can; i += A, j += B) {
                        if (cold[(int) i] && hot[(int) j]) {
                            can = true;
                        }
                    }
                } else {
                    can = ans[(int) A][(int) B] == 1;
                }

                if (can) {
                    if (Math.max(A, B) < D)
                        ans[(int) A][(int) B] = 1;
                    out.println("YES");
                } else {
                    if (Math.max(A, B) < D)
                        ans[(int) A][(int) B] = 0;
                    out.println("NO");
                }
            } else {
                out.println("NO");
            }
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(System.out);

            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
