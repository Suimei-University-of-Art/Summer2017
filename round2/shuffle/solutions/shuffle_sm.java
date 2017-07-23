import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class shuffle_sm {
    private Counter conflictsCounter;
    Array array;
    static class Array {
        int[] a;

        Array(int[] a) {
            moves = new ArrayList<Move>();
            this.a = a;
            tailBegin = a.length;
        }

        int tailBegin;
        int cutFrom = -1;

        int cutTo = -1;

        List<Move> moves;

        void move(int l, int r) {
            moves.add(new Move(l, r));
            if (cutFrom == -1 || r < cutFrom) {
                if (cutFrom != -1) {
                    tailBegin = cutFrom;
                }
                cutFrom = l;
                cutTo = r;
            }
            if (l < cutFrom && r >= cutFrom) {
                int before = cutFrom - l;
                int after = r - l - before;
                cutFrom -= before;
                cutTo += after;
            }
        }

        void normalize() {
            if (cutFrom == -1) return;
            int[] tmp = Arrays.copyOfRange(a, cutTo, tailBegin);
            System.arraycopy(tmp, 0, a, cutFrom, tailBegin - cutTo);
            tailBegin -= cutTo - cutFrom;
            cutFrom = -1;
            cutTo = -1;
        }

        private int get(int pos) {
            if (pos < cutFrom) {
                return a[pos];
            }
            if (pos < headLength()) {
                return a[cutTo + (pos - cutFrom)];
            }
            return a.length;

        }

        private int headLength() {
            return tailBegin - (cutTo - cutFrom);
        }

        private boolean isConflict(int pos) {
            boolean res = isConflictImpl(pos);
            return  res;
        }

        private boolean isConflictImpl(int pos) {
            if (pos >= headLength()) {
                return false;
            }
            return get(pos) == get(pos + 1);
        }

        @Override
        public String toString() {
            if (cutFrom != -1) {
                return Arrays.toString(Arrays.copyOf(a, cutFrom)) + Arrays.toString(Arrays.copyOfRange(a ,cutTo, tailBegin));
            } else {
                return Arrays.toString(Arrays.copyOf(a, tailBegin));
            }
        }
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
        array.normalize();
        conflictsCounter = null;

        for (int i = 0; i < length; i++) {
            if (array.get(i) == maxColor) {
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
                if (array.isConflict(i) || remainVirtual > 0) {
                    if (!array.isConflict(i)) {
                        remainVirtual--;
                    }
                    array.move(r, i + 1);
                    length -= i + 1 - r;
                    r--;
                    found = true;
                    break;
                }
            }
        }

        if (tooManyMaxColor && l > 0 && wasC && length % 2 == 1 && maxColorCount + 1 == (sourceLength + 1) / 2 && remainVirtual > 0) {
            array.move(r, r + 1);
            r--;
            length--;
            remainVirtual--;
        }

        found = true;
        while (found) {
            found = false;
            for (int i = l - 2; i >= -1; i--) {
                if ((i >= 0 && array.isConflict(i)) || remainVirtual > 0) {
                    if (i >= 0 && !array.isConflict(i)) {
                        remainVirtual--;
                    }
                    array.move(i + 1, l + 1);
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
            array.move(0, r);
        }
        return true;
    }


    void solve() throws IOException {
        int cnt = nextInt();
        for (int it = 0; it < cnt; it++) {
            solveOne();
        }
    }

    private void solveOne() throws IOException {
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
        array = new Array(b);
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

        int[] conflictsCount = new int[b.length + 1];
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
            while (lastPos >= length - 1 || !array.isConflict(lastPos) || array.isConflict(lastPos - 1)) {
                lastPos--;
            }
            int otherPos = lastPos - 1;
            while (!array.isConflict(otherPos - 1)) {
                otherPos--;
            }
            conflictsCounter.decCount(array.get(otherPos));
            conflictsCounter.decCount(array.get(lastPos));
            array.move(otherPos, lastPos + 1);
            length -= lastPos - otherPos + 1;
            lastPos = otherPos + 1;
//            a = Arrays.copyOf(a, length);
        }
        out.println(array.moves.size());
        for (Move move : array.moves) {
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
        new shuffle_sm().run();
    }
}
