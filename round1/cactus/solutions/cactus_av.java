import java.io.*;
import java.util.*;

public class cactus_av {

    class Edge {
        int from, to, color, id;

        public Edge(int from, int to, int color, int id) {
            this.from = from;
            this.to = to;
            this.color = color;
            this.id = id;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", color=" + color +
                    ", id=" + id +
                    '}';
        }
    }

    List<Edge>[] graph;
    List<Edge> stack = new ArrayList<>();
    List<Integer> vertexStack = new ArrayList<>();
    List<List<Edge>> cycles = new ArrayList<>();
    int[] color;

    void dfs(int u, Edge parentEdge) {
        color[u] = 1;
        vertexStack.add(u);

        for (int t = 0; t < graph[u].size(); t++) {
            Edge e = graph[u].get(t);
            if (e == parentEdge) {
                continue;
            }
            int v = u ^ e.from ^ e.to;
            if (color[v] == 1) {
                List<Edge> newCycle = new ArrayList<>();
                newCycle.add(e);
                for (int i = vertexStack.size() - 2; i >= 0; i--) {
                    newCycle.add(stack.get(i));
                    if (vertexStack.get(i) == v) {
                        break;
                    }
                }
                cycles.add(newCycle);
            } else if (color[v] == 0) {
                stack.add(e);
                dfs(v, e);
                stack.remove(e);
            }
        }

        vertexStack.remove(vertexStack.size() - 1);
        color[u] = 2;
    }

    public void solve() {
        int n = in.nextInt(), m = in.nextInt();
        graph = new List[n];
        for (int i =0 ; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1, color = in.nextInt() - 1;
            Edge edge = new Edge(from, to, color, i);
            edges[i] = edge;
            graph[from].add(edge);
            graph[to].add(edge);
        }
        color = new int[n];

        dfs(0, null);
//        System.err.println(cycles);
        Map<Integer, List<Integer>> onCycle = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int col = edges[i].color;
            if (!onCycle.containsKey(col)) {
                onCycle.put(col, new ArrayList<>());
            }
            onCycle.get(col).add(i);
        }
        boolean[] edgeOnCycle = new boolean[m];
        for (int i = 0; i < cycles.size(); i++) {
            for (Edge e : cycles.get(i)) {
                edgeOnCycle[e.id] = true;
            }
        }

        Set<Integer> present = new HashSet<>();
        for (int i = 0; i < m; i++) {
            if (!edgeOnCycle[i]) {
                present.add(edges[i].color);
            }
        }
        Flow flow = new Flow(1 + cycles.size() + m + 1);
        for (int i = 0; i < cycles.size(); i++) {
            flow.addEdge(0, i + 1, cycles.get(i).size() - 1);
        }

        int result = 0;
        for (int i = 0; i < m; i++) {
            if (present.contains(i)) {
                result++;
                continue;
            }
            flow.addEdge(1 + cycles.size() + i, 1 + cycles.size() + m, 1);
        }

        for (int i = 0; i < cycles.size(); i++) {
            for (Edge e : cycles.get(i)) {
                flow.addEdge(1 + i, 1 + cycles.size() + e.color, 1);
            }
        }

        result += flow.getFlow();
        out.println(result);
    }

    class Flow {
        class Edge {
            int from, to, flow, cap;
            Edge rev;

            public Edge(int from, int to, int cap) {
                this.from = from;
                this.to = to;
                this.cap = cap;
            }
        }

        List<Edge>[] graph;
        int[] start, dist, queue;

        public Flow(int n) {
            this.graph = new List[n];
            for (int i = 0; i < n; i++) {
                this.graph[i] = new ArrayList<>();
            }
            start = new int[n];
            dist = new int[n];
            queue = new int[n];
        }

        void addEdge(int from, int to, int cap) {
            Edge st = new Edge(from, to, cap);
            Edge rev = new Edge(to, from, 0);
            st.rev = rev;
            rev.rev = st;
            graph[from].add(st);
            graph[to].add(rev);
        }

        int getFlow() {
            int result = 0;
            while (bfs()) {
                Arrays.fill(start, 0);
                int t;
                while ((t = dfs(0, Integer.MAX_VALUE)) != 0) {
                    result += t;
                }
            }
            return result;
        }

        int dfs(int u, int flow) {
            if (u == graph.length - 1) {
                return flow;
            }
            for (; start[u] < graph[u].size(); start[u]++) {
                Edge e = graph[u].get(start[u]);
                int newFlow = Math.min(flow, e.cap - e.flow);
                if (dist[e.to] == dist[u] + 1 && newFlow > 0) {
                    int ret = dfs(e.to, newFlow);
                    if (ret > 0) {
                        e.flow += ret;
                        e.rev.flow -= ret;
                        return ret;
                    }
                }
            }
            return 0;
        }

        boolean bfs() {
            int head = 0, tail = 0;
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[0] = 0;
            queue[tail++] = 0;
            while (head < tail) {
                int cur = queue[head++];
                for (int t = 0; t < graph[cur].size(); t++) {
                    Edge e = graph[cur].get(t);
                    if (e.flow < e.cap && dist[e.to] == Integer.MAX_VALUE) {
                        dist[e.to] = dist[cur] + 1;
                        queue[tail++] = e.to;
                    }
                }
            }
            return dist[dist.length - 1] != Integer.MAX_VALUE;
        }
    }

    public void run() {
        in = new FastScanner();
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    FastScanner in;
    PrintWriter out;

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(String fileName) {
            try {
                br = new BufferedReader(new FileReader(fileName));
            } catch (FileNotFoundException e) {
            }
        }

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String nextToken() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(nextToken());
        }

        long nextLong() {
            return Long.parseLong(nextToken());
        }

        double nextDouble() {
            return Double.parseDouble(nextToken());
        }
    }

    public static void main(String[] args) {
        new cactus_av().run();
    }
}
