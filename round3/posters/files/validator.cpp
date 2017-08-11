#include <set>
#include <utility>
#include "testlib.h"

using namespace std;

int main(int argc, char* argv[]) {
  registerValidation(argc, argv);
  int n = inf.readInt(1, 100000, "n");
  inf.readEoln();
  for (int i = 1; i <= n; ++i) {
    int x1 = inf.readInt(0, 1000000000, "x_1");
    inf.readSpace();
    int y1 = inf.readInt(0, 1000000000, "y_1");
    inf.readSpace();
    int x2 = inf.readInt(0, 1000000000, "x_2");
    inf.readSpace();
    int y2 = inf.readInt(0, 1000000000, "y_2");
    inf.readEoln();
    ensuref(x1 < x2 && y1 < y2, "x1 < x2 and y1 < y2");
  }
  int m = inf.readInt(1, 100000, "m");
  inf.readEoln();
  for (int i = 1; i <= m; ++i) {
    int x1 = inf.readInt(0, 1000000000, "x_1");
    inf.readSpace();
    int y1 = inf.readInt(0, 1000000000, "y_1");
    inf.readSpace();
    int x2 = inf.readInt(0, 1000000000, "x_2");
    inf.readSpace();
    int y2 = inf.readInt(0, 1000000000, "y_2");
    inf.readEoln();
    ensuref(x1 < x2 && y1 < y2, "x1 < x2 and y1 < y2");
  }
  inf.readEof();
  return 0;
}
