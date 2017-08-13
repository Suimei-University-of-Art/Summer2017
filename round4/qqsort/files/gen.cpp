#include "testlib.h"
#include <vector>
#include <algorithm>

int main(int argc, char* argv[]) {
  registerGen(argc, argv, 1);
  int type = std::atoi(argv[1]);
  if (type == 0) { // gen all
    int n = std::atoi(argv[2]);
    int T = 1;
    for (int i = 1; i <= n; ++i) T *= i;
    printf("%d\n", T);
    std::vector<int> p(n);
    for (int i = 1; i <= n; ++i) p[i - 1] = i;
    do {
      printf("%d\n", n);
      for (int i = 0; i < n; ++i) {
        if (i) putchar(' ');
        printf("%d", p[i]);
      }
      puts("");
    } while (std::next_permutation(p.begin(), p.end()));
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
      std::vector<int> p(n);
      for (int i = 1; i <= n; ++i) p[i - 1] = i;
      shuffle(p.begin(), p.end());
      for (int i = 0; i < n; ++i) {
        if (i) putchar(' ');
        printf("%d", p[i]);
      }
      puts("");
    }
  }
  return 0;
}
