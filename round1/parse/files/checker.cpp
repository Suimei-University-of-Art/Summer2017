#include "testlib.h"
#include <string>
#include <set>
#include <vector>

using ull = unsigned long long;
const int N = 100000 + 10, seed = 65537;

ull pw[N];

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);
  std::string pans = ouf.readLine();
  std::string jans = ans.readLine();
  if (pans.length() == 0) {
    quitf(_wa, "Empty file");
  }
  if (pans == "Good vocabulary!") {
    if (jans == "Good vocabulary!") {
      quitf(_ok, "Solution doesn't exist");
    } else {
      quitf(_wa, "Contestant hasn't found existing solution");
    }
  }
  if (pans.length() > 100000) {
    quitf(_wa, "Contestant's answer is too long");
  }
  for (auto &&c: pans) {
    if (c < 'a' || c > 'z') {
      quitf(_wa,  "Contestant's answer has wrong character: %c", c);
    }
  }
  std::set<ull> voc, pre_voc;
  int n = inf.readInt();
  std::vector<std::string> s(n);
  for (int i = 0; i < n; ++i) {
    s[i] = inf.readToken();
    ull h = 0;
    for (int j = 0; j < s[i].size(); ++j) {
      h = h * seed + s[i][j];
      pre_voc.insert(h);
    }
    voc.insert(h);
  }
  pw[0] = 1;
  for (int i = 1; i < N; ++i) {
    pw[i] = pw[i - 1] * seed;
  }
  std::vector<ull> tH(pans.length() + 1);
  std::vector<bool> good(pans.length() + 1);
  good[0] = true;
  for (int i = 1; i <= pans.length(); ++i) {
    tH[i] = tH[i - 1] * seed + pans[i - 1];
    for (int j = 0; j < n; ++j) {
      int len = s[j].length();
      if (len > i) continue;
      if (!good[i - len]) continue;
      if (!voc.count(tH[i] - tH[i - len] * pw[len])) continue;
      good[i] = true; break;
    }
  }
  if (!good[pans.length()]) {
    quitf(_wa, "Contestant's answer isn't made from words from vocabulary");
  }
  ull th = 0;
  int tl = -1, l = 0;
  for (int i = 0; i < pans.length(); ++i) {
    th = th * seed + pans[i];
    ++l;
    if (voc.count(th)) tl = i;
    if (!pre_voc.count(th) || (i == pans.length() - 1)) {
      if (i - tl == l) break;
      l = 0, th = 0, i = tl;
    }
  }
  bool g = tl == pans.length() - 1;
  if (jans == "Good vocabulary!" && !g) {
    quitf(_fail, "Contestant has found answer, but jury hasn't");
  }
  if (g) {
    quitf(_wa, "Contestant's answer can be parsed with wrong parser");
  }
  if (pans.length() < jans.length()) {
    quitf(_fail, "Contestant's answer is shorter, then jury's");
  }
	if (pans.length() > jans.length()) {
    quitf(_wa, "Contestant's answer is longer, then jury's");
  }
  quitf(_ok, "Contestant's answer is good and has right length");
  return 0;
}
