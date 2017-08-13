import java.io.*;
import java.util.*;

public class equality_bm_wa {
    FastScanner in;
    PrintWriter out;

    class Gnome implements Comparable<Gnome> {
        int id;
        int h;

        public Gnome(int id, int h) {
            super();
            this.id = id;
            this.h = h;
        }

        @Override
        public int compareTo(Gnome arg0) {
            return Integer
                    .compare(Math.abs(h - id), Math.abs(arg0.h - arg0.id));
        }

    }

    void solve() {
        int testNumber = in.nextInt();
        Random rnd = new Random(123);
        for (int test = 0; test < testNumber; test++) {
            int n = in.nextInt();
            Gnome[] a = new Gnome[n];
            for (int i = 0; i < n; i++) {
                a[i] = new Gnome(i + 1, in.nextInt());
            }
            Arrays.sort(a);
            boolean[] used = new boolean[n];
            int cnt = 0;
            while (cnt++ < 100) {
                int fr = rnd.nextInt(n);
                used[fr] = true;
                long curSum = a[fr].h - a[fr].id;
                for (int i = fr - 1; i >= 0; i--) {
                    int nowVal = a[i].h - a[i].id;
                    long newVal = curSum + nowVal;
                    if (Math.abs(newVal) < Math.abs(curSum)) {
                        used[i] = true;
                        curSum = newVal;
                    }
                }
                if (curSum == 0) {
                    break;
                } else {
                	if (cnt == 100) break;
                    for (int i = 0; i <= fr; i++) {
                        used[i] = false;
                    }
                }
            }

            int sz = 0;
            for (int i = 0; i < n; i++)
                if (used[i])
                    sz++;
            out.println(sz);
            for (int i = 0; i < n; i++) {
                if (used[i]) {
                    out.print(a[i].id + " ");
                }
            }
            out.println();
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
        new equality_bm_wa().runIO();
    }
}