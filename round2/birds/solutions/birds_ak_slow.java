import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class birds_ak_slow {

    static enum Orientation {
        LEFT, RIGHT
    }

    static class Bird {
        int position, length;
        Orientation o;
        boolean flew;

        Bird(int position, int length, Orientation o) {
            this.position = position;
            this.length = length;
            this.o = o;
            flew = false;
        }

        int timeToFlew() {
            if (o == Orientation.LEFT)
                return position;
            else
                return length - position;
        }

        void advance(int a) {
            if (o == Orientation.LEFT) {
                position -= a;
                if (position < 0)
                    throw new AssertionError();
            } else {
                position += a;
                if (position > length)
                    throw new AssertionError();
            }
        }

        void reverse() {
            if (o == Orientation.LEFT)
                o = Orientation.RIGHT;
            else
                o = Orientation.LEFT;
        }

        @Override
        public String toString() {
            return "Bird{" +
                    "position=" + position +
                    ", length=" + length +
                    ", o=" + o +
                    ", flew=" + flew +
                    '}';
        }
    }

    void solve() throws IOException {
        int length = in.nextInt();
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        int m = in.nextInt();
        int[] bb = new int[m];
        for (int i = 0; i < m; i++) {
            bb[i] = in.nextInt();
        }
        Bird[] birds = new Bird[n + m];
        for (int i = 0; i < n; i++) {
            birds[i] = new Bird(a[i], length, Orientation.RIGHT);
        }
        for (int i = 0; i < m; i++) {
            birds[n + i] = new Bird(bb[i], length, Orientation.LEFT);
        }
        int flewCnt = 0;
        long ans = 0;

        HashSet<Integer> used = new HashSet<Integer>();
        for (Bird b : birds) {
            int now = b.position + 1;
            if (b.o == Orientation.LEFT)
                now = -now;
            if (used.contains(now))
                throw new AssertionError();
            used.add(now);
        }
        while (flewCnt != n + m) {
            int min = Integer.MAX_VALUE;
            for (Bird b : birds) {
                if (!b.flew) {
                    min = Math.min(min, b.timeToFlew());
                }
            }
            List<Bird> f = new ArrayList<Bird>();
            for (Bird b : birds) {
                if ((!b.flew) && b.timeToFlew() == min) {
                    f.add(b);
                }
            }
            if ((f.size() > 2) || (f.size() == 2 && f.get(0).o == f.get(1).o)) {
                System.err.println(min + " " + flewCnt);
                System.err.println(f);
                throw new AssertionError();
            }
            ans += min;
            for (Bird b : birds) {
                if (b.flew)
                    continue;
                b.advance(min);
                if (f.size() == 1) {
                    b.reverse();
                }
            }
            for (Bird b : f) {
                b.flew = true;
                flewCnt++;
            }
        }
        out.println(ans);
    }

    static MyScanner in;
    static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new MyScanner(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        new birds_ak_slow().solve();
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
