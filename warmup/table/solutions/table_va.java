import java.io.*;
import java.util.*;

public class table_va implements Runnable {
	public static void main(String[] args) {
		new Thread(new table_va()).run();
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

	public void solve() throws IOException {
		int n = nextInt();

               	if (n < 3 || n > MAX_SIZE)
		  throw new AssertionError("Wrong size!");
		int m = nextInt();
               	if (m < 3 || m > MAX_SIZE)
		  throw new AssertionError("Wrong size!");

		int[][] a = new int[n][m];
		int sumAll = 0;
		for (int i = 0; i < n; i++) {
			char[] c = nextToken().toCharArray();
			assert c.length == m;
			for (int j = 0; j < m; j++) {
				a[i][j] = c[j] - '0';
				assert a[i][j] == 0 || a[i][j] == 1;
				sumAll += a[i][j];
				sumAll %= 2;
			}
		}

		int[][] sumUp = new int[n][m];
		sumUp[0][0] = a[0][0];
		for (int i = 1; i < n; i++) {
			sumUp[i][0] = (sumUp[i - 1][0] + a[i][0]) % 2;
		}
		for (int i = 1; i < m; i++) {
			sumUp[0][i] = (sumUp[0][i - 1] + a[0][i]) % 2;
		}

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				sumUp[i][j] = (sumUp[i - 1][j] + sumUp[i][j - 1]
						- sumUp[i - 1][j - 1] + a[i][j] + 2) % 2;
			}
		}

		int[][] sumDown = new int[n][m];
		sumDown[n - 1][m - 1] = a[n - 1][m - 1];
		for (int i = n - 2; i >= 0; i--) {
			sumDown[i][m - 1] = (sumDown[i + 1][m - 1] + a[i][m - 1]) % 2;
		}
		for (int i = m - 2; i >= 0; i--) {
			sumDown[n - 1][i] = (sumDown[n - 1][i + 1] + a[n - 1][i]) % 2;
		}

		for (int i = n - 2; i >= 0; i--) {
			for (int j = m - 2; j >= 0; j--) {
				sumDown[i][j] = (sumDown[i + 1][j] + sumDown[i][j + 1]
						- sumDown[i + 1][j + 1] + a[i][j] + 2) % 2;
			}
		}

		int[][] sumUD = new int[n][m];
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < m - 1; j++) {
				sumUD[i][j] = (sumUp[i][j] + sumDown[i + 1][j + 1]) % 2;
			}
		}

		int[][] countE = new int[n][m];
		countE[0][0] = (sumUD[0][0] == 0 ? 1 : 0);
		for (int i = 1; i < n; i++) {
			countE[i][0] = countE[i - 1][0] + (sumUD[i][0] == 0 ? 1 : 0);
		}
		for (int i = 1; i < m; i++) {
			countE[0][i] = countE[0][i - 1] + (sumUD[0][i] == 0 ? 1 : 0);
		}

		long ans = 0;
		
		for (int i = 1; i < n - 1; i++) {
			for (int j = 1; j < m - 1; j++) {
				countE[i][j] = countE[i - 1][j] + countE[i][j - 1]
						- countE[i - 1][j - 1] + (sumUD[i][j] == 0 ? 1 : 0) ;
				if (sumUD[i][j] == sumAll) {
					ans += countE[i - 1][j - 1];
				} else {
					ans += (i * j - countE[i - 1][j - 1]);
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
