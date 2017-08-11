#include "testlib.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <algorithm>
#include <string>
#include <cstring>
#include <cstdio>
#include <set>
#include <cassert>
#include <cmath>

using namespace std;

typedef long long int ll;
typedef long double ld;

const int maly = 30 * 1000;

inline ll rand_range(ll a,ll b) {
  return rnd.next(a, b);
}

inline int rand_range(int a,int b) {
  return rnd.next(a, b);
}

int rand_range_without(int a,int b,int c) {
  if (a==b && b==c) printf("rand_rang_without(%d,%d,%d)\n",a,b,c);
  while (true) {
    int x = rand_range(a, b);
    if (x != c) return x;
  }
}

inline int rand_bool() {
  return rand_range(0, 1);
}

class Prostokat
{
public:
	int x1, y1, x2, y2;

	Prostokat(int x1, int y1, int x2, int y2) : x1(x1), y1(y1), x2(x2), y2(y2) {}
	friend ostream& operator << (ostream &out, const Prostokat &prostokat)
	{
		out << prostokat.x1 << " " << prostokat.y1 << " " << prostokat.x2 << " " << prostokat.y2;
		return out;
	}
	Prostokat outer() const
	{
		return Prostokat(x1 - 1, y1 - 1, x2 + 1, y2 + 1);
	}
	static Prostokat losowy(int min_wsp, int max_wsp)
	{
		int x1 = rand_range(min_wsp, max_wsp);
		int x2 = x1; while(x2 == x1)
			x2 = rand_range(min_wsp, max_wsp);;
		int y1 = rand_range(min_wsp, max_wsp);
		int y2 = y1;
		while(y2 == y1)
			y2 = rand_range(min_wsp, max_wsp);;
		return Prostokat(min(x1, x2), min(y1, y2), max(x1, x2), max(y1, y2));
	}
};

class TestCase
{
public:
	friend ostream& operator << (ostream& out, const TestCase& testCase)
	{
		out << testCase.hanged.size() << "\n";
		for(Prostokat p : testCase.hanged)
			out << p << "\n";
		out << testCase.newp.size() << "\n";
		for(Prostokat p : testCase.newp)
			out << p << "\n";
    return out;
	}
	const vector<Prostokat> &getHanged() const { return hanged; }
	const vector<Prostokat> &getNewp() const { return newp; }

protected:
	TestCase() {}
	vector<Prostokat> hanged, newp;
};

class SpecialCase1 : public TestCase
{
public:
	SpecialCase1()
	{
		hanged.push_back(Prostokat(0, 0, 5, 4));
		newp.push_back(Prostokat(0, 0, 5, 4));
	}
};

class SpecialCase2 : public TestCase
{
public:
	SpecialCase2()
	{
		hanged.push_back(Prostokat(1, 1, 2, 2));
		for(int i = 0; i <= 2; i++)
			for(int j = 0; j <= 2; j++)
				if(i != 1 || j != 1)
					newp.push_back(Prostokat(i, j, i + 1, j + 1));
	}
};

class RandomCase : public TestCase
{
public:
	RandomCase(int n, int m, int min_wsp, int max_wsp)
	{
		for(int i = 0; i < n; i++)
			hanged.push_back(Prostokat::losowy(min_wsp, max_wsp));
		for(int i = 0; i < m; i++)
			newp.push_back(Prostokat::losowy(min_wsp, max_wsp));
	}
};

class PaskiCase : public TestCase
{
public:
	PaskiCase(int n, int m, int min_wsp, int max_wsp)
	{
		for(int i = 0; i < n; i++)
		{
			int y = rand_range(min_wsp, max_wsp - 2);
			hanged.push_back(Prostokat(min_wsp, y, max_wsp, y + rand_range(1, 2)));
		}
		for(int i = 0; i < m; i++)
		{
			int x = rand_range(min_wsp, max_wsp - 2);
			newp.push_back(Prostokat(x, min_wsp, x + rand_range(1, 2), max_wsp));
		}
	}
};

class InnerRandomCase : public TestCase
{
public:
	InnerRandomCase(int n, int min_wsp, int max_wsp)
	{
		for(int i = 0; i < n; i++)
		{
			Prostokat p = Prostokat::losowy(min_wsp + 2, max_wsp - 2);
			newp.push_back(p);
			hanged.push_back(p.outer());
			newp.push_back(p.outer().outer());
		}
	}
};

