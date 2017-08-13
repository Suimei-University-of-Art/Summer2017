#include <iostream>
#include <bitset>
#include <vector>
#include <cstdlib>

using namespace std;

typedef bitset<200> bset;

vector<int> a, b, c;
int n;
bset s;

void gen(int done)
{
    if (done == n)
    {
        for (int i =0 ; i < n; i++)
            cout << a[i] << " ";
        cout << "\n";
        for (int i =0; i < n; i++)
            cout << b[i] << " ";
        cout << "\n";
        for (int i = 0; i < n; i++)
            cout << c[i] << " ";
        cout << "\n";
        exit(0);
    }
    for (int i = 3 * n - done; i >= n + 1; i--)
    {
        if (i == a[done] || !s[i])
            continue;
        int j = a[done] + i;
        if (!s[j])
            continue;
        b.push_back(i);
        c.push_back(j);
        s.reset(i);
        s.reset(j);
        gen(done + 1);
        s.set(i);
        s.set(j);
        b.pop_back();
        c.pop_back();
    }
}

int main()
{
    cin >> n;
    if (n % 4 == 2 || n % 4 == 3)
    {
        cout << "-1\n";
        return 0;
    }
    for (int i = n + 1; i <= 3 * n; i++)
        s.set(i);
    for (int i = n; i >= 1; i--)
        a.push_back(i);
    gen(0);
    cout << "-1\n";
}
