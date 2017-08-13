#include <iostream>
#include <bitset>
#include <vector>
#include <cstdlib>

using namespace std;

typedef bitset<200> bset;

vector<int> a, b, c;
int n;
bset s;

void gen(int remain, int start)
{
    if (remain == 0)
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
    while (!s[start])
        start--;
    for (int i = 1; i < start; i++)
    {
        int j = start - i;
        if (i == j || !s[i] || !s[j])
            continue;
        a.push_back(i);
        b.push_back(j);
        c.push_back(i + j);
        s.reset(i);
        s.reset(j);
        s.reset(i + j);
        gen(remain - 1, start-1);
        s.set(i);
        s.set(j);
        s.set(i + j);
        a.pop_back();
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
    for (int i = 1; i <= 3 * n; i++)
        s.set(i);
    gen(n, 3 * n);
    cout << "-1\n";
}
