#include <cstdio>
#include <iostream>
#include <algorithm>
#include <queue>
#include <vector>

using namespace std;

const long long INF = 1e10;

int main()
{
	long long lenght;
	cin >> lenght;
	int n, m;
	cin >> n;
	deque<long long> left, right, all;
	vector< pair<long long, int> > tmp(n), tmp2;
	for (int i = 0; i < n; i++)
	{
		cin >> tmp[i].first;
		tmp[i].second = i;
		tmp2.push_back(tmp[i]);
	}
	sort(tmp.begin(), tmp.end());
	for (int i = 0; i < n; i++)
		right.push_back(tmp[i].first);

	cin >> m;
	tmp.resize(m);
	for (int i = 0; i < m; i++)
	{
		cin >> tmp[i].first;
		tmp[i].second = n + i;
		tmp2.push_back(tmp[i]);
	}
	sort(tmp.begin(), tmp.end());
	for (int i = 0; i < m; i++)
		left.push_back(tmp[i].first);

	sort(tmp2.begin(), tmp2.end());
	for (int i = 0; i < n + m; i++)
		all.push_back(tmp2[i].second);

	long long add_l = 0, add_r = 0;

	vector<long long> ans(n + m);
	long long answer = 0;
	while (!left.empty() || !right.empty())
	{
		long long min_l = (left.size()) ? (add_l + left.front()): INF;
		long long min_r = (right.size()) ? (lenght - (add_r + right.back())) : INF;
		long long min_shift = min(min_l, min_r);
		add_l -= min_shift;
		add_r += min_shift;
		answer += min_shift;
		if (min_l == min_r)
		{
			ans[all.back()]= answer;
			right.pop_back();
			ans[all.front()] = answer;
			left.pop_front();
			all.pop_back(); all.pop_front();
		}
		if (min_l < min_r)
		{
			ans[all.front()] = answer;
			all.pop_front();
			left.pop_front();
			swap(left, right);
			swap(add_l, add_r);
		}
		if (min_l > min_r)
		{
			ans[all.back()] = answer;
			all.pop_back();
			right.pop_back();
			swap(left, right);
			swap(add_l, add_r);
		}
	}
	for (int i = 0; i < n; i++)
		cout << ans[i] << ((i == n - 1) ? "\n" : " ");
	for (int i = 0; i < m; i++)
		cout << ans[i + n] << ((i == m - 1) ? "\n" : " ");
}