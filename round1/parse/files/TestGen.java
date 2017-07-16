import java.io.*;
import java.util.*;

public class TestGen {

	PrintWriter out;
	Random rnd;
	int tNumber;

	class Test {
		void printTest() throws IOException {
			if (tNumber < 10)
				out = new PrintWriter("../tests/0" + tNumber);
			else
				out = new PrintWriter("../tests/" + tNumber);
			tNumber++;

			if ((tNumber >= 4) && (tNumber != 6) && !keepOrder)
				Collections.shuffle(s, rnd);
			HashSet<String> was = new HashSet<String>();
			for (int i = 0; i < Math.min(s.size(), 250); i++) {
				was.add(s.get(i));
			}
			out.println(was.size());
			was = new HashSet<String>();
			for (int i = 0; i < Math.min(s.size(), 250); i++) {
				if (!was.contains(s.get(i)))
					out.println(s.get(i));
				was.add(s.get(i));
			}

			out.close();
		}

		ArrayList<String> s;
        boolean keepOrder = false;

		void gen() throws IOException {
			s = new ArrayList<String>();
			int n = rnd.nextInt(6) + 1;
			for (int i = 0; i < n; i++) {
				int l = rnd.nextInt(5) + 1;
				String ss = "";
				for (int j = 0; j < l; j++) {
					ss = ss + ((char) (rnd.nextInt(2) + 'a'));
				}
				s.add(ss);
			}
			printTest();
		}

		void genSimpleTest(int l) throws IOException {
			s = new ArrayList<String>();
			char[] c = new char[l];
			for (int i = 0; i < l; i++) {
				c[i] = (char) (rnd.nextInt(26) + 'a');
			}
			String ss = new String(c);
			for (int i = 0; i < l; i++) {
				if (rnd.nextBoolean())
					continue;
				int t = rnd.nextInt(l);
				for (int j = i; j <= Math.min(i + t, ss.length()); j++) {
					if ((j > i) && (j - i < 500))
						s.add(ss.substring(i, j));
				}
			}
			printTest();
		}

		void genATest(double prob) throws IOException {
			s = new ArrayList<String>();
			String ss = "";
			for (int i = 0; i < 500; i++) {
				ss = ss + "a";
				if (rnd.nextDouble() < prob) {
					s.add(ss);
				}
			}
			printTest();
		}

		void genHorrorTest() throws IOException {
			s = new ArrayList<String>();
			String ss = "";
			int l = rnd.nextInt(30) + 70;
			for (int i = 0; i < l; i++) {
				ss = ss + (char) (rnd.nextInt(26) + 'a');
			}
			int a = rnd.nextInt(l / 4) + 1;
			int b = a + rnd.nextInt(l / 4) + 1;
			int c = b + rnd.nextInt(l / 4) + 1;
			s.add(ss.substring(0, a));
			s.add(ss.substring(0, b));
			s.add(ss.substring(0, c));
			s.add(ss.substring(c, ss.length()));
			s.add(ss.substring(a, ss.length()));
			printTest();
		}

		void genAntiNickolayTest(int ll) throws IOException {
			s = new ArrayList<String>();
			for (int mask = 0; mask < (1 << ll); mask++) {
				String ss = "";
				int tt = mask;
				for (int j = 0; j < ll; j++) {
					ss += (char) (tt % 2 + 'a');
					tt /= 2;
				}
				s.add(ss);
			}
			for (int i = 0; i < 50; i++) {
				int x = rnd.nextInt(180) + 300;
				char[] c = new char[x];
				for (int j = 0; j < x; j++) {
					c[j] = (char) (rnd.nextInt(2) + 'a');
				}
				String ss = new String(c);
				for (int k = 0; k < 2; k++) {
					ss = ss + (char) (rnd.nextInt(2) + 'a');
					s.add(ss);
				}
			}
			printTest();
		}

		void genPerfomanceTest() throws IOException {
			s = new ArrayList<String>();
			for (int i = 0; i < 250; i++) {
				int l = rnd.nextInt(100) + 400;
				char[] c = new char[l];
				for (int j = 0; j < l; j++) {
					c[j] = (char) (rnd.nextInt(26) + 'a');
				}
				s.add(new String(c));
			}
			printTest();
		}

		void genSample() throws IOException {
			s = new ArrayList<String>();
			s.add("ab");
			s.add("cd");
			s.add("abcd");
			printTest();
			s = new ArrayList<String>();
			s.add("aba");
			s.add("ab");
			s.add("ac");
			printTest();
		}

		void genAntiAdrewztaTest(int l) throws IOException {
			s = new ArrayList<String>();
			for (int i = 0; i < 249; i++) {
				char[] c = new char[l];
				for (int j = 0; j < l; j++) {
					c[j] = (char) (rnd.nextInt(26) + 'a');
				}
				s.add(new String(c));
			}
			String t = s.get(0) + (char) (rnd.nextInt(26) + 'a');
			s.add(t);
			printTest();
		}
        
        void genMe(String[] z) throws IOException {
			s = new ArrayList<String>();
            for (String x : z) {
                s.add(x);
            }
            keepOrder = true;
            printTest();
            keepOrder = false;
        }
	}

	public void solve() throws IOException {
		tNumber = 1;
		rnd = new Random(31);
		Test t = new Test();
		t.genSample();
		for (double p = 0.8; p <= 1; p += 0.1) {
			t.genATest(p);
		}
		for (int l = 1; l < 11; l++) {
			t.genAntiNickolayTest(l);
		}
		for (int l = 10; l <= 250; l *= 5) {
			t.genAntiAdrewztaTest(l);
		}
        t.genMe(new String[]{"bbbbb", "bb", "babbbaaa", "bbba", "a", "baababbaab"});
		for (int i = 0; i < 5; i++) {
			t.gen();
		}
		for (int i = 0; i < 3; i++) {
			t.genHorrorTest();
		}
		for (int i = 10; i < 1000; i += 200) {
			t.genSimpleTest(i);
		}
		for (int i = 0; i < 4; i++) {
			t.genPerfomanceTest();
		}
	}

	public void run() {
		try {

			solve();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TestGen().run();
	}
}
