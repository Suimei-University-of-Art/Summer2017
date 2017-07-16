#include <iostream>
#include <cstdio>
#include <cmath>
#include <algorithm>
#include <vector>
#include <set>
#include <assert.h>

using namespace std;

int n, m;
int times = 0;
int colors = 0;
int bridges = 0;

vector< vector<int> > g;
vector<int> enter, ret, cnt;
vector<int> size;
vector<bool> used;
vector<int> color;


long long ans = 0;

void dfs(int v)
{
	size[v] = 1;
	for (size_t i = 0; i < g[v].size(); i++)
	{
		int u = g[v][i];
		if (size[u] == 0)
		{
			dfs(u);
			size[v] += size[u];
		}
	}
}

void paint(int v, int col)
{
	used[v] = true;
	color[v] = col;
	for (size_t i = 0; i < g[v].size(); i++)
	{
		int u = g[v][i];
		if (!used[u])
			paint(u, col);
	}
}

void search_bridge(int v, int p = -1)
{
	used[v] = true;
	enter[v] = ret[v] = times++;
	for (size_t i = 0; i < g[v].size(); i++)
	{
		int t = g[v][i];
		if (t == p) continue;
		if (used[t])
			ret[v] = min(ret[v], enter[t]);
		else
		{
			search_bridge(t, v);
			ret[v] = min(ret[v], ret[t]);
			if (ret[t] > enter[v])
			{
				bridges++;
				cnt.push_back(size[t]);
			}
		}
	}
};

int main()
{
	// freopen("edges.in", "r", stdin);
	// freopen("edges.out", "w", stdout);
	cin >> n >> m;
	assert(1 <= n && n <= 1e5);
	assert(0 <= m && m <= 2e5);
	g.resize(n);
	int x, y;
    set< pair<int, int> > q;
	for (int i = 0; i < m; i++)
	{
		cin >> x >> y;
	    assert(1 <= x && x <= n);
		assert(1 <= y && y <= n);
		assert(x != y);
        pair<int, int> e = make_pair(min(x, y), max(x, y));
        assert(q.count(e) == 0);
        q.insert(e);
        g[x - 1].push_back(y - 1); 
		g[y - 1].push_back(x - 1);
	}
    q.clear();
	used.resize(n, false);
	color.resize(n);
	size.resize(n);
	for (size_t i = 0; i < n; i++)
		if (!used[i])
		{
			paint(i, colors++);
			dfs(i);		
		}
	if (colors > 2)
	{
		cout << "0\n";
		return 0;
	}
	enter.resize(n, 0);
	ret.resize(n, 0);
	used.assign(n, false);
	for (size_t i = 0; i < n; i++)
		if (!used[i]) search_bridge(i);
	if (colors == 2)
	{
		long long size1 = 0;
		long long size2 = 0;
		for (size_t i = 0; i < n; i++)
			if (color[i] == 1) size1++;
			else size2++;
		ans = size1 * size2;
		ans *= (m - bridges);
		cout << ans << endl;
		return 0;
	}
	if (colors == 1)
	{
		ans =  m - bridges;
		ans *= (long long)n * (long long)(n - 1) / 2 - m;
        for (size_t i = 0; i < cnt.size(); i++)
			ans += (long long)(n - cnt[i]) * cnt[i] - 1;
		cout << ans << endl;
		return 0;
	}
}
