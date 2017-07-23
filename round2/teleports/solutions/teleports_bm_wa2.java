import java.io.*;
import java.util.*;

public class teleports_bm_wa2 {
    FastScanner in;
    PrintWriter out;

    class Answer {
        ArrayList<Integer> ans;

        public Answer(ArrayList<Integer> ans) {
            super();
            this.ans = ans;
        }
    }

    
    class InputData {
        class Road {
            int fr, to;

            public Road(int fr, int to) {
                super();
                this.fr = fr;
                this.to = to;
            }

            @Override
            public String toString() {
                return "(" + fr + ", " + to + ")";
            }

        }

        int n;
        ArrayList<Road> roads;
        ArrayList<Road> teleports;
        int[] pair;
        int[] roadsCount;

        InputData(int n) {
            this.n = n;
            roads = new ArrayList<>();
            teleports = new ArrayList<>();
            pair = new int[n];
            roadsCount = new int[n];
            Arrays.fill(pair, -1);
        }

        void addRoad(int fr, int to) {
            roads.add(new Road(fr, to));
            roadsCount[fr]++;
            roadsCount[to]++;
        }

        void addTeleport(int fr, int to) {
            if (pair[fr] != -1 || pair[to] != -1) {
                throw new AssertionError("Vertex already has another pair");
            }
            pair[fr] = to;
            pair[to] = fr;
            teleports.add(new Road(fr, to));
        }

        @Override
        public String toString() {
            return "InputData [n=" + n + ", roads=" + roads + ", teleports="
                    + teleports + ", pair=" + Arrays.toString(pair)
                    + ", roadsCount=" + Arrays.toString(roadsCount) + "]";
        }

    }
    
    class Solver {
        InputData input;

        Solver(InputData input) {
            this.input = input;
        }

        class Road {
            int fr, to;
            boolean used;
            int id;
            char c;
            Road back;

            public Road(int fr, int to, boolean orientation, int id) {
                super();
                this.fr = fr;
                this.to = to;
                if (orientation)
                    c = '+';
                else
                    c = '-';
                this.id = id;
            }

        }

        ArrayList<Road>[] g;
        ArrayList<Integer>[] tel;

        boolean[] was;

        void go(int v) {
            was[v] = true;
            for (Road r : g[v]) {
                if (!was[r.to])
                    go(r.to);
            }
            for (Integer r : tel[v]) {
                if (!was[r])
                    go(r);
            }
        }

        Answer sol() {
            ArrayList<Integer> ans = new ArrayList<Integer>();

            int n = input.n;
            g = new ArrayList[n];
            tel = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                tel[i] = new ArrayList<>();
                g[i] = new ArrayList<>();
            }
            int m = input.roads.size();
            for (int i = 0; i < m; i++) {
                int fr = input.roads.get(i).fr;
                int to = input.roads.get(i).to;
                Road r1 = new Road(fr, to, true, i);
                Road r2 = new Road(to, fr, false, i);
                r1.back = r2;
                r2.back = r1;
                g[fr].add(r1);
                g[to].add(r2);
            }
            for (InputData.Road r : input.teleports) {
                tel[r.fr].add(r.to);
                tel[r.to].add(r.fr);
            }
            was = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (g[i].size() != 0) {
                    go(i);
                    break;
                }
            }
            for (int i = 0; i < n; i++)
                if (g[i].size() != 0)
                    if (!was[i]) {
                        return null;
                    }
            int first = 0, times = 0;
            for (int i = 0; i < n; i++)
                if (g[i].size() != 0)
                    first = i;
            for (int i = 0; i < n; i++) {
                if (g[i].size() % 2 != 0) {
                    if (input.pair[i] == -1) {
                        times++;
                        first = i;
                    }

                }
            }
            for (int i = 0; i < n; i++) {
                if (input.pair[i] != -1)
                    if (g[i].size() > g[input.pair[i]].size()) {
                        times += -g[input.pair[i]].size() + g[i].size();
                        first = i;
                    }
            }
            if (times > 4) {
                return null;
            }
            ArrayList<Integer> stack = new ArrayList<>();
            stack.add(first);
            ArrayList<Road> stackRoad = new ArrayList<>();
            while (stack.size() != 0) {
                int v = stack.get(stack.size() - 1);
                Road nextRoad = null;
                while (g[v].size() > 0) {
                    nextRoad = g[v].remove(g[v].size() - 1);
                    if (!nextRoad.used)
                        break;
                }
                if (nextRoad == null || nextRoad.used) {
                    stack.remove(stack.size() - 1);
                    if (stack.size() != 0) {
                        Road r = stackRoad.remove(stackRoad.size() - 1);
                        ans.add(r.id);
                    }
                } else {
                    nextRoad.used = true;
                    nextRoad.back.used = true;
                    int nextTo = nextRoad.to;
                    if (input.pair[nextTo] != -1)
                        nextTo = input.pair[nextTo];
                    stack.add(nextTo);
                    stackRoad.add(nextRoad);
                }
            }

            Answer answ = new Answer(ans);
            return answ;
        }
    }
    
    void sol(int n, int m, int k) {
        InputData input = new InputData(n);
        for (int i = 0; i < m; i++) {
            int fr = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            input.addRoad(fr, to);
        }
        for (int i = 0; i < k; i++) {
            int fr = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            input.addTeleport(fr, to);
        }
        Answer ans = new Solver(input).sol();
        if (ans == null) {
            out.println("No");
        } else {
            out.println("Yes");
            for (int x : ans.ans) {
                out.print((x + 1) + " ");
            }
            out.println();
        }
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
        new teleports_bm_wa2().runIO();
    }
}