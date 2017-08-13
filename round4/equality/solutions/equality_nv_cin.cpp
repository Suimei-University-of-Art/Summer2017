#include <cstdio>
#include <iostream>

using namespace std;

int a[1000007];
bool used[1000007];

int main() {
	int t;
	cin >> t;
	while (t--) {
		int n;
		cin >> n;
		for (int i = 0; i < n; i++)
			cin >> a[i];
		int cur = 0, cnt = 0;
		for (int i = 0 ; i <=n; i++)
			cur = a[cur] - 1;
	    while (!used[cur]) used[cur] = true, cur = a[cur] - 1, cnt++;
	    printf("%d\n", cnt);
	    for (int i = 0; i < n; i++) {
	    	if (used[i]) cout << i + 1 << " ";
	    	used[i] = false;
	    }
	    cout << "\n";
	}
}
              