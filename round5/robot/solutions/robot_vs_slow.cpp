#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <string>
#include <cstring>
#include <cmath>
#include <algorithm>
#include <vector>
#include <set>
#include <map>
#include <ctime>
#include <cassert>
 
#define fs first
#define sc second
#define pb push_back
#define mp make_pair
#define forn(i, n) for(int i = 0 ; (i) < (n) ; ++i)
#define forit(it,v) for(typeof((v).begin()) it = v.begin() ; it != (v).end() ; ++it)
#define eprintf(...) fprintf(stderr, __VA_ARGS__),fflush(stderr)
#define sz(a) ((int)(a).size())
#define all(a) (a).begin(),a.end()
 
using namespace std;
 
typedef long long ll;
typedef double ld;
typedef vector<int> vi;
typedef pair<int, int> pi;
 
const int inf = (int)1e9;
const ld eps = 1e-9;
 
/* --- main part --- */
 
#define TASK "a"
 
const int mod = 998244353;
const ll LIM = ((ll)8e18 / mod) * mod;
 
inline int mpow(int a, int b)
{
    int res = 1;
    while (b)
    {
        if (b & 1) res = (res * (ll)a) % mod;
        a = (a * (ll)a) % mod;
        b >>= 1;
    }
    return res;
}
 
const int maxn = (int)3e5 + 10;
 
int f[maxn];
int fr[maxn];
 
inline int cnk(int n, int k)
{
    if (k < 0 || k > n) return 0;
    return (((f[n] * (ll)fr[k]) % mod) * fr[n - k]) % mod;
}
 
inline int cnkfast(int n, int k)
{
    return (((f[n] * (ll)fr[k]) % mod) * fr[n - k]) % mod;
}
 
inline int getcnk(int x1, int y1, int x2, int y2)
{
    int N = x2 - x1;
    int K = y2 - (y1 - N);   
    if (K & 1) return 0;
    K >>= 1;
    return cnk(N, K);
}
 
inline int get(int st, int en, int t)
{
    if (((st - en) & 1) != (t & 1))
    {
        return 0;
    }
    int res = getcnk(0, st, t, en) - getcnk(0, -st, t, en);
    if (res < 0) res += mod;
    return res;
}
 
int X12[maxn], X22[maxn], Y12[maxn], Y22[maxn];
int ans[maxn];
int stay[maxn];
 
int main()
{
    #ifdef home
        assert(freopen(TASK".in", "r", stdin));
        assert(freopen(TASK".out", "w", stdout));
    #endif
    f[0] = 1;
    forn(i, maxn - 1)
    {
        f[i + 1] = (f[i] * (ll)(i + 1)) % mod;
    }
    forn(i, maxn) fr[i] = mpow(f[i], mod - 2);
    int x1, y1, x2, y2, t;
    scanf("%d%d%d%d%d", &x1, &y1, &x2, &y2, &t);
    forn(i, maxn)
    {
        ll C = fr[i];
        X12[i] = (get(x1, x2, i) * C) % mod;
        X22[i] = (get(x2, x2, i) * C) % mod;
        Y12[i] = (get(y1, y2, i) * C) % mod;
        Y22[i] = (get(y2, y2, i) * C) % mod;
    }
    int needX = abs(x1 - x2);
    int needY = abs(y1 - y2);
    for (int i = 0; i <= t; i += 2)
    {
        ll res = 0;
        for (int j = 0; j <= i; j += 2)
        {
            res = res + (X22[j] * (ll)Y22[i - j]);
            if (res >= LIM) res -= LIM;
        }
        res %= mod;
        stay[i] = (res * (ll)f[i]) % mod;
    }
    for (int i = needX + needY; i <= t; i += 2)
    {
        ll res = 0;
        for (int j = needX; j <= i; j += 2)
        {
            res = res + (X12[j] * (ll)Y12[i - j]);
            if (res >= LIM) res -= LIM;
        }
        res %= mod;
        res = (res * (ll)f[i]) % mod;
        ll sum = 0;
        for (int j = needX + needY; j < i; j += 2)
        {
            sum = sum + ans[j] * (ll)stay[i - j];
            if (sum >= LIM) sum -= LIM;
        }
        res = res + mod - sum % mod;
        ans[i] = res % mod;
    }
    printf("%d\n", ans[t]);
    #ifdef home
        eprintf("Time: %d ms\n", (int)(clock() * 1000. / CLOCKS_PER_SEC));
    #endif
    return 0;
}
