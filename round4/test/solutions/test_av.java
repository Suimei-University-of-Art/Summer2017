import java.io.*;
import java.util.*;

public class test_av {

	FastScanner in;
	PrintWriter out;
	final String fileName = "test";
	
	int n;

	char[] repeat(int n, char c) {
		char[] res = new char[this.n];
		Arrays.fill(res, '0');
		for (int i = 0; i < n; i++) {
			res[i] = c;
		}
		return res;
	}
	
	List<char[]> allCombs(int n, int k) {
		if (k == 0 || k == n) {
			List<char[]> ans = new ArrayList<char[]>();
			if (k == 0) {
				ans.add(repeat(n, '0'));
			} else {
				ans.add(repeat(n, '1'));
			}
			return ans;
		}
		List<char[]> ans1 = allCombs(n - 1, k);
		List<char[]> ans2 = allCombs(n - 1, k - 1);
		appendAll(ans1, n, '0');
		appendAll(ans2, n, '1');
		Collections.reverse(ans2);
		ans1.addAll(ans2);
		return ans1;
	}
	
	private void appendAll(List<char[]> ans, int pos, char c) {
		for (int i = 0; i < ans.size(); i++) {
			ans.get(i)[pos - 1] = c; 
		}
	}

	void solve() {
		this.n = in.nextInt();
		for (int count = n; count >= 0; count--) {
			List<char[]> ans = allCombs(n, count);
			for (char[] s : ans) {
				out.println(new String(s));
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
		new test_av().run();
	}
}
