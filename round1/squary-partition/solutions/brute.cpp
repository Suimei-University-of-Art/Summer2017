#include <cstdio>
#include <cstring>
#include <cstdlib>
#include <algorithm>
using namespace std;

const int MAXN = 200000 + 10;

int S[MAXN], A[MAXN], ret[MAXN];
int N, M, K, tot, size;

bool dfs(int dep, int cur, int sum) {
  if (dep == K && sum == tot) {
    puts("YES");
    for (int i = 0; i < K; ++ i) {
      printf("%d%c", ret[i], " \n"[i == K - 1]);
    }
    return true;
  }
  int need = K - dep, rest = size - cur;
  if (sum > tot || rest < need || sum + A[cur] - A[cur + need] < tot || sum + A[size - need] > N) return false;
  if (cur == size) return false;
  ret[dep] = A[cur] - A[cur + 1];
  if (sum + ret[dep] <= tot && dfs(dep + 1, cur + 1, sum + ret[dep])) return true;
  if (dfs(dep, cur + 1, sum)) return true;
  return false;
}

bool check(int square) {
  int extra = N - square;
  tot = square, size = 0;
  for (int i = tot; i >= 1; -- i) {
    if (i != extra) A[size ++] = i;
  }
  A[size] = 0;
  for (int i = size - 1; i >= 0; -- i) A[i] += A[i + 1];
  ret[0] = extra;
  return dfs(1, 0, 0);
}

int main() {
  int T;
  scanf("%d", &T);
  for (int cas = 1; cas <= T; ++cas) {
    scanf("%d%d", &N, &K);
    M = 0;
    for (int i = 1; i * i < N; ++ i) S[M ++] = i * i;
    bool flag = false;
    for (int i = M - 1; i >= 0; -- i) {
      if (check(S[i])) {
        flag = true;
        break;
      }
    }
    if (!flag) puts("NO");
  }
  return 0;
}
