import java.io.*;
import java.util.*;

/*
 * O(n * (MAX + n)) solution
 */
public class siege_ab {
    public static void main(String[] args) {
        new Object() {
            class Person {
                int a, b, number;

                Person(int a, int b, int num) {
                    this.a = a;
                    this.b = b;
                    this.number = num;
                }

                @Override
                public boolean equals(Object obj) {
                    return obj instanceof Person && ((Person) obj).a == a
                            && ((Person) obj).b == b;
                }
            }

            final int INF = Integer.MAX_VALUE / 2;

            void getAns(int A, int B, Person[] persons) {
                
                Comparator<Person> cmpA = new Comparator<Person>() {
                    @Override
                    public int compare(Person o1, Person o2) {
                        return o1.a < o2.a ? -1 : o1.a > o2.a ? 1 : 0;
                    }
                }; 
                
                Arrays.sort(persons, cmpA);
                
                List<Person> lp = new LinkedList<Person>();
                for (Person p : persons) {
                    lp.add(p);
                }
                
                Arrays.sort(persons, new Comparator<Person>() {
                    @Override
                    public int compare(Person o1, Person o2) {
                        return o1.b < o2.b ? -1 : o1.b > o2.b ? 1
                                : o1.a > o2.a ? -1 : o1.a < o2.a ? 1 : 0;
                    }
                });

                int[] D = new int[B + 1];
                BitSet[] use = new BitSet[persons.length];
                for (int i = 0; i < use.length; ++i) {
                    use[i] = new BitSet(B + 1);
                }
                Arrays.fill(D, INF);
                D[0] = 0;
                int ans = 0;
                int ak = 0, ami = 0, dami = 0;
                                
                for (int k = 0; k < persons.length; ++k) {
                    Person p = persons[k];
                    int mi = D.length - 1;
                    for (int i = Math.max(B - p.b + 1, 0); i < D.length; ++i) {
                        if (D[i] < D[mi]) {
                            mi = i;
                        }
                    }

                    if (D[mi] < INF) {
                        int rest = A - D[mi];
                        Iterator<Person> it = lp.iterator();
                        int tans = 0;
                        while (it.hasNext()) {
                            Person cur = it.next();
                            if (rest >= cur.a) {
                                rest -= cur.a;
                                ++tans;
                            } else {
                                break;
                            }
                        }
                        if (ans < tans) {
                            ans = tans;
                            ak = k;
                            ami = mi;
                            dami = D[ami];
                        }
                    }

                    lp.remove(p);

                    for (int i = D.length - 1 - p.b; i >= 0; --i) {
                        if (D[i + p.b] > D[i] + p.a) {
                            D[i + p.b] = D[i] + p.a;
                            use[k].set(i + p.b);
                        }
                    }
                }

                ArrayList<Person> al = new ArrayList<Person>();
                int x = ami;
                for (int j = ak - 1; j >= 0; --j) {
                    if (use[j].get(x)) {
                        al.add(persons[j]);
                        x -= persons[j].b;
                    }
                }

                int rest = A - dami;
                Person[] tp = new Person[persons.length - ak];
                System.arraycopy(persons, ak, tp, 0, tp.length);
                Arrays.sort(tp, cmpA);
                for (Person cur : tp) {
                    if (rest >= cur.a) {
                        rest -= cur.a;
                        al.add(cur);
                    } else {
                        break;
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
                    p[i] = new Person(in.loadLine().nextInt(), in.skipSpace()
                            .nextInt(), i);
                }
                assert constraintsOk(A, B, p);
                getAns(A, B, p);
            }

            boolean constraintsOk(int A, int B, Person[] p) {
                if (A < 0 || MAX_VAL < A)
                    return false;
                if (B < 0 || MAX_VAL < B)
                    return false;
                if (MAX_N < p.length)
                    return false;
                for (Person person : p) {
                    if (person.a < 1 || MAX_VAL < person.a)
                        return false;
                    if (person.b < 1 || MAX_VAL < person.b)
                        return false;
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
//                  in = new StrictScanner(new FileReader(new File("24.bak")));
//                  out = new PrintWriter("24.out");
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
                    while (currentPosition < currentString.length()
                            && currentString.charAt(currentPosition) != ' ') {
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
