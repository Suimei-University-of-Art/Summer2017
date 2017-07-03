import java.io.*;
import java.util.*;

public class rails_ak {

	final static double EPS = 1e-9;

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static class Point {
		int x, y;

		public Point(int x, int y) {
			myAssert(Math.abs(x) <= 1000 && Math.abs(y) <= 1000, "Bad coordinates of point");
			this.x = x;
			this.y = y;
		}
	}

	static class Line {
		int a, b, c;

		public Line(Point p1, Point p2) {
			myAssert(p1.x != p2.x || p1.y != p2.y, "Points are the same");
			a = p1.y - p2.y;
			b = p2.x - p1.x;
			c = -a * p1.x - b * p1.y;
			int g = gcd(gcd(a, b), c);
			a /= g;
			b /= g;
			c /= g;
			if (a < 0) {
				a = -a;
				b = -b;
				c = -c;
			}
			if (a == 0 && b < 0) {
				b = -b;
				c = -c;
			}
		}

		boolean isParrallelTo(Line l) {
			return a * l.b - b * l.a == 0;
		}

		double h() {
			return c / Math.sqrt(a * a + b * b);
		}

		@Override
		public String toString() {
			return "(" + a + ", " + b + ", " + c + ")";
		}

	}

	ArrayList<Double> findCorrectDistances(ArrayList<Double> a) {
		ArrayList<Double> ans = new ArrayList<Double>();
		if (a.size() % 2 == 1)
			return ans;
		int n = a.size();
		for (int i = 1; i < n; i++) {
			double D = a.get(i) - a.get(0);
			boolean[] used = new boolean[n];
			used[0] = used[i] = true;
			int p = 1;
			for (int j = 1; j < n; j++) {
				if (used[j])
					continue;
				while (p < n && a.get(p) - a.get(j) < D - EPS)
					p++;
				if (p == n)
					continue;
				if (Math.abs(a.get(p) - a.get(j) - D) < EPS) {
					used[j] = used[p] = true;
				}
			}
			boolean ok = true;
			for (boolean b : used)
				if (!b)
					ok = false;
			if (ok)
				ans.add(D);
		}
		return ans;
	}

	ArrayList<Double> intersect(ArrayList<Double> a, ArrayList<Double> b) {
		ArrayList<Double> ans = new ArrayList<Double>();
		int n = a.size(), m = b.size();
		int p = 0;
		for (int i = 0; i < n; i++) {
			for (; p < m && b.get(p) < a.get(i) - EPS; p++)
				;
			if (p < m && Math.abs(b.get(p) - a.get(i)) < EPS)
				ans.add(a.get(i));
		}
		return ans;
	}

	static void myAssert(boolean cond, String reason) {
		if (!cond)
			throw new AssertionError(reason);
	}

	void solve() {
		int n = in.nextInt() * 2;
		myAssert (1 <= n && n <= 4000, "Bad n");
		Line[] lines = new Line[n];
		for (int i = 0; i < n; i++) {
			lines[i] = new Line(new Point(in.nextInt(), in.nextInt()),
					new Point(in.nextInt(), in.nextInt()));
		}
		ArrayList<ArrayList<Double>> classes = new ArrayList<ArrayList<Double>>();
		{
			boolean[] used = new boolean[n];
			for (int i = 0; i < n; i++) {
				classes.add(new ArrayList<Double>());
				ArrayList<Double> now = classes.get(classes.size() - 1);
				for (int j = i; j < n; j++) {
					if (lines[i].isParrallelTo(lines[j]) && !used[j]) {
						now.add(lines[j].h());
						used[j] = true;
					}
				}
				Collections.sort(now);
				for (; i < n && used[i]; i++)
					;
				i--;
			}
		}
		ArrayList<ArrayList<Double>> correctDistances = new ArrayList<ArrayList<Double>>();
		for (ArrayList<Double> al : classes)
			correctDistances.add(findCorrectDistances(al));
		ArrayList<Double> answers = new ArrayList<Double>(correctDistances
				.get(0));
//		System.err.println(correctDistances);
		for (ArrayList<Double> al : correctDistances) {
			answers = intersect(answers, al);
		}
		Locale.setDefault(Locale.US);
		if (answers.size() == 0) {
			out.println("-1");
		} else {
			out.printf("%.20f\n", answers.get(0));
		}
	}

	PrintWriter out;
	Scanner in;

	void run() throws FileNotFoundException {
//		in = new Scanner(new File("rails.in"));
//		out = new PrintWriter("rails.out");
		in = new Scanner(System.in);
		out = new PrintWriter(System.out);
		solve();
		out.close();
	}

	public static void main(String[] args) throws FileNotFoundException {
		new rails_ak().run();
	}
}
