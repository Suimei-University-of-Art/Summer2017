#include "testlib.h"
#include <vector>
#include <string>
#include <algorithm>

long long numberOfWays(const std::vector<int> &ws, int w, long long mod) {
  std::vector<long long> dp(w + 1);
  dp[0] = 1 % mod;
  for (int x : ws) {
    for (int i = w; i >= x; --i) {
      dp[i] += dp[i - x];
      if (dp[i] >= mod) dp[i] -= mod;
    }
  }
  return dp[w];
}

bool canCollect(const std::vector<int> &ws, int w) {
  std::vector<bool> dp(w + 1);
  dp[0] = true;
  for (int x : ws) {
    for (int i = w; i >= x; --i) {
      dp[i] = dp[i] | dp[i - x];
    }
  }
  return dp[w];
}

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int n = ouf.readInt();
  if (n == -1) {
    quitf(_wa, "answer always exists");
  }
  int w = ouf.readInt();
  if (n < 1 || n > 200) {
    quitf(_wa, "n out of bound");
  }
  if (w < 1 || w > 500) {
    quitf(_wa, "w out of bound");
  }
  std::vector<int> ws(n);
  for (int i = 0; i < n; ++i) {
    ws[i] = ouf.readInt();
    if (ws[i] < 1 || ws[i] > w) {
      quitf(_wa, "a_%d out of bound", i + 1);
    }
  }
  if (!canCollect(ws, w)) {
    quitf(_wa, "cannot collect weight %d with given items", w);
  }
  long long mod = inf.readLong();
  long long result = numberOfWays(ws, w, mod);
  if (result != 0) {
    quitf(_wa, "number of ways = %lld, is not equal to zero modulo %lld", result, mod);
  }
  quitf(_ok, "OK");
  return 0;
}
