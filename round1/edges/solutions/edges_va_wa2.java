import java.io.*;
import java.util.*;

public class edges_va_wa2 implements Runnable {
	public static void main(String[] args) {
		new Thread(new edges_va_wa2()).start();
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

	public double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}

	public long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	int[] size;
	ArrayList<Integer>[] e;
	int time = 1;
	int[] tin, f;
	int bridges = 0;
	long ans = 0;
	int n;

	public void dfs(int v) {
		size[v] = 1;
		for (int u : e[v]) {
			if (size[u] == 0) {
				dfs(u);
				size[v] += size[u];
			}
		}
	}

	public int find(int v, int p) {
		tin[v] = time++;
		f[v] = tin[v];

		int z = 0;
		for (int u : e[v]) {
			if (u == p)
				continue;

			if (tin[u] != 0) {
				f[v] = Math.min(f[v], tin[u]);
			} else {
				z++;
				z += find(u, v);
				f[v] = Math.min(f[v], f[u]);
				if (f[u] > tin[v]) {
					bridges++;
					ans += (long) size[u] * (n - size[u]) - 1;
				}
			}
		}
		return z;
	}

	public void solve() throws IOException {
		n = nextInt();
		int m = nextInt();

		e = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			e[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < m; i++) {
			int x = nextInt() - 1;
			int y = nextInt() - 1;

			e[x].add(y);
			e[y].add(x);
		}

		size = new int[n];
		tin = new int[n];
		f = new int[n];

		int count = 0;
		for (int i = 0; i < n; i++) {
			if (size[i] == 0) {
				count++;
				dfs(i);
			}
		}

		if (count >= 3) {
			out.println(-1);
			return;
		}

		int[] b = new int[count];
		int[] s = new int[count];
		int c = 0;
		for (int i = 0; i < n; i++) {
			if (tin[i] == 0) {
				find(i, -1);
				s[c] = size[i];
				b[c++] = bridges;
			}
		}

		if (count == 2) {
			ans = (long) (m - b[0] - b[1]) * s[0] * s[1];
		} else {
			ans += (long) (m - b[0]) * ((long) n * (n - 1) / 2 - m);
		}

		out.println(ans);
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			solve();

			out.close();
		} catch (Exception e) {

		}
	}
}
