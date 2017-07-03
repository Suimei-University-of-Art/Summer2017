import java.io.*;
import java.util.*;

public class square_pk {
	public static void main(String[] args) {
		new square_pk().run();
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
		assert (r >= 0);
		assert (r <= 1000);
		int b = nextInt();
		assert (b >= 1);
		assert (b <= 1000);
		assert (b <= r * 3 + 1);
		assert (r <= b * 3 + 1);
		
		int ans = 0;
		String answer = "";
		
		if (r > b) {
			int t = b;
			b = r;
			r = t;
			ans++;
			answer = "W";
		}
		
		boolean t = true;
		
		while ((r > 0) || (b > 0)) {
			if (t) 
				b--;
			else
				r--;
			if ((!t) && (b > r)) {
				ans += 2;
				answer = answer.concat("NS");
				b--;
			}
			if ((!t) && (b > r)) {
				ans += 2;
				answer = answer.concat("SN");
				b--;
			}
			if ((!t) && (b > 0)) {
				ans++;
				answer = answer.concat("E");
				t = !t;
				continue;
			}
			if ((t) && (r > 0)) {
				ans++;
				answer = answer.concat("E");
				t = !t;
				continue;
			}
			
		}
		
		out.println(ans);
		out.println(answer);
	}

	public void run() {
		try {
			//br = new BufferedReader(new FileReader("square.in"));
			//out = new PrintWriter("square.out");

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