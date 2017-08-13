#include "testlib.h"
#include <vector>
#include <string>

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int n = inf.readInt();
  std::vector<int> masks(1 << n, 0), pos(1 << n, -1);
  for (int i = 0; i < (1 << n); ++i) {
    std::string mask = ouf.readLine();
    if (mask.length() != n) {
      quitf(_wa, "mask #%d does not consist of %d chars", i, n);
    }
    for (int j = 0; j < n; ++j) {
      if (mask[j] != '0' && mask[j] != '1') {
        quitf(_wa, "unexpected character %c at mask #%d at position %d", mask[j], i, j);
      }
      masks[i] = masks[i] * 2 + mask[j] - '0';
    }
    if (pos[masks[i]] != -1) {
      quitf(_wa, "mask #%d and mask #%d coincide", i, pos[masks[i]]);
    }
    pos[masks[i]] = i;
  }
  for (int i = 0; i < (1 << n) - 1; ++i) {
    if (__builtin_popcount(masks[i]) < __builtin_popcount(masks[i + 1])) {
      quitf(_wa, "mask #%d has less 1's then mask #%d", i + 1, i);
    }
    if (__builtin_popcount(masks[i] ^ masks[i + 1]) > 2) {
      quitf(_wa, "mask #%d differs from mask #%d in more than 2 positions", i + 1, i);
    }
  }
  quitf(_ok, "ok");
  return 0;
}
