#include "testlib.h"
#include <set>
#include <string>
#include <utility>

long long gcd(long long a, long long b) {
  return b ? gcd(b, a % b) : a;
}

int main(int argc, char * argv[]) {
  registerTestlibCmd(argc, argv);
  std::string ja = ans.readToken();
  std::string pa = ouf.readToken();
  for (auto &&c: ja) c = toupper(c);
  for (auto &&c: pa) c = toupper(c);
  if (pa != "YES" && pa != "NO") {
    quitf(_pe, "YES or NO expected, %s found", pa.c_str());
  }
  if (pa == "NO") {
    if (ja != pa) {
      quitf(_wa, "participant has not found answer but it exists");
    } else {
      quitf(_ok, "Solution is correct");
    }
  }
  int n = inf.readInt();
  std::vector<int> p(n), q(n);
  for (int i = 0; i < n; ++i) {
    p[i] = inf.readInt();
    q[i] = inf.readInt();
  }
  int pn = ouf.readInt();
  if (pn < 1 || pn > n) {
    quitf(_pe, "participant takes %d fractions, but there are only %d", pn, n);
  }
  long long pp = 0, qq = 1;
  std::vector<bool> used(n, false);
  for (int i = 0; i < pn; ++i) {
    int j = ouf.readInt();
    if (j < 1 || j > n) {
      quitf(_pe, "participant takes fraction #%d, but there are only %d", j, n);
    }

    if (used[j - 1]) {
      quitf(_pe, "fraction #%d has been taken more than once", j);
    }
    used[j - 1] = true;
    long long t = qq / gcd(qq, q[j - 1]) * q[j - 1];
    pp = pp * (t / qq) + p[j - 1] * (t / q[j - 1]);
    qq = t;
  }
  if (ja == "YES") {
    if (pp == qq) {
      quitf(_ok, "participant has found the correct answer");
    } else {
      quitf(_wa, "participant has found wrong solution, though one exists");
    }
  } else {
    if (pp == qq) {
      quitf(_fail, "participant has found solution, but jury has not");
    } else {
      quitf(_wa, "participant has found wrong solution, and no solution exists");
    }
  }
  quitf(_fail, "checker failed");
  return 0;
}
