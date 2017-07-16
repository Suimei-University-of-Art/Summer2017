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

struct Graph {
	struct Edge {
		int from, to, cap, flow;

		Edge() {}
		Edge(int from, int to, int cap) : from(from), to(to), cap(cap), flow(0) {}
	};

	int n;
	vector<Edge> edges;
	vector<vector<int> > e;

	Graph(int n) {
		this->n = n;
		e.resize(n);
	}

	void addEdge(int from, int to, int cap) {
		e[from].push_back(edges.size());
		edges.push_back(Edge(from, to, cap));
		e[to].push_back(edges.size());
		edges.push_back(Edge(to, from, 0));
	}

	vector<int> d, c;

	bool bfs() {
		d.assign(n, INF);
		c.assign(n, 0);
		queue<int> q;
		q.push(0);
		d[0] = 0;
		while (!q.empty()) {
			int v = q.front();
			q.pop();
			for (int id : e[v]) {
				Edge cur = edges[id];
				if (d[cur.to] > d[v] + 1 && cur.flow < cur.cap) {
					d[cur.to] = d[v] + 1;
					q.push(cur.to);
				}
			}
		}
		return d[n - 1] != INF;
	}

	int dfs(int v, int flow) {
		if (v == n - 1) return flow;
		if (flow == 0) return 0;
		for (int &i = c[v]; i < (int)e[v].size(); i++) {
			Edge cur = edges[e[v][i]];
			if (d[cur.to] != d[v] + 1) continue;
			int pushed = dfs(cur.to, min(flow, cur.cap - cur.flow));
			if (pushed) {
				edges[e[v][i]].flow += pushed;
				edges[e[v][i] ^ 1].flow -= pushed;
				return pushed;
			}
		}
		return 0;
	}

	int getFlow() {
		int res = 0;
		while (bfs()) {
			while (int pushed = dfs(0, INF)) {
				res += pushed;
			}
		}
		return res;
	}
};

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

int main() {
	scanf("%d%d", &n, &m);
	for (int i = 0; i < m; i++) {
		scanf("%d%d%d", &u[i], &v[i], &c[i]);
		u[i]--; v[i]--; c[i]--;
		e[u[i]].push_back(i);
		e[v[i]].push_back(i);
	}
	dfs(0, -1);
	int sz = 1 + vct.size() + m + 1;
	Graph gr(sz);
	for (int i = 0; i < (int)vct.size(); i++) {
		gr.addEdge(0, 1 + i, max(1, (int)vct[i].size() - 1));
		for (int id : vct[i]) {
			gr.addEdge(1 + i, 1 + vct.size() + c[id], 1);
		}
	}
	for (int i = 0; i < m; i++) gr.addEdge(1 + vct.size() + i, 1 + vct.size() + m, 1);

	cout << gr.getFlow() << endl;
    vector<char> was(m);
    for (int i = 0; i < m; i++) was[c[i]] = 1;
    int different = 0;
    for (int i = 0; i < m; i++) different += was[i];
    cerr << different << endl;
	return 0;
}