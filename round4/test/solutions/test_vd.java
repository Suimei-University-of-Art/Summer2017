import java.io.*;
import java.util.*;

public class test_vd {

	void print(int n, int k, String suf, boolean reverse) {
		if (k > n) {
			return;
		}
		if (k == 0) {
			for (int i = 0; i < n; i++) {
				out.print("0");
			}
			out.println(suf);
			return;
		}
		if (k == n) {
			for (int i = 0; i < n; i++) {
				out.print("1");
			}
			out.println(suf);
			return;
		}
		if (!reverse) {
			print(n - 1, k, "0" + suf, false);
			print(n - 1, k - 1, "1" + suf, true);
		} else {
			print(n - 1, k - 1, "1" + suf, false);
			print(n - 1, k, "0" + suf, true);
		}
	}

	void solve() throws IOException {
		int n = nextInt();
		for (int i = n; i >= 0; i--) {
			print(n, i, "", false);
		}
	}

	public void run() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		solve();
		br.close();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		new test_vd().run();
	}

	BufferedReader br;
	StringTokenizer str;
	PrintWriter out;

	String next() throws IOException {
		while (str == null || !str.hasMoreTokens()) {
			String s = br.readLine();
			if (s != null) {
				str = new StringTokenizer(s);
			} else {
				return null;
			}
		}
		return str.nextToken();
	}

	long nextLong() throws IOException {
		return Long.parseLong(next());
	}

	int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}

}
