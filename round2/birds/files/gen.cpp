#include "testlib.h"
#include <cassert>

const int L = 1000000000, N = 100000;

int maxl() {
  return L / 10 * 9 + rnd.next(L / 10);
}

int maxn() {
  return N / 10 * 9 + rnd.next(N / 10);
}
#include <iostream>

std::vector<int> randomUniqueNumbers(int n, int maxn, const std::vector<int> &except) {
  assert(maxn >= n && n >= 0 && maxn > 0);
  std::set<int> except_set(except.begin(), except.end());
  std::cout << except_set.size() << ' ' << maxn << std::endl;
  std::vector<int> res(n);
  if (maxn > 3 * n - (int)except.size()) {
    std::set<int> used(except_set);
    for (int i = 0; i < n; ++i) {
      int num = rnd.next(maxn);
      while (used.count(num)) {
        num = rnd.next(maxn);
      }
      res[i] = num;
      used.insert(num);
    }
  } else {
    std::vector<int> perm;
    for (int i = 0; i < maxn; ++i) {
      if (!except_set.count(i)) perm.push_back(i);
    }
    shuffle(perm.begin(), perm.end());
    for (int i = 0; i < n; ++i) {
      res[i] = perm[i];
    }
  }
  std::cout << res.size() << std::endl;
  return res;
}

std::vector<int> range(int n) {
  std::vector<int> res(n);
  for (int i = 1; i < n + 1; ++i) {
    res[i - 1] = i;
  }
  return res;
}

void print(std::vector<int> v) {
  printf("%d\n", (int)v.size());
  for (size_t i = 0; i < v.size(); ++i) {
    if (i) putchar(' ');
    printf("%d", v[i]);
  }
  puts("");
}

void gen_sample() {
  puts("10");
  print({8, 9});
  print({2, 5, 7});
}

void gen_sample2() {
  puts("10");
  print({});
  print({9});
}

void min_test() {
  puts("2");
  print({1});
  print({});
}

void rand_test(int length, int n, int m) {
  auto right = randomUniqueNumbers(n, length + 1, {0, length});
  std::vector<int> except = right;
  except.push_back(0);
  except.push_back(length);
  auto left = randomUniqueNumbers(m, length + 1, except);
  printf("%d\n", length);
  print(right);
  print(left);
}

void MaximalUnidirectionalRight(int length, int n, bool flip = false) {
  printf("%d\n", length);
  auto right = range(n);
  auto left = std::vector<int>();
  if (flip) {
    for (auto &&x: right) x = length - x;
    for (auto &&x: left) x = length - x;
  }
  print(right);
  print(left);
}

void RandomUnidirectionalRight(int length, int n, bool flip = false) {
  printf("%d\n", length);
  auto right = randomUniqueNumbers(n, length, {0, length});
  auto left = std::vector<int>();
  if (flip) {
    for (auto &&x: right) x = length - x;
    for (auto &&x: left) x = length - x;
  }
  print(right);
  print(left);
}

void TwoSameGroups(int length, int n, bool flip = false) {
  printf("%d\n", length);
  auto right = range(n);
  auto left = right;
  for (auto &&x: left) x += n;
  if (flip) {
    for (auto &&x: right) x = length - x;
    for (auto &&x: left) x = length - x;
  }
  print(right);
  print(left);
}

void TwoSameSymmetricGroups(int length, int n, bool flip = false) {
  printf("%d\n", length);
  auto right = range(n);
  auto left = right;
  for (auto &&x: left) x = length - x;
  if (flip) {
    for (auto &&x: right) x = length - x;
    for (auto &&x: left) x = length - x;
  }
  print(right);
  print(left);
}

int main(int argc, char **argv) {
  registerGen(argc, argv, 1);
  int cas = std::atoi(argv[1]);
  if (cas == 1) {
    gen_sample();
  } else if (cas == 2) {
    gen_sample2();
  } else if (cas == 3) {
    min_test();
  } else if (cas == 4) {
    rand_test(10, 3, 3);
  } else if (cas == 5) {
    MaximalUnidirectionalRight(maxl(), maxn());
  } else if (cas == 6) {
    MaximalUnidirectionalRight(maxl(), maxn(), true);
  } else if (cas == 7) {
    RandomUnidirectionalRight(maxl(), maxn());
  } else if (cas == 8) {
    RandomUnidirectionalRight(maxl(), maxn(), true);
  } else if (cas == 9) {
    rand_test(maxl(), maxn(), maxn());
  } else if (cas == 10) {
    rand_test(maxl(), maxn(), maxn() / 5);
  } else if (cas == 11) {
    TwoSameGroups(maxl(), maxn());
  } else if (cas == 12) {
    TwoSameSymmetricGroups(maxl(), maxn());
  } else {
    assert(false);
  }
  return 0;
}
