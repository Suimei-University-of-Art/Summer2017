#include "testlib.h"
#include <cassert>

const int N = 200000;

void print(std::vector<int> &v) {
  printf("%d\n", (int)v.size());
  for (size_t i = 0; i < v.size(); ++i) {
    if (i) putchar(' ');
    printf("%d", v[i] + 1);
  }
  puts("");
}

void gen_rand(int n, double prob) {
  std::vector<int> a = {0};
  for (int i = 1; i < n; ++i) {
    if (rnd.next() < prob) a.push_back(a.back());
    else a.push_back(a.back() + 1);
  }
  assert(a.size() == n);
  puts("1");
  print(a);
}

void gen_interesting(int n, int start, double p) {
  std::vector<int> a;
  int cnt = 0;
  for (int i = 0; i < start; ++i) {
    if (i && rnd.next() < p) ++cnt;
    a.push_back(cnt);
  }
  if (start) ++cnt;
  for (int i = start; i < n / 2 + n % 2 + start; ++i) {
    a.push_back(cnt);
  }
  if (rnd.next(2) && rnd.next(2)) ++cnt;
  for (int i = start + n / 2 + n % 2; i < n; ++i) {
    if (rnd.next() < p && i != start + n / 2 + n % 2) ++cnt;
    a.push_back(cnt);
  }
  assert(a.size() == n);
  puts("1");
  print(a);
}

void gen_melnikov(int i) {
  std::vector<int> a;
  for (int j = 0; j < 200000 / i; ++j) {
    for (int k = 0; k < i; ++k) {
      a.push_back(j);
    }
  }
  puts("1");
  print(a);
}

void gen_two_melnikov(bool first) {
  std::vector<int> a;
  if (first) {
    for (int j = 0; j < 2; ++j) {
      for (int k = 0; k < 100000 - j; ++k) {
        a.push_back(j);
      }
    }
  } else {
    for (int j = 0; j < 2; ++j) {
      for (int k = 0; k < 99999 + j; ++k) {
        a.push_back(j);
      }
    }
  }
  puts("1");
  print(a);
}

int main(int argc, char **argv) {
  registerGen(argc, argv, 1);
  int cas = std::atoi(argv[1]);
  if (7 <= cas && cas <= 12) {
    gen_rand(N - rnd.next(20), 0.2 * (cas - 7));
  } else if (13 <= cas && cas <= 30) {
    int mod = (cas - 13) % 3;
    double p = (cas - 13) / 3 * 0.2;
    if (mod == 0) gen_interesting(N - 1, 0, p);
    if (mod == 1) gen_interesting(N - 1, N / 3 + rnd.next(100), p);
    if (mod == 2) gen_interesting(N - 1, (N - 1) - 1 - (N - 1) / 2, p);
  } else if (cas == 31) {
    gen_melnikov(1);
  } else if (cas == 32) {
    gen_melnikov(2);
  } else if (cas == 33) {
    gen_melnikov(3);
  } else if (cas == 34) {
    gen_melnikov(447);
  } else if (cas == 35) {
    gen_melnikov(100000);
  } else if (cas == 36) {
    gen_two_melnikov(true);
  } else if (cas == 37) {
    gen_two_melnikov(false);
  } else {
    assert(false);
  }
  return 0;
}
