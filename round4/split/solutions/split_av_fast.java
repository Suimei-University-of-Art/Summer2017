import java.io.*;
import java.util.*;

public class split_av_fast {

	FastScanner in;
	PrintWriter out;
	final String fileName = "split_av".toLowerCase();

	int n;
	boolean[] used;
	int[] a, b, c;

	void go(int pos) {
		if (pos == n) {
			printArray(a);
			printArray(b);
			printArray(c);
			throw new IFoundAnswer();
		}

		int minUnused = 1;
		while (used[minUnused]) {
			minUnused++;
		}

		int curA = a[pos];

		// for (int curB = 3 * n - curA; curB >= minUnused; curB--) {
		for (int curB = minUnused; curB + curA <= 3 * n; curB++) {
			if (!used[curB] && !used[curB + curA]) {
				b[pos] = curB;
				c[pos] = curB + curA;
				used[curB] = used[curB + curA] = true;
				go(pos + 1);
				used[curB] = used[curB + curA] = false;
			}
		}
	}

	private void printArray(int[] a) {
		out.print('"');
		for (int i : a) {
			out.print(i + " ");
		}
		out.print('"');
		out.print(", ");
	}

	void solve(int n) {
		this.n = n;
		if (n % 4 > 1) {
			out.print("\"" + -1 + "\"");
			return;
		}
		used = new boolean[3 * n + 1];
		a = new int[n];
		b = new int[n];
		c = new int[n];
		for (int i = 1; i <= n; i++) {
			a[i - 1] = i;
			used[i] = true;
		}
		try {
			go(0);
			// out.println("FAIL " + n);
		} catch (IFoundAnswer e) {
		}
	}

	class IFoundAnswer extends RuntimeException {
	};

	void run() {
		try {
			in = new FastScanner();
			out = new PrintWriter(System.out);
			long time = System.currentTimeMillis();
			for (int i = 1; i <= 23; i++) {
				out.print("new String[] {");
				solve(i);
				out.println("},");
			}
			System.err.println(System.currentTimeMillis() - time);
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

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return st.nextToken();
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
		new split_av_fast().run();
	}
}
