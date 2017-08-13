import java.io.*;
import java.util.*;

public class test_va_tl {
	public static void main(String[] args) {
		new test_va_tl().run();
	}

	BufferedReader br;
	StringTokenizer in;
	PrintWriter out;

	public String nextToken() throws IOException {
		while (in == null || !in.hasMoreTokens())
			in = new StringTokenizer(br.readLine());
		return in.nextToken();
	}

	public int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	ArrayList<String>[][] ans;

	public void go(int n, int k) {
		if (ans[n][k] != null)
			return;

		go(n - 1, k);
		go(n - 1, k - 1);
		ans[n][k] = new ArrayList<>();
		for (String s : ans[n - 1][k]) {
			ans[n][k].add('0' + s);
		}

		for (int i = ans[n - 1][k - 1].size() - 1; i >= 0; i--) {
			ans[n][k].add('1' + ans[n - 1][k - 1].get(i));
		}
	}

	public void solve() throws IOException {
		int n = nextInt();

		ans = new ArrayList[n + 1][n + 1];

		for (int i = 0; i < n + 1; i++) {
			String z0 = "";
			String z1 = "";
			for (int j = 0; j < i; j++) {
				z0 += '0';
				z1 += '1';
			}

			ans[i][0] = new ArrayList<>();
			ans[i][0].add(z0);
			ans[i][i] = new ArrayList<>();
			ans[i][i].add(z1);
		}

		for (int i = n; i >= 0; i--) {
			go(n, i);
			for (String s : ans[n][i])
				out.println(s);
		}
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			solve();

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
