#include "testlib.h"
#include <vector>
#include <set>
#include <algorithm>
#include <cassert>

struct pii {
  int first, second;
  pii() {}
  pii(int a, int b): first(a), second(b) {
    if (first > second) std::swap(first, second);
  }
  bool operator < (const pii &rhs) const {
    return first < rhs.first || (first == rhs.first && second < rhs.second);
  }
};

std::vector<pii> data;
std::set<pii> edges;

void print(int n) {
  int m = data.size();
  printf("%d %d\n", n, m);
  shuffle(data.begin(), data.end());
  for (int i = 0; i < m; ++i) {
    printf("%d %d\n", data[i].first + 1, data[i].second + 1);
  }
}

void genFullGraph(int pos, int size) {
	for (int i = 0; i < size; i++) {
		for (int j = i + 1; j < size; j++) {
      pii e(pos + i, pos + j);
      if (!edges.count(e)) {
        data.push_back(e);
        edges.insert(e);
      }
    }
  }
}

void genBamboo(int pos, int size) {
	for (int i = 1; i < size; i++) {
		pii e(pos + i, pos + i - 1);
		if (!edges.count(e)) {
			data.push_back(e);
			edges.insert(e);
		}
	}
}

void genCycle(int pos, int size) {
	genBamboo(pos, size);
	pii e = {pos, pos + size - 1};
	if (!edges.count(e)) {
		data.push_back(e);
		edges.insert(e);
	}
}

void genTree(int pos, int size) {
	for (int i = 1; i < size; i++) {
		pii e(pos + i, pos + rnd.next(i));
		if (!edges.count(e)) {
			data.push_back(e);
			edges.insert(e);
		}
	}
}

void genRand(int pos, int size, int count) {
	genTree(pos, size);
	for (int i = 0; i < count; i++) {
		int t1 = rnd.next(size);
		int t2 = rnd.next(size);
		while (t1 == t2) {
			t2 = rnd.next(size);
    }
		pii e(pos + t1, pos + t2);
		while (edges.count(e)) {
      t1 = rnd.next(size);
  		t2 = rnd.next(size);
  		while (t1 == t2) {
  			t2 = rnd.next(size);
      }
			e = {pos + t1, pos + t2};
		}
		data.push_back(e);
		edges.insert(e);
	}
}

void connection(int pos1, int size1, int pos2, int size2, int count) {
	for (int i = 0; i < count; i++) {
		int t1 = rnd.next(size1);
		int t2 = rnd.next(size2);
		pii e(pos1 + t1, pos2 + t2);
		while (edges.count(e)) {
			t1 = rnd.next(size1);
			t2 = rnd.next(size2);
			e = {pos1 + t1, pos2 + t2};
		}
		data.push_back(e);
		edges.insert(e);
	}
}

int main(int argc, char **argv) {
  registerGen(argc, argv, 1);
  int cas = std::atoi(argv[1]);
  assert(cas >= 2 && cas <= 25);
	if (cas == 2) {
    genCycle(0, 7);
		print(8);
  }
  if (cas == 3) {
    genFullGraph(0, 100);
    for (int i = 1; i < 40; i++){
      genFullGraph(i * 100, 100);
      connection((i - 1) * 100, 100, i * 100, 100, 1);
    }
    print(4000);
  }
  if (cas == 4) {
    genTree(0, 50000);
		genTree(50000, 50000);
		connection(0, 50000, 50000, 50000, 100000);
		print(100000);
  }
  if (cas == 5) {
    print(100000);
  }
  if (cas == 6) {
    genBamboo(0, 50000);
		genCycle(50000, 50000);
		print(100000);
  }
  if (cas >= 7 && cas <= 12) {
    int i = (cas - 7) / 2;
    if ((cas - 7) % 2) {
      genRand(0, 9, 5 + 2 * i);
			print(9 + i);
    } else {
      genFullGraph(0, 5);
			print(5 + i);
    }
  }
  if (cas >= 13 && cas <= 18) {
    int si = 99999;
    int i = (cas - 13) / 3;
    int m = (cas - 13) % 3;
    if (m == 0) genBamboo(0, si);
    if (m == 1) genCycle(0, si);
    if (m == 2) genRand(0, si, si / 2);
    print(si + i);
  }
  if (cas == 19) {
		genFullGraph(0, 400);
		genFullGraph(400, 480);
		connection(0, 400, 400, 480, 10);
		print(980);
  }
  if (cas == 20) {
		genBamboo(0, 50000);
		genCycle(50000, 50000);
		print(100000);
  }
  if (cas == 21) {
		genBamboo(0, 50000);
		genCycle(50000, 50000);
		connection(0, 50000, 50000, 50000, 10);
		print(100000);
  }
  if (cas == 22) {
    genBamboo(0, 50000);
		genBamboo(50000, 50000);
		connection(0, 50000, 50000, 50000, 2);
		print(100000);
  }
  if (cas == 23) {
		genTree(0, 50000);
		genTree(50000, 50000);
		connection(0, 50000, 50000, 50000, 100000);
		print(100000);
  }
  if (cas == 24) {
    genTree(0, 50000);
		genFullGraph(50000, 400);
		connection(0, 50000, 50000, 400, 50);
		print(50400);
  }
  if (cas == 25) {
    genBamboo(0, 50000);
		genFullGraph(50000, 400);
		connection(0, 50000, 50000, 400, 50);
		print(50400);
  }
  return 0;
}
