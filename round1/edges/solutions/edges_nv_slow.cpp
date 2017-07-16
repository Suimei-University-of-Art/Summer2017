#include <iostream>
#include <cstdio>
#include <vector>

using namespace std;

int n, m;
int a[200][200];
vector<bool> used;

int dfs(int v)
{
    used[v] = true;
    int cnt = 1;
    for (int i = 0; i < n; i++)
        if (a[v][i] == 1 && !used[i])
            cnt += dfs(i);
    return cnt;    
}

int main()
{
    //freopen("edges.in", "r", stdin);
	//freopen("edges.out", "w", stdout);
	
    cin >> n >> m;
    for (int i = 0; i < m; i++)
    {
        int x, y;
        cin >> x >> y;
        a[x - 1][y - 1] = 1;
        a[y - 1][x - 1] = 1;
    }
    int ans = 0;
    for (int x1 = 0; x1 < n; x1++)
        for (int y1 = x1 + 1; y1 < n; y1++)
            for (int x2 = 0; x2 < n; x2++)
                for (int y2 = x2 + 1; y2 < n; y2++)
                    if (a[x1][y1] == 1 && a[x2][y2] == 0)
                    {
                        a[x1][y1] = a[y1][x1] = 0;
                        a[x2][y2] = a[y2][x2] = 1;
                        used.assign(n + 1, false);
                        if (dfs(0) == n) ans++;
                        a[x1][y1] = a[y1][x1] = 1;
                        a[x2][y2] = a[y2][x2] = 0;
                    }
   cout << ans;
}