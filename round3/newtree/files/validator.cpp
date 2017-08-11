#include <set>
#include <utility>
#include "testlib.h"

using namespace std;

int main(int argc, char* argv[]) {
  registerValidation(argc, argv);
  int n = inf.readInt(3, 100000, "n");
  inf.readSpace();
  int a = inf.readInt(1, n, "a");
  inf.readEoln();
  int x = inf.readInt(-1000000, 1000000, "x");
  inf.readSpace();
  int y = inf.readInt(-1000000, 1000000, "y");
  inf.readEoln();
  std::set<std::pair<int, int>> pts;
  pts.emplace(x, y);
  for (int i = 0; i < n; ++i) {
    x = inf.readInt(-1000000, 1000000, format("x[%d]", i + 1));
    inf.readSpace();
    y = inf.readInt(-1000000, 1000000, format("y[%d]", i + 1));
    inf.readEoln();
    ensuref(!pts.count({x, y}), "duplicate points are not allowed.");
    pts.emplace(x, y);
  }
  inf.readEof();
  return 0;
}
