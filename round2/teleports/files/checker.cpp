#include "testlib.h"
#include <string>
#include <set>
#include <vector>

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int cas = 0;
  for (; ;) {
    int n = inf.readInt();
    int m = inf.readInt();
    int k = inf.readInt();
    if (n == 0) break;
    ++cas;
    std::vector<std::pair<int, int>> edges;
    std::vector<int> mate(n, -1);
    for (int i = 0; i < m; ++i) {
      int u = inf.readInt() - 1;
      int v = inf.readInt() - 1;
      edges.emplace_back(u, v);
    }
    for (int i = 0; i < k; ++i) {
      int u = inf.readInt() - 1;
      int v = inf.readInt() - 1;
      mate[u] = v;
      mate[v] = u;
    }
    auto check = [&](int src, std::vector<int> &path) {
      for (auto &&e: path) {
        if (edges[e].first != src && edges[e].second != src) return false; 
        src = edges[e].first + edges[e].second - src;
        if (mate[src] != -1) src = mate[src];
      }
      return true;
    };
    std::string jans = ans.readToken();
    std::string pans = ouf.readToken();
    if (pans == "Yes") {
      std::vector<bool> mark(m, 0);
      std::vector<int> path(m);
      for (int i = 0; i < m; ++i) {
        path[i] = ouf.readInt() - 1;
        if (mark[path[i]]) {
          quitf(_wa, "In case #%d:, road #%d was used twice", cas, path[i] + 1);
        }
        mark[path[i]] = true;
      }
      if (!check(edges[path[0]].first, path) && !check(edges[path[0]].second, path)) {
        quitf(_wa, "In case #%d, path is not valid", cas);
      }
      if (jans != "Yes") {
        quitf(_fail, "In case #%d, participant found a good path, but jury didn't");
      } else {
        for (int i = 0; i < m; ++i) {
          ans.readInt();
        }
      }
    } else {
      if (jans != pans) {
        quitf(_wa, "In case #%d, path exists, but participant didn't find it");
      }
    }
  }
  quitf(_ok, "OK, %d cases", cas);
  return 0;
}
