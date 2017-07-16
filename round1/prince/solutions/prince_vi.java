import java.io.*;
import java.util.*;

public class prince_vi {


	private StringTokenizer st;
	private BufferedReader in;
	private PrintWriter out;

	static class Segment implements Comparable<Segment> {
		final int l, r;
		final boolean fixed;
		
		public Segment(int l, int r, boolean fixed) {
			super();
			this.l = l;
			this.r = r;
			this.fixed = fixed;
		}

		@Override
		public int compareTo(Segment o) {
			if (o == this) {
				return 0;
			}
			if (r <= o.l) {
				return -1;
			}
			if (o.r <= l) {
				return 1;
			}
			throw new AssertionError();
		}
	}
	
	static class Event implements Comparable<Event> {
		Segment s;
		int t;
		boolean add;
		
		public Event(Segment s, int t, boolean add) {
			super();
			this.s = s;
			this.t = t;
			this.add = add;
		}

		@Override
		public int compareTo(Event o) {
			return t - o.t;
		}
	}
	
	List<Segment> grow(List<Segment> segs0, Set<Segment> fixed, int dt) {
		List<Segment> segs = merge(fixed);
		segs.addAll(segs0);
		Collections.sort(segs);
		List<Segment> result = new ArrayList<Segment>();
		int last = Integer.MIN_VALUE;
		for (int i = 0; i < segs.size(); ) {
			Segment s = segs.get(i);
			if (s.fixed) {
				i++;
				last = s.r;
				continue;
			}
			s = new Segment(Math.max(s.l - dt, last), s.r + dt, false);
			int j = i + 1;
			while (j < segs.size()) {
				Segment s1 = segs.get(j);
				if (s1.fixed) {
					s = new Segment(s.l, Math.min(s.r, s1.l), false);
					break;
				} else if (s.r >= s1.l - dt) {
					s = new Segment(s.l, s1.r + dt, false);
					j++;
				} else {
					break;
				}
			}
			result.add(s);
			i = j;
		}
		return result;
	}

	private List<Segment> merge(Set<Segment> fixed) {
		class E1 implements Comparable<E1> {
			int x, add;
			
			E1(int x, int add) {
				this.x = x;
				this.add = add;
			}

			@Override
			public int compareTo(E1 o) {
				if (x == o.x) {
					return add - o.add;
				}
				return x - o.x;
			}
		}
		E1[] es = new E1[2 * fixed.size()];
		int it = 0;
		for (Segment s : fixed) {
			es[it++] = new E1(s.l, +1);
			es[it++] = new E1(s.r, -1);
		}
		Arrays.sort(es);
		List<Segment> res = new ArrayList<Segment>();
		int start = Integer.MIN_VALUE;
		int count = 0;
		for (E1 e : es) {
			if (e.add == 1 && count == 0) {
				start = e.x;
			}
			count += e.add;
			if (count == 0) {
				res.add(new Segment(start, e.x, true));
			}
		}
		return res;
	}

	public void solve() throws IOException {
		int n = nextInt();
		int x = nextInt();
		List<Segment> segments = new ArrayList<Segment>();
		segments.add(new Segment(0, 0, false));
		Event[] events = new Event[2 * n];
		for (int i = 0; i < n; ++i) {
			int t1 = nextInt();
			int t2 = t1 + nextInt();
			Segment s = new Segment(nextInt(), nextInt(), true);
			events[2 * i + 0] = new Event(s, t1, true);
			events[2 * i + 1] = new Event(s, t2, false);
		}
		Arrays.sort(events);
		int time = 0;
		Set<Segment> fixed = new HashSet<Segment>();
		for (Event event : events) {
			int dt = event.t - time;
			List<Segment> newSegments = grow(segments, fixed, dt);
			if (!newSegments.isEmpty() && newSegments.get(newSegments.size() - 1).r >= x) {
				break;
			}
			segments = newSegments;
			time = event.t;
			Segment es = event.s;
			if (event.add) {
				for (ListIterator<Segment> it = segments.listIterator(); it.hasNext(); ) {
					Segment s = it.next();
					if (s.fixed) {
						throw new AssertionError();
					}
					if (es.l < s.l && s.r < es.r) {
						it.remove();
					} else if (s.l <= es.l && es.r <= s.r) {
						it.remove();
						it.add(new Segment(s.l, es.l, false));
						it.add(new Segment(es.r, s.r, false));
					} else {
						Segment ns;
						if (s.l <= es.l) {
							ns = new Segment(s.l, Math.min(s.r, es.l), false);
						} else {
							ns = new Segment(Math.max(s.l, es.r), s.r, false);
						}
						it.remove();
						it.add(ns);
					}
				}
				fixed.add(es);
			} else {
				fixed.remove(es);
			}
		}
		if (segments.isEmpty()) {
			out.println("Impossible");
			return;
		} else {
			Segment s = segments.get(segments.size() - 1);
			out.println(time + (x - s.r));
		}
	}

	public void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		eat("");
		
//		Random rnd = new Random(42);
//		while (rnd != null) {
//			int n = 10;
//			int x = rnd.nextInt(10);
//			StringBuilder sb = new StringBuilder();
//			sb.append(n + " " + x + "\n");
//			for (int i = 0; i < n; ++i) {
//				int l, r;
//				do {
//					l = rnd.nextInt(21) - 10;
//					r = rnd.nextInt(21) - 10;
//				} while (l >= r);
//				sb.append(rnd.nextInt(10) + " " + rnd.nextInt(10) + " " + l + " " + r + "\n");
//			}
//			System.err.println(sb);
//			eat(sb.toString());
//			solve();
//		}
		solve();
		
		out.close();
	}
	
	void eat(String s) {
		st = new StringTokenizer(s);
	}
	
	String next() throws IOException {
		while (!st.hasMoreTokens()) {
			String line = in.readLine();
			if (line == null) {
				return null;
			}
			eat(line);
		}
		return st.nextToken();
	}
	
	int nextInt() throws IOException {
		return Integer.parseInt(next());
	}
	
	long nextLong() throws IOException {
		return Long.parseLong(next());
	}
	
	double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}
	
	static boolean failed = false;
	
	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		new prince_vi().run();
	}
	
}
