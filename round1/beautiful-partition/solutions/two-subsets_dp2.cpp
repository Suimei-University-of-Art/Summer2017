#include <bits/stdc++.h>

int gcd(int a, int b) {
  while (a && b) {
    int t = b;
    b = a % b;
    a = t;
  }
  return std::max(a, b);
}

void updateAnswer(int& answer, const std::vector<int>& as, int divisor) {
  int gcd1 = as[0];
  int gcd2 = 0;
  for (size_t i = 1; i < as.size(); i++) {
    if (as[i] % divisor == 0) {
      gcd1 = gcd(gcd1, as[i]);
    } else {
      gcd2 = gcd(gcd2, as[i]);
    }
  }
  if (gcd2 == 0) {
    gcd2 = (int)1e9;
  }
  answer = std::max(answer, std::min(gcd1, gcd2));
}

int main() {
  int tests;
  scanf("%d", &tests);
  while (tests--) {
    int n;
    scanf("%d", &n);
    std::vector<int> as(n);
    for (int i = 0; i < n; i++) {
      scanf("%d", &as[i]);
    }
    int answer = 1;
    for (int i = 1; i * i <= as[0]; i++) {
      if (as[0] % i != 0) {
        continue;
      }
      updateAnswer(answer, as, i);
      updateAnswer(answer, as, as[0] / i);
    }
    printf("%d\n", answer);
  }
}