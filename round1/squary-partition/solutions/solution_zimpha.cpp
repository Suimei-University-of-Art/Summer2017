#include <bits/stdc++.h>
using namespace std;

const int MAXN = 200000 + 10;
const int MAXK = 100;

bool square[MAXN];
int ret[MAXK], A[MAXN];
int N, M, K;

bool gao(int has, int N, int K) {
  int sum = 0, flag = false, M = 0;
  ret[M ++] = has;
  for (int i = 1; i < K; ++ i) {
    if (i == has) flag = true;
    if (flag) sum += i + 1, ret[M ++] = i + 1;
    else sum += i, ret[M ++] = i;
  }
  if (sum > N) return false;
  else if (sum == N) return true;
  else {
    sum -= ret[M - 1];
    ret[M - 1] = N - sum;
  }
  bool fff = true;
  for (int i = 1; i < M - 1; ++ i) {
    if (ret[M - 1] == ret[i]) {
      fff = false;
    }
  }
  if (!fff) return false;
  if (fff && flag && has == ret[M - 1]) return false;
  if (ret[M - 1] != has) return true;
  else {
    flag = false;
    while (ret[M - 2] + 1 < ret[M - 1] - 1 && !flag) {
      ++ ret[M - 2]; -- ret[M - 1];
      if (ret[M - 2] != has && ret[M - 1] != has) flag = true;
    }
    return flag;
  }
}

bool check() {
  sort(ret, ret + K);
  int sum = ret[0];
  for (int i = 1; i < K; ++ i) {
    sum += ret[i];
    if (ret[i] == ret[i - 1]) return false;
  }
  assert(sum == N);
  for (int i = 0; i < K; ++ i) {
    if (square[N - ret[i]]) return true;
  }
  return false;
}

int main() {
  for (int i = 1; i * i <= MAXN; ++ i) {
    square[i * i] = 1;
  }
  int T;
  scanf("%d", &T);
  for (int cas = 1; cas <= T; ++cas) {
    scanf("%d%d", &N, &K);
    if (K == 2) {
      if (N <= 2) puts("NO");
      else printf("YES\n1 %d\n", N - 1);
      continue;
    }
    int m = 0;
    for (int i = 1; i * i < N; ++ i) A[m ++] = i * i;
    bool flag = false;
    for (int i = m - 1; i >= 0; -- i) {
      if (gao(N - A[i], A[i], K)) {
        puts("YES");
        //assert(check());
        for (int i = 0; i < K; ++ i) {
          printf("%d%c", ret[i], " \n"[i == K - 1]);
        }
        flag = true;
        break;
      }
    }
    if  (!flag) puts("NO");
  }
  return 0;
}
