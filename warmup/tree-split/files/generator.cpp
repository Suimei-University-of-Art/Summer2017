#include "testlib.h"
#include <cstdio>
#include <set>
#include <map>
#include <utility>
#include <algorithm>
#include <cassert>
#include <numeric>

const int N = 200000 + 10, mod = 1e9 + 7;

struct Edge {
  int x, y;
  int opp(int z) {return x + y - z;}
} E[N];

std::map<std::pair<int, int>, int> bridge_id;
std::set<int> G[N];

struct Node {
  int mul, sz, sum;
  void mark(int x) {
    mul = 1ll * mul * x % mod;
  }
  int weight() const {
    return 1ll * mul * sum % mod;
  }
} tag[N];
int fa[N], sz, n;
int a[N];

int dfs_cnt;
void dfs(int u, int f) {
  if (--dfs_cnt == 0) return;
  for (auto &e: G[u]) {
    int v = E[e].opp(u);
    if (v == f) continue;
    dfs(v, u);
    if (!dfs_cnt) return;
  }
}

void split(int u, int f, int id) {
  tag[fa[u]].sum = (tag[fa[u]].sum + mod - a[u]) % mod;
  tag[fa[u]].sz -= 1;
  tag[id].sz += 1;
  tag[id].sum = (tag[id].sum + a[u]) % mod;
  fa[u] = id;
  for (auto &e: G[u]) {
    int v = E[e].opp(u);
    if (v == f) continue;
    split(v, u, id);
  }
}

int solve(int rid, int last) {
  int x = E[rid].x, y = E[rid].y, id = fa[x];
  last = (last - tag[id].weight() + mod) % mod;
  G[x].erase(rid);
  G[y].erase(rid);
  int smll;
  for (int i = 1; ; i <<= 1) {
    dfs_cnt = i;
    dfs(x, y);
    int xrem = dfs_cnt;
    dfs_cnt = i;
    dfs(y, x);
    int yrem = dfs_cnt;
    if (!xrem && !yrem) continue;
    smll = xrem > yrem ? x : y;
    break;
  }
  int block_size = tag[id].sz;
  tag[sz].mul = tag[id].mul;
  tag[sz].sz = 0;
  tag[sz].sum = 0;
  split(smll, x + y - smll, sz);
  tag[id].mark(tag[sz].sz + 1);
  tag[sz].mark(tag[id].sz + 1);
  last = (last + tag[id].weight()) % mod;
  last = (last + tag[sz].weight()) % mod;
  ++sz;
  return last;
}

std::vector<std::pair<int, int>> gen_binary(int n) {
  std::vector<std::pair<int, int>> edges;
  for (int i = 1; i <= n; ++i) {
    if (i * 2 <= n) edges.emplace_back(i, i * 2);
    if (i * 2 + 1 <= n) edges.emplace_back(i, i * 2 + 1);
  }
  return edges;
}

std::vector<std::pair<int, int>> gen_chain(int n) {
  std::vector<std::pair<int, int>> edges;
  std::vector<int> p(n);
  std::iota(p.begin(), p.end(), 1);
  shuffle(p.begin(), p.end());
  for (int i = 1; i < n; ++i) {
    edges.emplace_back(p[i - 1], p[i]);
  }
  return edges;
}

std::vector<std::pair<int, int>> gen_star(int n) {
  std::vector<std::pair<int, int>> edges;
  for (int i = 2; i <= n; ++i) {
    edges.emplace_back(1, i);
  }
  return edges;
}

std::vector<std::pair<int, int>> gen_rand1(int n) {
  std::vector<std::pair<int, int>> edges;
  std::vector<int> p(n);
  std::iota(p.begin(), p.end(), 1);
  shuffle(p.begin(), p.end());
  for (int i = 1; i < n; ++i) {
    edges.emplace_back(p[rnd.next(std::max(0, i - 10), i - 1)], p[i]);
  }
  return edges;
}

std::vector<std::pair<int, int>> gen_rand2(int n) {
  std::vector<std::pair<int, int>> edges;
  std::vector<int> p(n);
  std::iota(p.begin(), p.end(), 1);
  shuffle(p.begin(), p.end());
  for (int i = 1; i < n; ++i) {
    edges.emplace_back(p[rnd.next(0, int(sqrt(i)) - 1)], p[i]);
  }
  return edges;
}

std::vector<std::pair<int, int>> generate_tree(int n, int type) {
  if (type == 0) return gen_binary(n);
  else if (type == 1) return gen_chain(n);
  else if (type == 2) return gen_star(n);
  else if (type == 3) return gen_rand1(n);
  else return gen_rand2(n);
}

int main(int argc, char **argv) {
  registerGen(argc, argv, 1);
  int n = std::atoi(argv[1]);
  int type = std::atoi(argv[2]);
  std::vector<std::pair<int, int>> edges = generate_tree(n, type);
  for (int i = 0; i < n; ++i) {
    a[i] = rnd.next(1, 1000000000 + 6);
  }
  printf("%d\n", n);
  for (int i = 0; i < n; ++i) {
    if (i) putchar(' ');
    printf("%d", a[i]);
  }
  puts("");
  sz = 1;
  tag[0].mul = 1;
  tag[0].sz = n;
  for (int i = 0; i < n; ++i) {
    tag[0].sum = (tag[0].sum + a[i]) % mod;
  }
  for (int i = 0; i + 1 < n; ++i) {
    E[i].x = edges[i].first;
    E[i].y = edges[i].second;
    printf("%d %d\n", E[i].x, E[i].y);
    --E[i].x; --E[i].y;
    if (E[i].x > E[i].y) std::swap(E[i].x, E[i].y);
    bridge_id[std::make_pair(E[i].x, E[i].y)] = i;
    G[E[i].x].insert(i);
    G[E[i].y].insert(i);
  }
  shuffle(edges.begin(), edges.end());
  int last = 0;
  for (int i = 1; i < n; ++i) {
    int u = edges[i - 1].first;
    int v = edges[i - 1].second;
    if (rnd.next(0, 1)) std::swap(u, v);
    printf("%d %d\n", u - last, v - last);
    --u, --v;
    if (u > v) std::swap(u, v);
    int x = bridge_id[std::make_pair(u, v)];
    if (i == 1) last = tag[0].sum;
    last = solve(x, last);
  }
  return 0;
}
