#include "testlib.h"
#include <string>
#include <set>
#include <vector>

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int A = inf.readInt();
  int B = inf.readInt();
  int n = inf.readInt();
  std::vector<int> a(n), b(n);
  for (int i = 0; i < n; ++i) {
    a[i] = inf.readInt();
    b[i] = inf.readInt();
  }
  int jans = ans.readInt();
  int pans = ouf.readInt();
  int k = ouf.readInt();
  if (k < 0 || k > n) {
    quitf(_wa, "Number of selected artifacts out of bounds: %d", k);
  }
  std::vector<int> ar(k);
  std::vector<bool> mark(n);
  for (int i = 0; i < k; ++i) {
    int j = ouf.readInt();
    if (j <= 0 || j > n) {
      quitf(_wa, "Selected artifact #%d is out of bounds: %d", i + 1, j);
    }
    if (mark[j - 1]) {
      quitf(_wa, "Artifact #%d was selected twice", j);
    }
    mark[j - 1] = true;
    A -= a[j - 1];
    ar[i] = b[j - 1];
  }
  if (A < 0) {
    quitf(_wa, "Selected artifacts consume too much mana.");
  }
  std::sort(ar.begin(), ar.end());
  int tans = 0;
  for (size_t i = 0; i < ar.size(); ++i) {
    if (B >= ar[i]) B -= ar[i];
    else {
      tans = ar.size() - i;
      break;
    }
  }
  if (tans != pans) {
    quitf(_wa, "Number of active artefacts was miscalculated. Promised: %d, actual number: %d", pans, tans);
  }
  if (pans < jans) {
    quitf(_wa, "Number of activated artifacts is not optimal. Expected: %d, found %d", jans, pans);
  } else if (pans == jans) {
    quitf(_ok, "%d artifacts active", jans);
  } else {
    quitf(_fail, "Paticipant has a better answer: %d", pans);
  }
  return 0;
}
