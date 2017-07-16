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

#pragma comment(linker, "/STACK:256000000")
typedef long long ll;

using namespace std;

const int MAXN = 1 << 18;
const int INF = 1e9;
const double TL = 2.0;

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

vector<int> edgesByColor[MAXN];
int cycleId[MAXN];
vector<int> cyclesByColor[MAXN];

int main() {
	scanf("%d%d", &n, &m);
	for (int i = 0; i < m; i++) {
		scanf("%d%d%d", &u[i], &v[i], &c[i]);
		u[i]--; v[i]--; c[i]--;
		e[u[i]].push_back(i);
		e[v[i]].push_back(i);
		edgesByColor[c[i]].push_back(i);
	}
	dfs(0, -1);
	int ans = 0;
	for (int i = 0; i < m; i++) ans += !edgesByColor[i].empty();

	dfs(0, -1);

	queue<int> q;
	vector<char> wasColor(m);
	vector<char> wasCycle(vct.size());
	for (int i = 0; i < (int)vct.size(); i++) {
		sort(vct[i].begin(), vct[i].end());
		vector<int> vv = vct[i];
		vv.resize(unique(vv.begin(), vv.end()) - vv.begin());
		if ((vv.size() == 1 || vct[i] != vv) && !wasColor[c[vv[0]]]) {
			q.push(c[vv[0]]);
			wasCycle[i] = 1;
			wasColor[c[vv[0]]] = 1;
		}
		for (int j = 0; j < (int)vct[i].size(); j++) {
			cycleId[vct[i][j]] = i;
		}
	}

	while (!q.empty()) {
		int cc = q.front();
		q.pop();
		wasColor[cc] = 1;
		for (int edge : edgesByColor[cc]) {
			if (wasCycle[cycleId[edge]]) continue;
			wasCycle[cycleId[edge]] = 1;
			for (int nedge : vct[cycleId[edge]]) {
				if (wasColor[c[nedge]]) continue;
				wasColor[c[nedge]] = 1;
				q.push(c[nedge]);
			}
		}
	}

	vector<vector<int> > nvct;
	for (int i = 0; i < (int)vct.size(); i++) if (!wasCycle[i]) nvct.push_back(vct[i]);
	vct = nvct;
	cerr << "non-trivial cycles: " << vct.size() << endl;
	cerr << "different colors: " << ans << endl;

	wasCycle.assign(vct.size(), 0);
	for (int i = 0; i < (int)vct.size(); i++) {
		for (int j = 0; j < (int)vct[i].size(); j++) {
			cyclesByColor[c[vct[i][j]]].push_back(i);
		}
	}

	vector<int> p(vct.size());
	for (int i = 0; i < (int)vct.size(); i++) p[i] = i;

	function<int(int)> get = [&](int x) {
		return x == p[x] ? x : p[x] = get(p[x]);
	};
	auto uni = [&](int u, int v) {
		u = get(u);
		v = get(v);
		if (u == v) return false;
		p[v] = u;
		return true;
	};

	for (int i = 0; i < (int)vct.size(); i++) {
		if (wasCycle[i]) continue;
		int needDec = 1;
		queue<int> q;
		q.push(c[vct[i][0]]);
		wasColor[c[vct[i][0]]] = 1;
		while (!q.empty()) {
			int cc = q.front();
			q.pop();
			for (int j = 1; j < (int)cyclesByColor[cc].size(); j++) {
				needDec &= uni(cyclesByColor[cc][0], cyclesByColor[cc][j]);
			}
			for (int j = 0; j < (int)cyclesByColor[cc].size(); j++) {
				int ccycle = cyclesByColor[cc][j];
				if (!wasCycle[ccycle]) {
					wasCycle[ccycle] = 1;
					for (int nedge : vct[ccycle]) {
						int ncolor = c[nedge];
						if (!wasColor[ncolor]) {
							wasColor[ncolor] = 1;
							q.push(ncolor);
						}
					}
				}
			}
		}
		if (needDec) ans--;
	}
	cerr << "answer: " << ans << endl;
	cout << ans << endl;

	return 0;
}