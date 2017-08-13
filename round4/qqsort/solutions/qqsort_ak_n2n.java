import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class qqsort_ak_n2n {
    private static boolean sorted(ArrayList<Integer> a) {
        for (int i = 0; i < a.size() - 1; i++) {
            if (a.get(i) > a.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    static int count(ArrayList<Integer> a) {
        if (sorted(a)) {
            return 0;
        }
        int n = a.size();
        int best = a.size();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> lower = new ArrayList<Integer>();
            ArrayList<Integer> upper = new ArrayList<Integer>();
            for (int j = 0; j < n; j++) {
                if (a.get(j) < a.get(i)) {
                    lower.add(a.get(j));
                } else if (a.get(j) > a.get(i)) {
                    upper.add(a.get(j));
                }
            }
            if (lower.size() == n || upper.size() == n) {
                continue;
            }
            int now = count(lower) + 1 + count(upper);
            best = Math.min(best, now);
        }
        return best;
    }

    static int count(int[] a) {
        ArrayList<Integer> b = new ArrayList<Integer>();
        for (int i : a) {
            b.add(i);
        }
        return count(b);
    }

    public static void main(String[] args) throws IOException {
        MyScanner in = new MyScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = in.nextInt() - 1;
            }
            out.println(count(a));
        }

        out.close();
    }

    static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        MyScanner(Reader r) {
            br = new BufferedReader(r);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }


}
