#include <cstdio>
#include <vector>
#include <algorithm>

const int N = 100000 + 10;

using ll = long long;

std::vector<int> edges[N], cnt;
int size[N], color[N];
int dfn[N], low[N];
bool mark[N];
int n, m, it, bridges;

void dfs(int u) {
  size[u] = 1;
  for (auto &&v: edges[u]) {
    if (size[v] == 0) {
      dfs(v);
      size[u] += size[v];
    }
  }
}

void paint(int u, int c) {
  if (color[u] != -1) return;
  color[u] = c;
  for (auto &&v: edges[u]) {
    paint(v, c);
  }
}

void bridge(int u, int p = -1) {
  mark[u] = true;
  dfn[u] = low[u] = it++;
  for (auto &&v: edges[u]) {
    if (v == p) continue;
    if (mark[v]) {
      low[u] = std::min(low[u], dfn[v]);
    } else {
      bridge(v, u);
      low[u] = std::min(low[u], low[v]);
      if (low[v] > dfn[u]) {
        ++bridges;
        cnt.push_back(size[v]);
      }
    }
  }
}

int main() {
  scanf("%d%d", &n, &m);
  for (int i = 0; i < m; ++i) {
    int x, y;
    scanf("%d%d", &x, &y);
    edges[x - 1].push_back(y - 1);
    edges[y - 1].push_back(x - 1);
  }
  for (int i = 0; i < n; ++i) {
    color[i] = -1;
    mark[i] = 0;
    size[i] = 0;
  }
  int colors = 0;
  for (int i = 0; i < n; ++i) {
    if (color[i] == -1) {
      paint(i, colors++);
      dfs(i);
    }
  }
  if (colors > 2) {
    puts("0");
  } else {
    for (int i = 0; i < n; ++i) {
      if (!mark[i]) bridge(i);
    }
    if (colors == 2) {
      ll ls = 0, rs = 0;
      for (int i = 0; i < n; ++i) {
        if (color[i] == 0) ++ls;
        else ++rs;
      }
      printf("%lld\n", ls * rs * (m - bridges));
    } else {
      ll ret = 1ll * (m - bridges) * (1ll * n * (n - 1) / 2 - m);
      for (auto &&e: cnt) ret += 1ll * (n - e) * e - 1;
      printf("%lld\n", ret);
    }
  }
  return 0;
}
