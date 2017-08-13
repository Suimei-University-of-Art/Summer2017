import java.io.*;
import java.util.*;

public class equality_av {

	FastScanner in;
	PrintWriter out;

	void solve() {
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			int[] a = new int[n];
			for (int j = 0; j < n; j++) {
				a[j] = in.nextInt() - 1;
			}

			List<Integer> path = new ArrayList<Integer>();
			boolean[] used = new boolean[n];
			int cur = 0;
			while (!used[cur]) {
				used[cur] = true;
				path.add(cur);
				cur = a[cur];
			}
			
			List<Integer> result = path.subList(path.indexOf(cur), path.size());
			out.println(result.size());
			for (int j : result) {
				out.print((j + 1) + " ");
			}
			out.println();
		}
	}

	void run() {
		in = new FastScanner();
		out = new PrintWriter(System.out);
		solve();
		out.close();
	}

	class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String nextToken() {
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
			return Integer.parseInt(nextToken());
		}

		long nextLong() {
			return Long.parseLong(nextToken());
		}

		double nextDouble() {
			return Double.parseDouble(nextToken());
		}
	}

	public static void main(String[] args) {
		new equality_av().run();
	}
}
