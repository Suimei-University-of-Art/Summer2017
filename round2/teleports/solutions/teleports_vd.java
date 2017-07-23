import java.io.*;
import java.util.*;

public class teleports_vd {

	class Edge {
		int to;
		int num;
		Edge rev;
		boolean was;

		public Edge(int to, int num) {
			super();
			this.to = to;
			this.num = num;
		}
	}

	int[] index;
	int[] teleport;

	void dfs(int v) {
		if (teleport[v] != -1) {
			v = teleport[v];
		}
		Edge e;
		for (; index[v] < a[v].size(); index[v]++) {
			e = a[v].get(index[v]);
			if (!e.was) {
				e.was = true;
				e.rev.was = true;
				dfs(e.to);
				res.add(e.num);
			}
		}
	}

	void init(int n) {
		index = new int[n];
		teleport = new int[n];
		Arrays.fill(teleport, -1);
		res = new ArrayList<>();
		a = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			a[i] = new ArrayList<>();
		}
	}

	ArrayList<Edge>[] a;

	ArrayList<Integer> res = new ArrayList<>();

	void solve(int n) throws IOException {
		init(n);
		int m = nextInt();
		int k = nextInt();
		for (int i = 0; i < m; i++) {
			int st = nextInt() - 1;
			int en = nextInt() - 1;
			Edge e1 = new Edge(en, i + 1);
			Edge e2 = new Edge(st, i + 1);
			e1.rev = e2;
			e2.rev = e1;
			a[st].add(e1);
			a[en].add(e2);
		}
		for (int i = 0; i < k; i++) {
			int st = nextInt() - 1;
			int en = nextInt() - 1;
			teleport[st] = en;
			teleport[en] = st;
		}

		int sum = 0;
		int start = 0;
		for (int i = 0; i < n; i++) {
			if (teleport[i] == -1) {
				if (a[i].size() % 2 == 1) {
					sum++;
					start = i;
				}
			} else if (i < teleport[i]) {
				if (Math.abs(a[teleport[i]].size() - a[i].size()) >= 1) {
					sum += Math.abs(a[teleport[i]].size() - a[i].size());
					if (a[teleport[i]].size() > a[i].size()) {
						start = i;
					} else {
						start = teleport[i];
					}
				}
			}
		}

		if (sum > 2) {
			out.println("No");
			return;
		}

		while (teleport[start] == -1 ? a[start].size() == 0
				: a[teleport[start]].size() == 0) {
			start++;
		}
		dfs(start);

		if (res.size() != m) {
			out.println("No");
			return;
		}

		out.println("Yes");
		for (int i : res) {
			out.print(i + " ");
		}
		out.println();
	}

	public void run() throws IOException {
		br = new BufferedReader(new FileReader("teleports.in"));
		out = new PrintWriter(System.out);
		int n = nextInt();
		while (n > 0) {
			solve(n);
			n = nextInt();
		}
		br.close();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		new teleports_vd().run();
	}

	BufferedReader br;
	StringTokenizer str;
	PrintWriter out;

	String next() throws IOException {
		while (str == null || !str.hasMoreTokens()) {
			String s = br.readLine();
			if (s != null) {
				str = new StringTokenizer(s);
			} else {
				return null;
			}
		}
		return str.nextToken();
	}

	long nextLong() throws IOException {
		return Long.parseLong(next());
	}

	int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}

}
