#include <cstdio>
#include <vector>
#include <bitset>
#include <algorithm>

using State = std::bitset<100>;

struct bag {
  std::vector<std::pair<int, int>> value;
  std::vector<int> dp;
  std::vector<State> state;
  int delta, sum;
  void add(int v, int i) {
    value.emplace_back(v, i);
  }
  void build() {
    delta = sum = 0;
    for (auto &&e: value) {
      if (e.first < 0) delta += e.first;
      else sum += e.first;
    }
    int n = sum - delta;
    dp.assign(n + 1, 0);
    state.assign(n + 1, 0);
    dp[-delta] = 1;
    for (auto &&e: value) {
      std::vector<State> new_state = state;
      std::vector<int> new_dp = dp;
      for (int i = 0; i <= n; ++i) if (dp[i]) {
        int j = i + e.first;
        if (j >= 0 && j <= n && !new_dp[j]) {
          new_dp[j] = 1;
          new_state[j] = state[i];
          new_state[j][e.second] = 1;
        }
      }
      dp.swap(new_dp);
      state.swap(new_state);
    }
  }
  bool valid(int v) {
    return v >= delta && v <= sum && dp[v - delta];
  }
  State get(int v) {
    return state[v - delta];
  }
} bags[5];

std::vector<std::pair<int, State>> valid[5];

bool search(int d, int s, State now) {
  if (d == 5) {
    if (s == 1) {
      puts("YES");
      printf("%d\n", now.count());
      for (int i = 0; i < 100; ++i) {
        if (now[i]) printf("%d ", i + 1);
      }
      puts("");
    }
    return s == 1;
  }
  for (auto &&e: valid[d]) {
    if (search(d + 1, s + e.first, now | e.second)) return true;
  }
  return false;
}

int main() {
  int n;
  scanf("%d", &n);
  for (int i = 0; i < n; ++i) {
    int p, q;
    scanf("%d%d", &p, &q);
    if (q == 11) bags[0].add(p, i);
    else if (q == 13) bags[1].add(p, i);
    else if (q == 17) bags[2].add(p, i);
    else if (q == 19) bags[3].add(p, i);
    else bags[4].add(5040 / q * p, i);
  }
  const int dem[] = {11, 13, 17, 19, 5040};
  for (int i = 0; i < 5; ++i) {
    valid[i].clear();
    bags[i].build();
    for (int j = -100; j <= 100; ++j) {
      if (bags[i].valid(j * dem[i])) {
        valid[i].emplace_back(j, bags[i].get(j * dem[i]));
      }
    }
  }
  if (!search(0, 0, State())) puts("NO");
  return 0;
}
