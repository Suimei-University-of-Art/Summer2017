import java.io.*;
import java.sql.Array;
import java.util.*;

public class equality_va {
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

	public void solve() throws IOException {
		int n = nextInt();

		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = nextInt() - 1;
		}

		boolean[] was = new boolean[n];

		int pos = 0;
		while (!was[pos]) {
			was[pos] = true;
			pos = a[pos];
		}

		ArrayList<Integer> ans = new ArrayList<>();
		Arrays.fill(was, false);

		while (!was[pos]) {
			ans.add(pos);
			was[pos] = true;
			pos = a[pos];
		}

		out.println(ans.size());
		for (int x : ans) {
			out.print((x + 1) + " ");
		}
		out.println();
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			int t = nextInt();
			for (int i = 0; i < t; i++) {
				solve();
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		new equality_va().run();
	}
}
