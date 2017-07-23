import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class shuffle_sm_n2_improved {
    int[] tmp;
    List<Move> moves;
    private Counter conflictsCounter;

    void move(int[] a, int l, int r) {
        //System.err.println("move " + l + " " + r + " in " + Arrays.toString(a));
        moves.add(new Move(l, r));
        System.arraycopy(a, l, tmp, 0, r - l);
        System.arraycopy(a, r, a, l, a.length - r);
        System.arraycopy(tmp, 0, a, a.length - (r - l), r - l);
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
        int[] a;
        int sum;

        public Counter(int[] a) {
            this.a = a;
            for (int i = 0; i < a.length; i++) {
                sum += a[i];
            }
        }

        public int getMaxCount() {
            int maxKey = getMaxKey();
            if (maxKey == -1) {
                return 0;
            }
            return a[maxKey];
        }

        public int getMaxKey() {
            int max = 0;
            int maxi = -1;
            for (int i = 0; i < a.length; i++) {
                if (a[i] > max) {
                    max = a[i];
                    maxi = i;
                }
            }
            return maxi;
        }

        public int getSum() {
            return sum;
        }

        public int getCount(int key) {
            return a[key];
        }

        public void decCount(int key) {
            a[key]--;
            sum--;
        }
    }

    boolean solveIfBasic(int[] a, int length) {
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
            if (a[i] == maxColor) {
                if (l == -1) {
                    l = i;
                }
                r = i;
            }
        }
        int remainVirtual = 0;
        //System.err.println("totalConflicts = " + totalConflicts);
        if (tooManyMaxColor) {
            int fatConflicts = maxColorCount;
            int otherConflicts = totalConflicts - fatConflicts;
            remainVirtual = fatConflicts - otherConflicts;
        }
        //System.err.println("maxColor = " + maxColor + ", l = " + l + ", r = " + r + ", tooManyMaxColor = " + tooManyMaxColor);
        //System.err.println("remainVirtual = " + remainVirtual);
        //System.err.println("l = " + l + ", r = " + r);
//                [l..r]

        boolean wasC = r < length - 1;
        boolean found = true;
        while (found) {
            found = false;
            for (int i = r + 1; i < length - 1; i++) {
                if (a[i] == a[i + 1] || remainVirtual > 0) {
                    if (a[i] != a[i + 1]) {
                        remainVirtual--;
                    }
                    move(a, r, i + 1);
                    length -= i + 1 - r;
                    r--;
                    found = true;
                    break;
                }
            }
        }

        if (tooManyMaxColor && l > 0 && wasC && length % 2 == 1 && maxColorCount + 1 == (sourceLength + 1) / 2 && remainVirtual > 0) {
            //System.err.println("Crazy if");
            move(a, l, l + 1);
            r--;
            length--;
            remainVirtual--;
        }

        found = true;
        while (found) {
            found = false;
            for (int i = l - 2; i >= -1; i--) {
                //System.err.println("i = " + i + ", a = " + Arrays.toString(a));
                if ((i >= 0 && a[i] == a[i + 1]) || remainVirtual > 0) {
                    if (i >= 0 && a[i] != a[i + 1]) {
                        remainVirtual--;
                    }
                    move(a, i + 1, l + 1);
                    length -= l - i;
                    int shift = l - i;
                    l = l - shift + 1;
                    r = r - shift;

                    found = true;
                    break;
                }
            }
        }


        //System.err.println("a = " + Arrays.toString(a) + " l = " + l + " r = " + r);
        if (l < r) {
            assert l == r - 1;
            move(a, 0, r);
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
        int[] a = new int[n];
        tmp = new int[n];
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
            a[i] = val;
        }
        int[] numberCount = new int[n + 1];
        for (int i = 0; i < a.length; i++) {
            numberCount[a[i]]++;
        }
        for (int i = 0; i < numberCount.length; i++) {
            if (numberCount[i] > (n + 1) / 2) {
                out.println("-1");
                return;
            }
        }

        int[] conflictsCount = new int[a.length];
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i + 1]) {
                conflictsCount[a[i]]++;
            }
        }

        conflictsCounter = new Counter(conflictsCount);
        int length = a.length;
        while (true) {

            if (solveIfBasic(a, length)) {
                break;
            }
            int lastColor = -1;
            int lastPos = -1;
            int otherPos = -1;
            for (int i = length - 2; i >= 0; i--) {
                if (a[i] == a[i + 1]) {
                    if (lastColor == -1) {
                        lastColor = a[i];
                    }
                    if (lastColor == a[i]) {
                        lastPos = i;
                    } else {
                        otherPos = i;
                        break;
                    }
                }
            }
            conflictsCounter.decCount(a[otherPos]);
            conflictsCounter.decCount(a[lastPos]);
            move(a, otherPos + 1, lastPos + 1);
            length -= lastPos - otherPos;
        }
        //System.err.println("a = " + Arrays.toString(a));
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i + 1]) {
                throw new AssertionError("a[" + i + "] == a[" + (i + 1) + "]");
            }
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
        new shuffle_sm_n2_improved().run();
    }
}
