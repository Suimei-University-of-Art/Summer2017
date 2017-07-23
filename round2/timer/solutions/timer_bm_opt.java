import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class timer_bm_opt {
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

	String toString(int value, int digits) {
		String s = Integer.toString(value);
		while (s.length() < digits) {
			s = "0" + s;
		}
		return s;
	}

	int diff(String s1, String s2) {
		int diff = 0;
		for (int i = 0; i < s1.length(); i++) {
			diff += s1.charAt(i) != s2.charAt(i) ? 1 : 0;
		}
		return diff;
	}

	String gen(BigInteger value, Task t) {
		String tmp = value.toString();
		int diff = t.digits - tmp.length();
		char[] res = new char[t.digits];
		for (int i = 0; i < t.digits; i++) {
			res[i] = t.map[i][i < diff ? 0 : (tmp.charAt(i - diff) - '0')];
		}
		return new String(res);
	}

	final static int MAXN = 100;
	static BigInteger[] pow10;
	static {
		pow10 = new BigInteger[MAXN];
		pow10[0] = BigInteger.ONE;
		for (int i = 1; i < pow10.length; i++) {
			pow10[i] = pow10[i - 1].multiply(BigInteger.valueOf(10));
		}
	}

	// Complexity:
	// n * 10 * n
	BigInteger canDiff(char[] smaller, char[] bigger, Task t, int pos) {
		BigInteger biggerBI = new BigInteger(new String(bigger));
		BigInteger smallerBI = new BigInteger(new String(smaller));
		if (!gen(biggerBI, t).equals(gen(smallerBI, t))) {
			return BigInteger.ZERO;
		}
		BigInteger result = pow10[t.digits]
				.subtract(biggerBI);
		for (int p = pos; p >= 0; p--) {
			if (p != pos) {
				bigger[p + 1] = '0';
			} else {
				for (int p2 = p + 1; p2 < t.digits; p2++) {
					bigger[p2] = '0';
				}
			}
			BigInteger now = new BigInteger(new String(bigger));
			BigInteger POW = pow10[t.digits - 1 - p];
			for (int add = 1; add < 10; add++) {
				now = now.add(POW);
				BigInteger diff = now.subtract(biggerBI);
				BigInteger check = smallerBI.add(diff);
				String first = gen(check, t);
				String second = gen(now, t);
				if (!first.equals(second)) {
					if (result.compareTo(diff) > 0) {
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
		BigInteger res = BigInteger.ZERO;
		char[] start = new char[t.digits];
		for (int j = 0; j < t.digits; j++) {
			start[j] = (char) (t.number[j] + '0');
		}
		char[] next = start.clone();
		BigInteger startBI = new BigInteger(new String(start));
		for (int digit = 0; digit < t.digits; digit++) {
			for (int newDigit = 0; newDigit < 10; newDigit++) {
				if (newDigit == t.number[digit]) {
					continue;
				}
				BigInteger tmp;
				next[digit] = (char) ('0' + newDigit);
				if (newDigit < t.number[digit]) {
					tmp = canDiff(next.clone(), start.clone(), t, digit);
				} else {
					tmp = canDiff(start.clone(), next.clone(), t, digit);
				}
				next[digit] = start[digit];
				if (tmp.compareTo(res) > 0) {
					res = tmp;
				}
			}
		}
		BigInteger MAX = BigInteger.valueOf(10).pow(t.digits).subtract(startBI);
		if (res.compareTo(MAX) > 0) {
			res = MAX;
		}
		return res.toString();
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
		new timer_bm_opt().runIO();
	}
}