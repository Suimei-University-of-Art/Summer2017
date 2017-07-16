import java.util.*;
import java.io.*;

class prince_as_n3_go_right {
    FastScanner in;
    PrintWriter out;
    
    class Trap {
        int L;
        int R;
        int from;
        int to;

        Trap(int from, int period, int l, int r) {
            L = l;
            R = r;
            this.from = from;
            this.to = from + period;
        }
    }
    
    int n;
    Trap[] t;
    
    int canGo(int x1, int y1, int x2, int y2) {
//        if (x1 == 0 && y1 == 0 && x2 == 0 && y2 == 2) {
//            System.out.println();
//        }
		if (x2 < x1) {
			return -1;
		}
        int dist = Math.abs(x1 - x2);
        if (y2 < y1 + dist) {
            return -1;
        }
        int ys = y1 + dist;
        for (int i = 0; i < n; i++) {
            if (t[i].to >= ys && t[i].from < y2 && t[i].L < x2 && t[i].R > x2) {
                return -1;
            }
            int xL = Math.min(x1, x2);
            int xR = Math.max(x1, x2);
            if (t[i].R <= xL || t[i].L >= xR || t[i].from >= ys || t[i].to <= y1) {
                continue;
            }
            int left = t[i].L;
            if (left < xL) {
                left = xL; 
            }
            int right = t[i].R;
            if (right > xR) {
                right = xR;
            }
            int bottom = t[i].from;
            if (bottom < y1) {
                bottom = y1;
            }
            int top = t[i].to;
            if (top > ys) {
                top = ys;
            }
            if (x1 < x2) {
                if (left - x1 >= top - y1 || right - x1 <= bottom - y1) {
                    continue;
                }
            }
            if (x2 < x1) {
                if (x1 - right >= top - y1 || x1 - left <= bottom - y1) {
                    continue;
                }
            }
            return -1;
        }
//        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
        return y2 - y1;
    }

    public void solve() throws IOException {
        n = in.nextInt();
        int x = in.nextInt();
        
        t = new Trap[n];
        for (int i = 0; i < n; i++) {
            t[i] = new Trap(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
        }

        int[][] can = new int[2 * n + 2][2 * n + 2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                can[2 * i][2 * j] = canGo(t[i].L, t[i].to, t[j].L, t[j].to);
                can[2 * i + 1][2 * j] = canGo(t[i].R, t[i].to, t[j].L, t[j].to);
                can[2 * i][2 * j + 1] = canGo(t[i].L, t[i].to, t[j].R, t[j].to);
                can[2 * i + 1][2 * j + 1] = canGo(t[i].R, t[i].to, t[j].R, t[j].to);
            }
        }
        for (int i = 0; i < n; i++) {
            can[2 * n][2 * i] = canGo(0, 0, t[i].L, t[i].to);
            can[2 * n][2 * i + 1] = canGo(0, 0, t[i].R, t[i].to);
            can[2 * i][2 * n + 1] = canGo(t[i].L, t[i].to, x, t[i].to + x - t[i].L);
            can[2 * i + 1][2 * n + 1] = canGo(t[i].R, t[i].to, x, t[i].to + x - t[i].R);
        }
        
        can[2 * n][2 * n + 1] = canGo(0, 0, x, x);
        
        int N = 2 * n + 2;
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (can[i][k] != -1 && can[k][j] != -1 && (can[i][j] == -1 || can[i][j] > can[i][k] + can[k][j])) {
                        can[i][j] = can[i][k] + can[k][j];
                    }
                }
            }
        }
        
        int ans = can[2 * n][2 * n + 1];
        if (ans == -1) {
            out.println("Impossible");
        } else {
            out.println(ans);
        }
    }

    public void run() {
        try {
            in = new FastScanner(System.in);
            out = new PrintWriter(System.out);

            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream f) {
            br = new BufferedReader(new InputStreamReader(f));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] arg) {
        new prince_as_n3_go_right().run();
    }
}