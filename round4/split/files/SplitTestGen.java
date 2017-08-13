import java.io.*;

public class SplitTestGen {

	final static int MAX_N = 23;

	public static void main(String[] args) throws Exception {
		new SplitTestGen().generate();
	}

	PrintWriter out;
	int countTests = 0;

	void generate() {
		generateTests();
	}

	void generateTests() {
		for (int i = 1; i <= MAX_N; i++) {
			printTest(i);
		}
	}

	void printTest(int n) {
		try {
			countTests++;
			String fileName = "../tests/";
			fileName += String.format("%02d", countTests);
			out = new PrintWriter(fileName);

			out.println(n);

			System.out.println("Done printing test #" + countTests);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
