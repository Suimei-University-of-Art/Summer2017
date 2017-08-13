import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class qqsort_ak_n2n_2 {
    static int countL(ArrayList<Integer> a) {
        if (a.size() == 1) {
            return 0;
        }
        int best = a.size();
        for (int i = 1; i < a.size() - 1; i++) {
            if (a.get(i) == 1) {
                ArrayList<Integer> newA = new ArrayList<Integer>();
                for (int j = 0; j < i - 1; j++) {
                    newA.add(a.get(j));
                }
                newA.add(a.get(i - 1) + 1 + a.get(i + 1));
                for (int j = i + 2; j < a.size(); j++) {
                    newA.add(a.get(j));
                }
                int now = countL(newA) + 1;
                best = Math.min(best, now);
            }
        }

        for (int i = 0; i < a.size() - 1; i++) {
            if (1 <= i && i < a.size() - 1 && a.get(i) == 1) {
                continue;
            }
            ArrayList<Integer> newA = new ArrayList<Integer>();
            for (int j = 0; j < i; j++) {
                newA.add(a.get(j));
            }
            newA.add(a.get(i) + a.get(i + 1));
            for (int j = i + 2; j < a.size(); j++) {
                newA.add(a.get(j));
            }
            int now = countL(newA) + 1;
            best = Math.min(best, now);
        }
        return best;
    }

    static int count(int[] a) {
        int n = a.length;
        int[] where = new int[n];
        for (int i = 0; i < n; i++) {
            where[a[i]] = i;
        }
        ArrayList<Integer> bounds = new ArrayList<Integer>();
        bounds.add(0);
        for (int i = 0; i < n - 1; i++) {
            if (where[i] > where[i + 1]) {
                bounds.add(i + 1);
            }
        }
        bounds.add(n);
        ArrayList<Integer> lengths = new ArrayList<Integer>();
        for (int i = 0; i < bounds.size() - 1; i++) {
            lengths.add(bounds.get(i + 1) - bounds.get(i));
        }
        return countL(lengths);
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
