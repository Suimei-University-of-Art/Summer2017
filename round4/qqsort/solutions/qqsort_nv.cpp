#include <cstdio>
#include <vector>
#include <iostream>

using namespace std;

int id[1000001];

int main()
{
	int t;
	scanf("%d", &t);
	while (t--)
	{
		int n, x;
		scanf("%d", &n);		
		for (int i = 0; i < n; i++)
		{
			scanf("%d", &x);
			id[x - 1] = i;
		}
		int last = -1;
		vector<int> len;
		len.reserve(n / 2);
		for (int i = 0; i < n - 1; i++)
			if (id[i] > id[i + 1]) len.push_back(i - last), last = i;
		len.push_back(n - 1 - last);
		int ans = 0;
		vector<int> dp(len.size() + 1);
		for (int i = 2; i < len.size() + 1; i++)
			dp[i] = i;
		for (int i = 1; i < len.size() - 1; i++)
		{
			dp[i + 1] = min(dp[i + 1], dp[i] + 1);
			if (len[i] == 1) dp[i + 2] = min(dp[i + 2], dp[i] + 1);
		}
		ans = min(dp.back(), dp[len.size() - 1] + 1); 
		printf("%d\n", ans); 
	}
}


