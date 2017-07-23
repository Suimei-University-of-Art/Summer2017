import java.io.*;
import java.util.*;

/*
 * A bunch of greedy solution
 */
public class siege_ab_greedy {
    public static void main(String[] args) {
        new Object() {
            class Person {
                long a, b, number;

                Person(int a, int b, int num) {
                    this.a = a;
                    this.b = b;
                    this.number = num;
                }

                @Override
                public boolean equals(Object obj) {
                    return obj instanceof Person && ((Person)obj).a == a && ((Person)obj).b == b;
                }
            }
            
            void getAns(final int A, final int B, final Person[] persons) {
                new Object() {
                    class Answer {
                        Set<Person> set;
                        int survivorCache = -1;
                                                
                        public Answer() {
                            set = new TreeSet<Person>(new Comparator<Person>() {
                                @Override
                                public int compare(Person o1, Person o2) {
                                    return o1.b < o2.b ? -1 : o1.b > o2.b ? 1 : 0;
                                }                           
                            });
                        }
                        
                        void add(Person p) {
                            survivorCache = -1;
                            set.add(p);
                        }
                        
                        int survivors() {
                            if (survivorCache >= 0) {
                                return survivorCache;
                            }
                            int rest = B;
                            int ans = set.size();
                            for (Person p : set) {
                                if (p.b <= rest) {
                                    rest -= p.b;
                                    ans--;
                                }
                            }
                            return ans;
                        }
                    }
                    
                    final Comparator<Person> NO_COMPARE = new Comparator<Person>() {
                        @Override public int compare(Person o1, Person o2) {                                
                            return 0;
                        }
                    }; 
                                
                    List<? extends Comparator<Person>> cmpList = Arrays.asList(
                        NO_COMPARE
                        ,new Comparator<Person>() {@Override public int compare(Person o1, Person o2) {                             
                            return o1.a < o2.a ? -1 : o1.a > o2.a ? 1 : 0;
                        }}
                        ,new Comparator<Person>() {@Override public int compare(Person o1, Person o2) {                             
                            return o1.b < o2.b ? -1 : o1.b > o2.b ? 1 : 0;
                        }}
                        ,new Comparator<Person>() {@Override public int compare(Person o1, Person o2) {                             
                            return o1.a * o2.b < o2.a * o1.b ? -1 : o1.a * o2.b > o2.a * o1.b ? 1 : 0;
                        }}
                        ,new Comparator<Person>() {@Override public int compare(Person o1, Person o2) {                             
                            return o1.a - o1.b < o2.a - o2.b ? -1 : o1.a - o1.b > o2.a - o2.b ? 1 : 0;
                        }}                      
                        ,new Comparator<Person>() {@Override public int compare(Person o1, Person o2) {                             
                            return o1.a + o1.b < o2.a + o2.b ? -1 : o1.a + o1.b > o2.a + o2.b ? 1 : 0;
                        }}
                        ,new Comparator<Person>() {@Override public int compare(Person o1, Person o2) {                             
                            return o1.a * o1.b < o2.a * o2.b ? -1 : o1.a * o1.b > o2.a * o2.b ? 1 : 0;
                        }}
                        );
                
                    abstract class Behavior {
                    
                        abstract Answer getAns(Comparator<Person> cmp);
                        
                        void relax(Comparator<Person> cmp) {
                            Answer a = getAns(cmp);
                            if (a.survivors() > ans.survivors()) {
                                ans = a;
                            }
                        }                       
                    }
                    
                    List<? extends Behavior> bhList = Arrays.asList(
                            new Behavior() {@Override Answer getAns(Comparator<Person> cmp) {
                                Arrays.sort(persons, cmp);
                                Answer ans = new Answer();
                                int restA = A;
                                for (int i = 0; i < persons.length; ++i) {
                                    Person p = persons[i];
                                    if (restA >= p.a) {
                                        restA -= p.a;
                                        ans.add(p);
                                    }
                                }
                                return ans;
                            }}
                            ,new Behavior() {@Override Answer getAns(Comparator<Person> cmp) {
                                Arrays.sort(persons, cmp);
                                Answer ans = new Answer();
                                int restA = A;
                                int restB = B;
                                long maxB = 0;
                                for (int i = 0; i < persons.length; ++i) {
                                    Person p = persons[i];
                                    if (restA < p.a || ans.set.contains(p)) continue;
                                    if (restB >= p.b) {
                                        restA -= p.a;
                                        restB -= p.b;
                                        maxB = Math.max(maxB, p.b);
                                        ans.add(p);
                                    }
                                }
                                for (int i = 0; i < persons.length; ++i) {
                                    Person p = persons[i];
                                    if (maxB < p.b) continue;
                                    if (restA >= p.a) {
                                        restA -= p.a;
                                        ans.add(p);
                                    }
                                }
                                
                                return ans;
                            }}
                            );
                    
                    
                    void bt(int level, final Comparator<Person> cmp) {
                        if (level == 0) {
                            for (Behavior b : bhList) {
                                b.relax(cmp);
                            }
                        } else {
                            for (final Comparator<Person> c2 : cmpList) {                               
                                bt(level - 1, new Comparator<Person>(){
                                    @Override
                                    public int compare(Person o1, Person o2) {
                                        int a = cmp.compare(o1, o2);
                                        if (a != 0) return a;
                                        return c2.compare(o1, o2);
                                    }                                   
                                });
                                bt(level - 1, new Comparator<Person>(){
                                    @Override
                                    public int compare(Person o1, Person o2) {
                                        int a = cmp.compare(o1, o2);
                                        if (a != 0) return a;
                                        return -c2.compare(o1, o2);
                                    }                                   
                                });
                            }
                        }
                    }
                    
                    Answer ans = new Answer();
                    
                    void solve() {
                        bt(3, NO_COMPARE);
                        out.println(ans.survivors());
                        out.print(ans.set.size());
                        for (Person person : ans.set) {
                            out.print(" " + (person.number + 1));
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
//                    in = new StrictScanner(new FileReader("input.txt"));
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
