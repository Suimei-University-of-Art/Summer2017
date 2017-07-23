import java.io.*;
import java.util.*;

public class gen_siege implements Runnable {
    public static void main(String[] args) {
        if (args.length < 1) {
            for (I = 1; (new File(getName(I) + ".t")).exists(); I++)
                ;
        } else {
            if (args[0].equals("stress")) {
                STRESS_MODE = true;
            } else {
                I = Integer.parseInt(args[0]);
            }
        }
        new Thread(new gen_siege()).start();
    }

    PrintWriter out;

    Random rand = new Random(6439586L);

    static int I;

    static boolean STRESS_MODE;

    static String getName(int i) {
        return ((i < 10) ? "0" : "") + i;
    }

    void open() {
        try {
            if (STRESS_MODE) {
                out = new PrintWriter(System.out);
            } else {
                System.out.println("Generating test " + I);
                out = new PrintWriter("../tests/" + getName(I));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    void close() {
        out.close();
        I++;
    }

    final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

    String randString(int len, String alpha) {
        StringBuilder ans = new StringBuilder();
        int k = alpha.length();
        for (int i = 0; i < len; i++) {
            ans.append(alpha.charAt(rand.nextInt(k)));
        }
        return ans.toString();
    }

    long rand(long l, long r) {
        return l + (rand.nextLong() >>> 1) % (r - l + 1);
    }

    int rand(int l, int r) {
        return l + rand.nextInt(r - l + 1);
    }


    void myAssert(boolean e) {
        if (!e) {
            throw new Error("Assertion failed");
        }
    }

    
    void genRandom(int A, int B, int n, int MIN_A_V, int MAX_A_V, int MIN_B_V, int MAX_B_V) {
    	open();
		out.println(A + " " + B + " " + n);
		for (int i = 0; i < n; ++i) {
			out.println(rand(MIN_A_V, MAX_A_V) + " " + rand(MIN_B_V, MAX_B_V));
		}
    	close();
    }

    void genTest01() {
    	open();
    	out.print("1 1 2\r\n1 1\r\n1 2\r\n");
    	close();
    }
    
    void genTest02() {
    	open();
    	out.print("3 4 3\r\n1 2\r\n2 2\r\n1 3\r\n");
    	close();
    }
    
    void genTest03() {
    	open();
    	out.print("5 6 7\r\n1 1\r\n1 1\r\n1 1\r\n1 1\r\n1 2\r\n2 2\r\n2 3\r\n");
    	close();
    }
    
    void genTest04() {
    	open();
    	out.print("18 15 4\r\n5 5\r\n1 5\r\n12 9\r\n6 5\r\n");
    	close();
    }
    
    void genTest05() {
    	open();
    	out.print("17 14 5\r\n20 8\r\n6 11\r\n13 11\r\n4 19\r\n11 1\r\n");
    	close();
    }
     
    void gen2Power(int A, int B, int n) {
    	open();
    	out.println(A + " " + B + " " + n);
    	--A; --B;
    	for (int i = 0; i < n; ++i) {
    		out.println((1 << rand(0, Integer.bitCount(Integer.highestOneBit(A) - 1))) + " " + (1 << rand(0, Integer.bitCount(Integer.highestOneBit(B) - 1))));
    	}
    	close();
    }
    
    void genGrid(int A, int B, int factor, int LA, int RA, int LB, int RB) {
    	open();
    	out.println(A + " " + B + " " + (RA - LA + 1) * (RB - LB + 1));
    	for (int i = LA; i <= RA; ++i) {
    		for (int j = LB; j <= RB; ++j) {
    			out.println(factor * i + " " + factor * j);
    		}
    	}
    	close();
    }
    
	final static int MAX_V = 100000;
	final static int MAX_N = 1000;    
    
    public void solve() {
    	genTest01();
    	genTest02();
    	genTest03();
    	genTest04();
    	genTest05();
    	
    	genRandom(3, 0, MAX_N, 1, 1, 1, 1);
    	genRandom(0, 3, MAX_N, 1, 1, 1, 1);
    	genRandom(10, 10, MAX_N, 1, 5, 6, 10);
    	
    	for (int i = 0; i < 5; ++i) {
    		genRandom(rand(0, 20), rand(0, 20), rand(1, 5), 1, 20, 1, 20);
    	}
    	genRandom(MAX_V, MAX_V, MAX_N, 1, MAX_V, 1, MAX_V);
    	gen2Power(MAX_V, MAX_V, MAX_N);
    	genRandom(MAX_V, MAX_V, MAX_N, 1, 300, 1, 300);
    	genRandom(MAX_V, MAX_V, MAX_N, 1, 200, 1, 200);    	
    	
    	for (int i = 0; i < 5; ++i) {
    		genRandom(rand(0, 1000), rand(0, 1000), rand(1, 20), i * 20 + 1, 1000, i * 20 + 1, 1000);
    	}
    	for (int i = 0; i < 5; ++i) {
    		genRandom(rand(0, MAX_V), rand(0, MAX_V), rand(1, MAX_N), i * 2000 + 1, MAX_V, i * 2000 + 1, MAX_V);
    	}
    	
    	genRandom(MAX_V, MAX_V, MAX_N, 1, MAX_V / 2, MAX_V / 2, MAX_V);
    	genRandom(MAX_V, MAX_V, MAX_N, 1, 10, MAX_V - 10, MAX_V);
    	genRandom(MAX_V, MAX_V, MAX_N, 1, 100, 100, MAX_V);
    	genRandom(MAX_V, 0, MAX_N, 1, 1, 1, 1);
    	genRandom(0, MAX_V, MAX_N, 1, 1, 1, 1);
    }
    
    
    public void stress() {
    	Random rand = new Random(System.currentTimeMillis());
    	final int MAX_V = 20;
    	final int MAX_N = 10;        
    	int A = rand.nextInt(MAX_V + 1);
		int B = rand.nextInt(MAX_V + 1);
		int n = rand.nextInt(MAX_N + 1);
		System.out.println(A + " " + B + " " + n);
		for (int i = 0; i < n; ++i) {
			System.out.println((1 + rand.nextInt(MAX_V)) + " " + (1 + rand.nextInt(MAX_V)));
		}
    }
    
    public void run() {
        try {
            if (STRESS_MODE) {
                stress();
            } else {
                solve();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}