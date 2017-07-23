import java.io.*;
import java.util.*;

public class siege_va implements Runnable {
	public static void main(String[] args) {
		new Thread(new siege_va()).run();
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

	public class Artefact implements Comparable<Artefact> {
		int a, b, id;

		public Artefact(int a, int b, int id) {
			this.a = a;
			this.b = b;
			this.id = id;
		}

		public int compareTo(Artefact z) {
			return b == z.b ? a - z.a : b - z.b;
		}
	}

	public void solve() throws IOException {
		int A = nextInt();
		int B = nextInt();
		int n = nextInt();

		Artefact[] a = new Artefact[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = new Artefact(nextInt(), nextInt(), i + 1);
		}

		Arrays.sort(a);

		int[] minA = new int[B + 1];
		int[][] from = new int[B + 1][];
		int[] len = new int[B + 1];

		for (int i = 0; i < B + 1; i++) {
			from[i] = new int[100];
		}
		
		Arrays.fill(minA, Integer.MAX_VALUE);
		minA[0] = 0;
		int ans = 0;
		ArrayList<Integer> sert = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			// Try stop on this.
			Artefact[] b = Arrays.copyOfRange(a, i, n);

			Arrays.sort(b, new Comparator<Artefact>() {
				@Override
				public int compare(Artefact a, Artefact b) {
					return a.a - b.a;
				}
			});

			int mA = -1;
			for (int j = Math.max(0, minA.length - a[i].b); j < minA.length; j++) {
				if (minA[j] == Integer.MAX_VALUE)
					continue;
				if (mA == -1 || minA[mA] > minA[j])
					mA = j;
			}

			if (mA != -1) {
				ArrayList<Integer> now = new ArrayList<Integer>();
				for (int j = 0; j < len[mA]; j++) {
					now.add(a[from[mA][j]].id);
				}

				int last = A - minA[mA];
				int count = 0;
				for (int j = 0; j < b.length && last >= 0; j++) {
					last -= b[j].a;
					if (last >= 0) {
						now.add(b[j].id);
						count++;
					}
				}
				if (count > ans) {
					ans = count;
					sert = now;
				}
			}

			// Add this one.
			for (int j = minA.length - a[i].b - 1; j >= 0; j--) {
				if (minA[j] == Integer.MAX_VALUE)
					continue;
				if (minA[j + a[i].b] > minA[j] + a[i].a) {
					minA[j + a[i].b] = minA[j] + a[i].a;
					if (from[j + a[i].b].length < len[j] + 1) {
					  from[j + a[i].b] = new int[Math.min(2 * (len[j] + 1), 1000)];
                    }

                    for (int k = 0; k < len[j]; k++) {
                      from[j + a[i].b][k] = from[j][k];
                    }
					from[j + a[i].b][len[j]] = i;
					len[j + a[i].b] = len[j] + 1;
				}
			}
		}

		out.println(ans);
		out.print(sert.size() + " ");
		for (int x : sert) {
			out.print(x + " ");
		}
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			solve();

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			;
		}
	}
}