class DolosujCase : public TestCase
{
public:
	DolosujCase(const TestCase &testCase, int ile, int min_wsp, int max_wsp, int typ = 0)
	{
		hanged = testCase.getHanged();
		newp = testCase.getNewp();
		if(typ <= 0)
		{
			for(int i = 0; i < ile; i++)
				hanged.push_back(Prostokat::losowy(min_wsp, max_wsp));
		}
		if(typ >= 0)
		{
			for(int i = 0; i < ile; i++)
				newp.push_back(Prostokat::losowy(min_wsp, max_wsp));
		}
	}
};

class RamkaCase : public TestCase
{
public:
	RamkaCase(const TestCase &testCase)
	{
		hanged = testCase.getHanged();
		newp = testCase.getNewp();
		int mnx = 1000 * 1000 * 1000, mxx = 0;
		int mny = mnx, mxy = mxx;
		for(Prostokat p : hanged)
		{
			mnx = min(mnx, p.x1); mxx = max(mxx, p.x2);
			mny = min(mny, p.y1); mxy = max(mxy, p.y2);
		}
		for(Prostokat p : newp)
		{
			mnx = min(mnx, p.x1); mxx = max(mxx, p.x2);
			mny = min(mny, p.y1); mxy = max(mxy, p.y2);
		}
		newp.push_back(Prostokat(mnx, mny, mxx, mxy));
		shuffle(newp.begin(), newp.end());
	}
};

int main(int argc, char* argv[]) {
  registerGen(argc, argv, 1);
  int group = std::atoi(argv[1]);
  int cas = std::atoi(argv[2]);
  if (group == 1) {
    assert(0 <= cas && cas < 4);
    const TestCase t[] = {
      SpecialCase1(),
			SpecialCase2(),
			RandomCase(10, 10, 0, 4), // many posters on a small wall
			RandomCase(10, 10, 0, 100), // large wall
    };
    std::cout << t[cas];
  } else if (group == 2) {
    assert(0 <= cas && cas < 3);
    const TestCase t[] = {
			DolosujCase(PaskiCase(47, 47, 0, 1000 * 1000 * 1000), 3, 0, 1000 * 1000 * 1000),
			RamkaCase(DolosujCase(PaskiCase(47, 46, 0, 100), 3, 0, 1000 * 1000 * 1000)),
			RamkaCase(RandomCase(50, 49, 0, 1000 * 1000 * 1000)),
    };
    std::cout << t[cas];
  } else if (group == 3) {
    assert(0 <= cas && cas < 2);
    const TestCase t[] = {
			RamkaCase(DolosujCase(InnerRandomCase(199, 0, maly), 201, 0, maly, -1)),
			RandomCase(400, 400, 0, maly),
    };
    std::cout << t[cas];
  } else if (group == 4) {
    assert(0 <= cas && cas < 3);
    const TestCase t[] = {
			RamkaCase(PaskiCase(1000, 999, 0, 1000 * 1000 * 1000)),
			RandomCase(512, 512, 0, 1000 * 1000 * 1000),
			RandomCase(1000, 1000, 0, 1000 * 1000 * 1000),
    };
    std::cout << t[cas];
  } else if (group == 5) {
    assert(0 <= cas && cas < 3);
    const TestCase t[] = {
			RamkaCase(PaskiCase(10000, 9999, 0, maly)),
			RandomCase(8192, 8192, 0, maly),
			RandomCase(10000, 10000, 0, maly),
    };
    std::cout << t[cas];
  } else if (group == 6) {
    assert(0 <= cas && cas < 3);
    const TestCase t[] = {
			RamkaCase(DolosujCase(InnerRandomCase(4999, 0, 1000 * 1000 * 1000), 5001, 0, 1000 * 1000 * 1000, -1)),
			RandomCase(8192, 8192, 0, 1000 * 1000 * 1000),
			RandomCase(10000, 10000, 0, 1000 * 1000 * 1000),
    };
    std::cout << t[cas];
  } else if (group == 7) {
    assert(0 <= cas && cas < 2);
    const TestCase t[] = {
			RamkaCase(DolosujCase(InnerRandomCase(49999, 0, maly), 50001, 0, maly, -1)),
			RandomCase(100000, 100000, 0, maly),
    };
    std::cout << t[cas];
  } else if (group == 8) {
    assert(0 <= cas && cas < 2);
    const TestCase t[] = {
			DolosujCase(RamkaCase(PaskiCase(100000 - 100, 99999 - 100, 0, 1000 * 1000 * 1000)), 100, 0, 1000 * 1000 * 1000),
			RandomCase(100000, 100000, 0, 1000 * 1000 * 1000),
    };
    std::cout << t[cas];
  } else {
    assert(false);
  }
  return 0;
}
