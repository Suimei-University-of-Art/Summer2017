import java.util.*;
import java.io.*;

class parse_as_slow {
    FastScanner in;
    PrintWriter out;

    boolean canParse(Word w, Word[] d) {
        int L = w.s.length();
        boolean[] can = new boolean[L + 1];
        can[0] = true;
        for (int j = 0; j < L; j++) {
            if (can[j]) {
                for (Word s : d) {
                    int kl = s.s.length();
                    if (j + kl <= L && compStrings(w.h, j, s.h, 0, kl)) {
                        can[j + kl] = true;
                    }
                }
            }
        }
        return can[L];
    }

    boolean greedyParse(Word w, Word[] d) {
        int p = 0;
        boolean good = true;
        while (p < w.s.length()) {
            int q = 0;
            for (Word z : d) {
                if (z.s.length() > q && p + z.s.length() <= w.s.length() && compStrings(w.h, p, z.h, 0, z.s.length())) {
                    q = z.s.length();
                }
            }
            if (q == 0) {
                good = false;
                break;
            } else {
                p += q;
            }
        }
        return good;
    }
    
    final static long P = 239;
    final static long[] Ppow = new long[501];
    
    static {
        Ppow[0] = 1;
        for (int i = 1; i < Ppow.length; i++) {
            Ppow[i] = Ppow[i - 1] * P;
        }
    }
    
    long[] compHashes(String s) {
        long[] res = new long[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            res[i + 1] = res[i] * P + s.charAt(i);  
        }
        return res;
    }
    
    boolean compStrings(long[] hashes1, int from1, long[] hashes2, int from2, int len) {
        long h1 = hashes1[from1 + len] - hashes1[from1] * Ppow[len];
        long h2 = hashes2[from2 + len] - hashes2[from2] * Ppow[len];
        return h1 == h2;
    }
    
    class Word {
        String s;
        long[] h;

        Word(String s) {
            this.s = s;
            h = compHashes(s);
        }
    }

    public void solve() throws IOException {
        int n = in.nextInt();
        assert 1 <= n && n <= 250;
        Word[] d = new Word[n];
        Set<String> words = new HashSet<String>();
        for (int i = 0; i < n; i++) {
            String dd = in.next();
            assert 1 <= dd.length() && dd.length() <= 500;
            assert dd.matches("[a-z]*");
            d[i] = new Word(dd);
            words.add(dd);
        }
        assert words.size() == n;

        Set<String> tails = new HashSet<String>();
        for (Word w : d) {
            int L = w.s.length();
            boolean[] can = new boolean[L + 1];
            can[0] = true;
            for (int j = 0; j < L; j++) {
                if (can[j]) {
                    for (int k = 0; k < n; k++) {
                        int kl = d[k].s.length();
                        if (j + kl <= L && compStrings(w.h, j, d[k].h, 0, kl)) {
                            can[j + kl] = true;
                        } else if (j + kl > L && compStrings(w.h, j, d[k].h, 0, L - j)) {
                            tails.add(d[k].s.substring(L - j));
                        }
                    }
                }
            }
        }

//        System.out.println(tails);
        
        String ans = null;
        for (String w : words) {
            for (String t : tails) {
                if (ans != null && w.length() + t.length() > ans.length()) {
                    continue;
                }
                Word c = new Word(w + t);
                if (canParse(c, d) && !greedyParse(c, d)) {
                    ans = c.s;
                }
            }
        }
        
        if (ans == null) {
            out.println("Good vocabulary!");
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
        new parse_as_slow().run();
    }
}