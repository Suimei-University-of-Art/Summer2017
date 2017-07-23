import java.io.*;
import java.util.*;

public class siege_mb {
    static class Item implements Comparable<Item> {
        final int index;
        final int a;
        final int b;

        public Item(int index, int a, int b) {
            this.index = index;
            this.a = a;
            this.b = b;
        }

        public int compareTo(Item that) {
            return b - that.b;
        }
    }

    static class Link {
        Link prev;
        int index;

        public Link(Link prev, int index) {
            this.prev = prev;
            this.index = index;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int A = in.nextInt();
        int B = in.nextInt();
        int n = in.nextInt();

        Item[] it = new Item[n];
        for (int i = 0; i < n; ++i) {
            it[i] = new Item(i, in.nextInt(), in.nextInt());
        }
        Item[] it2 = it.clone();
        Arrays.sort(it);

        int[] sumb = new int[A + 1];
        int[] exc = new int[A + 1];
        short[][] back = new short[n][A + 1];
        boolean[] has = new boolean[A + 1];
        has[0] = true;

        for (int i = 0; i < n; ++i) {
            for (int w = A - it[i].a; w >= 0; --w) {
                if (has[w]) {
                    int nsumb = sumb[w] + it[i].b;
                    int nexc;
                    if (exc[w] > 0) {
                        nexc = exc[w] + 1;
                    } else {
                        if (nsumb > B) {
                            nexc = 1;
                        } else {
                            nexc = 0;
                        }
                    }

                    int nw = w + it[i].a;
                    has[nw] = true;
                    if (exc[nw] < nexc || exc[nw] == nexc && sumb[nw] < nsumb) {
                        exc[nw] = nexc;
                        sumb[nw] = nsumb;

                        back[i][nw] = (short) (it[i].index + 1);
                    }
                }
            }
        }

        int max = -1, maxi = -1;
        for (int w = 0; w <= A; ++w) {
            if (max < exc[w]) {
                max = exc[w];
                maxi = w;
            }
        }

        List<Integer> ans = new ArrayList<Integer>();
        int cs = n - 1, ci = maxi;
        while (ci >= 0 && cs >= 0) {
            while (cs >= 0 && back[cs][ci] == 0) cs--;
            if (cs >= 0) {
                ans.add((int) back[cs][ci]);
                ci -= it2[back[cs][ci] - 1].a;
                --cs;
            }
        }

        System.out.println(max);
        System.out.print(ans.size());
        for (int i : ans) {
            System.out.print(" " + i);
        }
        System.out.println();
    }
}
