import java.io.*;
import java.util.*;

public class knapsack_av {

	final double MAX_VAL = 1e18;
	final int MAX_N = 200;
	final int MAX_W = 500;
	final int MAX_ITERS = 10;

	public void solve() {
		long mod = in.nextLong();
		Random rng = new Random(0);

		for (int n = 1; n <= MAX_N; n++) {
			for (int IT = 0; IT < MAX_ITERS; IT++) {
				int[] weights = new int[n];
				for (int i = 0; i < n; i++) {
					weights[i] = 1 + rng.nextInt((MAX_W) / 2 / n);
				}

				long[] dp = new long[MAX_W + 1];
				dp[0] = 1;

				for (int w : weights) {
					for (int i = MAX_W; i >= w; i--) {
						dp[i] += dp[i - w];
						if (dp[i] >= mod) {
							dp[i] -= mod;
						}
					}
				}

				Item[] items = new Item[MAX_W + 1];
				for (int i = 0; i <= MAX_W; i++) {
					items[i] = new Item(dp[i], i);
				}
				Arrays.sort(items);
				int ptr = 0;
				List<Integer> add = new ArrayList<>();

				long cur = mod;
				for (int i = 0; i < MAX_N - n; i++) {
					while (ptr < items.length && items[ptr].lVal > cur) {
						ptr++;
					}

					if (ptr < items.length && items[ptr].lVal > 0) {
						cur -= items[ptr].lVal;
						add.add(MAX_W - items[ptr].weight);
					}
				}
				if (cur == 0) {
					out.println((n + add.size()) + " " + (MAX_W));
					for (int w : weights) {
						out.print(w + " ");
					}
					for (int w : add) {
						out.print(w + " ");
					}
					return;
				}
			}
		}
		throw new AssertionError();
	}

	class Item implements Comparable<Item> {
		final long lVal;
		final int weight;

		public Item(long lVal, int weight) {
			this.lVal = lVal;
			this.weight = weight;
		}

		@Override
		public int compareTo(Item o) {
			return -Long.compare(lVal, o.lVal);
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
		new knapsack_av().run();
	}
}
