import java.io.*;
import java.util.*;
import java.math.*;

public class blackjohn_aa_mitm {
	public static void main(String[] args) {
		new blackjohn_aa_mitm().run();
	}

	BufferedReader br;
	StringTokenizer st;
	PrintWriter out;
	boolean eof = false;
	Random rand = new Random(12498);

	private void run() {
		Locale.setDefault(Locale.US);
		try {
			// int min = 11;
			// out = new PrintWriter("blackjohn.in");
			// out.println(MAXN);
			// int c = rand.nextInt(MAXN);
			// for (int i = 0; i < MAXN; i++) {
			// int q = min + rand.nextInt(MAXQ - min + 1);
			// while (q == 11 || q == 13 || q == 17 || q == 19) {
			// q = min + rand.nextInt(MAXQ - min + 1);
			// }
			// int p = (q + 2) / 2 + rand.nextInt((q + 1) / 2 - 1);
			// while (gcd(p, q) != 1) {
			// p = (q + 2) / 2 + rand.nextInt((q + 1) / 2 - 1);
			// }
			// if (i == c) {
			// p *= -1;
			// }
			// out.println(p + " " + q);
			// }
			// out.close();
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			// br = new BufferedReader(new FileReader("blackjohn.in"));
			// out = new PrintWriter("blackjohn.out");
			solve();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(566);
		}
	}

	String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (Exception e) {
				eof = true;
				return "0";
			}
		}
		return st.nextToken();
	}

	int nextInt() {
		return Integer.parseInt(nextToken());
	}

	void myAssert(boolean u, String message) {
		if (!u) {
			throw new Error("Assertion failed!!! " + message);
		}
	}

	int MAXN = 100;
	int MAXQ = 21;
	int shift = 0;

	private void solve() throws IOException {
		int n = nextInt();
		myAssert(1 <= n && n <= MAXN, "n not in bounds: n = " + n);
		int[] p = new int[n];
		int[] q = new int[n];
		int[] a = new int[n];
		for (int i = 0; i < p.length; i++) {
			p[i] = nextInt();
			q[i] = nextInt();
			a[i] = i;
			myAssert(Math.abs(p[i]) <= q[i], "wrong fraction: " + p[i] + "/"
					+ q[i]);
			myAssert(1 <= q[i] && q[i] <= MAXQ, "wrong fraction: " + p[i] + "/"
					+ q[i]);
			int j = rand.nextInt(i + 1);
			int tmp = p[i];
			p[i] = p[j];
			p[j] = tmp;
			tmp = q[i];
			q[i] = q[j];
			q[j] = tmp;
			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
		long time = System.currentTimeMillis();
		int lcm = 1;
		for (int i = 0; i < q.length; i++) {
			lcm = lcm / gcd(lcm, q[i]) * q[i];
		}
		for (int i = 0; i < q.length; i++) {
			p[i] *= lcm / q[i];
		}
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		for (int i = 0; i < 1 << (n / 2); i++) {
			if (System.currentTimeMillis() - time > 1800) {
				System.err.println("exit first");
				break;
			}
			int sum = 0;
			for (int j = 0; j < n / 2; j++) {
				if ((i & (1 << j)) != 0) {
					sum += p[j];
				}
			}
			hm.put(sum, i);
			if (sum == lcm) {
				ArrayList<Integer> ans = new ArrayList<Integer>();
				for (int j = 0; j < n / 2; j++) {
					if ((i & (1 << j)) != 0) {
						ans.add(a[j]);
					}
				}
				print(ans);
				System.err
						.println((System.currentTimeMillis() - time) / 1000.0);
				return;
			}
		}
		for (int i = 0; i < 1 << (n - n / 2); i++) {
			if (System.currentTimeMillis() - time > 1800) {
				break;
			}
			int sum = 0;
			for (int j = 0; j < n - n / 2; j++) {
				if ((i & (1 << j)) != 0) {
					sum += p[n - 1 - j];
				}
			}
			if (hm.containsKey(lcm - sum)) {
				int prof = hm.get(lcm - sum);
				ArrayList<Integer> ans = new ArrayList<Integer>();
				for (int j = 0; j < n / 2; j++) {
					if ((prof & (1 << j)) != 0) {
						ans.add(a[j]);
					}
				}
				for (int j = n - n / 2 - 1; j >= 0; j--) {
					if ((i & (1 << j)) != 0) {
						ans.add(a[n - j - 1]);
					}
				}
				print(ans);
				System.err
						.println((System.currentTimeMillis() - time) / 1000.0);
				return;
			}
		}
		out.println("NO");
		System.err.println((System.currentTimeMillis() - time) / 1000.0);
	}

	private void print(ArrayList<Integer> ans) {
		Collections.sort(ans);
		out.println("YES");
		out.println(ans.size());
		for (int i : ans) {
			out.print(i + 1 + " ");
		}
	}

	int gcd(int a, int b) {
		while (b != 0) {
			int t = a % b;
			a = b;
			b = t;
		}
		return a;
	}

}