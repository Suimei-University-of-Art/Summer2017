import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class timer_bm_long_wa {
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

	String gen(long value, Task t) {
		String tmp = Long.toString(value);
		int diff = t.digits - tmp.length();
		char[] res = new char[t.digits];
		for (int i = 0; i < t.digits; i++) {
			res[i] = t.map[i][i < diff ? 0 : (tmp.charAt(i - diff) - '0')];
		}
		return new String(res);
	}

	final static int MAXN = 100;
	static long[] pow10;
	static {
		pow10 = new long[MAXN];
		pow10[0] = 1;
		for (int i = 1; i < pow10.length; i++) {
			pow10[i] = pow10[i - 1] * 10;
		}
	}

	// Complexity:
	// n * 10 * n
	long canDiff(char[] smaller, char[] bigger, Task t, int pos) {
		long biggerBI = Long.parseLong(new String(bigger));
		long smallerBI = Long.parseLong(new String(smaller));
		if (!gen(biggerBI, t).equals(gen(smallerBI, t))) {
			return 0;
		}
		long result = pow10[t.digits] - (biggerBI);
		for (int p = pos; p >= 0; p--) {
			if (p != pos) {
				bigger[p + 1] = '0';
			} else {
				for (int p2 = p + 1; p2 < t.digits; p2++) {
					bigger[p2] = '0';
				}
			}
			long now = Long.parseLong(new String(bigger));
			long POW = pow10[t.digits - 1 - p];
			for (int add = 1; add < 10; add++) {
				now = now + (POW);
				long diff = now - (biggerBI);
				long check = smallerBI + (diff);
				String first = gen(check, t);
				String second = gen(now, t);
				if (!first.equals(second)) {
					if (result > diff) {
						result = diff;
					}
				}
			}
		}
		return result;
	}

	// Complexity:
	// n * 10 * canDiff = 100 * n^3
	String changeOne(Task t) {
		long res = 0;
		char[] start = new char[t.digits];
		for (int j = 0; j < t.digits; j++) {
			start[j] = (char) (t.number[j] + '0');
		}
		char[] next = start.clone();
		long startBI = Long.parseLong(new String(start));
		for (int digit = 0; digit < t.digits; digit++) {
			long zz =0;
			for (int newDigit = 0; newDigit < 10; newDigit++) {
				if (newDigit == t.number[digit]) {
					continue;
				}
				long tmp;
				next[digit] = (char) ('0' + newDigit);
				if (newDigit < t.number[digit]) {
					tmp = canDiff(next.clone(), start.clone(), t, digit);
				} else {
					tmp = canDiff(start.clone(), next.clone(), t, digit);
				}
				next[digit] = start[digit];
				zz = Math.max(zz, tmp);
			}
			if (zz != 0) {
				return Long.toString(zz);
			}
		}
		long MAX = pow10[t.digits] - (startBI);
		if (res > MAX) {
			res = MAX;
		}
		return Long.toString(res);
	}

	// Complexity:
	// tc * changeOne = 100 * 100 * n^3 = 8 * 10^6
	void solve() {
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
			out.println(changeOne(task));
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
		new timer_bm_long_wa().runIO();
	}
}