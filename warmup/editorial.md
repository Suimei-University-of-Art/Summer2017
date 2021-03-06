# Warmup Round

## A. Rails

考虑和第1条直线平行的那些直线，答案肯定在这里面。于是枚举这个答案d，把那些平行的且距离是d的直线连边，最后得到一些链。如果每条链都是偶数长度，那么这个d可行，否则不可行。

## B. Square

要有解，一定是b <= r * 3 + 1或者r <= b * 3 + 1。就是一条黑白相间的链，然后上下伸出一些脚，直接构造就好了。

## C. Table

令sum[i,j]是以(i,j)为右下角的子矩形的和，化简下S的公式可以发现S和sum[n,m]，sum[n,c1]，sum[n, c2]，sum[r1, m]，sum[r2, m]的和的奇偶性有关。随便统计下就好了。

## D. Black John

可以观察到所有分母里面有11, 13, 17或19的分数之和必须要是个整数，否则最后不可能消掉这些因子。于是，可以按照分母分成5类搞。每一类对分母通分，然后搞个背包就好了。

## E. Teams Creation

按照a[i]的大小sort一下，然后可以把相同的归为同一类。令S[i,j]是[第二类Stirling number](https://en.wikipedia.org/wiki/Stirling_numbers_of_the_second_kind)，然后令dp[i][j][0/1/2/3]分别表示处理完前i类，总共恰好分了j组，这一类的分组状态是：0. 这一类有一些人和上一类在同一组但是没有人会和下一类在同一组；1. 这一类没有人和上一类在同一组但是会有些人和下一类在同一组；2. 这一类有一些人和上一类在同一组并且也有些人会和下一类在同一组；3. 这一类的人都之和自己类的人归为一组。

然后转移就是可以从0->1, 0->3, 1->0, 1->2, 2->0, 2->2, 3->1, 3->3. 有些系数需要自己算一算。

## F. Barbarians

考虑直接暴力做，每次删一条边的话，就从这个树中把较小的子树分裂出去。于是就和启发式合并差不多，复杂度是O(n log n)的。
