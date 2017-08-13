import java.io.*;
import java.util.*;

public class split_va3 implements Runnable {
	public static void main(String[] args) {
		new Thread(new split_va3()).run();
	}

	BufferedReader br;
	StringTokenizer in;
	PrintWriter out;

	public String nextToken() throws IOException {
		while (in == null || !in.hasMoreTokens()) {
			in = new StringTokenizer(br.readLine());
		}

		return in.nextToken();
	}

	public int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	public long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	public double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}

	int[] a, b, c;
	boolean[] take;

	void print(int[] a) {
		for (int i = 0; i < a.length; i++) {
			out.print(a[i] + " ");
		}
		out.println();
	}

	public void go(int l) {
		if (l == a.length) {
			print(a);
			print(b);
			print(c);
			throw new RuntimeException();
		}

		int n = a.length;

		a[l] = 1;
		while (take[a[l]])
			a[l]++;

		take[a[l]] = true;

		for (int j = 3 * n; j > 2 * a[l]; j--) {
			if (take[j] || take[j - a[l]])
				continue;

			a[l] = a[l];
			b[l] = j - a[l];
			c[l] = j;

			take[b[l]] = true;
			take[c[l]] = true;

			go(l + 1);

			take[b[l]] = false;
			take[c[l]] = false;
		}
		take[a[l]] = false;
	}

	public void solve() throws IOException {
		int n = nextInt();

		a = new int[n];
		b = new int[n];
		c = new int[n];
		take = new boolean[3 * n + 1];

		long time = System.currentTimeMillis();

		try {
			go(0);
		} catch (Exception e) {
		}

		System.err.println(System.currentTimeMillis() - time);
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			int t = nextInt();
			for (int i = 0; i < t; i++)
				solve();

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
