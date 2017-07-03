import java.io.*;
import java.util.*;

public class square_pk_wa2 {
	public static void main(String[] args) {
		new square_pk_wa2().run();
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

	public void solve() throws IOException {
		int r = nextInt();
		int b = nextInt();
		b--;
		boolean t = false;
		int ans = 0;
		String answer = "";
		while ((r > 0) && (b > 0)) {
			t = !t;
			if (t)
				r--;
			else
				b--;
			ans++;
			answer = answer.concat("E");
		}
		int ost = r + b;
		if ((t && (r > 0)) || ((!t) && (b > 0))) {
			ans++;
			answer = answer.concat("W");
		}
		boolean fl = false;
		while (ost > 0) {
			if (fl) {
				ans += 2;
				answer = answer.concat("WW");
			}
			else
				fl = true;
			ans++;
			answer = answer.concat("N");
			ost--;
			if (ost > 0){
				ans += 3;
				answer = answer.concat("SSN");
				ost--;
			}
		}
		out.println(ans + 8);
		out.println(answer.concat("EEEEEEEE"));
	}

	public void run() {
		try {
			// br = new BufferedReader(new FileReader("square.in"));
			// out = new PrintWriter("square.out");

			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);


			solve();

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}