#include <stdio.h>
#include <memory.h>
#include <set>
#include <vector>
#include <map>
#include <algorithm>
#include <stdlib.h>

using namespace std;

int A,B,n,i,j;
pair <int,pair<int,int> > a[1010];
vector <pair<int,int> > d;
char p[1010][100010];
vector <int> c;
int f[100010];

int main() {
  scanf("%d %d %d",&A,&B,&n);
  for (i=0;i<n;i++) {
    scanf("%d %d",&a[i].second.first,&a[i].first);
    a[i].second.second = i;
  }
  sort(a,a+n);
  for (i=0;i<=A;i++) f[i] = B;
  int ans = 0, ai = 0, aj = 0;
  for (i=0;i<n;i++) {
    c.clear();
    for (j=i;j<n;j++) c.push_back(a[j].second.first);
    sort(c.begin(),c.end());
    c.push_back(A+A);
    int q = 0, tot = 0;
    int x = a[i].first, y = a[i].second.first;
    for (j=0;j<=A;j++) {
      if (f[j] >= x) break;
      if (j >= tot+c[q]) tot += c[q], q++;
      if (q > ans) {
        ans = q;
        ai = i; aj = j;
      }
    }
    for (j=0;j<=A;j++) p[i][j] = 0;
    for (j=y;j<=A;j++)
      if (f[j] >= x && f[j]-x < f[j-y]) {
        f[j-y] = f[j]-x;
        p[i][j-y] = 1;
      }
  }
  printf("%d\n",ans);
  c.clear();
  j = aj;
  for (i=ai;i>0;i--)
    if (p[i-1][j]) {
      c.push_back(a[i-1].second.second);
      j += a[i-1].second.first;
    }
  d.clear();
  for (i=ai;i<n;i++) d.push_back(make_pair(a[i].second.first,a[i].second.second));
  sort(d.begin(),d.end());
  for (i=0;i<d.size();i++)
    if (aj >= d[i].first) {
      c.push_back(d[i].second);
      aj -= d[i].first;
    }
  sort(c.begin(),c.end());
  printf("%d",c.size());
  for (i=0;i<c.size();i++) printf(" %d",c[i]+1);
  printf("\n");
  return 0;
}
