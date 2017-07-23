import java.io.*;
import java.util.*;

public class shuffle_pk {

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

	Node[] split(Node v, int count) {
		if (v == null)
			return new Node[2];
		if (count == 0) {
			Node[] res = new Node[2];
			res[1] = v;
			return res;
		}
		if (v.L != null) {
			if (v.L.size >= count) {
				Node[] res = split(v.L, count);
				v.L = res[1];
				res[1] = v;
				refresh(v);
				return res;
			} else {
				count -= v.L.size;
			}
		}
		Node[] res = split(v.R, count - 1);
		v.R = res[0];
		res[0] = v;
		refresh(v);
		return res;
	}

	Node merge(Node l, Node r) {
		if (l == null)
			return r;
		if (r == null)
			return l;
		if (l.y > r.y) {
			l.R = merge(l.R, r);
			refresh(l);
			return l;
		} else {
			r.L = merge(l, r.L);
			refresh(r);
			return r;
		}
	}

	void refresh(Node v) {
		if (v == null)
			return;
		v.size = 1;
		if (v.L != null) {
			v.size += v.L.size;
			v.L.par = v;
		}
		if (v.R != null) {
			v.size += v.R.size;
			v.R.par = v;
		}
	}

	int findMyPlace(Node v) {
		if (v == null)
			return -1;
		int ans = 0;
		if (v.L != null)
			ans += v.L.size;
		while (v.par != null) {
			if (v.par.R == v) {
				ans++;
				if (v.par.L != null) {
					ans += v.par.L.size;
				}
			}
			v = v.par;
		}
		return ans;
	}

	void dfs(Node v) {
		/*if (v == null)
			return;
		dfs(v.L);
		System.err.print(v.color + " ");
		dfs(v.R);
		if (v.par == null)
			System.err.println();*/
	}

	Node find(Node v, int number) {
		if (v == null)
			return null;
		if (v.L != null) {
			if (v.L.size > number)
				return find(v.L, number);
			else
				number -= v.L.size;
		}
		if (number == 0)
			return v;
		number--;
		return find(v.R, number);
	}

	class Node {
		int color, size, y;
		Node L, R, par;

		Node(int color) {
			this.color = color;
			size = 1;
			y = rnd.nextInt();
		}
	}

	class Color implements Comparable<Color> {
		int color, count;
		Node first, last;

		@Override
		public int compareTo(Color arg0) {
			return arg0.count - count;
		}

		Color(int color) {
			count = 0;
			this.color = color;
		}
	}

	class Action {
		int l, r;

		public Action(int l, int r) {
			super();
			this.l = l;
			this.r = r;
		}

		@Override
		public String toString() {
			return l + " " + r;
		}
	}

	class Conflicts {
		int[] a, b;
		int max, first, second, sum;

		public Conflicts(int[] count) {
			a = count;
			int s = 0;
			for (int i : a) {
				s = Math.max(i, s);
				sum += i;
			}
			s++;
			b = new int[s];
			for (int i : a)
				b[i]++;
			max = s - 1;
			second = a.length - 1;
			first = a.length - 1;
			refresh();
		}

		void dec(int x) {
			b[a[x]]--;
			a[x]--;
			sum--;
			b[a[x]]++;
			while ((max > 0) && (b[max] == 0))
				max--;
			refresh();
		}

		void refresh() {
			while ((second > 0) && (a[second] == 0))
				second--;
			if (first >= second)
				first = second - 1;
			while ((first >= 0) && (a[first] == 0))
				first--;
		}

		int getPositionOfMax() {
			for (int i = 0; i < a.length; i++) {
				if (a[i] == max)
					return i;
			}
			return -1;
		}

		boolean isEmpty() {
			return max == 0;
		}

		int getFirst() {
			return first;
		}

		int getSecond() {
			return second;
		}

		boolean check() {
			return max < sum / 2 + sum % 2;
		}
	}

	private void moveToFront(Node root, int x, int y, ArrayList<Action> ans) {
		ans.add(new Action(x + 1, y + 1));
		Node[] res = split(root, y + 1);
		Node[] res2 = split(res[0], x);
		res2[0] = merge(res2[0], res[1]);
		root = merge(res2[0], res2[1]);
	}

	private void moveWithControl(int x, int y, Color[] c, Node root,
			ArrayList<Action> ans) {
		Node u = c[x].last;
		Node v = c[y].first;
		if (c[x].last != c[x].first)
			c[x].last = find(root, findMyPlace(c[x].last) - 1);
		if (c[y].last != c[y].first)
			c[y].first = find(root, findMyPlace(c[y].first) + 1);
		x = findMyPlace(u);
		y = findMyPlace(v);
		moveToFront(root, x, y, ans);
	}

	private void anotherMoveWithControl(int x, int y, Color[] c, Node root,
			ArrayList<Action> ans) {
		Node u = c[x].first;
		Node v = c[y].last;
		if (c[y].last != c[y].first)
			c[y].last = find(root, findMyPlace(c[y].last) - 1);
		if (c[x].last != c[x].first)
			c[x].first = find(root, findMyPlace(c[x].first) + 1);
		u = c[x].first;
		v = c[y].last;
		x = findMyPlace(u);
		y = findMyPlace(v);
		moveToFront(root, x, y, ans);
	}

