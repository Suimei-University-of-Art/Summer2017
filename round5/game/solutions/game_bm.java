import java.io.*;
import java.util.*;

public class game_bm {
	FastScanner in;
	PrintWriter out;

	// 0:26
	// 0:35

	ArrayList<Integer>[] g;
	int[] left;
	boolean[] was;

	boolean go(int v) {
		if (was[v])
			return false;
		was[v] = true;
		for (int to : g[v]) {
			if (left[to] == -1 || go(left[to])) {
				left[to] = v;
				return true;
			}
		}
		return false;
	}

	void solve() {
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		BitSet[] a = new BitSet[n + m];
		for (int i = 0; i < n + m; i++) {
			a[i] = new BitSet();
			int cnt = in.nextInt();
			for (int j = 0; j < cnt; j++) {
				a[i].set(in.nextInt() - 1);
			}
		}
		int[][] where = new int[n][k];
		for (int i = 0; i < n; i++) {
			Arrays.fill(where[i], -1);
		}
		BitSet tmp = new BitSet();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				tmp.clear();
				tmp.or(a[n + j]);
				tmp.andNot(a[i]);
				if (tmp.cardinality() == 1) {
					int what = tmp.nextSetBit(0);
					where[i][what] = j;
				}
			}
		boolean[] already = new boolean[k];
		for (int i = 0; i < n; i++)
			for (int z = a[i].nextSetBit(0); z >= 0; z = a[i].nextSetBit(z + 1))
				already[z] = true;
		g = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<>();
			for (int j = 0; j < k; j++) {
				if (where[i][j] != -1 && !already[j]) {
					g[i].add(j);
				}
			}
		}
		left = new int[k];
		was = new boolean[n];
		Arrays.fill(left, -1);
		for (int i = 0; i < n; i++) {
			Arrays.fill(was, false);
			go(i);
		}
		for (int i = 0; i < k; i++)
			if (!already[i] && left[i] == -1) {
				out.println(2);
				return;
			}
		out.println(1);
		int[] right = new int[n];
		Arrays.fill(right, 0);
		for (int i = 0; i < k; i++) {
			if (left[i] != -1) {
				right[left[i]] = where[left[i]][i];
			}
		}
		for (int i = 0; i < n; i++) {
			out.print((right[i] + 1) + " ");
		}
		out.println();
	}

	void run() {
		try {
			in = new FastScanner(new File("game.in"));
			out = new PrintWriter(new File("game.out"));

			solve();

			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	void runIO() {

		in = new FastScanner(System.in);
		out = new PrintWriter(System.out);

		solve();

		out.close();
	}

	class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(File f) {
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		public FastScanner(InputStream f) {
			br = new BufferedReader(new InputStreamReader(f));
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				String s = null;
				try {
					s = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (s == null)
					return null;
				st = new StringTokenizer(s);
			}
			return st.nextToken();
		}

		boolean hasMoreTokens() {
			while (st == null || !st.hasMoreTokens()) {
				String s = null;
				try {
					s = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (s == null)
					return false;
				st = new StringTokenizer(s);
			}
			return true;
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
		new game_bm().runIO();
	}
}