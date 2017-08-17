import java.io.*;
import java.util.*;

public class salary_bm {
    FastScanner in;
    PrintWriter out;

    class Edge {
        int to, color;

        public Edge(int to, int color) {
            super();
            this.to = to;
            this.color = color;
        }

        @Override
        public String toString() {
            return "Edge [to=" + to + ", color=" + color + "]";
        }
        
        

    }

    boolean go(int v, int[] color, ArrayList<Edge>[] g, ArrayList<Integer> was,
            int curColor) {
        was.add(v);
        color[v] = curColor;
        for (Edge e : g[v]) {
            if (color[e.to] == 0) {
                if (e.color == 0) {
	               	if (!go(e.to, color, g, was, curColor))
	               		return false;
                } else {
                    if (!go(e.to, color, g, was, 3 - curColor))
						return false;
                }
            } else {
                if (e.color == 0 && color[e.to] != color[v])
                    return false;
                if (e.color == 1 && color[e.to] == color[v])
                    return false;
            }
        }
        return true;
    }

    void solve() {
        int tt = in.nextInt();
        for (int t = 0; t < tt; t++) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[] x = new int[n];
            int[] y = new int[n];
            for (int i = 0; i < n; i++) {
                x[i] = in.nextInt();
                y[i] = in.nextInt();
            }
            ArrayList<Edge>[] g = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new ArrayList<>();
            }
            boolean ok = true;
            for (int i = 0; i < m; i++) {
                int fr = in.nextInt() - 1;
                int to = in.nextInt() - 1;
                boolean okNow = x[fr] > x[to] || y[fr] > y[to];
                boolean okChange = y[fr] > x[to] || x[fr] > y[to];
                if (!okNow && !okChange) {
                 	ok = false;
                } else {
                 	if (okNow && !okChange) {
                        g[fr].add(new Edge(to, 0));
	                    g[to].add(new Edge(fr, 0));
                 	}
                 	if (!okNow && okChange) {
                        g[fr].add(new Edge(to, 1));
	                    g[to].add(new Edge(fr, 1));
                 	}
                }
            }
            if (!ok) {
                out.println(-1);
            } else {
                int res = 0;
                int[] color = new int[n];
                for (int i = 0; i < n; i++)
                    if (color[i] == 0) {
                        ArrayList<Integer> was = new ArrayList<>();
                        if (!go(i, color, g, was, 1)) {
                            ok = false;
                            break;
                        }
                        int cnt1 = 0, cnt2 = 0;
                        for (int v : was) {
                            if (color[v] == 1) {
                                cnt1++;
                            } else {
                                cnt2++;
                            }
                        }
                        if (cnt1 > cnt2) {
                            for (int v : was) {
                                color[v] = 3 - color[v];
                            }
                        }
                        res += Math.min(cnt1, cnt2);
                    }
                if (!ok) {
                    out.println(-1);
                } else {
                    out.println(res);
                    for (int i = 0; i < n; i++)
                        if (color[i] == 1) {
                            out.print((i + 1) + " ");
                        }
                    out.println();
                }
            }
        }

    }

    void run() {
        try {
            in = new FastScanner(new File("salary.in"));
            out = new PrintWriter(new File("salary.out"));

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
        new salary_bm().runIO();
    }
}