#include <iostream>
#include <cstdio>
#include <vector>
#include <string>
#include <algorithm>
#include <stack>
#include <cmath>

using namespace std;

const int MAXC = 100500;
void out() {
     cout.flush();
     int n;
     cin >> n;
     
     exit(0);
}

void solve() {
     int n, k;
     cin >> n >> k;
     if (k == 2) {
         //   cout << "YES\n0 " << n << "\n";
         //return; 
         if (n <= 2)
             cout << "NO\n";
         else
             cout << "YES\n1 " << n-1 << "\n"; 
         return;
     }
     
     /*int sq = sqrt(n);
     for (int i = sq - 2; i <= sq+2; ++i)
         if (i > 0 && i*i == n)
             if ((k-1)*k/2 <= n) {
                 cout << "YES\n";
                 for (int i = 1; i <= k-2; ++i)
                     cout << i << ' ';
                 cout << i*i - (k-2)*(k-1)/2 << " 0\n";
                 return;
             }
     */
     if (k == 3) {
         if (n < 6 || n == 7)
            cout << "NO\n";
         else
             cout << "YES\n1 3 " << n-4 << "\n";
         return;
     }
     
     for (int i = 30; i >= 1; --i) {
         if ((k-1)*(k)/2 > i*i)
             break;
         if (i*i >= n)
             continue;
         int delta = n - i*i;
         if (delta < k) {
            int minsum = k*(k+1)/2 - delta;
            if (minsum > i*i)
                continue;
            cout << "YES\n";
            for (int j = 1; j < k; ++j)
                cout << j << ' ';
            cout << i*i - minsum + k << "\n";
            return;
         }
         else /* (delta >= k) */{
              vector<int> ans(k);
              int sum = (k-2)*(k-1)/2;
              for (int j = 0; j < k-2; ++j)
                  ans[j] = j + 1;
              ans[k-2] = i*i - sum;
              ans[k-1] = delta;
              if (ans[k-2] == ans[k-1]) {
                  if (ans[k-3] >= ans[k-2] - 2)
                     continue;
                  ++ans[k-3];
                  --ans[k-2];
              }
              cout << "YES\n";
              for (int i = 0; i < k; ++i) {
                  if (i)
                     cout << ' ';
                  cout << ans[i];
              }
              cout << "\n";
              return;
         }
     }
     
     cout << "NO\n";
     return;
}

int main() {
    ios_base::sync_with_stdio(0);
    cin.tie(NULL);

    int t;
    cin >> t;
    while (t--)
          solve();    
    
    out();
    return 0;
}

