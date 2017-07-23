#include <cstdio>
#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
#include <iterator>

using namespace std;

struct art
{
	int a, b, id;
	art() {};
	art(int a, int b, int id): a(a), b(b), id(id) {};
	bool operator<(const art & o) const
	{
		if (a == o.a) return b < o.b;
		return a < o.a;
	}
};

bool comp(art a, art b)
{
	if (a.b == b.b) return a.a > b.a;
	return a.b > b.b;
}

const int MAX_COUNT = 1e5;
const int INF = 2e9;

int main()
{
	int A, B, n;
	cin >> A >> B >> n;
	vector<art> arts(n);
	for (int i = 0; i < n; i++)
	{
		int x, y;
		cin >> x >> y;
		arts[i] = art(x, y, i);
	}
	sort(arts.begin(), arts.end(), comp);
	set<art> s;
	for (int i = 0; i < n; i++)
		s.insert(arts[i]);
	int count = B + MAX_COUNT + 1;
	vector<int> cost(count, INF);
	vector<art> par(count);
	cost[0] = 0;
	int answer = 0;
	vector<art> ans;
	for (int i = 0; i < n; i++)
	{
		art a = arts[i];
		int m = count - 1;
		for (int j = max(0, B - a.b + 1); j < count; j++)
			if (cost[j] < cost[m]) m = j;

		if (cost[m] < INF)
		{
			vector<art> cur_ans;
			int x = m;
			while (x > 0)
			{
				cur_ans.push_back(par[x]);
				x -= par[x].b;
			}
			int rest = A - cost[m];
			int cur_answer = 0;
			set<art>::iterator it = s.begin();
			do 
			{
				art cur = *it;
				if (rest >= cur.a)
				{
					rest -= cur.a;
					cur_answer++;
					cur_ans.push_back(cur);
				}
				else break;
				it++;
			} while (it != s.end());
			
			if (answer < cur_answer)
			{
				ans = cur_ans;
				answer = cur_answer;
			}
		}
		s.erase(a);

		for (int j = max(count - 1 - a.b, 0); j>= 0; j--)
			if (cost[j + a.b] > cost[j] + a.a)
			{
				cost[j + a.b] = cost[j] + a.a;
				par[j + a.b] = a;
			}
	}
	cout << answer << "\n";
	cout << ans.size();
	for (int i = 0; i < ans.size(); i++)
		cout << " " << ans[i].id + 1;
	cout << "\n";
}