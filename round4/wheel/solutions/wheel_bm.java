import java.io.*;
import java.util.*;

public class wheel_bm {
    FastScanner in;
    PrintWriter out;

    double[] answers = { 0.0, 1.5, 2.25, 2.75, 3.4375, 3.9375, 4.453125,
            4.9296875, 5.40234375, 5.8671875, 6.326171875, 6.78955078125,
            7.254638671875, 7.7220458984375, 8.192626953125, 8.666595458984375,
            9.143417358398438, 9.623077392578125, 10.105350494384766,
            10.589944839477539, 11.076598167419434, 11.565094470977783,
            12.055210828781128, 12.54674232006073, 13.03950423002243,
            13.533333122730255, 14.028083637356758, 14.523627571761608,
            15.019852049648762, 15.516658805310726, 16.013962573371828,
            16.511689593084157, 17.00977622694336, 17.508167792577296,
            18.006817448651418, 18.505685175972758, 19.004736859205877,
            19.50394348215923, 20.003280418106442, 20.502726807673753,
            21.002265014179102, 21.50188015080721, 22.00155967164369,
            22.501293019287232, 23.00107132205426, 23.500887134587288,
            24.000734216234747, 24.50060734219708, 25.000502142959117,
            25.50041496806874, 26.000342770795374, 26.500283010646605,
            27.000233571117544, 27.50019269040264, 28.000158903116006,
            28.500130991338118, 29.000107943550642, 29.50008892023173,
            30.000073225063073, 30.50006028086091, 31.000049609476214,
            31.50004081502608, 32.000033569916376, 32.500027603202646,
            33.00002269090419, 33.50001864795172, 34.0000153214963,
            34.50001258535481, 35.0000103354013, 35.50000848574669,
            36.00000696557029, 36.50000571649869, 37.00000469043585,
            37.50000384776475, 38.000003155867795, 38.50000258789328,
            39.00000212174695, 39.50000173925263, 40.00000142546242,
            40.5000011680874, 41.000000957023936, 41.500000783970926,
            42.000000642109654, 42.50000052583839, 43.00000043055682,
            43.5000003524887, 44.00000028853524, 44.50000023615224,
            45.00000019325333, 45.50000015812632, 46.00000012936777,
            46.500000105825926, 47.00000008655798, 47.500000070789454,
            48.00000005788658, 48.5000000473301, 49.00000003869462,
            49.500000031630854, 50.00000002585372, 50.50000002112977,
            51.00000001726714, 51.50000001410902, 52.00000001152772,
            52.50000000941746, 53.00000000769269, 53.50000000628295,
            54.00000000513127, 54.50000000419037, 55.00000000342191,
            55.50000000279383, 56.00000000228093, 56.50000000186174,
            57.000000001519616, 57.50000000124019, 58.00000000101204,
            58.50000000082584, 59.00000000067405, 59.50000000054933,
            60.00000000044805, 60.50000000036539, 61.00000000029802,
            61.50000000024298, 62.000000000198064, 62.50000000016159,
            63.00000000013133, 63.500000000107136, 64.00000000008679,
            64.50000000006966, 65.00000000005673, 65.50000000004628,
            66.00000000003756, 66.50000000003, 67.00000000002451,
            67.50000000001897, 68.00000000001552, 68.50000000001262,
            69.00000000000902, 69.50000000000742, 70.0000000000055,
            70.50000000000414, 71.00000000000296, 71.50000000000188,
            72.00000000000139, 72.50000000000017, 72.99999999999967,
            73.49999999999945, 73.99999999999969, 74.49999999999913,
            74.99999999999902, 75.4999999999979, 75.99999999999784,
            76.49999999999854 };

    double getAnswer(int n) {
        if (n < answers.length) {
            return answers[n];
        }
        return (n + 2.) / 2;
    }

    /**
     * generates first 150 answers in less than 5 seconds
     * 
     * @param n
     *            max cities count
     * @return answers for citiesCount <= n
     */
    double[] okSolutionArray(int n) {
        n += 2;
        double[] result = new double[n];
        double[][][][] dp = new double[2][n][n][n];
        dp[0][0][0][0] = 0.5;
        for (int used = 0; used < n - 1; used++) {
            for (int cntOnes = 0; cntOnes < n; cntOnes++) {
                for (int maxZeros = 0; maxZeros < n; maxZeros++) {
                    for (int curZeros = 0; curZeros <= maxZeros; curZeros++) {
                        double curDpHalf = 0.5 * dp[0][cntOnes][maxZeros][curZeros];
                        if (curDpHalf == 0) {
                            continue;
                        }
                        dp[1][cntOnes + 1][maxZeros][0] += curDpHalf;
                        dp[1][cntOnes][Math.max(maxZeros, curZeros + 1)][curZeros + 1] += curDpHalf;
                    }
                }
            }
            double res = ((used + 1) + 0.) / Math.pow(2., used + 1);
            for (int cntOnes = 0; cntOnes < n; cntOnes++) {
                for (int maxZeros = 0; maxZeros < n; maxZeros++) {
                    for (int curZeros = 0; curZeros <= maxZeros; curZeros++) {
                        double curDp = dp[0][cntOnes][maxZeros][curZeros];
                        res += (curZeros + 1) * curDp
                                * Math.max(cntOnes + 2, maxZeros);
                    }
                }
            }
            result[used + 1] = res;
            for (int cntOnes = 0; cntOnes < n; cntOnes++) {
                for (int maxZeros = 0; maxZeros < n; maxZeros++) {
                    for (int curZeros = 0; curZeros <= maxZeros; curZeros++) {
                        dp[0][cntOnes][maxZeros][curZeros] = dp[1][cntOnes][maxZeros][curZeros];
                        dp[1][cntOnes][maxZeros][curZeros] = 0;
                    }
                }
            }
        }

        return result;
    }

    double stupidSolution(int n) {
        int res = n;
        for (int st = 1; st < 1 << n; st++) {
            int max = Integer.bitCount(st) + 1;
            for (int l = 0; l < n; l++) {
                int r = l;
                while (((1 << (r % n)) & st) == 0) {
                    r++;
                }
                max = Math.max(max, r - l);
                l = Math.max(l, r - 1);
            }
            res += max;
        }
        return (res + 0.) / (1 << n);
    }

    void solve() {
        int testNumber = in.nextInt();
        for (int test = 0; test < testNumber; test++) {
            int n = in.nextInt();
            out.println(getAnswer(n));
        }
    }

    void run() {
        try {
            in = new FastScanner(new File("wheel.in"));
            out = new PrintWriter(new File("wheel.out"));

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
        new wheel_bm().runIO();
    }
}