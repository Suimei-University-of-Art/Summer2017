#include "testlib.h"
#include <string>
#include <set>
#include <vector>

const int N = 200000 + 10;

namespace treap {
  struct node {
    int sz, val;
    node *l, *r;
    node() = default;
    node(int v): sz(1), val(v), l(0), r(0) {}
    node* update() {
      sz = 1;
      if (l) sz += l->sz;
      if (r) sz += r->sz;
      return this;
    }
  } pool[N], *p, *rt;
  node* new_node(int v) {
    *p = node(v);
    return p++;
  }
  void init() {
    p = pool;
    rt = 0;
  }
  bool random(int a, int b) {
    return rnd.next(a + b) < a;
  }
  node* merge(node *l, node *r) {
    if (!l || !r) return l ? l : r;
    if (random(l->sz, r->sz)) {
      l->r = merge(l->r, r);
      return l->update();
    } else {
      r->l = merge(l, r->l);
      return r->update();
    }
  }
  std::pair<node*, node*> split(node *o, int sz) {//(-inf, sz], [sz + 1, inf)
    node *l = 0, *r = 0;
    if (!o) return {l, r};
    int ls = o->l ? o->l->sz : 0;
    if (ls + 1 <= sz) {
      std::tie(o->r, r) = split(o->r, sz - ls - 1);
      l = o;
    } else {
      std::tie(l, o->l) = split(o->l, sz);
      r = o;
    }
    o->update();
    return {l, r};
  }
}

using namespace treap;

void dfs(node *o, std::vector<int> &a) {
  if (!o) return;
  dfs(o->l, a);
  a.push_back(o->val);
  dfs(o->r, a);
}

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  int T = inf.readInt();
  for (int cas = 1; cas <= T; ++cas) {
    int n = inf.readInt();
    std::vector<int> a(n);
    int sum = 0, cnt = 0, max = 0;
    for (int i = 0; i < n; ++i) {
      a[i] = inf.readInt();
      if (i) {
        if (a[i] == a[i - 1]) {
          cnt++;
          sum++;
          if (cnt > max) max = cnt;
        } else {
          cnt = 0;
        }
      }
    }
    int jans = ans.readInt();
    for (int i = 0; i < jans; ++i) {
      ans.readInt();
      ans.readInt();
    }
    int ans = sum / 2 + sum % 2;
    if (max > ans) ans = max;
    if ((max + 1) * 2 - 1 > n) ans = -1;
    if (max == 0) ans = 0;
		if (jans > ans) {
      quitf(_fail, "Jury is stupid!");
		}
    init();
    for (int i = 0; i < n; ++i) {
      rt = merge(rt, new_node(a[i]));
    }
    int pans = ouf.readInt();
    for (int i = 0; i < pans; ++i) {
      int x = ouf.readInt();
      int y = ouf.readInt();
      if (x > y) std::swap(x, y);
      node *a, *b, *c;
      std::tie(b, c) = split(rt, y);
      std::tie(a, b) = split(b, x - 1);
      rt = merge(a, merge(c, b));
    }
    std::vector<int> res;
    dfs(rt, res);
    if (ans != -1) {
      for (int i = 1; i < n; ++i) {
        if (res[i] == res[i - 1]) {
          quitf(_wa, "Contestant's answer contains conflicts on case #%d, position #%d.", cas, i);
        }
      }
    }
    if (pans < ans) {
      quitf(_fail, "Contestant's answer is better, then jury's one");
    }
  }
  quitf(_ok, "Contestant has passed all tests!");
  return 0;
}
