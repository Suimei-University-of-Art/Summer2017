import java.io.*;
import java.util.*;
import java.math.*;

public class blackjohn_aa {
	public static void main(String[] args) {
		new blackjohn_aa().run();
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

	int[] special = new int[] { 11, 13, 17, 19, 0 };
	int[] shift = new int[] { 0, 0, 0, 0, 0 };
	int[] p;
	int[] q;

	private void solve() throws IOException {
		int n = nextInt();
		myAssert(1 <= n && n <= MAXN, "n not in bounds: n = " + n);
		p = new int[n];
		q = new int[n];
		for (int i = 0; i < p.length; i++) {
			p[i] = nextInt();
			q[i] = nextInt();
			myAssert(Math.abs(p[i]) <= q[i], "wrong fraction: " + p[i] + "/"
					+ q[i]);
			myAssert(1 <= q[i] && q[i] <= MAXQ, "wrong fraction: " + p[i] + "/"
					+ q[i]);
		}
		long time = System.currentTimeMillis();
		int lcm = 1;
		for (int i = 0; i < p.length; i++) {
			if (special(q[i]) == special.length - 1) {
				lcm = lcm / gcd(lcm, q[i]) * q[i];
			}
		}
		special[special.length - 1] = lcm;
		for (int i = 0; i < shift.length; i++) {
			shift[i] = special[i] * n;
		}
		byte[][][] a = new byte[special.length][p.length + 1][];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = new byte[2 * shift[i] + 1];
				Arrays.fill(a[i][j], (byte) -1);
			}
			a[i][0][shift[i]] = 0;
		}
		for (int i = 0; i < p.length; i++) {
			for (int k = 0; k < a.length; k++) {
				for (int j = 0; j < a[k][i].length; j++) {
					if (a[k][i][j] >= 0) {
						a[k][i + 1][j] = 0;
					}
				}
			}
			int s = special(q[i]);
			int x = special[s] / q[i] * p[i];
			if (x > 0) {
				for (int j = a[s][i].length - 1; j >= x; j--) {
					if (a[s][i][j - x] >= 0) {
						a[s][i + 1][j] = 1;
					}
				}
			} else {
				for (int j = 0; j - x < a[s][i].length; j++) {
					if (a[s][i][j - x] >= 0) {
						a[s][i + 1][j] = 1;
					}
				}
			}
		}
		byte[][] c = new byte[special.length + 1][2 * n + 1];
		for (int i = 0; i < c.length; i++) {
			Arrays.fill(c[i], (byte) (-(n + 1)));
		}
		c[0][n] = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < c[i].length; j++) {
				if (c[i][j] >= -n) {
					c[i + 1][j] = 0;
				}
			}
			for (byte j = 1; shift[i] + j * special[i] < a[i][n].length; j++) {
				if (a[i][n][shift[i] + j * special[i]] >= 0) {
					for (int k = c[i].length - 1; k >= j; k--) {
						if (c[i][k - j] >= -n) {
							c[i + 1][k] = j;
						}
					}
				}
			}
			for (byte j = -1; shift[i] + j * special[i] >= 0; j--) {
				if (a[i][n][shift[i] + j * special[i]] >= 0) {
					for (int k = 0; k - j < c[i].length; k++) {
						if (c[i][k - j] >= -n) {
							c[i + 1][k] = j;
						}
					}
				}
			}
		}
		System.err.println((System.currentTimeMillis() - time) / 1000.0);
		int m = special.length;
		out.println(c[m][n + 1] >= -n ? "YES" : "NO");
		if (c[m][n + 1] >= -n) {
			int y = n + 1;
			ArrayList<Integer> ans = new ArrayList<Integer>();
			for (int i = a.length; i > 0; i--) {
				// System.out.println(pa.length);
				// System.out.println(special.length);
				// System.out.println(pc.length);
				addAns(ans, a[i - 1], special[i - 1], c[i][y], shift[i - 1]);
				y -= c[i][y];
			}
			Collections.sort(ans);
			out.println(ans.size());
			for (int i : ans) {
				out.print(i + " ");
			}
		}
	}

	private void addAns(ArrayList<Integer> ans, byte[][] a, int m, int x,
			int shift) {
		x = shift + x * m;
		for (int i = a.length - 1; i > 0; i--) {
			if (a[i][x] > 0) {
				ans.add(i);
				x -= p[i - 1] * m / q[i - 1];
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