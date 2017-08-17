import java.io.*;
import java.util.*;

public class salary_pk_stupid {

	void solve() throws IOException {
		int n = nextInt();
		int m = nextInt();
		int[][] s = new int[n][2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 2; j++) {
				s[i][j] = nextInt();
			}
		}
		int[][] v = new int[m][2];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < 2; j++) {
				v[i][j] = nextInt() - 1;
			}
		}
		int ansMask =  -1;
		for (int mask = 0; mask < (1 << n); mask++) {
			for (int i = 0; i < n; i++) {
				if ((mask & (1 << i)) > 0) {
					int t = s[i][0];
					s[i][0] = s[i][1];
					s[i][1] = t;
				}
			}
			boolean good = true;
			for (int i = 0; i < m; i++) {
				if (s[v[i][0]][0] <= s[v[i][1]][0]
						&& s[v[i][0]][1] <= s[v[i][1]][1])
					good = false;
			}
			if (good) {
				if (ansMask == -1 || Integer.bitCount(mask) < Integer.bitCount(ansMask))
					ansMask = mask;
			}
			for (int i = 0; i < n; i++) {
				if ((mask & (1 << i)) > 0) {
					int t = s[i][0];
					s[i][0] = s[i][1];
					s[i][1] = t;
				}
			}
		}
		if (ansMask != -1) {
			out.print(Integer.bitCount(ansMask));
			for (int i = 0; i < n; i++) {
				if ((ansMask & (1 << i)) > 0)
					out.print(" " + (i + 1));
			}
			out.println();
			return;
		}

		out.println(-1);
	}

	BufferedReader br;
	StringTokenizer st;
	PrintWriter out;

	void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			// br = new BufferedReader(new FileReader(new File(
			// "salary_pk_stupid.in")));
			// out = new PrintWriter("salary_pk_stupid.out");
			int n = nextInt();
			for (int i = 0; i < n; i++) {
				solve();
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new salary_pk_stupid().run();
	}

	String nextToken() throws IOException {
		while ((st == null) || !st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return st.nextToken();
	}

	int nextInt() throws NumberFormatException, IOException {
		return Integer.parseInt(nextToken());
	}

	double nextDouble() throws NumberFormatException, IOException {
		return Double.parseDouble(nextToken());
	}

	long nextLong() throws NumberFormatException, IOException {
		return Long.parseLong(nextToken());
	}
}
