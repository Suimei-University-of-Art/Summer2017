import java.io.*;
import java.util.*;

public class knapsack_bm {
	FastScanner in;
	PrintWriter out;

	// 0:47
	// 0:56
	
	class Obj implements Comparable<Obj> {
		int pos;
		long cost;

		public Obj(int pos, long cost) {
			super();
			this.pos = pos;
			this.cost = cost;
		}

		@Override
		public int compareTo(Obj o) {
			return -Long.compare(cost, o.cost);
		}

	}

	void solve() {
		long m = in.nextLong();
		Random rnd = new Random(13);
		int n = 100;
		int w = 500;
		while (true) {
			int use = 1 + rnd.nextInt(n);
			int maxW = Math.max(1, w / 2 / use);
			int[] cur = new int[use];
			for (int i = 0; i < use; i++) {
				cur[i] = 1 + rnd.nextInt(maxW);
			}
			long[] sum = new long[w + 1];
			sum[0] = 1;
			for (int i = 0; i < use; i++)
				for (int j = w - cur[i]; j >= 0; j--) {
					sum[j + cur[i]] = (sum[j + cur[i]] + sum[j]) % m;
				}
			ArrayList<Obj> all = new ArrayList<>();
			for (int i = 0; i < w; i++) {
				if (sum[i] != 0) {
					all.add(new Obj(i, sum[i]));
				}
			}
			Collections.sort(all);
			long curM = m;
			long need = 0;
			ArrayList<Integer> answer = new ArrayList<>();
			for (int i = 0; i < use; i++) {
				answer.add(cur[i]);
			}
			for (Obj o : all) {
				need += curM / o.cost;
				curM %= o.cost;
			}
			if (need + use < n) {
				curM = m;
				for (Obj o : all) {
					while (curM >= o.cost) {
						curM -= o.cost;
						answer.add(w - o.pos);
					}
				}
				out.println((need + use) + " " + w);
				for (int x : answer) {
					out.print(x + " ");
				}
				return;
			}
		}
	}

	void run() {
		try {
			in = new FastScanner(new File("knapsack.in"));
			out = new PrintWriter(new File("knapsack.out"));

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
		new knapsack_bm().runIO();
	}
}