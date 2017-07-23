import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class shuffle_sm_nlogn {
    List<Move> moves;
    private Counter conflictsCounter;

    void move(int l, int r) {
        moves.add(new Move(l, r));
        //System.arraycopy(a, r, a, l, a.length - r);
//        print();
//        System.err.println("l = " + l + " r = " + r);
        moveToBack(l + 1 , r);
//        print();
//        System.err.println("");
    }

    private int get(int i) {
//        ArrayList<Integer> array = new ArrayList<Integer>();
//        root.save(array);
//        return array.get(i);
        Tree[] r = split(root, i);
        Tree[] r2 = split(r[1], 1);
        int x = r2[0].v;
        r[1] = merge(r2[0], r2[1]);
        root = merge(r[0], r[1]);
        return x;
    }
    void print() {
        ArrayList<Integer> array = new ArrayList<Integer>();
        root.save(array);
        System.err.println(array);
    }

    private Tree root;

    class Tree {
        int v;
        int y;

        int ls;
        int sz;

        Tree l;
        Tree r;


        public Tree(int v, int y) {
            this.v = v;
            this.y = y;
            ls = 0;
            sz = 1;
        }

        void save(ArrayList<Integer> store) {
            if (l != null) {
                l.save(store);
            }
            store.add(v);
            if (r != null) {
                r.save(store);
            }
        }

        void calcSizes() {
            if (l != null) {
                l.calcSizes();
                ls = l.sz;
                sz += l.sz;
            }
            if (r != null) {
                r.calcSizes();
                sz += r.sz;
            }
        }

        int check() {
            int ll = 0;
            if (l != null) {
                ll = l.check();
            }
            if (ll != ls) {
                throw new AssertionError("Left size corrupted");
            }
            int rr = 0;
            if (r != null) {
                rr = r.check();
            }
            if (ll + rr + 1 != sz) {
                throw new AssertionError("Total size corrupted");
            }
            return ll + rr + 1;
        }
    }

    Tree merge(Tree l, Tree r) {
        if (l == null) {
            return r;
        }
        if (r == null) {
            return l;
        }
        if (l.y < r.y) {
            l.sz += r.sz;
            l.r = merge(l.r, r);
            return l;
        } else {
            r.sz += l.sz;
            r.ls += l.sz;
            r.l = merge(l, r.l);
            return r;
        }
    }

    Tree[] split(Tree t, int s) {
        if (t == null) {
            return new Tree[]{null, null};
        }

        Tree[] res = new Tree[2];
        if (t.ls >= s) {
            Tree[] tmp = split(t.l, s);
            res[0] = tmp[0];
            t.sz -= s;
            t.ls -= s;
            t.l = tmp[1];
            res[1] = t;
        } else {
            Tree[] tmp = split(t.r, s - t.ls - 1);
            res[1] = tmp[1];
            t.sz = s;
            t.r = tmp[0];
            res[0] = t;
        }
        return res;
    }

    private void buildTree(int[] a) {
        Random rand = new Random(12345678987654321L);
        ArrayList<Tree> rightPath = new ArrayList<Tree>();
        for (int i = 0; i < a.length; i++) {
            Tree lst = null;
            Tree t = new Tree(a[i], rand.nextInt());
            int j = rightPath.size();
            while (j > 0 && rightPath.get(j - 1).y < t.y) {
                lst = rightPath.remove(j - 1);
                j--;
            }
            if (j > 0) {
                Tree p = rightPath.get(j - 1);
                t.l = p.r;
                p.r = t;
            } else {
                t.l = lst;
            }
            rightPath.add(t);
        }
        root = rightPath.get(0);
        root.calcSizes();
    }

    private void moveToBack(int l, int r) {
        Tree[] tt = split(root, l - 1);
        Tree[] tt2 = split(tt[1], r - l + 1);
        Tree t3 = merge(tt2[1], tt2[0]);
        root = merge(tt[0], t3);
    }

    static class Move {
        int l, r;

        Move(int l, int r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public String toString() {
            return "<" + l + ", " + r + ">";
        }
    }

    static class Counter {
        int[] head;
        int[] next;
        int[] prev;
        int[] key;
        int sum;
        int max;

        public Counter(int[] key) {
            this.key = key;
            for (int i = 0; i < key.length; i++) {
                sum += key[i];
            }
            head = new int[key.length];
            next = new int[key.length];
            prev = new int[key.length];
            Arrays.fill(head, -1);
            Arrays.fill(next, -1);
            Arrays.fill(prev, -1);
            for (int i = 0; i < key.length; i++) {
                next[i] = head[key[i]];
                head[key[i]] = i;
            }
            for (int i = 0; i < key.length; i++) {
                if (next[i] != -1) {
                    prev[next[i]] = i;
                }
            }
            max = head.length - 1;
        }

        public int getMaxCount() {
            while (max >= 0 && head[max] == -1) {
                max--;
            }
            return max;
        }

        public int getMaxKey() {
            int maxCount = getMaxCount();
            if (maxCount == -1) {
                return -1;
            }
            return head[maxCount];
        }

        public int getSum() {
            return sum;
        }

        public void decCount(int key) {
            if (next[key] != -1) {
                prev[next[key]] = prev[key];
            }
            if (prev[key] != -1) {
                next[prev[key]] = next[key];
            }
            if (head[this.key[key]] == key) {
                head[this.key[key]] = next[key];
            }
            this.key[key]--;
            next[key] = head[this.key[key]];
            prev[key] = -1;
            head[this.key[key]] = key;
            if (next[key] != -1) {
                prev[next[key]] = key;
            }
            sum--;
        }
    }

    boolean solveIfBasic(int length) {
        int sourceLength = length;
        int l = -1;
        int r = -1;
        boolean tooManyMaxColor = false;
        int maxColor = conflictsCounter.getMaxKey();
        int maxColorCount = conflictsCounter.getMaxCount();
        int totalConflicts = conflictsCounter.getSum();

        if (maxColorCount > (totalConflicts + 1) / 2) {
            tooManyMaxColor = true;
        }
        if (maxColorCount < (totalConflicts + 1) / 2) {
            return false;
        }
        conflictsCounter = null;

        for (int i = 0; i < length; i++) {
            if (get(i) == maxColor) {
                if (l == -1) {
                    l = i;
                }
                r = i;
            }
        }
        int remainVirtual = 0;
        if (tooManyMaxColor) {
            int fatConflicts = maxColorCount;
            int otherConflicts = totalConflicts - fatConflicts;
            remainVirtual = fatConflicts - otherConflicts;
        }

        boolean wasC = r < length - 1;
        boolean found = true;
        while (found) {
            found = false;
            for (int i = r + 1; i < length - 1; i++) {
                if (get(i) == get(i + 1) || remainVirtual > 0) {
                    if (get(i) != get(i + 1)) {
                        remainVirtual--;
                    }
                    move(r, i + 1);
                    length -= i + 1 - r;
                    r--;
                    found = true;
                    break;
                }
            }
        }

        if (tooManyMaxColor && l > 0 && wasC && length % 2 == 1 && maxColorCount + 1 == (sourceLength + 1) / 2 && remainVirtual > 0) {
            move(l, l + 1);
            r--;
            length--;
            remainVirtual--;
        }

        found = true;
        while (found) {
            found = false;
            for (int i = l - 2; i >= -1; i--) {
                if ((i >= 0 && get(i) == get(i + 1)) || remainVirtual > 0) {
                    if (i >= 0 && get(i) != get(i + 1)) {
                        remainVirtual--;
                    }
                    move(i + 1, l + 1);
                    length -= l - i;
                    int shift = l - i;
                    l = l - shift + 1;
                    r = r - shift;

                    found = true;
                    break;
                }
            }
        }


        if (l < r) {
            assert l == r - 1;
            move(0, r);
        }
        return true;
    }


    void solve() throws IOException {
        int n = nextInt();
        for (int it = 0; it < n; it++) {
            solveOne();
        }
    }

    private void solveOne() throws IOException {
        moves = new ArrayList<Move>();
        int n = nextInt();
        int[] b = new int[n];
        int cur = -1;
        int val = 0;
        for (int i = 0; i < n; i++) {
            int x = nextInt();
            if (x < cur) {
                throw new AssertionError();
            }
            if (x > cur) {
                cur = x;
                val++;
            }
            b[i] = val;
        }
        buildTree(b);
        int[] numberCount = new int[n + 1];
        for (int i = 0; i < b.length; i++) {
            numberCount[b[i]]++;
        }
        for (int i = 0; i < numberCount.length; i++) {
            if (numberCount[i] > (n + 1) / 2) {
                out.println("-1");
                return;
            }
        }

        int[] conflictsCount = new int[b.length];
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == b[i + 1]) {
                conflictsCount[b[i]]++;
            }
        }

        conflictsCounter = new Counter(conflictsCount);
        int length = n;
        int lastPos = length - 2;
        while (true) {
            if (solveIfBasic(length)) {
                break;
            }
            while (lastPos >= length - 1 || get(lastPos) != get(lastPos + 1) || get(lastPos - 1) == get(lastPos)) {
                lastPos--;
            }
            int otherPos = lastPos - 1;
            while (get(otherPos) != get(otherPos - 1)) {
                otherPos--;
            }
            conflictsCounter.decCount(get(otherPos));
            conflictsCounter.decCount(get(lastPos));
            move(otherPos, lastPos + 1);
            length -= lastPos - otherPos + 1;
            lastPos = otherPos + 1;
//            a = Arrays.copyOf(a, length);
        }
        out.println(moves.size());
        for (Move move : moves) {
            out.println((move.l + 1) + " " + move.r);
        }
    }

    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    int nextInt() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return Integer.parseInt(st.nextToken());
    }

    public void run() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        br.close();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new shuffle_sm_nlogn().run();
    }
}
