#include <bits/stdc++.h>

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
    printf("%d\n", *min_element(as.begin(), as.end()));
  }
}