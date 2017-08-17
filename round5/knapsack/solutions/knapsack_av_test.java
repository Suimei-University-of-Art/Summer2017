import java.io.*;
import java.util.*;

public class knapsack_av_test {

	final int MAX_N = 200;
	final int MAX_W = 500;
	final int MAX_ITERS = 10;
	final long MAX = (long) 1e18;


	public void solve() {
		int globalMin = Integer.MAX_VALUE;
		for (;;) {
			Random rng = new Random();
			final int n = 60 + rng.nextInt() % 5;

			int[] weights = new int[n];
			int sum = 0;
			for (int i = 0; i < n; i++) {
				weights[i] = 1 + rng.nextInt(MAX_W  * 3 / 4 / n);
				sum += weights[i];
			}
			if (sum >= MAX_W / 2) {
				continue;
			}

			long[] dp = new long[MAX_W + 1];
			dp[0] = 1;

			for (int w : weights) {
				for (int i = MAX_W; i >= w; i--) {
					dp[i] += dp[i - w];
				}
			}

			Arrays.sort(dp);
			List<Long> newDp = new ArrayList<>();
			newDp.add(dp[0]);
			for (int i = 1; i < dp.length; i++) {
				if (dp[i] != dp[i - 1] && dp[i] <= MAX) {
					newDp.add(dp[i]);
				}
			}
			calcMax(newDp);
	
			int mx = 0;
			for (int i = 0; i < this.dp.length; i++) {
				mx = Math.max(mx, this.dp[i] + n);
			}
			globalMin = Math.min(mx, globalMin);
			if (mx < 110) {
				System.err.println(globalMin + " " + mx + " " + Arrays.toString(this.dp));
			}
			if (mx <= MAX_N) {
				break;
			}
		}
	}

	void calcMax(List<Long> vals) {
		vals.add(MAX + 1);
		v = new long[vals.size()];
		for (int i = 0; i < vals.size(); i++) {
			v[i] = vals.get(i);
		}

		dp = new int[vals.size() - 1];
		dp[0] = 0;
		for (int i = 1; i < dp.length; i++) {
			dp[i] = getSegment(v[i], v[i + 1]);
		}

	}

	long[] v;
	int[] dp;

	int getSegment(long x, long y) { // for some k: x = v[k], y <= v[k + 1]
		if (x >= y) {
			return 0;
		}
		long newY = y - x;
		if (newY >= x) {
			return 1 + Math.max(getPrefix(x), getSegment(x, newY));
		} else {
			return 1 + getPrefix(newY);
		}
		
	}

	private int getPrefix(long r) { // [0, r)
		int curMax = 0;
		for (int i = 0;; i++) {
			if (v[i + 1] <= r) { // [v_i, v_i+1) is a subset of [0, r)
				curMax = Math.max(curMax, dp[i]);
			} else {
				return Math.max(curMax, getSegment(v[i], r)); // [v_i, r) is a subset of [v_i, v_i+1]
			}
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
		new knapsack_av_test().run();
	}
}
