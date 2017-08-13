#include <set>
#include <utility>
#include "testlib.h"

using namespace std;

int main(int argc, char* argv[]) {
  registerValidation(argc, argv);
  int T = inf.readInt(1, 1000000, "T");
  inf.readEoln();
  int sum = 0;
  for (int cas = 1; cas <= T; ++cas) {
    int n = inf.readInt(1, 1000000, "n");
    inf.readEoln();
    sum += n;
    ensuref(sum <= 1000000, "the sum of n does not exceed 1000000");
    for (int i = 1; i <= n; ++i) {
      if (i > 1) inf.readSpace();
      int a = inf.readInt(1, n, format("a[%d]", i));
    }
    inf.readEoln();
  }
  inf.readEof();
  return 0;
}
