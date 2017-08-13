#include <iostream>
#include <vector>
#include <cstdlib>
#include <set>

using namespace std;

struct bitset
{
    long long X[128];
    long long hash;
    bitset()
    {
        a[0] = a[1] = 0;
        for (int i = 0; i < 128; i++)
            X[i] = (((long long)rand()) << 32) | rand();
        hash = 0;
    }

    void set(int i)
    {
        hash ^= X[i] * (1 - operator[](i));
        a[i >> 6] |= 1LL << (i & 63);
    }

    void reset(int i)
    {
        hash ^= X[i] * operator[](i);
        a[i >> 6] &= ~(1LL << (i & 63));
    }

    bool operator[](int i) const
    {
        return (a[i >> 6] >> (i & 63)) & 1;
    }

    long long a[2];
};

vector<int> a, b, c;
int n;
bitset s;
set<long long> used;

void gen(int remain, int start)
{
    if (used.count(s.hash))
    {
        cerr << ":)";
        return;
    }
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
    for (int i = 1; i < start / 2; i++)
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
    used.insert(s.hash);
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
