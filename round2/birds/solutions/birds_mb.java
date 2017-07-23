import java.io.*;
import java.util.*;

public class birds_mb {
    static class Bird implements Comparable<Bird> {
        final int index;
        final int location;

        long time;

        Bird(int index, int location) {
            this.index = index;
            this.location = location;
        }

        public int compareTo(Bird that) {
            return location - that.location;
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int L = Integer.parseInt(in.readLine());

        int moveR = Integer.parseInt(in.readLine());
        StringTokenizer stR = new StringTokenizer(in.readLine());
        Bird[] birdsR = new Bird[moveR];
        for (int i = 0; i < moveR; ++i) {
            birdsR[i] = new Bird(i, Integer.parseInt(stR.nextToken()));
        }

        int moveL = Integer.parseInt(in.readLine());
        StringTokenizer stL = new StringTokenizer(in.readLine());
        Bird[] birdsL = new Bird[moveL];
        for (int i = 0; i < moveL; ++i) {
            birdsL[i] = new Bird(i, Integer.parseInt(stL.nextToken()));
        }

        TreeSet<Bird> currR = new TreeSet<Bird>(Arrays.asList(birdsR));
        TreeSet<Bird> currL = new TreeSet<Bird>(Arrays.asList(birdsL));
        List<Bird> all = new ArrayList<Bird>(Arrays.asList(birdsR));
        all.addAll(Arrays.asList(birdsL));
        Collections.sort(all);
        int left = 0, right = all.size() - 1;
        
        long currTime = 0;
        int offset = 0;

        while (!currR.isEmpty() || !currL.isEmpty()) {
            boolean useRight;
            boolean useLeft;
            if (currL.isEmpty()) {
                useRight = true;
                useLeft = false;
            } else if (currR.isEmpty()) {
                useRight = false;
                useLeft = true;
            } else {
                int lengthL = currL.first().location + offset;
                int lengthR = L - currR.last().location + offset;
                useRight = lengthL >= lengthR;
                useLeft = lengthL <= lengthR;
            }

            if (useRight) {
                Bird bird = currR.pollLast();
                int dt = L - bird.location + offset;
                currTime += dt;
                offset -= dt;
                all.get(right--).time = currTime;
            } 
            if (useLeft) {
                Bird bird = currL.pollFirst();
                int dt = bird.location + offset;
                currTime += dt;
                offset -= dt;
                all.get(left++).time = currTime;
            }

            if (useRight != useLeft) {
                TreeSet<Bird> swp = currR;
                currR = currL;
                currL = swp;

                offset = -offset;
            }
        }

        for (Bird b : birdsR) {
            System.out.print(b.time + " ");
        }
        System.out.println();
        for (Bird b : birdsL) {
            System.out.print(b.time + " ");
        }
        System.out.println();
    }
}
