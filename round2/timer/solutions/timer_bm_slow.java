import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class timer_bm_slow {
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

	String slowSolve(Task t) {
		int N = (int) Math.pow(10, t.digits);
		String[] words = new String[N + 1];
		words[N] = "$$$$$";
		char[] tmp = new char[t.digits];
		for (int i = 0; i < N; i++) {
			int value = i;
			for (int j = 0; j < t.digits; j++) {
				tmp[t.digits - 1 - j] = t.map[t.digits - 1 - j][value % 10];
				value /= 10;
			}
			words[i] = new String(tmp);
		}
		int asked = 0;
		for (int i = 0; i < t.digits; i++) {
			asked = asked * 10 + t.number[i];
		}
		int lcp = 0;
		for (int i = 0; i < N; i++) {
			if (i == asked) {
				continue;
			}
			for (int j = 0;; j++) {
				lcp = Math.max(lcp, j);
				if (!words[i + j].equals(words[asked + j])) {
					break;
				}
			}
		}
		return Integer.toString(lcp);
	}

	void solve() {
		int tc = in.nextInt();
		for (int t = 0; t < tc; t++) {
			Task task = new Task(in.nextInt());
			String s = in.next();
			for (int i = 0; i < task.digits; i++) {
				task.number[i] = s.charAt(i) - '0';
			}
			for (int i = 0; i < task.digits; i++) {
				task.map[i] = in.next().toCharArray();
			}
			out.println(slowSolve(task));
		}
	}

	void run() {
		try {
			in = new FastScanner(new File("object.in"));
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
		new timer_bm_slow().runIO();
	}
}