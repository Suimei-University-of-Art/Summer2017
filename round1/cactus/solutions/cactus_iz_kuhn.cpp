#include <sstream>
#include <vector>
#include <algorithm>
#include <cstring>
#include <cstdlib>
#include <iostream>
#include <string>
#include <cassert>
#include <ctime>
#include <map>
#include <math.h>
#include <cstdio>
#include <set>
#include <deque>
#include <memory.h>
#include <queue>
#include <functional>

#pragma comment(linker, "/STACK:64000000")
typedef long long ll;

using namespace std;

const int MAXN = 1 << 18;
const int INF = 1e9;

int n, m;
int u[MAXN], v[MAXN], c[MAXN];
vector<int> e[MAXN];
char col[MAXN];
int p[MAXN];

vector<vector<int> > vct;

int dfs(int cur, int pr) {
	int res = -1;
	col[cur] = 1;
	for (int edge : e[cur]) {
		int to = u[edge] ^ v[edge] ^ cur;
		if (to == pr) continue;
		if (col[to] == 1) {
			vector<int> vv;
			int x = cur;
			while (x != to) {
				vv.push_back(p[x]);
				x ^= u[p[x]] ^ v[p[x]];
			}
			vv.push_back(edge);
			vct.push_back(vv);
			res = to;
			continue;
		}
		if (col[to] == 2) continue;
		p[to] = edge;
		int vert = dfs(to, cur);
		if (vert != -1) {
			if (vert != cur) res = vert;
		}
		else {
			vct.push_back(vector<int>(1, edge));
		}
	}
	col[cur] = 2;
	return res;
}

#define tm ttm
vector<int> g[MAXN];
vector<vector<int> > mt;
int tm[MAXN];
int cc[MAXN], sz[MAXN];
char vis[MAXN];

bool dfs1(int v) {
	if (vis[v]) return 0;
	vis[v] = 1;
	for (int to : g[v]) {
		if (sz[to] < (int)mt[to].size()) {
			mt[to][sz[to]++] = v;
			tm[v] = 1;
			return 1;
		}
	}
	for (int to : g[v]) {
		for (int &ii = cc[to]; ii < sz[to]; ii++) {
		    int i = ii;
			if (dfs1(mt[to][i])) {
				mt[to][i] = v;
				tm[v] = 1;
				return 1;
			}
		}
	}
	return 0;
}

int main() {
	scanf("%d%d", &n, &m);
	for (int i = 0; i < m; i++) {
		scanf("%d%d%d", &u[i], &v[i], &c[i]);
		u[i]--; v[i]--; c[i]--;
		e[u[i]].push_back(i);
		e[v[i]].push_back(i);
	}
	dfs(0, -1);

	for (int i = 0; i < (int)vct.size(); i++) {
		for (int j = 0; j < (int)vct[i].size(); j++) {
			g[c[vct[i][j]]].push_back(i);
		}
	}
	mt.resize(vct.size());
	for (int i = 0; i < (int)mt.size(); i++) mt[i].assign(max(1, (int)vct[i].size() - 1), -1);

	for (int i = 0; i < m; i++) tm[i] = 0;
	for (int i = 0; i < (int)mt.size(); i++) sz[i] = 0;

	for (int run = 1; run;) {
		run = 0;
		for (int i = 0; i < (int)vct.size(); i++) cc[i] = 0;
		for (int i = 0; i < m; i++) vis[i] = 0;
		for (int i = 0; i < m; i++)	if (tm[i] == 0 && dfs1(i)) run = 1;
	}
	int ans = 0;
	for (int i = 0; i < m; i++) ans += tm[i];
	cout << ans << endl;

	return 0;
}