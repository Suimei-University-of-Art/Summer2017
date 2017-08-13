import java.io.*;
import java.util.*;

public class test_bm {
    FastScanner in;
    PrintWriter out;
    
    String toBinaryString(int x, int n) {
        char[] ans = new char[n];
        for (int i = 0; i < n; i++) {
            ans[i] = ((x >> i) & 1) == 1 ? '1' : '0';
        }
        return new String(ans);
    }
    
    void solve() {
        int n = in.nextInt();
        int cur = 1;
        ArrayList<Integer> ans = new ArrayList<>();
        ArrayList<Integer> bitCount = new ArrayList<>();
        ans.add(1);
        bitCount.add(1);
        ans.add(0);
        bitCount.add(0);
        while (cur != n) {
            cur++;
            int st = 0;
            ArrayList<Integer> newAns = new ArrayList<>();
            ArrayList<Integer> newBitCount = new ArrayList<>();
            while (st < ans.size()) {
                int sp = st;
                while (sp != ans.size() && bitCount.get(sp) == bitCount.get(st))
                    sp++;
                for (int i = st; i < sp; i++) {
                    newAns.add((ans.get(i) << 1) + 1);
                    newBitCount.add(bitCount.get(st) + 1);
                }
                for (int i = sp - 1; i >= st; i--) {
                    newAns.add((ans.get(i) << 1) + 0);
                    newBitCount.add(bitCount.get(st) + 0);
                }
                st = sp;
            }
            ans = newAns;
            bitCount = newBitCount;
        }
        for (int x : ans) {
            out.println(toBinaryString(x, n));
        }
    }

    void run() {
        try {
            in = new FastScanner(new File("object.in"));
            out = new PrintWriter(new File("object.out"));

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
        new test_bm().runIO();
    }
}