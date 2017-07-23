import java.io.*;
import java.util.*;

public class Gen {

    static int tests = 1;
    static Random rnd = new Random(123);
    final static int MAX = (int) 1e5;

    class Road {
        int fr, to;

        public Road(int fr, int to) {
            super();
            this.fr = fr;
            this.to = to;
        }

    }

    class Data {
        int n;
        ArrayList<Road> roads;
        ArrayList<Road> teleports;
        int[] pair;
        int[] roadsCount;

        public Data(int n) {
            this.n = n;
            roads = new ArrayList<>();
            teleports = new ArrayList<>();
            pair = new int[n];
            Arrays.fill(pair, -1);
            roadsCount = new int[n];
        }

        void addRoad(int fr, int to) {
            roads.add(new Road(fr + 1, to + 1));
            roadsCount[fr]++;
            roadsCount[to]++;
        }

        void addTeleport(int fr, int to) {
            if (pair[fr] != -1 || pair[to] != -1) {
                throw new AssertionError("Vertex already has another pair");
            }
            pair[fr] = to;
            pair[to] = fr;
            teleports.add(new Road(fr + 1, to + 1));
        }
    }

    public void writeTest(ArrayList<Data> allData) throws IOException {
        PrintWriter out = new PrintWriter("../tests/"
                + String.format("%02d", tests++));
        for (Data data : allData) {
            out.println(data.n + " " + data.roads.size() + " "
                    + data.teleports.size());
            for (Road r : data.roads) {
                out.println(r.fr + " " + r.to);
            }
            for (Road r : data.teleports) {
                out.println(r.fr + " " + r.to);
            }
        }
        out.println("0 0 0");
        out.close();
    }

    public ArrayList<Data> getExample() {
        ArrayList<Data> res = new ArrayList<>();
        Data first = new Data(4);
        first.roads.add(new Road(1, 2));
        first.roads.add(new Road(3, 4));
        first.teleports.add(new Road(3, 4));
        res.add(first);
        Data second = new Data(4);
        second.roads.add(new Road(1, 2));
        second.roads.add(new Road(2, 3));
        second.roads.add(new Road(3, 4));
        second.teleports.add(new Road(1, 4));
        res.add(second);
        Data third = new Data(8);
        third.roads.add(new Road(1, 3));
        third.roads.add(new Road(1, 2));
        third.roads.add(new Road(3, 4));
        third.roads.add(new Road(2, 4));
        third.roads.add(new Road(4, 5));
        third.roads.add(new Road(2, 5));
        third.roads.add(new Road(5, 7));
        third.roads.add(new Road(5, 6));
        third.roads.add(new Road(6, 8));
        third.roads.add(new Road(7, 8));
        third.teleports.add(new Road(1, 8));
        res.add(third);
        return res;
    }

    private ArrayList<Data> shuffleData(ArrayList<Data> cur) {
        Data[] all = new Data[cur.size()];
        for (int i = 0; i < cur.size(); i++) {
            all[i] = cur.get(i);
        }
        for (int i = 0; i < cur.size(); i++) {
            int another = rnd.nextInt(i + 1);
            Data tmp = all[another];
            all[another] = all[i];
            all[i] = tmp;
        }
        ArrayList<Data> res = new ArrayList<>();
        for (Data data : all) {
            res.add(data);
        }
        return res;
    }

    class Dsu {
        int n;
        int[] p;
        int[] sz;

        public Dsu(int n) {
            super();
            this.n = n;
            p = new int[n];
            for (int i = 0; i < n; i++)
                p[i] = i;
            sz = new int[n];
        }

        int get(int v) {
            return p[v] == v ? v : (p[v] = get(p[v]));
        }

