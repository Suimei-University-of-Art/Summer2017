#include "testlib.h"
#include <vector>
#include <string>

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int jans = ans.readInt();
  if (jans == -1) {
    if (ouf.readInt() == -1) {
      quitf(_ok, "ok");
    } else {
      quitf(_wa, "expected -1");
    }
  }
  int n = inf.readInt();
  std::vector<int> a[3];
  std::vector<bool> mark(n * 3);
  for (int i = 0; i < 3; ++i) {
    std::string line = ouf.readLine() + " ";
    std::vector<int> tokens;
    int now = 0;
    for (size_t j = 0; j < line.size(); ++j) {
      if (line[j] == '-') {
        quitf(_wa, "-1 unexpected");
      }
      if (!((line[j] >= '0' && line[j] <= '9') || line[j] == ' ')) {
        quitf(_wa, "unexpected character %c at line #%d at position #%d", line[j], i + 1, j + 1);
      }
      if (line[j] >= '0' && line[j] <= '9') now = now * 10 + line[j] - '0';
      else {
        if (j && line[j - 1] >= '0' && line[j - 1] <= '9') {
          tokens.push_back(now);
        }
        now = 0;
      }
    }
    if (tokens.size() != n) {
      quitf(_wa, "line #%d has %d elements while %d is expected", i + 1, tokens.size(), n);
    }
    a[i] = tokens;
    for (int j = 0; j < n; ++j) {
      if (tokens[j] < 1 | tokens[j] > n * 3) {
        quitf(_wa, "invalid element %d", tokens[j]);
      }
      if (mark[tokens[j] - 1]) {
        quitf(_wa, "duplicate element %d", tokens[j]);
      }
      mark[tokens[j] - 1] = true;
    }
  }
  for (int i = 0; i < n; ++i) {
    if (a[0][i] + a[1][i] != a[2][i]) {
      quitf(_wa, "%d + %d != %d", a[0][i], a[1][i], a[2][i]);
    }
  }
  quitf(_ok, "ok");
  return 0;
}
