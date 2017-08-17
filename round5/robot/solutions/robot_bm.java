import java.io.*;
import java.util.*;

public class robot_bm {

	final static int MOD = 998244353;
	final static int MAX = (int) 1e6 + 10;

	int[] factInv;
	int[] fact;
	final static int[] dx = new int[] { -1, 0, 0, 1 };
	final static int[] dy = new int[] { 0, -1, 1, 0 };

	int choose(int n, int k) {
		int res = (int) ((fact[n] * 1L * factInv[k]) % MOD);
		res = (int) ((res * 1L * factInv[n - k]) % MOD);
		return res;
	}

	static int pow(int x, int e) {
		int res = 1, mul = x;
		while (e > 0) {
			if (e % 2 == 1) {
				res = (int) ((1l * res * mul) % MOD);
			}
			mul = (int) ((1l * mul * mul) % MOD);
			e /= 2;
		}
		return res;
	}

	static int inv(int x) {
		return pow(x, MOD - 2);
	}

	void prepare() {
		fact = new int[MAX];
		factInv = new int[MAX];
		fact[0] = factInv[0] = 1;
		for (int i = 1; i < MAX; i++) {
			fact[i] = (int) ((fact[i - 1] * 1L * i) % MOD);
			factInv[i] = inv(fact[i]);
		}
	}

	static class FFT {
		static int[][] tail;
		static int[][] omegas;
		final static int LOG = 20;

		final static int G = 3;
		static int[] divs = new int[] { 2, 7, 17 };

		static int findGen() {
			for (int g = 2;; g++) {
				boolean ok = true;
				for (int t : divs) {
					int p = pow(g, (MOD - 1) / t);
					if (p == 1) {
						ok = false;
					}
				}
				if (ok) {
					return g;
				}
			}
		}

		static void doFFT(int[] a, int size, boolean inverse) {
			int l = Integer.numberOfTrailingZeros(size);
			int[] tail = FFT.tail[l], omegas = FFT.omegas[l];
			for (int i = 0; i < size; i++) {
				if (i < tail[i]) {
					int tmp = a[i];
					a[i] = a[tail[i]];
					a[tail[i]] = tmp;
				}
			}
			for (int len = 2; len <= size; len *= 2) {
				int add = size / len;
				for (int i = 0; i < size; i += len) {
					int pos = 0;
					for (int j = 0, k = len / 2; k < len; j++, k++) {
						int u = a[i + j], v = (int) ((1l * a[i + k] * omegas[pos]) % MOD);
						a[i + j] = (u + v);
						a[i + k] = u - v + MOD;
						if (a[i + j] >= MOD) {
							a[i + j] -= MOD;
						}
						if (a[i + k] >= MOD) {
							a[i + k] -= MOD;
						}

						if (inverse) {
							pos -= add;
							if (pos < 0) {
								pos += size;
							}
						} else {
							pos += add;
						}
					}
				}
			}
			if (inverse) {
				int inv = pow(size, MOD - 2);
				for (int i = 0; i < size; i++) {
					a[i] = (int) ((1l * inv * a[i]) % MOD);
				}
			}
		}

		static void prepare() {
			tail = new int[LOG][];
			omegas = new int[LOG][];
			for (int l = 0; l < LOG; l++) {
				tail[l] = new int[1 << l];
				omegas[l] = new int[1 << l];
				int w = pow(G, (MOD - 1) / (1 << l));

				omegas[l][0] = 1;
				for (int i = 1; i < 1 << l; i++) {
					tail[l][i] = (tail[l][i >> 1] >> 1) | ((i & 1) << (l - 1));
					omegas[l][i] = (int) ((1l * omegas[l][i - 1] * w) % MOD);
				}
			}
		}

		static void mul(int[] a, int[] b, int len) {
			doFFT(a, 2 * len, false);
			doFFT(b, 2 * len, false);
			for (int i = 0; i < 2 * len; i++) {
				a[i] = (int) ((1l * a[i] * b[i]) % MOD);
			}
			doFFT(a, 2 * len, true);
		}
	}

	int getNumberOfWays(int x1, int x2, int time) {
		int open = Math.abs(x2 - x1);
		int more = time - open;
		if (more % 2 != 0 || more < 0)
			return 0;
		more /= 2;
		return choose(time, more);
	}

