#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

static const int INF = 2000000000;

struct deque
{
    vector<int> v;
    int _begin, _end;
    int add;

    deque(const vector<int>& v) :
        v(v), 
        _begin(0),
        _end(v.size()),
        add(0)
    {}

    int peek_first() const
    {   return v[_begin] + add;}

    int peek_last() const
    {   return v[_end - 1] + add;}

    void pop_first() 
    {   ++_begin;}

    void pop_last()
    {   --_end;}

    void add_to_all(int a)
    {   add += a;}

    int size() const
    {   return _end - _begin;}
};

namespace std {
template<>
void swap<deque>(deque& d1, deque& d2) 
{
    swap(d1.v, d2.v);
    swap(d1._begin, d2._begin);
    swap(d1._end, d2._end);
    swap(d1.add, d2.add);
}
}

int main()
{
    int length;
    cin >> length;
    int n;
    cin >> n;
    vector<int> to_right_(n);
    for (int i = 0; i < n; i++)
        cin >> to_right_[i];
    int m;
    cin >> m;
    vector<int> to_left_(m);
    for (int i = 0; i < m; i++)
        cin >> to_left_[i];
    sort(to_right_.begin(), to_right_.end());
    sort(to_left_.begin(), to_left_.end());
    deque to_right(to_right_);
    deque to_left(to_left_);
    long long ans = 0;
    while (to_left.size() || to_right.size())
    {
        int left = to_left.size() ? to_left.peek_first() : INF;
        int right = to_right.size() ? (length - to_right.peek_last()) : INF;
        int mn = min(left, right);
        to_right.add_to_all(mn);
        to_left.add_to_all(-mn);
        ans += mn;
        if (left < right)
        {
            to_left.pop_first();
            swap(to_left, to_right);
        }
        else if (right < left)
        {
            to_right.pop_last();
            swap(to_left, to_right);
        }
        else
        {
            to_left.pop_first();
            to_right.pop_last();
        }
    }
    cout << ans << endl;
}

