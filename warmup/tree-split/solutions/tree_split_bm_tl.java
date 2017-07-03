import java.io.*;
import java.util.*;

public class tree_split_bm_tl {
	FastScanner in;
	PrintWriter out;

	final int mod = (int) 1e9 + 7;

	ArrayList<Integer>[] g;

	int[] a;
	int[] comp;
	int comps;

	void go(int v, int compId, int nextCompId, ArrayList<Integer> a) {
		a.add(v);
		comp[v] = nextCompId;
		for (int to : g[v]) {
			if (comp[to] == compId) {
				go(to, compId, nextCompId, a);
			}
		}
	}

	void solve() {
		int n = in.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		comp = new int[n];
		g = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<>();
		}
		for (int i = 0; i + 1 < n; i++) {
			int fr = in.nextInt() - 1;
			int to = in.nextInt() - 1;
			g[fr].add(to);
			g[to].add(fr);
		}
		comps = 1;
		long res = 0;
		for (int i = 0; i + 1 < n; i++) {
			int fr = (in.nextInt() + (int) res) - 1;
			int to = (in.nextInt() + (int) res) - 1;
			if (i == 0) {
				for (int v = 0; v < n; v++) {
					res = res + a[v];
				}
				res %= mod;
			}
			ArrayList<Integer> first = new ArrayList<>();
			ArrayList<Integer> second = new ArrayList<>();
			int cur = comp[to];
			comp[to] = comps++;
			go(fr, comp[fr], comps++, first);
			go(to, cur, comp[to], second);
			for (int v : first) {
				res -= a[v];
				a[v] = (int) (a[v] * 1L * (1 + second.size()) % mod);
				res += a[v];
			}
			for (int v : second) {
				res -= a[v];
				a[v] = (int) (a[v] * 1L * (1 + first.size()) % mod);
				res += a[v];
			}
			res %= mod;
			if (res < 0) {
				res += mod;
			}
			out.println(res);
		}
	}

	void run() {
		try {
			in = new FastScanner(new File("object.in"));
			out = new PrintWriter(new File("object.out"));

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
		new tree_split_bm_tl().runIO();
	}
}