#include "testlib.h"

int main(int argc, char* argv[]) {
  registerGen(argc, argv, 1);
  int type = std::atoi(argv[1]);
  if (type == 0) { // gen all
    int n = std::atoi(argv[2]);
    int T = 1;
    for (int i = 1; i <= n; ++i) T *= n;
    printf("%d\n", T);
    for (int mask = 0; mask < T; ++mask) {
      printf("%d\n", n);
      for (int i = 0, m = mask; i < n; ++i) {
        if (i) putchar(' ');
        printf("%d", m % n + 1);
        m /= n;
      }
      puts("");
    }
  } else {
    auto min_n = std::atoi(argv[2]);
    auto max_n = std::atoi(argv[3]);
    std::vector<int> ns;
    int N = 1000000;
    while (N >= min_n) {
      auto n = rnd.next(min_n, std::min(max_n, N));
      ns.push_back(n);
      N -= n;
    }
    if (N > 0) ns.push_back(N);
    printf("%d\n", (int)ns.size());
    for (auto &&n: ns) {
      printf("%d\n", n);
      for (int i = 0; i < n; ++i) {
        if (i) putchar(' ');
        printf("%d", rnd.next(1, n));
      }
      puts("");
    }
  }
  return 0;
}
