import java.io.*;
import java.util.*;

public class parse_pk_stupid {

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

	final long S = 23917;
	
	boolean check(String pans) {
		String th = "";
		int tl = -1;
		for (int i = 0; i < pans.length(); i++) {
			th = th + pans.charAt(i);
			if (voc.contains(th))
				tl = i;
			if (!prefVoc.contains(th) || (i == pans.length() - 1)) {
				if (i - tl == th.length())
					break;
				th = "";
				i = tl;
			}
		}
		if ((tl == pans.length() - 1) || pans.equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	void gen(String ss, int t) {
		if (t > 5) {
			return;
		}
		if (check(ss)) {
			if ((ss.length() < ans.length()) || (ans.equals("")))
				ans = ss;
			return;
		}
		
		for (int i = 0; i < n; i++) {
			gen(ss + s[i], t + 1);
			
		}
	}
	
	int n;
	String[] s;
	HashSet<String> voc, prefVoc; 
	String ans = "";
	
	public void solve() throws IOException {
		n = nextInt();
		s = new String[n];
		for (int i = 0; i < n; i++) {
			s[i] = nextToken();
		}
		
		voc = new HashSet<String>();
		prefVoc = new HashSet<String>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < s[i].length(); j++) {
				prefVoc.add(s[i].substring(0, j + 1));
			}
			voc.add(s[i]);
		}
		
		gen("", 0);
		
		if (ans.equals(""))
			out.println("Good vocabulary!");
		else
			out.println(ans);
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

//			br = new BufferedReader(new FileReader("parse.in"));
//			out = new PrintWriter("parse.out");

			solve();

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new parse_pk_stupid().run();
	}
}
