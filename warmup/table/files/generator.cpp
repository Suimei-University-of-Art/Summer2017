#include "testlib.h"

#include <cassert>
#include <cmath>
#include <vector>
#include <numeric>

void gen_rand(int n, int m) {
  printf("%d %d\n", n, m);
  for (int i = 0; i < n; ++i) {
    for (int j = 0; j < m; ++j) {
      putchar('0' + rnd.next(0, 1));
    }
    puts("");
  }
}

void all_zero(int n, int m) {
  printf("%d %d\n", n, m);
  for (int i = 0; i < n; ++i) {
    for (int j = 0; j < m; ++j) {
      putchar('0');
    }
    puts("");
  }
}

void all_one(int n, int m) {
  printf("%d %d\n", n, m);
  for (int i = 0; i < n; ++i) {
    for (int j = 0; j < m; ++j) {
      putchar('1');
    }
    puts("");
  }
}

void all_zero2(int n, int m) {
  printf("%d %d\n", n, m);
  for (int i = 0; i < n; ++i) {
    for (int j = 0; j < m; ++j) {
      if (i == 0 && j == 0) putchar('1');
      else putchar('0');
    }
    puts("");
  }
}

int main(int argc, char* argv[]) {
  registerGen(argc, argv, 1);
  int n = std::atoi(argv[1]);
  int m = std::atoi(argv[2]);
  int type = std::atoi(argv[3]);
  if (type >= 3) gen_rand(n, m);
  else if (type == 2) all_zero2(n, m);
  else if (type == 1) all_one(n, m);
  else if (type == 0) all_zero(n, m);
  return 0;
}
