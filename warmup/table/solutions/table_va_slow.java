import java.io.*;
import java.util.*;

public class table_va_slow implements Runnable {
	public static void main(String[] args) {
		new Thread(new table_va_slow()).run();
	}

	BufferedReader br;
	StringTokenizer in;
	PrintWriter out;

	public String nextToken() throws IOException {
		while (in == null || !in.hasMoreTokens()) {
			in = new StringTokenizer(br.readLine());
		}

		return in.nextToken();
	}

	public int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	public long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	public double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}

	public final int MAX_SIZE = 3000;

	int[][] sum;

	public int sum(int x1, int y1, int x2, int y2) {
		int ans = sum[x2][y2];
		if (x1 > 0) {
			ans -= sum[x1 - 1][y2];
		}
		if (y1 > 0) {
			ans -= sum[x2][y1 - 1];
		}
		if (x1 * y1 > 0) {
			ans += sum[x1 - 1][y1 - 1];
		}
		return ans;
	}

	public void solve() throws IOException {
		int n = nextInt();
		int m = nextInt();

		int[][] a = new int[n][m];
		for (int i = 0; i < n; i++) {
			char[] c = nextToken().toCharArray();
			for (int j = 0; j < m; j++) {
				a[i][j] = c[j] - '0';
			}
		}

		sum = new int[n][m];
		sum[0][0] = a[0][0];
		for (int i = 1; i < n; i++) {
			sum[i][0] = sum[i - 1][0] + a[i][0];
		}

		for (int i = 1; i < m; i++) {
			sum[0][i] = sum[0][i - 1] + a[0][i];
		}

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1]
						+ a[i][j];
			}
		}

		long ans = 0;

		for (int x1 = 0; x1 < n - 2; x1++) {
			for (int y1 = 0; y1 < m - 2; y1++) {
				for (int x2 = x1 + 1; x2 < n - 1; x2++) {
					for (int y2 = y1 + 1; y2 < m - 1; y2++) {
						int s = sum(0, 0, x1, y1) + sum(0, y2 + 1, x1, m - 1)
								+ sum(x1 + 1, y1 + 1, x2, y2)
								+ sum(x2 + 1, 0, n - 1, y1)
								+ sum(x2 + 1, y2 + 1, n - 1, m - 1);

						if (s % 2 == 0) {
							ans++;
						}
					}
				}
			}
		}

		out.println(ans);
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			solve();

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
