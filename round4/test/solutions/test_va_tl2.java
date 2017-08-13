import java.io.*;
import java.util.*;

public class test_va_tl2 {
	public static void main(String[] args) {
		new test_va_tl2().run();
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

	ArrayList<char[]>[][] ans;

	public void go(int n, int k) {
		if (ans[n][k] != null)
			return;

		go(n - 1, k);
		go(n - 1, k - 1);
		ans[n][k] = new ArrayList<>();
		char[] put;
		for (char[] s : ans[n - 1][k]) {
			put = Arrays.copyOf(s, n);
			put[n - 1] = '0';
			ans[n][k].add(put);
		}

		for (int i = ans[n - 1][k - 1].size() - 1; i >= 0; i--) {
			put = Arrays.copyOf(ans[n - 1][k - 1].get(i), n);
			put[n - 1] = '1';
			ans[n][k].add(put);
		}
	}

	public void solve() throws IOException {
		int n = nextInt();

		ans = new ArrayList[n + 1][n + 1];

		for (int i = 0; i < n + 1; i++) {
			char[] z0 = new char[i];
			char[] z1 = new char[i];
			Arrays.fill(z0, '0');
			Arrays.fill(z1, '1');

			ans[i][0] = new ArrayList<>();
			ans[i][0].add(z0);
			ans[i][i] = new ArrayList<>();
			ans[i][i].add(z1);
		}

		for (int i = n; i >= 0; i--) {
			go(n, i);
			for (char[] s : ans[n][i])
				out.println(new String(s));
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
