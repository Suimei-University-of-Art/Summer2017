import java.io.PrintWriter;
import java.util.*;

public class shuffle_sm_bruteforce {
    int[] tmp;

    void move(int[] a, int l, int r) {
        System.arraycopy(a, l, tmp, 0, r - l);
        System.arraycopy(a, r, a, l, a.length - r);
        System.arraycopy(tmp, 0, a, a.length - (r - l), r - l);
    }

    boolean isCorrect(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i + 1]) {
                return false;
            }
        }
        return true;
    }

    static class Move {
        int l, r;

        Move(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    static class Position {
        int[] a;
        Move inMove;
        Position from;

        Position(int[] a, Move inMove, Position from) {
            this.a = a;
            this.inMove = inMove;
            this.from = from;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Position position = (Position) o;

            if (!Arrays.equals(a, position.a)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(a);
        }
    }

    List<Move> solve(int[] request) {
        Position start = new Position(request, null, null);
        Queue<Position> queue = new ArrayDeque<Position>();
        queue.add(start);
        HashSet<Position> used = new HashSet<Position>();
        used.add(start);
        Position result = null;
        while (!queue.isEmpty()) {
            Position p = queue.poll();
            if (isCorrect(p.a)) {
                result = p;
                break;
            }
            for (int l = 0; l < p.a.length; l++) {
                for (int r = l + 1; r <= p.a.length; r++) {
                    int[] b = Arrays.copyOf(p.a, p.a.length);
                    move(b, l, r);
                    Position np = new Position(b, new Move(l, r), p);
                    if (!used.contains(np)) {
                        used.add(np);
                        queue.add(np);
                    }
                }
            }
        }
        if (result == null) {
            return null;
        }

        List<Move> ans = new ArrayList<Move>();
        while (result.from != null) {
            ans.add(result.inMove);
            result = result.from;
        }
        Collections.reverse(ans);
        return ans;
    }

    Scanner in;
    PrintWriter out;

    public void run() {
        in = new Scanner(System.in);
        out = new PrintWriter(System.out);
        int n = in.nextInt();
        for (int it = 0; it < n; it++) {
            solveOne();
        }
        out.close();
    }

    private void solveOne() {
        int n = in.nextInt();
        tmp = new int[n];
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            if (i > 0 && a[i - 1] > a[i]) {
                throw new AssertionError();
            }
        }
        List<Move> moves = solve(a);
        if (moves != null) {
            out.println(moves.size());
            for (Move move : moves) {
                out.println((move.l + 1) + " " + move.r);
            }
        } else {
            out.println(-1);
        }
    }

    public static void main(String[] args) {
        new shuffle_sm_bruteforce().run();
    }
}
