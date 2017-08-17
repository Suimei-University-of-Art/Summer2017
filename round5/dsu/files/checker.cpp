#include "testlib.h"
#include <vector>
#include <string>
#include <algorithm>

struct DSU {
  std::vector<int> parent, rank;
  DSU(int size) {
    parent.resize(size);
    rank.resize(size);
    for (int i = 0; i < size; ++i) {
      parent[i] = i;
      rank[i] = 0;
    }
  }
  void merge(int a, int b) {
    if (rank[a] == rank[b]) ++rank[a];
    if (rank[a] > rank[b]) parent[b] = a;
    else parent[a] = b;
  }
};

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int n = inf.readInt();
  std::vector<int> expected(n);
  for (int i = 0; i < n; ++i) {
    expected[i] = inf.readInt() - 1;
  }
  int partOps = ouf.readInt();
  int juryOps = ans.readInt();
  if (partOps == -1 && juryOps == -1) {
    quitf(_ok, "Impossible, OK");
  }
  DSU dsu(n);
  for (int i = 0; i < partOps; ++i) {
    int a = ouf.readInt() - 1;
    int b = ouf.readInt() - 1;
    if (a < 0 || a >= n) {
      quitf(_wa, "query #%d: element %d is out of bound", i + 1, a + 1);
    }
    if (b < 0 || b >= n) {
      quitf(_wa, "query #%d: element %d is out of bound", i + 1, b + 1);
    }
    if (a == b) {
      quitf(_wa, "query #%d: elements are equal", i + 1);
    }
    if (dsu.parent[a] != a) {
      quitf(_wa, "query #%d: element %d is not root", i + 1, a + 1);
    }
    if (dsu.parent[b] != b) {
      quitf(_wa, "query #%d: element %d is not root", i + 1, b + 1);
    }
    dsu.merge(a, b);
  }
  if (dsu.parent != expected) {
    quitf(_wa, "parent array is not equal to expected");
  }
  if (juryOps == -1) {
    quitf(_fail, "Impossible itself says \"I'm possible\"");
  }
  quitf(_ok, "OK");
  return 0;
}
