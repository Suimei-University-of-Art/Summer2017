import java.io.*;
import java.util.*;

public class partition_bm {
	FastScanner in;
	PrintWriter out;

	int gcd(int x, int y) {
		return x == 0 ? y : gcd(y % x, x);
	}

	void solve() {
		int tc = in.nextInt();
		for (int t = 0; t < tc; t++) {
			int n = in.nextInt();
			int[] a = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = in.nextInt();
			}
			Arrays.sort(a);
			int best = 0;
			for (int z = 1; z * z <= a[0]; z++) {
				if (a[0] % z != 0) {
					continue;
				}
				for (int it = 0; it < 2; it++) {
					int check = it == 0 ? z : (a[0] / z);
					int f2 = 0;
					for (int i = 0; i < n; i++) {
						if (a[i] % check != 0) {
							f2 = gcd(f2, a[i]);
						}
					}
					int ans = f2 == 0 ? check : Math.min(f2, check);
					best = Math.max(best, ans);
				}
			}
			out.println(best);
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

		double nextDouble() {
			return Double.parseDouble(next());
		}
	}

	public static void main(String[] args) {
		new partition_bm().runIO();
	}
}