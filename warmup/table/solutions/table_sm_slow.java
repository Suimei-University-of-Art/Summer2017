import java.io.*;
import java.util.Locale;

public class table_sm_slow {

    void solve() {
        int n = in.nextInt();
        assert 1 <= n && n <= 3000;
        int m = in.nextInt();
        assert 1 <= m && m <= 3000;
        in.nextLine();
        char[][] a = new char[n][];
        for (int i = 0; i < n; i++) {
            String s = in.next();
            assert s.length() == m;
            a[i] = s.toCharArray();
            for (int j = 0; j < a[i].length; j++) {
                assert a[i][j] == '0' || a[i][j] == '1';
            }
            in.nextLine();
        }
        long ans = 0;
        for (int b = 1; b < n; b++) {
            for (int c = b + 1; c < n; c++) {
                for (int d = 1; d < m; d++) {
                    for (int e = d + 1; e < m; e++) {
                        int sum = 0;
                        for (int i = 1; i <= n; i++) {
                            for (int j = 1; j <= m; j++) {
                                if (!((i <= b || i >= c + 1) ^ (j <= d || j >= e + 1))) {
                                    sum += a[i - 1][j - 1] - '0';
                                }
                            }
                        }
                        if (sum % 2 == 0) {
                            ans++;
                        }
                    }
                }
            }
        }
        out.println(ans);
    }

    Scanner in;
    PrintWriter out;

    void run() throws IOException {
        in = new Scanner(System.in);
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new table_sm_slow().run();
    }


//----------------- just for validation ------------------

    /**
     * Strict scanner to veryfy 100% correspondence between input files and input file format specification.
     * It is a drop-in replacement for {@link java.util.Scanner} that could be added to a soulution source
     * (cut-and-paste) without breaking its ability to work with {@link java.util.Scanner}.
     */
    private static class Scanner {
        private final BufferedReader in;
        private String line = "";
        private int pos;
        private int lineNo;
        private boolean localeset;

        public Scanner(File source) throws FileNotFoundException {
            in = new BufferedReader(new FileReader(source));
            nextLine();
        }

        public Scanner(InputStream inputStream) throws FileNotFoundException {
            in = new BufferedReader(new InputStreamReader(inputStream));
            nextLine();
        }

        public void close() {
            assert line == null : "Extra data at the end of file";
            try {
                in.close();
            } catch (IOException e) {
                throw new AssertionError("Failed to close with " + e);
            }
        }

        public void nextLine() {
            assert line != null : "EOF";
            assert pos == line.length() : "Extra characters on line " + lineNo;
            try {
                line = in.readLine();
            } catch (IOException e) {
                throw new AssertionError("Failed to read line with " + e);
            }
            pos = 0;
            lineNo++;
        }

        public String next() {
            assert line != null : "EOF";
            assert line.length() > 0 : "Empty line " + lineNo;
            if (pos == 0)
                assert line.charAt(0) > ' ' : "Line " + lineNo + " starts with whitespace";
            else {
                assert pos < line.length() : "Line " + lineNo + " is over";
                assert line.charAt(pos) == ' ' : "Wrong whitespace on line " + lineNo;
                pos++;
                assert pos < line.length() : "Line " + lineNo + " is over";
                assert line.charAt(0) > ' ' : "Line " + lineNo + " has double whitespace";
            }
            StringBuilder sb = new StringBuilder();
            while (pos < line.length() && line.charAt(pos) > ' ')
                sb.append(line.charAt(pos++));
            return sb.toString();
        }

        public int nextInt() {
            String s = next();
            assert s.length() == 1 || s.charAt(0) != '0' : "Extra leading zero in number " + s + " on line " + lineNo;
            assert s.charAt(0) != '+' : "Extra leading '+' in number " + s + " on line " + lineNo;
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                throw new AssertionError("Malformed number " + s + " on line " + lineNo);
            }
        }

        public long nextLong() {
            String s = next();
            assert s.length() == 1 || s.charAt(0) != '0' : "Extra leading zero in number " + s + " on line " + lineNo;
            assert s.charAt(0) != '+' : "Extra leading '+' in number " + s + " on line " + lineNo;
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                throw new AssertionError("Malformed number " + s + " on line " + lineNo);
            }
        }

        public double nextDouble() {
            assert localeset : "Locale must be set with useLocale(Locale.US)";
            String s = next();
            assert s.length() == 1 || s.startsWith("0.") || s.charAt(0) != '0' : "Extra leading zero in number " + s + " on line " + lineNo;
            assert s.charAt(0) != '+' : "Extra leading '+' in number " + s + " on line " + lineNo;
            // at most two digits
            assert s.indexOf('.') < 0 || s.indexOf('.') >= s.length() - 3;
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                throw new AssertionError("Malformed number " + s + " on line " + lineNo);
            }
        }

        public void useLocale(Locale locale) {
            assert locale == Locale.US;
            localeset = true;
        }
    }

}