        void union(int v, int u) {
            v = get(v);
            u = get(u);
            if (u == v)
                return;
            if (sz[u] > sz[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            p[u] = v;
        }

    }

    private Data genRandomYesTest(int n, int m) {
        Data res = new Data(n);
        int totalPairs = rnd.nextInt(n / 2);
        
        for (int i = 0; i < totalPairs; i++) {
            int fr = rnd.nextInt(n);
            int to = rnd.nextInt(n);
            if (res.pair[fr] != -1 || res.pair[to] != -1 || fr == to) {
                i--;
                continue;
            }
            res.addTeleport(fr, to);
        }
        for (int i = 0; i < m; i++) {
            int fr = rnd.nextInt(n);
            int to = rnd.nextInt(n);
            if (fr == to) {
                i--;
                continue;
            }
            res.addRoad(fr, to);
        }

        while (true) {
            int needAdd1 = -1;
            int needAdd2 = -1;
            Dsu dsu = new Dsu(n);
            for (Road r : res.roads) {
                dsu.union(r.fr - 1, r.to - 1);
            }
            for (Road r : res.teleports) {
                dsu.union(r.fr - 1, r.to - 1);
            }
            for (int i = 0; i < n - 1; i++)
                for (int j = i + 1; j < n; j++)
                    if (dsu.get(i) != dsu.get(j)) {
                        if (res.roadsCount[i] != 0 && res.roadsCount[j] != 0) {
                            needAdd1 = i;
                            needAdd2 = j;
                        }
                    }
            if (needAdd1 == -1)
                break;
            res.addRoad(needAdd1, needAdd2);
        }
        int it = 0;
        while (true) {
            ArrayList<Integer> needAddV = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (res.pair[i] == -1) {
                    if (res.roadsCount[i] % 2 != 0) {
                        needAddV.add(i);
                    }
                } else {
                    if (res.roadsCount[i] < res.roadsCount[res.pair[i]]) {
                        int diff = Math.min(3, res.roadsCount[res.pair[i]]
                                - res.roadsCount[i]);
                        for (int j = 0; j < diff; j++)
                            needAddV.add(i);
                    }
                }
            }
            if (it++ > 1000) {
                System.err.println(res.toString());
                throw new AssertionError("Something bad hapened");
            }
            if (needAddV.size() == 1) {
                throw new AssertionError("Error while creating graph");
            }
            if (needAddV.size() > 2) {
				if (needAddV.get(0) == needAddV.get(1)) {
					while (true) {
						int xx = rnd.nextInt(n);
						if (xx != needAddV.get(0)) {
							res.addRoad(needAddV.get(0), xx);
							break;
						}
					}
				} else
                res.addRoad(needAddV.get(0), needAddV.get(1));
            } else {
                break;
            }
        }
        return res;
    }
    
    private Data genRandomYesTest(int n) {
        int m = Math.min(100, 1 + rnd.nextInt(n * n / 10));
        return genRandomYesTest(n, m);
    }

    private boolean isAnswerYes(Data data) {
        int n = data.n;
        int[] cnt = new int[n];
        for (int i = 0; i < n; i++) {
            if (data.pair[i] == -1)
                cnt[i] = data.roadsCount[i] % 2;
            else
                cnt[i] = Math.max(0, data.roadsCount[i]
                        - data.roadsCount[data.pair[i]]);
        }
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += cnt[i];
        return sum <= 2;
    }

    private Data genRandomNoTest(int n) {
        Data res = genRandomYesTest(n);
        while (isAnswerYes(res)) {
            int fr = rnd.nextInt(n);
            int to = rnd.nextInt(n);
            if (fr != to) {
                res.addRoad(fr, to);
            }
        }
        return res;
    }

    private ArrayList<Data> getTest2() {
        ArrayList<Data> res = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            res.add(genRandomYesTest(5 + rnd.nextInt(20)));
        }
        for (int i = 0; i < 300; i++) {
            res.add(genRandomNoTest(5 + rnd.nextInt(20)));
        }
        return res;
    }
    
    private ArrayList<Data> getTest3() {
        ArrayList<Data> res = new ArrayList<>();
        res.add(genRandomYesTest(3, MAX - 1000));    
        return res;
    }
    
    private ArrayList<Data> getTest4() {
        ArrayList<Data> res = new ArrayList<>();
        res.add(genRandomYesTest(5, MAX - 1000));    
        return res;
    }
    
    private ArrayList<Data> getTest5() {
        ArrayList<Data> res = new ArrayList<>();
        res.add(genRandomYesTest(10, MAX - 1000));    
        return res;
    }

    private void gen() throws IOException {
        File dir = new File("../tests");
        if (!dir.exists()) {
            dir.mkdir();
        }
        ArrayList<Data> first = getExample();
        writeTest(first);
        ArrayList<Data> second = shuffleData(getTest2());
        writeTest(second);
        ArrayList<Data> test3 = getTest3();
        writeTest(test3);
        ArrayList<Data> test4 = getTest4();
        writeTest(test4);
        ArrayList<Data> test5 = getTest5();
        writeTest(test5);
    }

    public static void main(String[] args) throws IOException {
        new Gen().gen();
    }
}
