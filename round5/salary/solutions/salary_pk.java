import java.io.*;
import java.util.*;

public class salary_pk {

	int dsuGet(int a, int[] p) {
		if (p[a] == a)
			return a;
		else {
			p[a] = dsuGet(p[a], p);
			return p[a];
		}
	}

	Random rnd = new Random();

	void dsuUnion(int a, int b, int[] p) {
		a = dsuGet(a, p);
		b = dsuGet(b, p);
		if (rnd.nextBoolean())
			p[a] = b;
		else
			p[b] = a;
	}

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
		int[] p = new int[n];
		for (int i = 0; i < n; i++)
			p[i] = i;
		for (int i = 0; i < m; i++) {
			if (!good(s[v[i][0]][0], s[v[i][0]][1], s[v[i][1]][0],
					s[v[i][1]][1])
					&& !good(s[v[i][0]][0], s[v[i][0]][1], s[v[i][1]][1],
							s[v[i][1]][0])) {
				out.println(-1);
				return;
			}

			if (good(s[v[i][0]][0], s[v[i][0]][1], s[v[i][1]][0], s[v[i][1]][1])
					&& !good(s[v[i][0]][0], s[v[i][0]][1], s[v[i][1]][1],
							s[v[i][1]][0])) {
				dsuUnion(v[i][0], v[i][1], p);
			}
		}
		HashMap<Integer, Integer> tr = new HashMap<Integer, Integer>();
		for (int i = 0; i < n; i++) {
			if (!tr.containsKey(dsuGet(i, p)))
				tr.put(p[i], tr.size());
		}
		int cnt = tr.size();
		int[] size = new int[cnt];
		for (int i = 0; i < n; i++) {
			p[i] = tr.get(p[i]);
			size[p[i]]++;
		}
		ArrayList<Integer>[] e = new ArrayList[cnt];
		for (int i = 0; i < cnt; i++) {
			e[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < m; i++) {
			if (!good(s[v[i][0]][0], s[v[i][0]][1], s[v[i][1]][0],
					s[v[i][1]][1])
					&& good(s[v[i][0]][0], s[v[i][0]][1], s[v[i][1]][1],
							s[v[i][1]][0])) {
				e[p[v[i][0]]].add(p[v[i][1]]);
				e[p[v[i][1]]].add(p[v[i][0]]);
			}
		}
		int[] color = new int[cnt];
		for (int i = 0; i < cnt; i++) {
			if (color[i] == 0) {
				ArrayList<Integer> pos = new ArrayList<Integer>();
				ArrayList<Integer> neg = new ArrayList<Integer>();
				if (!dfs(i, e, color, 1, pos, neg)) {
					out.println(-1);
					return;
				}
				int pSize = 0;
				int nSize = 0;
				for (int j = 0; j < pos.size(); j++) {
					pSize += size[pos.get(j)];
				}
				for (int j = 0; j < neg.size(); j++) {
					nSize += size[neg.get(j)];
				}
				if (pSize < nSize) {
					for (int j = 0; j < pos.size(); j++) {
						color[pos.get(j)] = -color[pos.get(j)];
					}
					for (int j = 0; j < neg.size(); j++) {
						color[neg.get(j)] = -color[neg.get(j)];
					}
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (color[p[i]] == -1)
				ans++;
		}
		out.print(ans);
		for (int i = 0; i < n; i++) {
			if (color[p[i]] == -1)
				out.print(" " + (i + 1));
		}
		out.println();
	}

	private boolean dfs(int v, ArrayList<Integer>[] e, int[] color, int t,
			ArrayList<Integer> pos, ArrayList<Integer> neg) {
		if (color[v] != 0 && color[v] == t)
			return true;
		if (color[v] != 0 && color[v] != t)
			return false;
		color[v] = t;
		if (t == 1)
			pos.add(v);
		else
			neg.add(v);
		for (int i = 0; i < e[v].size(); i++) {
			if (!dfs(e[v].get(i), e, color, -t, pos, neg))
				return false;
		}
		return true;
	}

	private boolean good(int a, int b, int c, int d) {
		return a > c || b > d;
	}

	BufferedReader br;
	StringTokenizer st;
	PrintWriter out;

	void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			// br = new BufferedReader(new FileReader(new
			// File("salary_pk.in")));
			// out = new PrintWriter("salary_pk.out");
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
		new salary_pk().run();
	}

	String nextToken() throws IOException {
		while ((st == null) || !st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return st.nextToken();
	}

	int nextInt() throws NumberFormatException, IOException {
		try {
			return Integer.parseInt(nextToken());
		} catch (Exception e) {
			return Integer.parseInt(nextToken());
		}
	}

	double nextDouble() throws NumberFormatException, IOException {
		return Double.parseDouble(nextToken());
	}

	long nextLong() throws NumberFormatException, IOException {
		return Long.parseLong(nextToken());
	}
}
