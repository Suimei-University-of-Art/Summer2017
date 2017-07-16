import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class prince_aa {
	public static void main(String[] args) {
		new prince_aa().run();
	}

	BufferedReader br;
	StringTokenizer st;
	PrintWriter out;
	boolean eof = false;

	private void run() {
		Locale.setDefault(Locale.US);
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			solve();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(566);
		}
	}

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

	int nextBoundedInt(int l, int r, String name) {
		int x = nextInt();
		myAssert(l <= x && x <= r, name + " is not in bounds: " + x
				+ " not in " + l + ".." + r);
		return x;
	}

	char nextChar() {
		String s = nextToken();
		myAssert(s.length() == 1, "char expected, but string found: " + s);
		return s.charAt(0);
	}

	void myAssert(boolean u, String message) {
		if (!u) {
			throw new Error("Assertion failed!!! " + message);
		}
	}

	final int MAXN = 1000;
	final int MAXX = (int) 1e5;
	final int MAXT = (int) 1e6;
	final int MAXC = (int) 1e6;

	public void solve() throws IOException {
		int n = nextBoundedInt(0, MAXN, "n");
		int door = nextBoundedInt(1, MAXX, "x");
		Trap[] traps = new Trap[n];
		Point[] points = new Point[2 * n + 1];
		Event[] events = new Event[2 * n + 1];
		for (int i = 0; i < traps.length; i++) {
			traps[i] = new Trap(nextBoundedInt(1, MAXT, "a_" + (i + 1)),
					nextBoundedInt(1, MAXT, "t_" + (i + 1)), nextBoundedInt(
							-MAXC, MAXC, "l_" + (i + 1)), nextBoundedInt(-MAXC,
							MAXC, "r_" + (i + 1)), i);
			points[2 * i] = traps[i].p1;
			points[2 * i + 1] = traps[i].p2;
			events[2 * i] = new Event(traps[i].p1.x, 1, i);
			events[2 * i + 1] = new Event(traps[i].p2.x, -1, i);
		}
		points[2 * n] = new Point(0, 0);
		points[2 * n].u = true;
		events[2 * n] = new Event(door, 0, -1);
		Arrays.sort(points);
		Arrays.sort(events);
		int ans = Integer.MAX_VALUE / 2;
		Point last = null;
		for (Point p : points) {
			if (!p.u) {
				continue;
			}
			int sbound = Integer.MAX_VALUE / 2;
			{// move up
				for (Trap t : traps) {
					if (t.s >= p.t && t.l < p.x && p.x < t.r) {
						sbound = Math.min(sbound, t.s);
					}
				}
				for (Trap t : traps) {
					if (t.f >= p.t && t.f <= sbound) {
						if (t.p1.x == p.x && t.p1 != p && t.p1.t > p.t) {
							t.p1.u = true;
							t.p1.prev = p;
						}
						if (t.p2.x == p.x && t.p2 != p && t.p2.t > p.t) {
							t.p2.u = true;
							t.p2.prev = p;
						}
					}
				}
			}
			{// move diagonal right
				int bound = sbound;
				TreeSet<Trap> cut = new TreeSet<Trap>();
				int q = 0;
				while (q < events.length
						&& (events[q].x < p.x || events[q].x == p.x
								&& events[q].type < 0)) {
					if (events[q].type < 0) {
						cut.remove(traps[events[q].n]);
					} else if (events[q].type > 0) {
						cut.add(traps[events[q].n]);
					}
					q++;
				}
				int x = p.x;
				int t = p.t;
				while (!cut.isEmpty() && cut.first().s < t) {
					cut.pollFirst();
				}
				loop: while (q < events.length) {
					int dx = events[q].x - x;
					x += dx;
					t += dx;
					if (t > bound) {
						break loop;
					}

					int j = q;
					while (j < events.length && events[j].x == events[q].x
							&& events[j].type < 0) {
						cut.remove(traps[events[j].n]);
						j++;
					}
					bound = Integer.MAX_VALUE / 2;
					if (!cut.isEmpty()) {
						bound = Math.min(bound, cut.first().s);
					}
					for (int i = q; i < j; i++) {
						if (t <= traps[events[i].n].f
								&& traps[events[i].n].f <= bound
								&& traps[events[i].n].p2 != p
								&& traps[events[i].n].p2.t > p.t) {
							traps[events[i].n].p2.u = true;
							traps[events[i].n].p2.prev = p;
						}
					}
					int mid = j;
					while (j < events.length && events[q].x == events[j].x) {
						if (events[j].type == 0) {
							if (ans > t) {
								ans = t;
								last = p;
							}
							j++;
							continue;
						}
						if (t <= traps[events[j].n].f
								&& traps[events[j].n].f <= bound
								&& traps[events[j].n].p1 != p
								&& traps[events[j].n].p1.t > p.t) {
							traps[events[j].n].p1.u = true;
							traps[events[j].n].p1.prev = p;
						}
						j++;
					}
					q = mid;
					boolean end = false;
					while (q < j) {
						if (events[q].type > 0) {
							cut.add(traps[events[q].n]);
							if (traps[events[q].n].s <= t
									&& t < traps[events[q].n].f) {
								end = true;
							}
						}
						q++;
					}
					if (end) {
						break loop;
					}
					while (!cut.isEmpty() && cut.first().s < t) {
						cut.pollFirst();
					}
					if (!cut.isEmpty()) {
						bound = cut.first().s;
					} else {
						bound = Integer.MAX_VALUE / 2;
					}
				}
			}
			{// move diagonal left
				int bound = sbound;
				TreeSet<Trap> cut = new TreeSet<Trap>();
				int q = events.length - 1;
				while (q >= 0
						&& (events[q].x > p.x || events[q].x == p.x
								&& events[q].type > 0)) {
					if (events[q].type > 0) {
						cut.remove(traps[events[q].n]);
					} else if (events[q].type < 0) {
						cut.add(traps[events[q].n]);
					}
					q--;
				}
				int x = p.x;
				int t = p.t;
				while (!cut.isEmpty() && cut.first().s < t) {
					cut.pollFirst();
				}
				loop: while (q >= 0) {
					int dx = events[q].x - x;
					x += dx;
					t -= dx;
					if (t > bound) {
						break loop;
					}
					int j = q;
					while (j >= 0 && events[j].x == events[q].x
							&& events[j].type > 0) {
						cut.remove(traps[events[j].n]);
						j--;
					}
					bound = Integer.MAX_VALUE / 2;
					if (!cut.isEmpty()) {
						bound = Math.min(bound, cut.first().s);
					}
					for (int i = q; i > j; i--) {
						if (t <= traps[events[i].n].f
								&& traps[events[i].n].f <= bound
								&& traps[events[i].n].p1 != p
								&& traps[events[i].n].p1.t > p.t) {
							traps[events[i].n].p1.u = true;
							traps[events[i].n].p1.prev = p;
						}
					}
					int mid = j;
					while (j >= 0 && events[q].x == events[j].x) {
						if (events[j].type == 0) {
							if (ans > t) {
								ans = t;
								last = p;
							}
							j--;
							continue;
						}
						if (t <= traps[events[j].n].f
								&& traps[events[j].n].f <= bound
								&& traps[events[j].n].p2 != p
								&& traps[events[j].n].p2.t > p.t) {
							traps[events[j].n].p2.u = true;
							traps[events[j].n].p2.prev = p;
						}
						j--;
					}
					q = mid;
					boolean end = false;
					while (q > j) {
						if (events[q].type < 0) {
							cut.add(traps[events[q].n]);
							if (traps[events[q].n].s <= t
									&& t < traps[events[q].n].f) {
								end = true;
							}
						}
						q--;
					}
					if (end) {
						break loop;
					}
					while (!cut.isEmpty() && cut.first().s < t) {
						cut.pollFirst();
					}
					if (!cut.isEmpty()) {
						bound = cut.first().s;
					} else {
						bound = Integer.MAX_VALUE / 2;
					}
				}
			}
		}
		if (ans >= Integer.MAX_VALUE / 2) {
			moves.add(new Point(0, 0));
			out.println("Impossible");
			return;
		}
		out.println(ans);
		moves.add(new Point(door, ans));
		while (last != null) {
			moves.add(last);
			last = last.prev;
		}
		Collections.reverse(moves);
	}

	public ArrayList<Point> moves = new ArrayList<prince_aa.Point>();

	class Event implements Comparable<Event> {
		public Event(int x2, int i, int i2) {
			x = x2;
			type = i;
			n = i2;
		}

		int x;
		int type;
		int n;

		@Override
		public int compareTo(Event o) {
			if (x == o.x) {
				return type - o.type;
			}
			return x - o.x;
		}

		@Override
		public String toString() {
			return x + " " + type + " " + n;
		}
	}

	public static class Point implements Comparable<Point> {

		@Override
		public String toString() {
			return x + " " + t + " " + u + " (" + prev + ")\n";
		}

		public Point(int x, int t) {
			this.x = x;
			this.t = t;
			u = false;
		}

		int x, t;
		boolean u;
		Point prev;

		@Override
		public int compareTo(Point o) {
			if (t == o.t) {
				return x - o.x;
			}
			return t - o.t;
		}
	}

	class Trap implements Comparable<Trap> {

		@Override
		public String toString() {
			return n + "";
		}

		public Trap(int s, int d, int l, int r, int i) {
			myAssert(l < r, "l = " + l + "; r = " + r);
			this.s = s;
			this.f = s + d;
			this.l = l;
			this.r = r;
			this.n = i;
			p1 = new Point(l, f);
			p2 = new Point(r, f);
		}

		int s, f;
		int l, r;
		int n;
		Point p1, p2;

		@Override
		public boolean equals(Object o) {
			return (o instanceof Trap) && this.compareTo((Trap) o) == 0;
		}

		@Override
		public int compareTo(Trap o) {
			if (s == o.s) {
				return n - o.n;
			}
			return s - o.s;
		}
	}
}