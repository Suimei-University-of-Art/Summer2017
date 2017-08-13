#include <cstdio>
#include <algorithm>
#include <ctime>

using namespace std;

int a[1000007], id[1000007];

int main()
{
	srand(556668949LL);
	int t;
	scanf("%d", &t);

START:
	while (t--)
	{
		int n;
		scanf("%d", &n);
	
		long long sum = 0, sum_id = 0;
		for (int i = 0; i < n; i++)
			scanf("%d", &a[i]), id[i] = i + 1, sum += a[i], sum_id += id[i];
		
		for (int i = 0; i < n; i++)
			if (a[i] == id[i]) 
			{
				printf("1\n%d\n", id[i]);
				goto START;
			}

	   	if (sum == sum_id)
	    {
	    	printf("%d\n", n);
	    	for (int i = 0; i < n; i++)
	    		printf("%d ", i + 1);
	    	printf("\n");	
	    	goto START;
	    }
	    int cnt = max((int)2e7 / n, 50);
	    while (cnt--)
	    {
	    	random_shuffle(id, id + n);
	    	long long cur_sum = 0, cur_sum_id = 0;
	    	for (int i = 0; i < n; ++i)
	    	{
	    		cur_sum += a[id[i] - 1], cur_sum_id += id[i];
	    		if (cur_sum == cur_sum_id)
	    		{
	    			printf("%d\n", i + 1);
	    			for (int k = 0; k <= i; ++k)
	    				printf("%d ", id[k]);
	    			printf("\n");
	    			goto START;
	    		}
	    	}	
	    }
	    
	    printf("%d\n", n / 2);
	    for (int k = 0; k < n / 2; ++k)
	    	printf("%d ", id[k]);
	    printf("\n");
	}
}                                    