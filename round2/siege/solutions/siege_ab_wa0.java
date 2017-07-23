import java.io.*;
import java.util.*;

/*
 * O(n * (MAX + n log n)) solution
 */
public class siege_ab_wa0 {
    public static void main(String[] args) {
        new Object() {
            class Person implements Comparable<Person> {
                int a, b, number;

                Person(int a, int b, int num) {
                    this.a = a;
                    this.b = b;
                    this.number = num;
                }

                @Override
                public int compareTo(Person o) {
                    return a < o.a ? -1 : a > o.a ? 1 : b < o.b ? -1 : b > o.b ? 1 : 0;
                }

                @Override
                public boolean equals(Object obj) {
                    return obj instanceof Person && compareTo((Person)obj) == 0;
                }
            }

            final int MAX = 100000;
            final int INF = Integer.MAX_VALUE / 2;

            void getAns(int A, int B, Person[] persons) {
                Arrays.sort(persons, new Comparator<Person>() {
                    @Override
                    public int compare(Person o1, Person o2) {
                        return o1.b < o2.b ? -1 : o1.b > o2.b ? 1 : o1.a > o2.a ? -1 : o1.a < o2.a ? 1 : 0;
                    }
                });


                TreeSet<Person> ts = new TreeSet<Person>();
                for (Person p : persons) {
                    ts.add(p);
                }

                int[] D = new int[B + MAX + 1];
                Person[] par = new Person[B + MAX + 1];
                Arrays.fill(D, INF);
                D[0] = 0;
                int ans = 0;
                List<Person> al = Collections.emptyList();
                for (Person p : persons) {
                    int mi = D.length - 1;
                    for (int i = Math.max(B - p.b + 1, 0); i < D.length; ++i) {
                        if (D[i] < D[mi]) {
                            mi = i;
                        }
                    }
                    
                    
                    
                    if (D[mi] < INF) {
                        ArrayList<Person> tal = new ArrayList<Person>();
                        int x = mi;
                        while (x > 0) {
                            tal.add(par[x]);
                            x -= par[x].b;
                        }                       
                        
                        int rest = A - D[mi];
                        Iterator<Person> it = ts.iterator();
                        int tans = 0;
                        while (it.hasNext()) {
                            Person cur = it.next();
                            if (rest >= cur.a) {
                                rest -= cur.a;
                                ++tans;
                                tal.add(cur);
                            } else {
                                break;
                            }
                        }
                        if (ans < tans) {
                            ans = tans;
                            al = tal;
                        }
                    }

                    ts.remove(p);

                    for (int i = Math.max(0, D.length - 1 - p.b); i >= 0; --i) {
                        if (D[i + p.b] > D[i] + p.a) {
                            D[i + p.b] = D[i] + p.a;
                            par[i + p.b] = p;
                        }
                    }
                }

                out.println(ans);
                out.print(al.size());
                for (Person person : al) {
                    out.print(" " + (person.number + 1));
                }
                out.println();                
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
