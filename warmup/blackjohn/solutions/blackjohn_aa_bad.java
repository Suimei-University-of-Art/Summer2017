import java.io.*;
import java.util.*;
import java.math.*;

public class blackjohn_aa_bad {
	public static void main(String[] args) {
		new blackjohn_aa_bad().run();
	}

	BufferedReader br;
	StringTokenizer st;
	PrintWriter out;
	boolean eof = false;
	Random rand = new Random(12498);

	private void run() {
		Locale.setDefault(Locale.US);
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			// br = new BufferedReader(new FileReader("blackjohn.in"));
			// out = new PrintWriter("blackjohn.out");
			solve();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(566);
		}
	}

	String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (Exception e) {
				eof = true;
				return "0";
			}
		}
		return st.nextToken();
	}

	int nextInt() {
		return Integer.parseInt(nextToken());
	}

	void myAssert(boolean u, String message) {
		if (!u) {
			throw new Error("Assertion failed!!! " + message);
		}
	}

	int MAXN = 100;
	int MAXQ = 21;
	int shift = 0;

	int[] special = new int[] { 11, 13, 17, 19, 0 };
	Fraction[] f;

	private void solve() throws IOException {
		int n = nextInt();
		myAssert(1 <= n && n <= MAXN, "n not in bounds: n = " + n);
		f = new Fraction[n];
		for (int i = 0; i < f.length; i++) {
			f[i] = new Fraction(nextInt(), nextInt());
			myAssert(Math.abs(f[i].p) <= f[i].q, "wrong fraction: " + f[i].p
					+ "/" + f[i].q);
			myAssert(1 <= f[i].q && f[i].q <= MAXQ, "wrong fraction: " + f[i].p
					+ "/" + f[i].q);
		}
		long time = System.currentTimeMillis();
		int lcm = 1;
		for (int i = 0; i < f.length; i++) {
			if (special(f[i].q) == special.length - 1) {
				lcm = lcm / gcd(lcm, f[i].q) * f[i].q;
			}
		}
		special[special.length - 1] = lcm;
		shift = n * lcm;
		boolean[][][] a = new boolean[special.length][f.length + 1][2 * shift + 1];
		short[][][] pa = new short[special.length][f.length + 1][2 * shift + 1];
		for (int i = 0; i < pa.length; i++) {
			for (int j = 0; j < pa[i].length; j++) {
				Arrays.fill(pa[i][j], (short) -1);
			}
		}
		for (int i = 0; i < a.length; i++) {
			a[i][0][shift] = true;
		}
		for (int i = 0; i < f.length; i++) {
			for (int k = 0; k < a.length; k++) {
				for (int j = 0; j < a[k][i].length; j++) {
					a[k][i + 1][j] = a[k][i][j];
				}
			}
			int s = special(f[i].q);
			int x = special[s] / f[i].q * f[i].p;
			if (x > 0) {
				for (int j = a[s][i].length - 1; j >= x; j--) {
					if (a[s][i][j - x]) {
						a[s][i + 1][j] = true;
						pa[s][i + 1][j] = (short) i;
					}
				}
			} else {
				for (int j = 0; j - x < a[s][i].length; j++) {
					if (a[s][i][j - x]) {
						a[s][i + 1][j] = true;
						pa[s][i + 1][j] = (short) i;
					}
				}
			}
		}
		boolean[][] c = new boolean[special.length + 1][2 * shift + 1];
		int[][] pc = new int[special.length + 1][2 * shift + 1];
		c[0][shift] = true;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < c[i].length; j++) {
				c[i + 1][j] = c[i][j];
			}
			for (int j = 1; shift + j * special[i] < a[i][n].length; j++) {
				if (a[i][n][shift + j * special[i]]) {
					for (int k = c[i].length - 1; k >= j; k--) {
						if (c[i][k - j]) {
							c[i + 1][k] = true;
							pc[i + 1][k] = j;
						}
					}
				}
			}
			for (int j = -1; shift + j * special[i] >= 0; j--) {
				if (a[i][n][shift + j * special[i]]) {
					for (int k = 0; k - j < c[i].length; k++) {
						if (c[i][k - j]) {
							c[i + 1][k] = true;
							pc[i + 1][k] = j;
						}
					}
				}
			}
		}
		System.err.println((System.currentTimeMillis() - time) / 1000.0);
		int m = special.length;
		out.println(c[m][shift + 1] ? "YES" : "NO");
		if (c[m][shift + 1]) {
			int y = shift + 1;
			ArrayList<Integer> ans = new ArrayList<Integer>();
			for (int i = a.length; i > 0; i--) {
				// System.out.println(pa.length);
				// System.out.println(special.length);
				// System.out.println(pc.length);
				addAns(ans, pa[i - 1], special[i - 1], pc[i][y]);
				y -= pc[i][y];
			}
			Collections.sort(ans);
			out.println(ans.size());
			for (int i : ans) {
				out.print(i + " ");
			}
		}
	}

	private void addAns(ArrayList<Integer> ans, short[][] p, int m, int x) {
		x = shift + x * m;
		for (int i = p.length - 1; i > 0; i--) {
			if (p[i][x] >= 0) {
				ans.add(p[i][x] + 1);
				x -= f[p[i][x]].p * m / f[p[i][x]].q;
			}
		}
	}

	int gcd(int a, int b) {
		while (b != 0) {
			int t = a % b;
			a = b;
			b = t;
		}
		return a;
	}

	int special(int x) {
		int res = 0;
		for (int i : special) {
			if (i == x) {
				return res;
			}
			res++;
		}
		return special.length - 1;
	}

	class Fraction {
		public Fraction(int p2, int q2) {
			p = p2;
			q = q2;
		}

		int p, q;
	}

}