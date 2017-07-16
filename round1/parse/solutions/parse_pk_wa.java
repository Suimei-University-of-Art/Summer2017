import java.io.*;
import java.util.*;

public class parse_pk_wa {

	PrintWriter out;
	BufferedReader br;
	StringTokenizer st;

	String nextToken() throws IOException {
		while ((st == null) || (!st.hasMoreTokens()))
			st = new StringTokenizer(br.readLine());
		return st.nextToken();
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

	class ByLength implements Comparator<String> {

		@Override
		public int compare(String arg0, String arg1) {
			return arg1.length() - arg0.length();
		}

	}

	final long S = 17239;

	public void solve() throws IOException {
		int n = nextInt();
		String[] v = new String[n];
		for (int i = 0; i < n; i++) {
			v[i] = nextToken();
		}

		Arrays.sort(v, new ByLength());
		long[][] hash = new long[n][];
		for (int i = 0; i < n; i++) {
			hash[i] = new long[v[i].length() + 1];
			for (int j = 1; j <= v[i].length(); j++) {
				hash[i][j] = hash[i][j - 1] * S + v[i].charAt(j - 1);
			}
		}

		long[] pow = new long[10000];
		pow[0] = 1;
		for (int i = 1; i < 10000; i++) {
			pow[i] = pow[i - 1] * S;
		}

		HashSet<Long> voc = new HashSet<Long>();
		for (int i = 0; i < n; i++) {
			voc.add(hash[i][v[i].length()]);
		}
		
		boolean[][] pref = new boolean[n][];
		for (int i = 0; i < n; i++) {
			pref[i] = new boolean[v[i].length() + 1];
			pref[i][0] = true;
			for (int j = 1; j <= v[i].length(); j++) {
				for (int k = n - 1; k >= 0; k--) {
					if (v[k].length() > j)
						break;
					if (!pref[i][j - v[k].length()])
						continue;
					if (voc.contains(hash[i][j] - hash[i][j - v[k].length()] * pow[v[k].length()])) {
						pref[i][j] = true;
						break;
					}
				}
			}
		}
		
		boolean[][] suf = new boolean[n][];
		for (int i = 0; i < n; i++) {
			suf[i] = new boolean[v[i].length() + 1];
			suf[i][v[i].length()] = true;
			for (int j = v[i].length() - 1; j >= 0; j--) {
				for (int k = n - 1; k >= 0; k--) {
					if (v[k].length() + j > v[i].length())
						break;
					if (!suf[i][j + v[k].length()])
						continue;
					if (voc.contains(hash[i][j + v[k].length()] - hash[i][j] * pow[v[k].length()])) {
						suf[i][j] = true;
						continue;
					}
				}
			}
		}
		
		HashSet<Long> checked = new HashSet<Long>();
		checked.addAll(voc);
		int ti = -1;
		int tj = -1;
		int tk = -1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < v[i].length(); j++) {
				if (!pref[i][j])
					continue;
				for (int k = 0; k < n; k++) {
					if (j + v[k].length() <= v[i].length())
						break;
					if (suf[k][v[i].length() - j])
						continue;
					if (hash[k][v[i].length() - j] != hash[i][v[i].length()] - hash[i][j] * pow[v[i].length() - j]) {
						continue;
					}
					if (checked.contains(hash[i][j] * pow[v[k].length()] + hash[k][v[k].length()]))
						continue;
					if ((ti == -1) || (j + v[k].length() < tj + v[tk].length())) {
						ti = i;
						tj = j;
						tk = k;
					}
				}
			}
		}
		if (ti == -1) {
			out.println("Good vocabulary!");
		} else {
			out.println(v[ti].substring(0, tj) + v[tk]);
		}
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

//			br = new BufferedReader(new FileReader("parse.in"));
//			out = new PrintWriter("parse.out");

			solve();

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new parse_pk_wa().run();
	}
}
