#include "testlib.h"
#include <set>
#include <string>
#include <utility>

int main(int argc, char * argv[]) {
  registerTestlibCmd(argc, argv);
  int r = inf.readInt();
  int b = inf.readInt();
  int n = ouf.readInt();
  if (n < 0 || n > 100000) {
    quitf(_wa, "Wrong path length: %d", n);
  }
  std::string s = "";
  if (n != 0) {
    s = ouf.readToken();
  }
  if (n != s.size()) {
    quitf(_pe, "Length of path isn't equal to n");
  }
  for (auto &&c: s) {
    if (c != 'W' && c != 'E' && c != 'S' && c != 'N') {
      quitf(_pe, "Path contains wrong symbol: %c", c);
    }
  }
  std::set<std::pair<int, int>> black, red;
  int x = 0, y = 0;
  black.emplace(x, y);
  for (int i = 0; i < n; ++i) {
    if (s[i] == 'N') --y;
    if (s[i] == 'S') ++y;
    if (s[i] == 'W') --x;
    if (s[i] == 'E') ++x;
    if (i & 1) black.emplace(x, y);
    else red.emplace(x, y);
  }
  if (red.size() != r) {
    quitf(_wa, "Count of red cells: expected: %d, found: %d", r, (int)red.size());
  }
  if (black.size() != b) {
    quitf(_wa, "Count of black cells: expected: %d, found: %d", b, (int)black.size());
  }
  quitf(_ok, "Solution is correct");
  return 0;
}
