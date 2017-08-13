#include <cstdio>

using namespace std;

int a[1000007];
bool used[1000007];

int main()
{
	int t;
	scanf("%d", &t);
	while (t--)
	{
		int n;
		scanf("%d", &n);
		for (int i = 0; i < n; i++)
			scanf("%d", &a[i]);
		int cur = 0, cnt = 0;
		for (int i = 0 ; i <=n; i++)
			cur = a[cur] - 1;
	    while (!used[cur]) used[cur] = true, cur = a[cur] - 1, cnt++;
	    printf("%d\n", cnt);
	    for (int i = 0; i < n; i++)
	    	if (used[i]) printf("%d ", i + 1);
	    printf("\n");
	}
}
              