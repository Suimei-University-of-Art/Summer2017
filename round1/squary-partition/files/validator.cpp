#include <stdio.h>
#include <string>
#include "testlib.h"

int main() {
  registerValidation();
  int T;
  T = inf.readInt(1, 100000, "T");
  inf.readEoln();
  for (int cas = 1; cas <= T; ++cas) {
    int n = inf.readInt(1, 1000000, "n");
    inf.readSpace();
    int k = inf.readInt(2, 30, "k");
    inf.readEoln();
  }
  inf.readEof();
  return 0;
}
