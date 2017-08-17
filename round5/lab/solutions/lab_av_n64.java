import java.io.*;
import java.util.*;

public class lab_av_n64 {

	final int MAX = 100_000;

	public void solve() {
		int n = in.nextInt(), m = in.nextInt(), C = in.nextInt(), H = in.nextInt(), k = in
				.nextInt();

		BitSet[] as = new BitSet[MAX + 1], bs = new BitSet[MAX + 1];
		for (int i = 1; i <= MAX; i++) {
			as[i] = new BitSet();
			bs[i] = new BitSet();
		}

		for (int i = 0; i < n; i++) {
			int cold = in.nextInt();
			for (int j = 1; j * j <= cold; j++) {
				if (cold % j == 0) {
					as[j].set(cold / j);
					as[cold / j].set(j);
				}
			}
		}

		for (int i = 0; i < m; i++) {
			int hot = in.nextInt();
			for (int j = 1; j * j <= hot; j++) {
				if (hot % j == 0) {
					bs[j].set(hot / j);
					bs[hot / j].set(j);
				}
			}
		}

		for (int i = 0; i < k; i++) {
			int p = in.nextInt(), q = in.nextInt();

			if (1l * C * q < p && p < 1l * H * q) {

				long num = 1l * (H - C) * q;
				long denom = p - 1l * C * q;
				num -= denom;
				long gcd = gcd(num, denom);
				num /= gcd;
				denom /= gcd;

				if (num <= MAX && denom <= MAX && intersects(as[(int) num], bs[(int) denom])) {
					out.println("YES");
				} else {
					out.println("NO");
				}
			} else {
				out.println("NO");
			}
		}
	}

	private boolean intersects(BitSet a, BitSet b) {
		return a.intersects(b);
	}

	long gcd(long a, long b) {
		if (a == 0) {
			return b;
		}
		return gcd(b % a, a);
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
		new lab_av_n64().run();
	}
}
