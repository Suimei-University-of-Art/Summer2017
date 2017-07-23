import java.io.*;
import java.util.*;

public class birds_ak {

    static class MyDeque {
        Deque<Integer> d;
        int add;

        MyDeque() {
            d = new ArrayDeque<Integer>();
            add = 0;
        }

        void addToAll(int a) {
            add += a;
        }

        int peekFirst() {
            return d.peekFirst() + add;
        }

        int peekLast() {
            return d.peekLast() + add;
        }

        int pollFirst() {
            return d.pollFirst() + add;
        }

        int pollLast() {
            return d.pollLast() + add;
        }

        void addLast(int val) {
            d.addLast(val - add);
        }

        boolean isEmpty() {
            return d.isEmpty();
        }
    }

    static class Bird implements Comparable<Bird> {
        int id, pos;
        int direction; // <- 0   -> 1

        Bird(int id, int pos, int direction) {
            this.id = id;
            this.pos = pos;
            this.direction = direction;
        }

        @Override
        public int compareTo(Bird b) {
            return pos - b.pos;
        }

        @Override
        public String toString() {
            return "Bird{" +
                    "id=" + id +
                    ", pos=" + pos +
                    ", direction=" + direction +
                    '}';
        }
    }
                 //// УБИТЬ ПТИЦ НА ОДИНАКОВЫХ ПОЗИЦИЯЯХ!!!
    void solve() throws IOException {
        int len = in.nextInt();
        assert 1 <= len && len <= 1000000000;
        int n = in.nextInt();
        assert 0 <= n && n <= 100000;
        int[] a = new int[n];
        MyDeque toRight = new MyDeque();
        ArrayList<Bird> birds = new ArrayList<Bird>();
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            birds.add(new Bird(i, a[i], 0));
            assert 0 <= a[i] && a[i] <= len;
        }
        Arrays.sort(a);
        for (int i = 0; i < a.length - 1; i++)
            assert a[i] != a[i + 1];
        for (int i : a) {
            toRight.addLast(i);
        }
        int m = in.nextInt();
        assert 0 <= m && m <= 100000;
        a = new int[m];
        MyDeque toLeft = new MyDeque();
        for (int i = 0; i < m; i++) {
            a[i] = in.nextInt();
            birds.add(new Bird(i, a[i], 1));
            assert 0 <= a[i] && a[i] <= len;
        }
        Collections.sort(birds);
//        System.err.println(birds);
        Deque<Bird> birdsDeque = new ArrayDeque<Bird>(birds);
//        System.err.println(birdsDeque);
        Arrays.sort(a);
        for (int i = 0; i < a.length - 1; i++)
            assert a[i] != a[i + 1];
        for (int i : a) {
            toLeft.addLast(i);
        }
        long ans = 0;
        long[][] times = new long[2][];
        times[0] = new long[n];
        times[1] = new long[m];
        int reverse = 0;
        while (!toRight.isEmpty() || !toLeft.isEmpty()) {
            int right = Integer.MAX_VALUE;
            if (!toRight.isEmpty()) {
                right = len - toRight.peekLast();
            }
            int left = Integer.MAX_VALUE;
            if (!toLeft.isEmpty()) {
                left = toLeft.peekFirst();
            }
            toLeft.addToAll(-Math.min(left, right));
            toRight.addToAll(Math.min(left, right));
            if (left < right) {
//                System.err.print("< ");
                ans += left;
                toLeft.pollFirst();
                MyDeque tmp = toLeft;
                toLeft = toRight;
                toRight = tmp;
                Bird flewn;
//                if (reverse == 0)
                    flewn = birdsDeque.pollFirst();
//                else
//                    flewn = birdsDeque.pollLast();
                times[flewn.direction][flewn.id] = ans;
                reverse = 1 - reverse;
            } else if (right < left) {
//                System.err.print("> ");
                ans += right;
                toRight.pollLast();
                MyDeque tmp = toLeft;
                toLeft = toRight;
                toRight = tmp;
                Bird flewn;
//                if (reverse == 0)
                    flewn = birdsDeque.pollLast();
//                else
//                    flewn = birdsDeque.pollFirst();
                times[flewn.direction][flewn.id] = ans;
                reverse = 1 - reverse;
            } else if (left == right) {
//                System.err.print("<> ");
                ans += left;
                toRight.pollLast();
                toLeft.pollFirst();
                Bird flewn = birdsDeque.pollLast();
                times[flewn.direction][flewn.id] = ans;
                flewn = birdsDeque.pollFirst();
                times[flewn.direction][flewn.id] = ans;
            }
        }
//        System.err.println(birdsDeque);
        for (long[] xs : times) {
            for (long x : xs) {
                out.print(x + " ");
            }
            out.println();
        }
    }

    static MyScanner in;
    static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new MyScanner(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        new birds_ak().solve();
        out.close();
    }

    static class MyScanner {
        private StringTokenizer st;
        private BufferedReader br;

        public MyScanner(Reader r) {
            br = new BufferedReader(r);
        }

        public String nextToken() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(nextToken());
        }
    }
}
