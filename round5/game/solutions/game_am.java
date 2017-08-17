import java.io.*;
import java.util.*;


public class game_am {
    public class Edge {
        int to;
        int info;

        public Edge(int to, int info) {
            this.to = to;
            this.info = info;
        }
    }

    public Integer isEdge(BitSet info1, BitSet info2) {
        BitSet diff = new BitSet(k);
        diff.or(info2);
        diff.andNot(info1);
        return (diff.cardinality() == 1 ? diff.nextSetBit(0) : null);
    }

    public void solve() {
        n = in.nextInt(); m = in.nextInt();
        k = in.nextInt();

        info1 = new BitSet[n];
        info2 = new BitSet[m];
        boolean[] info = new boolean[k];
        int known = 0;
        for (int i = 0; i < n; i++) {
            int cnt = in.nextInt();
            info1[i] = new BitSet(k);
            for (int j = 0; j < cnt; j++) {
                int w = in.nextInt() - 1;
                info1[i].set(w);
                if (!info[w]) {
                    known++;
                }
                info[w] = true;
            }
        }

        for (int i = 0; i < m; i++) {
            int cnt = in.nextInt();
            info2[i] = new BitSet(k);
            for (int j = 0; j < cnt; j++) {
                int w = in.nextInt() - 1;
                info2[i].set(w);
            }
        }

        g = new ArrayList<ArrayList<Edge>>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<Edge>());
            for (int j = 0; j < m; j++) {
                Integer to = isEdge(info1[i], info2[j]);
                if ((to != null) && !info[to]) {
                    g.get(i).add(new Edge(to, j));
                }
            }
        }
        match = new int[k];
        matchEdge = new int[n];
        Arrays.fill(match, -1);
        Arrays.fill(matchEdge, -1);
        visit = new boolean[n];
        Arrays.fill(visit, false);
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            Arrays.fill(visit, false);

            if (!visit[i]) {
                cnt += (dfs(i)? 1 : 0);
            }
        }
        if (cnt == k - known) {
            out.println(1);
            for (int i = 0; i < n; i++) {
                out.print((matchEdge[i] == -1 ? 1 : matchEdge[i] + 1) + " ");
            }
        } else {
            System.out.println(2);
        }
    }

    boolean dfs(int v)
    {
        if (visit[v])
            return false;

        visit[v] = true;
        for (int i = 0; i < g.get(v).size(); i++)
        {
            int to = g.get(v).get(i).to;
            if (match[to] == -1 || dfs(match[to]))
            {
                match[to] = v;
                matchEdge[v] = g.get(v).get(i).info;
                return true;
            }
        }
        return false;
    }


    boolean[] visit;
    ArrayList<ArrayList<Edge>> g;
    BitSet[] info1;
    BitSet[] info2;
    int[] match, matchEdge;
    int n, m, k;
    MyScanner in;
    PrintWriter out;

    public void run() {
        try {
            in = new MyScanner();
            out = new PrintWriter(System.out);
            solve();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public MyScanner(String name) {
            try {
                br = new BufferedReader(new FileReader(name));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public String nextToken() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(nextToken());
        }

        public long nextLong() {
            return Long.parseLong(nextToken());
        }

        public double nextDouble() {
            return Double.parseDouble(nextToken());
        }
    }

    public static void main(String[] args) {
        new game_am().run();
    }
}