	Node prev(Node v) {
		if (v == null)
			return null;
		Node ans = null;
		if (v.L != null) {
			ans = v.L;
			while (ans.R != null)
				ans = ans.R;
		}
		if (ans == null) {
			while (v.par != null) {
				if (v.par.R == v)
					return v.par;
				v = v.par;
			}
		}
		return ans;
	}

	Node next(Node v) {
		if (v == null)
			return null;
		Node ans = null;
		if (v.R != null) {
			ans = v.R;
			while (ans.L != null)
				ans = ans.L;
		}
		if (ans == null) {
			while (v.par != null) {
				if (v.par.L == v)
					return v.par;
				v = v.par;
			}
		}
		return ans;
	}

	Random rnd;
	int bigSum;
	
	
	public void solve() throws IOException {
		rnd = new Random(31);
		int n = nextInt();
		assert(n > 0);
		bigSum += n;
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = nextInt() - 1;
			if (i == 0) {
				assert(a[i] == 0);
			} else {
				assert((a[i] >= a[i - 1]) && (a[i] <= a[i - 1] + 1));
			}
		}
		int k = a[n - 1] + 1;
		Node root = null;
		Color[] c = new Color[k];
		int[] count = new int[k];
		int MAX = 0;
		for (int i = 0; i < n; i++) {
			if ((i > 0) && (a[i] == a[i - 1])) {
				count[a[i]]++;
				if (count[a[i]] > MAX)
					MAX = count[a[i]];
				c[a[i]].last = new Node(a[i]);
				root = merge(root, c[a[i]].last);
			} else {
				c[a[i]] = new Color(a[i]);
				c[a[i]].first = new Node(a[i]);
				c[a[i]].last = c[a[i]].first;
				root = merge(root, c[a[i]].last);
			}
		}
		if ((MAX + 1) * 2 - 1 > n) {
			out.println(-1);
			return;
		}
		Conflicts q = new Conflicts(count);
		ArrayList<Action> ans = new ArrayList<shuffle_pk.Action>();
		dfs(root);
		while (q.check()) {
			int x = q.getFirst();
			int y = q.getSecond();
			q.dec(x);
			q.dec(y);
			moveWithControl(x, y, c, root, ans);
			dfs(root);

		}
		int max = q.getPositionOfMax();
		for (int j = max + 1; j < k; j++) {
			while ((count[j] > 0) && (count[max] > 0)) {
				moveWithControl(max, j, c, root, ans);
				count[j]--;
				count[max]--;
				dfs(root);

			}
		}
		for (int j = 0; j < max; j++) {
			while ((count[j] > 0) && (count[max] > 0)) {
				anotherMoveWithControl(j, max, c, root, ans);
				count[j]--;
				count[max]--;
				dfs(root);
			}
		}

		if (count[max] > 0) {
			Node end = root;
			while (end.R != null)
				end = end.R;
/*			if ((end.color == max) && (end != c[max].last)) {
				Node v = prev(c[max].first);
				int x = findMyPlace(v);
				c[max].last = prev(c[max].last);
				int y = findMyPlace(c[max].last);
				moveToFront(root, x, y, ans);
				dfs(root);
				count[max]--;
			}*/
			Node current = next(c[max].last);
			if (next(current) == null)
				current = c[max].last;
			while ((current != null) && (count[max] > 0)) {

				while ((next(current) != null)
						&& (((next(current).color == max)) || (current.color == max)))
					current = next(current);
				Node tmp = next(current);
				if (tmp == null)
					break;
				int x = findMyPlace(c[max].last);
				int y = findMyPlace(current);
				c[max].last = prev(c[max].last);
				count[max]--;
				moveToFront(root, x, y, ans);
				dfs(root);
				if (current.color == max)
					break;
				current = tmp;
			}
			if ((count[max] > 0) && (end.color != max)) {
				int x = findMyPlace(c[max].first);
				int y = findMyPlace(prev(c[max].last));
				c[max].last = prev(c[max].last);
				count[max]--;
				moveToFront(root, x, y, ans);
				dfs(root);
			}
			current = c[max].first;
			while ((current != null) && (count[max] > 0)) {
				while ((prev(current) != null)
						&& ((prev(current).color == max) || (current.color == max)))
					current = prev(current);
				Node tmp = prev(current);
				int y = findMyPlace(c[max].first);
				int x = findMyPlace(current);
				c[max].first = next(c[max].first);
				count[max]--;
				moveToFront(root, x, y, ans);
				dfs(root);
				current = tmp;
			}

		}
		if (count[max] > 0) {
			out.println(-1);
		} else {
			out.println(ans.size());
			for (Action x : ans)
				out.println(x.toString());
		}
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

//			br = new BufferedReader(new FileReader("shuffle.in"));
//			out = new PrintWriter("shuffle.out");
			
			bigSum = 0;
			int n = nextInt();
			for (int i = 0; i < n; i++)
				solve();
			assert(bigSum <= 200000);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new shuffle_pk().run();
	}
}
