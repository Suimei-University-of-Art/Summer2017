#include "testlib.h"
#include <string>

const int MAXN = 100000 + 10;

bool square[MAXN];

bool check(std::vector<int> &v, int n) {
  std::sort(v.begin(), v.end());

  int sum=v[0];

  for (size_t i = 1; i < v.size(); ++i) {
    if (v[i] == v[i - 1]) return false;
    sum+=v[i];
  }
  
  if (sum != n) return false;
  
  for (auto &&x: v) {
    if (square[n - x]) return true;
  }
  
  return false;
}

int main(int argc, char *argv[]) {
  registerTestlibCmd(argc, argv);

  for (int i = 1; i * i < MAXN; ++i) {
    square[i * i] = true;
  }

  int T = inf.readInt();
  for (int cas = 1; cas <= T; ++cas) {
    int n = inf.readInt();
    int k = inf.readInt();
    std::vector<int> val(k);

    std::string jury_ans = ans.readToken();
    std::string user_ans = ouf.readToken();
    for (auto &&c: jury_ans) c = std::tolower(c);
    for (auto &&c: user_ans) c = std::tolower(c);

    if (user_ans == "yes") {
      for (int i = 0; i < k; ++i) {
        val[i] = ouf.readInt(1,n);
      }
    }
    else if (user_ans != "no") quitf (_pe, "yes or no expected, %s found",user_ans.c_str());

    if (user_ans == "yes" && !check(val, n)) {
      quitf(_wa, format("participant's output are not correct on case #%d.", cas).c_str());
    }
    
    
    if (jury_ans != user_ans) {
      if (jury_ans == "yes") {
        quitf(_wa, format("jury has a solution while participant doesn't on case #%d.", cas).c_str());
      }
      quitf(_fail, format("participant has a solution while jury doesn't on case #%d.", cas).c_str());
    }
    if (jury_ans == "yes") {
      for (int i = 0; i < k; ++i) {
        ans.readInt();
      }
    }
  }
  quitf(_ok, "ok\n");
  return 0;
}
