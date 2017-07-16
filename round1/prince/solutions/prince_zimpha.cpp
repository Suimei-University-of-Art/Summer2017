#include <cstdio>
#include <vector>
#include <utility>
#include <set>
#include <functional>
#include <algorithm>

using pii = std::pair<int, int>;

std::vector<pii> merge(std::vector<pii> &u) {
  if (u.empty()) return {};
  std::vector<pii> v;
  std::sort(u.begin(), u.end());
  int l = u[0].first, r = u[0].second;
  for (auto &&e: u) {
    if (e.first <= r) r = std::max(r, e.second);
    else {
      v.emplace_back(l, r);
      std::tie(l, r) = e;
    }
  }
  v.emplace_back(l, r);
  return v;
}

std::vector<pii> merge_obs(std::vector<pii> &u) {
  if (u.empty()) return {};
  std::vector<pii> v;
  std::sort(u.begin(), u.end());
  int l = u[0].first, r = u[0].second;
  for (auto &&e: u) {
    if (e.first < r) r = std::max(r, e.second);
    else {
      v.emplace_back(l, r);
      std::tie(l, r) = e;
    }
  }
  v.emplace_back(l, r);
  return v;
}

bool inter(const pii &a, const pii &b) {

}

int main() {
  int n, x;
  scanf("%d%d", &n, &x);
  std::vector<pii> event;
  std::vector<int> l(n), r(n);
  for (int i = 0; i < n; ++i) {
    int a, t;
    scanf("%d%d%d%d", &a, &t, &l[i], &r[i]);
    event.emplace_back(a, i);
    event.emplace_back(a + t, i);
  }
  std::sort(event.begin(), event.end());
  int now = 0, ret = 1e9;
  std::set<int> obs;
  std::vector<pii> bid;
  std::vector<pii> reach = {{0, 0}};
  for (size_t i = 0, j; i < event.size(); i = j) {
    int delta = event[i].first - now;
    std::vector<pii> tmp;
    for (auto &&e: reach) {
      auto it = std::lower_bound(bid.begin(), bid.end(), e);
      int l = e.first, r = e.second;
      r += delta, l -= delta;
      if (it != bid.end()) r = std::min(r, it->first);
      if (it != bid.begin()) l = std::max(l, (it - 1)->second);
      tmp.emplace_back(l, r);
      if (l <= x && x <= r) {
        ret = std::min(ret, std::abs(x - e.first) + now);
        ret = std::min(ret, std::abs(x - e.second) + now);
      }
    }
    if (ret != 1e9) break;
    tmp = merge(tmp);
    for (j = i; j < event.size() && event[i].first == event[j].first; ++j) {
      if (obs.count(event[j].second)) obs.erase(event[j].second);
      else obs.insert(event[j].second);
    }
    bid.clear();
    for (auto &&x: obs) {
      bid.emplace_back(l[x], r[x]);
    }
    bid = merge_obs(bid);
    reach.clear();
    size_t a = 0, b = 0;
    for (; a < tmp.size() && b < bid.size(); ) {
      if (tmp[a].second <= bid[b].first) reach.emplace_back(tmp[a++]);
      else if (bid[b].second <= tmp[a].first) ++b;
      else if (tmp[a].first > bid[b].first && tmp[a].second < bid[b].second) ++a;
      else {
        if (tmp[a].first <= bid[b].first) {
          reach.emplace_back(tmp[a].first, bid[b].first);
        }
        tmp[a].first = std::min(tmp[a].second, bid[b].second);
      }
    }
    while (a < tmp.size()) reach.emplace_back(tmp[a++]);
    now = event[i].first;
  }
  if (ret == 1e9) {
    for (auto &&e: reach) {
      ret = std::min(ret, std::abs(x - e.first) + now);
      ret = std::min(ret, std::abs(x - e.second) + now);
    }
  }
  if (ret == 1e9) puts("Impossible");
  else printf("%d\n", ret);
  return 0;
}
