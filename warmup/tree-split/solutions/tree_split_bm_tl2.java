import java.io.*;
import java.util.*;

public class tree_split_bm_tl2 {
	FastScanner in;
	PrintWriter out;

	final int mod = (int) 1e9 + 7;

	ArrayList<Integer>[] g;
	int[] comp;
	int[] compSum;
	int[] compSz;
	int[] compMul;
	int[] a;
	int comps;

	class BFS {
		int[] q;
		boolean[] was;
		int qIt, qSz;

		BFS(int v, int[] q, boolean[] was) {
			this.q = q;
			this.was = was;
			was[v] = true;
			q[0] = v;
			qIt = 0;
			qSz = 1;
		}

		int clear(int newCompId) {
			long res = 0;
			for (int i = 0; i < qSz; i++) {
				was[q[i]] = false;
				comp[q[i]] = newCompId;
				res += a[q[i]];
			}
			return (int) (res % mod);
		}

		boolean go() {
			if (qIt == qSz) {
				return false;
			}
			int v = q[qIt];
			for (int u : g[q[qIt]]) {
				if (comp[u] != comp[v] || was[u]) {
					continue;
				}
				was[u] = true;
				q[qSz++] = u;
			}
			qIt++;
			return true;
		}
	}

	void solve() {
		int n = in.nextInt();
		a = new int[n];
		long tot = 0;
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			tot += a[i];
		}
		comp = new int[n];
		compMul = new int[n];
		compMul[0] = 1;
		compSz = new int[n];
		compSz[0] = n;
		comps = 1;
		compSum = new int[n];
		compSum[0] = (int) (tot % mod);
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
		int res = 0;
		int[] q1 = new int[n];
		int[] q2 = new int[n];
		boolean[] was = new boolean[n];
		for (int i = 0; i + 1 < n; i++) {
			int fr = (in.nextInt() + res) - 1;
			int to = (in.nextInt() + res) - 1;
			if (i == 0) {
				res = compSum[0];
			}
			int cId = comp[fr];
			res = (int) ((res - compSum[cId] * 1L * compMul[cId]) % mod);
			if (res < 0) {
				res += mod;
			}
			BFS bfs1 = new BFS(fr, q1, was);
			BFS bfs2 = new BFS(to, q2, was);
			while (bfs1.go() && bfs2.go())
				;
			if (!bfs2.go()) {
				int tmp = fr;
				fr = to;
				to = tmp;
				BFS tmp2 = bfs1;
				bfs1 = bfs2;
				bfs2 = tmp2;
			}
			// first one is smaller
			int sz1 = bfs1.qSz;
			int sz2 = compSz[cId] - sz1;
			compSz[cId] -= sz1;
			compSz[comps] = sz1;
			compMul[comps] = (int) (compMul[cId] * 1L * (sz2 + 1) % mod);
			compMul[cId] = (int) (compMul[cId] * 1L * (sz1 + 1) % mod);
			int sum = bfs1.clear(comps);
			compSum[cId] -= sum;
			if (compSum[cId] < 0) {
				compSum[cId] += mod;
			}
			compSum[comps] = sum;
			bfs2.clear(cId);
			res = (int) ((res + compSum[cId] * 1L * compMul[cId] + compSum[comps]
					* 1L * compMul[comps]) % mod);
			comps++;
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
		new tree_split_bm_tl2().runIO();
	}
}