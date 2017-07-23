import java.io.*;
import java.util.*;

public class teleports_bm_tl2 {
    FastScanner in;
    PrintWriter out;

    class Road {
        int fr, to, id;
        Road back;
        boolean used;

        public Road(int fr, int to, int id) {
            super();
            this.fr = fr;
            this.to = to;
            this.id = id;
        }

    }

    int[] pair;
    ArrayList<Road>[] roads;
    boolean[] was;

    void go(int v) {
        was[v] = true;
		
        for (Road r : roads[v]) {
            if (!was[r.to]) {
                go(r.to);
            }
        }
        if (pair[v] != -1 && !was[pair[v]]) {
            go(pair[v]);
        }
    }

    void go2(int v) {
        for (int i = 0; i < roads[v].size(); i++) {
            if (!roads[v].get(i).used) {
                roads[v].get(i).used = true;
                roads[v].get(i).back.used = true;
                if (pair[roads[v].get(i).to] == -1)
                    go2(roads[v].get(i).to); else 
                        go2(pair[roads[v].get(i).to]);
                out.print(roads[v].get(i).id + " ");
            }
        }
    }

    void sol(int n, int m, int k) {
        pair = new int[n];
        Arrays.fill(pair, -1);
        roads = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            roads[i] = new ArrayList<>();
        }
        int[] cntRoads = new int[n];
        for (int i = 0; i < m; i++) {
            int fr = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            Road r1 = new Road(fr, to, i + 1);
            Road r2 = new Road(to, fr, i + 1);
            r1.back = r2;
            r2.back = r1;
            roads[fr].add(r1);
            roads[to].add(r2);
            cntRoads[fr]++;
            cntRoads[to]++;
        }
        for (int i = 0; i < k; i++) {
            int fr = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            pair[fr] = to;
            pair[to] = fr;
        }
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            if (pair[i] == -1) {
                a[i] = cntRoads[i] % 2;
            } else {
                a[i] = Math.max(0, cntRoads[i] - cntRoads[pair[i]]);
            }
        }
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += a[i];
        if (sum > 2) {
            out.println("No");
            return;
        }
        was = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (roads[i].size() != 0) {
                go(i);
                break;
            }
        }
        for (int i = 0; i < n; i++)
            if (roads[i].size() != 0)
                if (!was[i]) {
                    out.println("No");
                    return;
                }
        int first = 0;
        for (int i = 0; i < n; i++)
            if (roads[i].size() != 0)
                first = i;
        for (int i = 0; i < n; i++) {
            if (a[i] > 0)
                first = i;
        }
        out.println("Yes");
        go2(first);
        out.println();
    }

    void solve() {
        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            int k = in.nextInt();
            if (n == 0) {
                return;
            }
            sol(n, m, k);
        }
    }

    void run() {
        try {
            in = new FastScanner(new File("teleports.in"));
            out = new PrintWriter(new File("teleports.out"));

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
        new teleports_bm_tl2().runIO();
    }
}