#include "testlib.h"
#include <algorithm>
#include <set>

int main(int argc, char **argv) {
  registerValidation(argc, argv);
  int n = inf.readInt(1, 100000, "n");
  inf.readSpace();
  int m = inf.readInt(0, 200000, "m");
  inf.readEoln();
  std::set<std::pair<int, int>> edges;
  for (int i = 0; i < m; ++i) {
    int a = inf.readInt(1, n, format("a[%d]", i + 1));
    inf.readSpace();
    int b = inf.readInt(1, n, format("b[%d]", i + 1));
    inf.readEoln();
    ensuref(a != b, "self loops are not allowed");
    if (a > b) std::swap(a, b);
    ensuref(!edges.count(std::make_pair(a, b)), "multiple edges are not allowed");
    edges.emplace(a, b);
  }
  inf.readEof();
  return 0;
}
