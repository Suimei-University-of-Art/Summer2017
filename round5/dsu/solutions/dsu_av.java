import java.io.*;
import java.util.*;

public class dsu_av {

	List<Integer>[] graph;
	boolean[] vis;

	List<Query> ans;
	boolean fail = false;

	int dfs(int u) {
		vis[u] = true;
		Subtree[] q = new Subtree[graph[u].size()];

		for (int i = 0; i < graph[u].size(); i++) {
			int v = graph[u].get(i);
			q[i] = new Subtree(dfs(v), v);
		}

		Arrays.sort(q);

		int height = 0;
		for (int i = 0; i < q.length; i++) {
			if (q[i].height > height) {
				fail = true;
			}

			ans.add(new Query(u, q[i].root));
			height = Math.max(height, q[i].height + 1);
		}

		return height;
	}

	public void solve() {
		int n = in.nextInt();
		int[] parent = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = in.nextInt() - 1;
		}
		graph = new List[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int i = 0; i < n; i++) {
			if (parent[i] != i) {
				graph[parent[i]].add(i);
			}
		}
		vis = new boolean[n];
		ans = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			if (parent[i] == i) {
				dfs(i);
			}
		}

		for (int i = 0; i < n; i++) {
			if (!vis[i]) {
				fail = true;
			}
		}

		if (fail) {
			out.println(-1);
		} else {
			out.println(ans.size());
			for (Query q : ans) {
				out.println((q.a + 1) + " " + (q.b + 1));
			}
		}

	}

	class Query {
		final int a, b;

		public Query(int a, int b) {
			this.a = a;
			this.b = b;
		}

	}

	class Subtree implements Comparable<Subtree> {
		final int height, root;

		public Subtree(int height, int root) {
			this.height = height;
			this.root = root;
		}

		@Override
		public int compareTo(Subtree o) {
			return Integer.compare(height, o.height);
		}
	}

	FastScanner in;
	PrintWriter out;

	public void run() {
		try {
			in = new FastScanner();
			out = new PrintWriter(System.out);
			solve();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public FastScanner(String name) {
			try {
				br = new BufferedReader(new FileReader(name));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		public String nextToken() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextToken());
		}

		public long nextLong() {
			return Long.parseLong(nextToken());
		}

		public double nextDouble() {
			return Double.parseDouble(nextToken());
		}
	}

	public static void main(String[] args) {
		new dsu_av().run();
	}
}
