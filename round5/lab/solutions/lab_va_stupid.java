import java.io.*;
import java.util.*;

public class lab_va_stupid {

	final int MAX = 100_000;

	public void solve() {
		int n = in.nextInt(), m = in.nextInt(), C = in.nextInt(), H = in.nextInt(), k = in
				.nextInt();

		boolean[] cold = new boolean[MAX + 1];

		for (int i = 0; i < n; i++) {
			cold[in.nextInt()] = true;
		}

		boolean[] hot = new boolean[MAX + 1];
		for (int i = 0; i < m; i++) {
			hot[in.nextInt()] = true;
		}


		for (int t = 0; t < k; t++) {
			int p = in.nextInt(), q = in.nextInt();
			
			if (1l * C * q < p && p < 1l * H * q) {     //
                long A = q * 1L * H - p;
                long B = p - q * 1L * C;

				long gcd = gcd(A, B);
				A /= gcd;
				B /= gcd;

				boolean ans = false;
				for (long i = A, j = B; i <= MAX && j <= MAX; i += A, j += B) {
					if (cold[(int)i] && hot[(int)j]) {
						ans = true;
						break;
					}
				}
				if (ans) {
					out.println("YES");
				} else {
					out.println("NO");
				}
			} else {
				out.println("NO");
			}
		}
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
		new lab_va_stupid().run();
	}
}
