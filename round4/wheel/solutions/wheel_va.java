import java.io.*;
import java.util.*;

public class wheel_va implements Runnable {
	public static void main(String[] args) {
		new Thread(new wheel_va()).run();
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

	double[] res;

	public void precalc() {
		double[][][] ans = new double[1][1][1];

		int n = 125;

		res = new double[n + 1];

		ans[0][0][0] = 0.5;
		for (int i = 0; i < n - 1; i++) {
			double[][][] tmp = new double[i + 2][i + 2][i + 2];

			for (int cntZero = 0; cntZero < i + 1; cntZero++) {
				for (int maxOnes = 0; maxOnes < i + 1; maxOnes++) {
					for (int lenOnes = 0; lenOnes < i + 1; lenOnes++) {
						tmp[cntZero + 1][maxOnes][0] += 0.5 * ans[cntZero][maxOnes][lenOnes];
						tmp[cntZero][Math.max(maxOnes, lenOnes + 1)][lenOnes + 1] += 0.5 * ans[cntZero][maxOnes][lenOnes];
					}
				}
			}
			ans = tmp;

			double z = 1. * (i + 2) / Math.pow(2, i + 2);
			for (int cntZero = 0; cntZero < i + 2; cntZero++) {
				for (int maxOnes = 0; maxOnes < i + 2; maxOnes++) {
					for (int lenOnes = 0; lenOnes < i + 2; lenOnes++) {
						z += Math.max(cntZero + 2, maxOnes) * (lenOnes + 1)
								* ans[cntZero][maxOnes][lenOnes];
					}
				}
			}

			res[i + 2] = z;
		}
	}

	public void solve() throws IOException {
		int n = nextInt();
		if (n > 125) {
			out.println(0.5 * (n + 2));
		} else {
			out.println(res[n]);
		}
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			precalc();

			int t = nextInt();
			for (int i = 0; i < t; i++)
				solve();

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