	int[] solveOneDTask(int x1, int x2, int time) {
		int[] result = new int[time];
		for (int t = 0; t < time; t++) {
			result[t] = getNumberOfWays(x1, x2, t);
			result[t] -= getNumberOfWays(-x1, x2, t);
			if (result[t] < 0)
				result[t] += MOD;
		}
		return result;
	}

	int[] solveTwoDTask(int x1, int y1, int x2, int y2, int time) {
		int[] onX = solveOneDTask(x1, x2, time);
		int[] onY = solveOneDTask(y1, y2, time);
		return mulWithC(onX, onY);
	}

	int[] mulWithC(int[] a, int[] b) {

		Arrays.fill(tmp1, 0);
		Arrays.fill(tmp2, 0);

		for (int i = 0; i < a.length; i++) {
			tmp1[i] = (int) ((a[i] * 1L * factInv[i]) % MOD);
		}
		for (int i = 0; i < b.length; i++) {
			tmp2[i] = (int) ((b[i] * 1L * factInv[i]) % MOD);
		}
		FFT.mul(tmp1, tmp2, a.length);
		for (int i = 0; i < a.length; i++) {
			tmp1[i] = (int) ((tmp1[i] * 1L * fact[i]) % MOD);
		}
		return Arrays.copyOf(tmp1, a.length);
	}

	int[] cycle;

	int[] tmp1;
	int[] tmp2;

	int[] getX2Y2;

	public void solve() {
		prepare();
		FFT.prepare();

		int x1 = in.nextInt();
		int y1 = in.nextInt();
		int x2 = in.nextInt();
		int y2 = in.nextInt();
		int t2 = in.nextInt();
		int t = 1;
		while (t <= t2) {
			t = t + t;
		}
		this.tmp1 = new int[2 * t];
		this.tmp2 = new int[2 * t];

		getX2Y2 = solveTwoDTask(x1, y1, x2, y2, t);
		cycle = solveTwoDTask(x2, y2, x2, y2, t);

		int[] q = inverse(cycle, t);

		Arrays.fill(tmp1, 0);
		Arrays.fill(tmp2, 0);

		System.arraycopy(q, 0, tmp1, 0, t);
		System.arraycopy(getX2Y2, 0, tmp2, 0, t);

		FFT.mul(tmp1, tmp2, t);

		out.println(tmp1[t2]);
	}

	private int[] inverse(int[] p, int t) {
		if (t == 1) {
			int[] ret = new int[p.length];
			ret[0] = inv(p[0]);
			return ret;
		}

		int[] inverse = inverse(p, t / 2);

		Arrays.fill(tmp1, 0, 2 * t, 0);
		Arrays.fill(tmp2, 0, 2 * t, 0);
		System.arraycopy(inverse, 0, tmp1, 0, t);
		System.arraycopy(p, 0, tmp2, 0, t);

		FFT.doFFT(tmp1, 2 * t, false);
		FFT.doFFT(tmp2, 2 * t, false);

		for (int i = 0; i < 2 * t; i++) {
			tmp1[i] = (int) ((1l * tmp1[i] * tmp1[i]) % MOD);
			tmp1[i] = (int) ((1l * tmp1[i] * tmp2[i]) % MOD);
		}

		FFT.doFFT(tmp1, 2 * t, true);

		for (int i = 0; i < t; i++) {
			inverse[i] = (2 * inverse[i] - tmp1[i]);
			if (inverse[i] >= MOD) {
				inverse[i] -= MOD;
			}
			if (inverse[i] < 0) {
				inverse[i] += MOD;
			}
		}
		return inverse;
	}

	FastScanner in;
	PrintWriter out;

	public void run() {
		try {
			in = new FastScanner();
			out = new PrintWriter(System.out);
			solve();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public FastScanner(String name) {
			try {
				br = new BufferedReader(new FileReader(name));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		public String nextToken() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextToken());
		}

		public long nextLong() {
			return Long.parseLong(nextToken());
		}

		public double nextDouble() {
			return Double.parseDouble(nextToken());
		}
	}

	public static void main(String[] args) {
		new robot_bm().run();
	}
}
