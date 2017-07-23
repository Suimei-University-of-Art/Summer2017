import java.io.*;
import java.util.*;
/*
 * O(n*2^n) solution.
 */
public class siege_ab_exp {
    public static void main(String[] args) {
        new Object() {

            class Person {
                int a, b, num;

                Person(int a, int b, int num) {
                    this.a = a;
                    this.b = b;
                    this.num = num;
                }

                @Override
                public boolean equals(Object obj) {
                    return obj instanceof Person && ((Person)obj).a == a && ((Person)obj).b == b;
                }
            }            
            
            final int MAX = 100000;
            final int INF = Integer.MAX_VALUE / 2;

            void getAns(final int A, final int B, Person[] persons) {
                Arrays.sort(persons, new Comparator<Person>() {
                    @Override
                    public int compare(Person o1, Person o2) {
                        return o1.a < o2.a ? -1 : o1.a > o2.a ? 1 : o1.b < o2.b ? -1 : o1.b > o2.b ? 1 : 0;
                    }
                });
                final Person[] pa = persons;
                new Object() {
                    Person[] st = new Person[pa.length];
                    int top = 0;
                    
                    int ans = 0;
                    Person[] ansl = new Person[0];
                    
                    Comparator<Person> cmpB = new Comparator<Person>() {
                        @Override
                        public int compare(Person o1, Person o2) {
                            return o1.b < o2.b ? -1 : o1.b > o2.b ? 1 : 0;
                        }
                    };
                    
                    int countSurvivors() {
                        Person[] tmp = Arrays.copyOf(st, top);
                        Arrays.sort(tmp, cmpB);
                        int rest = B;
                        for (int i = 0; i < tmp.length; ++i) {
                            if (tmp[i].b <= rest) {
                                rest -= tmp[i].b;
                            } else {
                                return tmp.length - i;
                            }
                        }
                        return 0;
                    }
                    
                    void bt(int A, int p) {
                        if (p >= pa.length) {
                            int tans = countSurvivors();
                            if (tans > ans) {
                                ans = tans;
                                ansl = Arrays.copyOf(st, top);
                            }
                        } else {
                            bt(A, p + 1);
                            Person current = pa[p];
                            if (A >= current.a) {
                                st[top++] = current;
                                bt(A - current.a, p + 1);
                                --top;
                            }
                        }
                    }
                    
                    void solve() {
                        bt(A, 0);
                        out.println(ans);
                        out.print(ansl.length);
                        for (Person person : ansl) {
                            out.print(" " + (person.num + 1));
                        }
                        out.println();
                    }
                }.solve();
            }

            private void solve() throws IOException {
                int A = in.loadLine().nextInt();
                int B = in.skipSpace().nextInt();
                int n = in.skipSpace().nextInt();
                Person[] p = new Person[n];
                for (int i = 0; i < p.length; ++i) {
                    p[i] = new Person(in.loadLine().nextInt(), in.skipSpace().nextInt(), i);
                }
                assert constraintsOk(A, B, p);
                getAns(A, B, p);
            }

            boolean constraintsOk(int A, int B, Person[] p) {
                if (A < 0 || MAX_VAL < A) return false;
                if (B < 0 || MAX_VAL < B) return false;
                if (MAX_N < p.length) return false;
                for (Person person : p) {
                    if (person.a < 1 || MAX_VAL < person.a) return false;
                    if (person.b < 1 || MAX_VAL < person.b) return false;
                }
                return true;
            }

            final int MAX_N = 1000;
            final int MAX_VAL = 100000;


            StrictScanner in;
            PrintWriter out;

            public void run() {
                try {
                    Locale.setDefault(Locale.US);
                    in = new StrictScanner(new InputStreamReader(System.in));
                    out = new PrintWriter(System.out);
                    solve();
                    out.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }

            class StrictScanner extends BufferedReader {

                private String currentString;
                private int currentPosition;

                public StrictScanner(Reader a) {
                    super(a);
                }

                boolean eoln() {
                    return currentPosition == currentString.length();
                }

                StrictScanner loadLine() throws IOException {
                    assert currentString == null || eoln();
                    currentString = readLine();
                    currentPosition = 0;
                    return this;
                }

                char nextChar() {
                    return currentString.charAt(currentPosition++);
                }

                String nextToken() {
                    StringBuilder sb = new StringBuilder();
                    while (currentPosition < currentString.length() && currentString.charAt(currentPosition) != ' ') {
                        sb.append(currentString.charAt(currentPosition));
                        ++currentPosition;
                    }
                    return sb.toString();
                }

                int nextInt() {
                    return Integer.parseInt(nextToken());
                }

                double nextDouble() {
                    return Double.parseDouble(nextToken());
                }

                long nextLong() {
                    return Long.parseLong(nextToken());
                }

                StrictScanner skipSpace() {
                    char c = nextChar();
                    assert c == ' ';
                    return this;
                }
            }

        }.run();
    }
}

