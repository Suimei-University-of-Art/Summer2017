import java.util.*;
import java.io.*;

public class birds_sp implements Runnable {
	public static void main(String[] args) {
		new Thread(new birds_sp()).start();
	}

	@Override
	public void run() {
//		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			br = new BufferedReader(new FileReader("birds.in"));
		} catch (FileNotFoundException e) {
		}
		out = new PrintWriter(System.out);
		solve();
		out.close();
	}

	BufferedReader br;
	StringTokenizer st;
	PrintWriter out;
	boolean eof = false;

	String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (Exception e) {
				eof = true;
				return "0";
			}
		}
		return st.nextToken();
	}

	int nextInt() {
		return Integer.parseInt(nextToken());
	}

	double nextDouble() {
		return Double.parseDouble(nextToken());
	}

	long nextLong() {
		return Long.parseLong(nextToken());
	}
	
	class Bird implements Comparable<Bird> {
		public Bird(int nextInt, int i, int j) {
			pos = nextInt;
			num = i;
			dir = j;
		}

		int pos, num, dir;

		@Override
		public int compareTo(Bird o) {
			return pos - o.pos;
		}
	}
	
	void solve() {
		int L = nextInt();
		int n = nextInt();
		ArrayList<Bird> rightAL = new ArrayList<Bird>();
		for (int i = 0; i < n; i++) {
			rightAL.add(new Bird(nextInt(), i, 0));
		}
		int m = nextInt();
		ArrayList<Bird> leftAL = new ArrayList<Bird>();
		for (int i = 0; i < m; i++) {
			leftAL.add(new Bird(nextInt(), i, 1));
		}
		long[][] ans = new long[2][];
		ans[0] = new long[n];
		ans[1] = new long[m];
		ArrayList<Bird> allAL = new ArrayList<Bird>();
		allAL.addAll(rightAL);
		allAL.addAll(leftAL);
		long time = 0;
		Collections.sort(rightAL);
		Collections.sort(leftAL);
		Collections.sort(allAL);
		ArrayDeque<Bird> left = new ArrayDeque<Bird>(leftAL);
		ArrayDeque<Bird> right = new ArrayDeque<Bird>(rightAL);
		ArrayDeque<Bird> all = new ArrayDeque<Bird>(allAL);
		int addL = 0;
		int addR = 0;
		while (all.size() > 0) {
			int l = left.size() > 0 ? left.peekFirst().pos + addL : Integer.MAX_VALUE;
			int r = right.size() > 0 ? L - right.peekLast().pos - addR : Integer.MAX_VALUE;
			int t = Math.min(l, r);
			time += t;
			addL -= t;
			addR += t;
			Bird b = null;
			if (l < r) {
				b = all.pollFirst();
				left.pollFirst();
			} else if (l > r){
				b = all.pollLast();
				right.pollLast();
			} else {
				b = all.pollFirst();
				ans[b.dir][b.num] = time;
				left.pollFirst();
				b = all.pollLast();
				ans[b.dir][b.num] = time;
				right.pollLast();
				continue;
			}
			ans[b.dir][b.num] = time;
			ArrayDeque<Bird> tmp = right; right = left; left = tmp;
			t = addL; addL = addR; addR = t;
		}
		for (int i = 0; i < ans.length; i++) {
			for (int j = 0; j < ans[i].length; j++) {
				out.print(ans[i][j] + " ");
			}
			out.println();
		}
	}
}