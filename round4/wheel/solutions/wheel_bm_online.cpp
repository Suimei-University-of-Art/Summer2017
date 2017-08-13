#include <iostream>
#include <algorithm>
#include <cmath>
#include "stdio.h"

const int MAX = 115;
double res[MAX];
double dp[2][MAX][MAX][MAX];

double get_answer(int n) {
   if (n < MAX) {
		return res[n];
   }
   return (n + 2.) / 2;
}

void precalc() {
    dp[0][0][0][0] = 0.5;
    for (int used = 0; used < MAX - 1; used++) {
        for (int cntOnes = 0; cntOnes < MAX; cntOnes++) {
            for (int maxZeros = 0; maxZeros < MAX; maxZeros++) {
                for (int curZeros = 0; curZeros <= maxZeros; curZeros++) {
                    double curDpHalf = 0.5 * dp[0][cntOnes][maxZeros][curZeros];
                    if (curDpHalf == 0) {
                        continue;
                    }
                    dp[1][cntOnes + 1][maxZeros][0] += curDpHalf;
                    dp[1][cntOnes][std::max(maxZeros, curZeros + 1)][curZeros + 1] += curDpHalf;
                }
            }
        }
        double result = ((used + 1) + 0.) / pow(2., used + 1);
        for (int cntOnes = 0; cntOnes < MAX; cntOnes++) {
            for (int maxZeros = 0; maxZeros < MAX; maxZeros++) {
                for (int curZeros = 0; curZeros <= maxZeros; curZeros++) {
                    double curDp = dp[0][cntOnes][maxZeros][curZeros];
                    result += (curZeros + 1) * curDp
                            * std::max(cntOnes + 2, maxZeros);
                }
            }
        }
        res[used + 1] = result;
        for (int cntOnes = 0; cntOnes < MAX; cntOnes++) {
            for (int maxZeros = 0; maxZeros < MAX; maxZeros++) {
                for (int curZeros = 0; curZeros <= maxZeros; curZeros++) {
                    dp[0][cntOnes][maxZeros][curZeros] = dp[1][cntOnes][maxZeros][curZeros];
                    dp[1][cntOnes][maxZeros][curZeros] = 0;
                }
            }
        }
    }
}

  
int main() {
	precalc();
    int testNumber;
	std::cin >> testNumber;
    for (int test = 0; test < testNumber; test++) {
        int n;
		std::cin >> n;
		printf("%.18f\n", get_answer(n));
    }
}