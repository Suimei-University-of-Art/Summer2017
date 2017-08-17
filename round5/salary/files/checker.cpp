#include "testlib.h"
#include <vector>
#include <string>
#include <algorithm>

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int tests = inf.readInt();
  for (int cas = 1; cas <= tests; ++cas) {
    int n = inf.readInt();
    int m = inf.readInt();
    std::vector<int> a(n), b(n);
    for (int i = 0; i < n; ++i) {
      a[i] = inf.readInt();
      b[i] = inf.readInt();
    }
    std::vector<int> u(m), v(m);
    for (int i = 0; i < m; ++i) {
      u[i] = inf.readInt() - 1;
      v[i] = inf.readInt() - 1;
    }
    int jans = ans.readInt();
    int pans = ouf.readInt();
    for (int i = 0; i < jans; ++i) ans.readInt();
    if (jans == -1 && pans == -1) {
      continue;
    }
    if (jans != -1 && pans == -1) {
      quitf(_wa, "participant didn't find solution for test #%d", cas);
    }
    if (pans < 0 || pans > n) {
      quitf(_wa, "illegal answer for test #%d", cas);
    }
    std::vector<int> was(n, false);
    for (int i = 0; i < pans; ++i) {
      int x = ouf.readInt() - 1;
      if (x < 0 || x >= n) {
        quitf(_wa, "illegal index for test #%d", cas);
      }
      if (was[x]) {
        quitf(_wa, "duplicate indices for test %d", cas);
      }
      was[x] = true;
    }
    for (int i = 0; i < n; ++i) {
      if (was[i]) std::swap(a[i], b[i]);
    }
    for (int i = 0; i < m; ++i) {
      if (a[u[i]] <= a[v[i]] && b[u[i]] <= b[v[i]]) {
        quitf(_wa, "main condition failed for edge #%d on test #%d", i + 1, cas);
      }
    }
    if (jans == -1) {
      quitf(_fail, "jury didn't find a solution for test #%d, while participant did", cas);
    }
    if (jans > pans) {
      quitf(_fail, "participant has better solution on test #%d", cas);
    }
    if (jans < pans) {
      quitf(_wa, "jury has better solution on test #%d", cas);
    }
  }
  quitf(_ok, "all %d tests have passed", tests);
  return 0;
}
