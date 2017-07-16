#include <iostream>
#include <cstdio>
#include <vector>
#include <string>
#include <algorithm>
#include <cmath>

using namespace std;

const int MAX_SIZE = 200000;

int fre = 1;
int go[MAX_SIZE][26];
vector<bool> top(MAX_SIZE);

void addString(const string & s)
{
	int p = 0;
	for (int i = 0; i < s.length(); i++)
	{
		int ic = s[i] - 'a';
		if (go[p][ic] == -1)
			go[p][ic] = fre++;
		p = go[p][ic];
	}
	top[p] = true;
}

bool check(const string & s)
{
	int si = s.length();
	int uk = 0;
	int p = 0;
	int t = 0;
	int p1 = 0;
	while (uk < si)
	{
		p = 0;
		bool f = true;
		while (uk < si && go[p][s[uk] - 'a'] != -1)
		{
			p = go[p][s[uk] - 'a'];
			if (top[p]) 
			{
				t = uk + 1;
				f = false;
			}
			uk++;
		}
		if (f) return true;
		uk = t;
	}
	return !(top[p]);
}

bool comp(const string & a, const string & b)
{
	return (a.length() < b.length()) || (a.length() == b.length() && a < b);
}

int main()
{
//	freopen("parse.in", "r", stdin);
//	freopen("parse.out", "w", stdout);
	for (int i = 0; i < MAX_SIZE; i++)
		for (int j = 0; j < 26; j++)
			go[i][j] = -1;
	int n;
	cin >> n;
	vector<string> s(n);
	for (int i = 0; i < n; i++)
		cin >> s[i];
	for (int i = 0; i < n; i++)
		addString(s[i]);
	s.push_back("");
	top[0] = true;
	sort(s.begin(), s.end(), comp);
	string ans = "-1";
    int si = (min(n, (int)sqrt(n) + 5));
	for (int i = 0; i <= si; i++)
		for (int j = 0; j <= si; j++)
			for (int k = 0; k <= si; k++)
                for (int l = 0; l <= si; l++)
			    {
				    string t = s[i] + s[j] + s[k] + s[l];
                    if (ans != "-1" && t.length() > ans.length()) continue;
				    if (check(t))
					   if (ans == "-1" || ans.length() > t.length())
				           ans = t;
			    }
	if (ans == "-1") cout << "Good vocabulary!\n";
	else cout << ans;
}
