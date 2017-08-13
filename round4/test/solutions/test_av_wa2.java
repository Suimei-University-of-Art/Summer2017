import java.io.*;
import java.util.*;

public class test_av_wa2 {

	FastScanner in;
	PrintWriter out;
	final String fileName = "test";
	
	int n;

	void solve() {
		this.n = in.nextInt();
		char[] c = new char[n];
		for (int count = n; count >= 0; count--) {
			for (int i = 0; i < 1 << n; i++) {
				if (Integer.bitCount(i) == count) {
					int mask = i;
					for (int j = 0; j < n; j++) {
						c[j] = (char) ('0' + (mask & 1));
						mask >>>= 1;
					}
					out.println(new String(c));
				}
			}
		}
	}

	void run() {
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

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return st.nextToken();
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
		new test_av_wa2().run();
	}
}
