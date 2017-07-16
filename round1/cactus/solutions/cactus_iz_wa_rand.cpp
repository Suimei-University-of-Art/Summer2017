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

const int MAXN = 1 << 17;
const int INF = 1e9;
const double TL = 2.0;

int n, m;

int main() {
	scanf("%d%d", &n, &m);
	vector<pair<int, pair<int, int> > > e;
	vector<int> cnt(m);
	for (int i = 0; i < m; i++) {
		int u, v, c;
		scanf("%d%d%d", &u, &v, &c);
		--u; --v; --c;
		e.push_back(make_pair(c, make_pair(u, v)));
		cnt[c]++;
	}

	sort(e.begin(), e.end(), [&](const pair<int, pair<int, int> > &a, const pair<int, pair<int, int> > &b) {
		if (cnt[a.first] != cnt[b.first]) return cnt[a.first] < cnt[b.first];
		return a.first < b.first;
	});
	int ans = 0;
	while (clock() / (double)CLOCKS_PER_SEC < TL * 0.8) {
		vector<int> p(n);
		for (int i = 0; i < n; i++) p[i] = i;
		function<int(int)> get = [&](int x) {
			if (x == p[x]) return x;
			return p[x] = get(p[x]);
		};
		vector<char> was(m);
		int cans = 0;
		for (int i = 0; i < m; i++) {
			if (was[e[i].first]) continue;
			int u = get(e[i].second.first);
			int v = get(e[i].second.second);
			if (u == v) continue;
			was[e[i].first] = 1;
			cans++;
			p[v] = u;
		}
		ans = max(ans, cans);
		random_shuffle(e.begin(), e.end());
	}
	cout << ans << endl;

	return 0;
}