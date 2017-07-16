import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class gen_prince {
	public static void main(String[] args) {
		new gen_prince().generate();
	}

	final int MAXN = 1000;
	final int MAXX = (int) 1e5;
	final int MAXT = (int) 1e6;
	final int MAXC = (int) 1e6;

	private void generate() {
		sample();
		thereIsNoEscape();
		empty();
		smallObject();
		narrowPassage();
		youCanTouchThis();
		trapception();
		twoSteps();
		followTheSmallPath();
		followTheImspossiblePath();
		followTheStrangePath();
		checked();
		chess();
		smallSteps();
		bigAnswer();
		toTheLeft();
		followThePath();
		touch();
		for (int i = 0; i < 3; i++) {
			random();
		}
		finish();
	}

	private void touch() {
		next();
		int n = MAXN / 2;
		out.println(2 * n + " " + (MAXN + 10));
		for (int i = 0; i < n; i++) {
			out.println((i + 1) + " 1 " + i + " " + (i + 1));
			out.println((i + 1) + " 1 " + (i + 2) + " " + (i + 3));
		}
	}

	private void twoSteps() {
		next();
		out.println("2 5");
		out.println("1 2 1 2");
		out.println("1 6 3 5");
	}

	private void followTheStrangePath() {
		next();
		rand.nextBoolean();
		int n = 200 / 2;
		int max = 300 / n;
		out.println(2 * n + " " + 10);
		int x = 0;
		int t = 1;
		for (int i = 0; i < n; i++) {
			int dx = -max + rand.nextInt(2 * max + 1);
			if (rand.nextInt(3) == 0) {
				dx = 0;
			}
			int dt = 1 + rand.nextInt(max);
			dt = Math.max(dt, Math.abs(dx));
			out.println(t + " " + dt + " " + -42 + " " + Math.min(x, x + dx));
			out.println(t + " " + dt + " " + Math.max(x, x + dx) + " " + 42);
			x += dx;
			t += dt;
		}
	}

	private void followTheImspossiblePath() {
		next();
		int n = 200 / 2;
		int max = 300 / n;
		out.println(2 * n + " " + 21);
		int x = 0;
		int t = 1;
		for (int i = 0; i < n; i++) {
			int dx = -max + rand.nextInt(2 * max + 1);
			if (rand.nextInt(3) == 0) {
				dx = 0;
			}
			int dt = 1 + rand.nextInt(max);
			out.println(t + " " + dt + " " + -42 + " " + Math.min(x, x + dx));
			out.println(t + " " + dt + " " + Math.max(x, x + dx) + " " + 42);
			x += dx;
			t += dt;
		}
	}

	private void smallSteps() {
		next();
		int n = MAXN;
		out.println(n + " " + (n + 10));
		int x = 1;
		int t = 1;
		for (int i = 0; i < n; i++) {
			out.println(t + " 1 " + x + " " + (x + 1));
			t += 2;
			x++;
		}
	}

	private void followTheSmallPath() {
		next();
		int n = 200 / 2;
		int max = 300 / n;
		out.println(2 * n + " " + 21);
		int x = 0;
		int t = 1;
		for (int i = 0; i < n; i++) {
			int dx = -max + rand.nextInt(2 * max + 1);
			if (rand.nextInt(3) == 0) {
				dx = 0;
			}
			int dt = 1 + rand.nextInt(max);
			dt = Math.max(dt, Math.abs(dx));
			out.println(t + " " + dt + " " + -42 + " " + Math.min(x, x + dx));
			out.println(t + " " + dt + " " + Math.max(x, x + dx) + " " + 42);
			x += dx;
			t += dt;
		}
	}

	private void chess() {
		next();
		int n = (int) Math.floor(Math.sqrt(2 * MAXN));
		n -= n % 2;
		out.println(n * n / 2 + " " + (n + 10));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n / 2; j++) {
				out.println((i + 1) + " 1 " + (2 * j + 1 + i % 2) + " "
						+ (2 * j + 2 + i % 2));
			}
		}
	}

	private void checked() {
		next();
		int n = (int) Math.floor(Math.sqrt(MAXN));
		out.println(n * n + " " + (2 * n + 10));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				out.println((2 * i + 1) + " 1 " + (2 * j + 1) + " "
						+ (2 * j + 2));
			}
		}
	}

	private void random() {
		next();
		int n = rand.nextInt(MAXN + 1);
		int x = 1 + rand.nextInt(5000);
		out.println(n + " " + x);
		for (int i = 0; i < n; i++) {
			int a = x / 2 + rand.nextInt(3 * x);
			int t = 1 + rand.nextInt(100);
			int l = -100 + rand.nextInt(x + 1);
			int r = l + 1 + rand.nextInt(100);
			while (l >= r) {
				l = -100 + rand.nextInt(x + 1);
				r = l + 1 + rand.nextInt(100);
			}
			long s = (0L + t) * (r - l);
			if (s < 1L * MAXC * MAXC / n / 10) {
				out.println(a + " " + t + " " + l + " " + r);
			} else {
				i--;
			}
		}
	}

	private void followThePath() {
		next();
		int n = MAXN / 2;
		int max = MAXT / n;
		out.println(2 * n + " " + MAXX);
		int x = 0;
		int t = 1;
		for (int i = 0; i < n; i++) {
			int dx = -max + rand.nextInt(2 * max + 1);
			if (rand.nextInt(3) == 0) {
				dx = 0;
			}
			int dt = rand.nextInt(max);
			dt = Math.max(dt, Math.abs(dx));
			out.println(t + " " + dt + " " + -MAXC + " " + Math.min(x, x + dx));
			out.println(t + " " + dt + " " + Math.max(x, x + dx) + " " + MAXC);
			x += dx;
			t += dt;
		}
	}

	private void bigAnswer() {
		next();
		out.println("2 " + MAXX);
		out.println("1 " + MAXT + " 0 " + MAXC);
		out.println(MAXT + " " + MAXT + " " + -MAXC + " " + MAXC);
	}

	private void toTheLeft() {
		next();
		int ratio = MAXT / MAXN;
		out.println(MAXN + " " + MAXX);
		for (int i = 0; i < MAXN; i++) {
			out.println((i * ratio + 1) + " " + MAXT + " " + (-i * ratio - 1)
					+ " " + MAXC);
		}
	}

	private void trapception() {
		next();
		out.println("3 5");
		out.println("1 5 1 6");
		out.println("2 3 2 5");
		out.println("3 1 3 4");
	}

	private void thereIsNoEscape() {
		next();
		out.println("1 2");
		out.println("1 1 -2 2");
	}

	private void youCanTouchThis() {
		next();
		out.println("1 2");
		out.println("1 1 0 1");
	}

	private void narrowPassage() {
		next();
		out.println("2 2");
		out.println("1 1 -2 0");
		out.println("1 1 0 2");
	}

	private void smallObject() {
		next();
		out.println("1 3");
		out.println("1 1 1 2");
	}

	private void empty() {
		next();
		out.println("0 10");
	}

	private void sample() {
		next();
		out.println("3 2");
		out.println("1 1 -1 2");
		out.println("3 2 1 3");
		out.println("6 1 0 2");
	}

	PrintWriter out;
	int test;
	String sTest;
	Random rand = new Random(987654321L);

	void next() {
		finish();
		test++;
		sTest = (test / 10) + "" + (test % 10);
		try {
			out = new PrintWriter("../tests/" + sTest);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void finish() {
		if (out != null) {
			out.close();
		}
	}
}
