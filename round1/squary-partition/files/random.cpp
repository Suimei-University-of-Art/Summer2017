#include "testlib.h"
#include <cstdio>
#include <vector>
#include <numeric>
#include <algorithm>

int main(int argc, char* argv[]) {
  registerGen(argc, argv, 1);
  int k = std::atoi(argv[1]);
  int T = 100000;
  printf("%d\n", T);
  for (int i = 1; i <= T; ++i) {
    printf("%d %d\n", i, k);
  }
  return 0;
}

