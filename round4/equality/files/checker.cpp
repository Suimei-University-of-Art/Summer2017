#include "testlib.h"
#include <vector>
#include <string>

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int tests = inf.readInt();
  for (int cas = 1; cas <= tests; ++cas) {
    int n = inf.readInt();
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) {
      a[i] = inf.readInt();
    }
    int k = ouf.readInt();
    if (k == -1) {
      quitf(_wa, "participant hasn't found a solution for test #%d", cas);
    }
    if (k == 0) {
      quitf(_wa, "participant has an empty set for test #%d", cas);
    }
    if (k > n) {
      quitf(_wa, "participan' answer has more than %d elements for test #%d", n, cas);
    }
    std::vector<bool> mark(n);
    for (int i = 0; i < k; ++i) {
      int b = ouf.readInt();
      if (b <= 0 || b > n) {
        quitf(_wa, "participant's %d-th number for test #%d not in range [%d, %d]", i + 1, cas, 1, n);
      }
      if (mark[b - 1]) {
        quitf(_wa, "participant has two equal numbers in answer for test #%d", cas);
      }
      mark[b - 1] = true;
    }
    long long sum = 0;
    for (int i = 0; i < n; ++i) {
      if (mark[i]) sum += i + 1 - a[i];
    }
    if (sum != 0) {
      quitf(_wa, "sums are not euqal for test #%d", cas);
    }
  }
  quitf(_ok, "all %d tests have passed", tests);
  return 0;
}
