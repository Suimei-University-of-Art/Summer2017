import java.io.*;
import java.util.*;

public class WheelTestGen {

	final static int MAX_N = 1000;
	final static int[] SAMPLE = {3, 4};

	public static void main(String[] args) throws Exception {
		new WheelTestGen().generate();
	}

	PrintWriter out;
	int countTests = 0;

	void generate() {
		genSample();
		generateTests();
	}

	void genSample() {
		printTest(SAMPLE);
	}

	void generateTests() {
		ArrayList<Integer> interesting = new ArrayList<>();
		for (int i = 3; i <= 160; i++) {
			interesting.add(i);
		}
		for (int i = 500; i >= 486; i--) {
			interesting.add(i);
		}
		Random rnd = new Random(123);
		for (int i = 0; i < 20; i++) {
			interesting.add(100 + rnd.nextInt(400));
		}
		int[] array = new int[interesting.size()];
		for (int i = 0; i < interesting.size(); i++) {
			array[i] = interesting.get(i);
			int xx = rnd.nextInt(i + 1);
			int tmp = array[xx];
			array[xx] = array[i];
			array[i] = tmp;
		}
		for (int l = 0; l < array.length; l++) {
			int sum = 0;
			int r = l;
			while (sum <= MAX_N && r < array.length) {
				sum += array[r++];
			}
			int[] curTask = new int[r - l];
			for (int i = 0; i < curTask.length; i++) {
				curTask[i] = array[l + i];
			}
			printTest(curTask);
			l = r - 1;
		}
	}

	void printTest(int[] arr) {
		try {
			countTests++;
			String fileName = "../tests/";
			fileName += String.format("%02d", countTests);
			out = new PrintWriter(fileName);

			out.println(arr.length);
			for (int i = 0; i < arr.length; i++) {
				out.println(arr[i]);
			}

			System.out.println("Done printing test #" + countTests);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
