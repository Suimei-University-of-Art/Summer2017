#include "testlib.h"
#include <set>
#include <vector>
#include <string>
#include <algorithm>

std::set<int> un;

int get(std::set<int> a, std::set<int> b) {
  std::set<int> diff(un);
  for (auto &&x: a) diff.erase(x);
  std::vector<int> all;
  for (auto &&x: b) {
    if (diff.count(x)) all.push_back(x);
  }
  if (all.size() == 1) return all[0];
  else return -1;
}

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int n = inf.readInt();
  int m = inf.readInt();
  int k = inf.readInt();
  for (int i = 0; i < k; ++i) {
    un.insert(i);
  }
  std::vector<std::set<int>> info1(n), info2(m);
  std::vector<bool> info(k);
  for (int i = 0; i < n; ++i) {
    int cnt = inf.readInt();
    for (int j = 0; j < cnt; ++j) {
      int w = inf.readInt() - 1;
      info1[i].insert(w);
      info[w] = true;
    }
  }
  for (int i = 0; i < m; ++i) {
    int cnt = inf.readInt();
    for (int j = 0; j < cnt; ++j) {
      int w = inf.readInt() - 1;
      info2[i].insert(w);
    }
  }
  int pans = ouf.readInt();
  int jans = ans.readInt();
  if (pans == 1) {
    for (int i = 0; i < n; ++i) {
      int to = ouf.readInt() - 1;
      if (to < 0 || to >= m) {
        quitf(_wa, "index for second team out of bound");
      }
      int clue = get(info1[i], info2[to]);
      if (clue != -1) info[clue] = true;
    }
    for (int i = 0; i < k; ++i) {
      if (!info[i]) {
        quitf(_wa, "clue #%d is not known for the first team", i + 1);
      }
    }
    if (jans == 2) {
      quitf(_fail, "solution exists, but nothing found");
    }
  } else {
    if (jans == 1) {
      quitf(_wa, "solution exists, but nothing found");
    }
  }
  quitf(_ok, "OK");
  return 0;
}
