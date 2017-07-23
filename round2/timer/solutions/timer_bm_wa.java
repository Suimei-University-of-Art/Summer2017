import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class timer_bm_wa {
	FastScanner in;
	PrintWriter out;

	class Task {
		int[] number;
		int digits;
		char[][] map; // digits x 10

		// 0 - highest digit

		Task(int digits) {
			this.digits = digits;
			number = new int[digits];
			map = new char[digits][10];
		}

		void print() {
			System.err.println("digits = " + digits);
			for (int i = 0; i < digits; i++) {
				System.err.print(number[i]);
			}
			System.err.println();
			for (int i = 0; i < digits; i++) {
				for (int j = 0; j < 10; j++) {
					System.err.print(map[i][j]);
				}
				System.err.println();
			}
		}
	}


	String solveWA(Task t) {
		for (int digit = 0; digit < t.digits; digit++) {
			int lcp = 0;
			for (int i = 0; i < 10; i++) {
				if (i == t.number[digit]) {
					continue;
				}
				int curLcp = 0;
				while (i + curLcp < 10
						&& t.number[digit] + curLcp < 10
						&& t.map[digit][i + curLcp] == t.map[digit][t.number[digit]
								+ curLcp]) {
					curLcp++;
				}
				lcp = Math.max(lcp, curLcp);
			}
			if (lcp != 0) {
				char[] need = new char[t.digits];
				for (int j = 0; j < t.digits; j++) {
					if (j <= digit) {
						need[j] = (char) (t.number[j] + '0');
					} else {
						need[j] = '0';
					}
				}
				BigInteger needBI = new BigInteger(new String(need));
				needBI = needBI.add(BigInteger.valueOf(10)
						.pow(t.digits - digit - 1)
						.multiply(BigInteger.valueOf(lcp)));
				char[] start = new char[t.digits];
				for (int j = 0; j < t.digits; j++) {
					start[j] = (char) (t.number[j] + '0');
				}
				BigInteger startBI = new BigInteger(new String(start));
				return needBI.subtract(startBI).toString();
			}
		}
		return "0";
	}


	// Complexity: 
	// tc * changeOne = 100 * 100 * n^3 = 8 * 10^6
	void solve() {
		long START = System.currentTimeMillis();
		int tc = in.nextInt();
		for (int t = 0; t < tc; t++) {
			// System.err.println(t);
			Task task = new Task(in.nextInt());
			String s = in.next();
			for (int i = 0; i < task.digits; i++) {
				task.number[i] = s.charAt(i) - '0';
			}
			for (int i = 0; i < task.digits; i++) {
				task.map[i] = in.next().toCharArray();
			}
			out.println(solveWA(task));
		}
	}

	void run() {
		try {
			in = new FastScanner(new File("test.txt"));
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
		new timer_bm_wa().runIO();
	}
}