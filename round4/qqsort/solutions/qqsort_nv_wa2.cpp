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
		size_t i = 0;
		if (len.size() > 1)
		{ 
			while (i < len.size())
			{
				if (len[i] > 1)
				{
					ans++, i++;
					continue;
				}
				int k = 0;
				while (i < len.size() && len[i] == 1) i++, k++;
				ans += (k) / 2;
			}
			printf("%d\n", ans - (len[0] > 1 && len.back() > 1 ? 1 : 0));
		}
		else printf("0\n"); 
	}
}

