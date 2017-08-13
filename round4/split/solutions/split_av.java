import java.io.*;
import java.util.*;

public class split_av {

	FastScanner in;
	PrintWriter out;
	final String fileName = "split_av".toLowerCase();
	
	String[][] ans = new String[][] {
			new String[] {"1 ", "2 ", "3 ", },
			new String[] {"-1"},
			new String[] {"-1"},
			new String[] {"1 2 3 4 ", "5 8 9 7 ", "6 10 12 11 ", },
			new String[] {"1 2 3 4 5 ", "6 9 12 10 8 ", "7 11 15 14 13 ", },
			new String[] {"-1"},
			new String[] {"-1"},
			new String[] {"1 2 3 4 5 6 7 8 ", "9 11 14 18 19 15 16 12 ", "10 13 17 22 24 21 23 20 ", },
			new String[] {"1 2 3 4 5 6 7 8 9 ", "10 12 15 19 22 20 17 13 16 ", "11 14 18 23 27 26 24 21 25 ", },
			new String[] {"-1"},
			new String[] {"-1"},
			new String[] {"1 2 3 4 5 6 7 8 9 10 11 12 ", "13 15 16 20 23 26 29 27 25 21 22 18 ", "14 17 19 24 28 32 36 35 34 31 33 30 ", },
			new String[] {"1 2 3 4 5 6 7 8 9 10 11 12 13 ", "14 16 17 19 25 26 28 31 29 27 22 24 21 ", "15 18 20 23 30 32 35 39 38 37 33 36 34 ", },
			new String[] {"-1"},
			new String[] {"-1"},
			new String[] {"1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 ", "17 19 20 22 24 30 32 33 37 38 34 35 31 28 25 27 ", "18 21 23 26 29 36 39 41 46 48 45 47 44 42 40 43 ", },
			new String[] {"1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 ", "18 20 21 23 25 29 34 36 37 39 40 38 32 28 33 31 26 ", "19 22 24 27 30 35 41 44 46 49 51 50 45 42 48 47 43 ", },
			new String[] {"-1"},
			new String[] {"-1"},
			new String[] {"1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 ", "21 23 24 26 28 29 34 40 42 39 45 46 47 43 44 37 38 36 31 32 ", "22 25 27 30 33 35 41 48 51 49 56 58 60 57 59 53 55 54 50 52 ", },
			new String[] {"1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 ", "22 24 25 27 29 30 32 42 43 41 44 47 48 49 45 46 37 40 38 33 35 ", "23 26 28 31 34 36 39 50 52 51 55 59 61 63 60 62 54 58 57 53 56 ", },
			new String[] {"-1"},
			new String[] {"-1"},
	};

	void solve() {
		String[] result = ans[in.nextInt() - 1];
		for (String s : result) {
			out.println(s);
		}
	}

	void run() {
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
		new split_av().run();
	}
}
