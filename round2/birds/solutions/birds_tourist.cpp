#include <stdio.h>
#include <iostream>
#include <memory.h>
#include <set>
#include <vector>
#include <map>
#include <algorithm>
#include <stdlib.h>

using namespace std;

set < pair<long long, long long> > a,b;
set < pair<long long, long long> >::iterator it;
vector < pair <long long, long long> > c;
long long n,m,i,L,tt;
long long x[100010], y[100010];
long long nx[200010], when[200010];
long long ans1[100010], ans2[100010];

int main() {
  a.clear(); b.clear();
  tt = 0;
  cin >> L >> n;
  for (i=0;i<n;i++) {
    cin >> x[i];
/*    if (x[i] == 0 || x[i] == L) {
      nx[tt] = ((x[i] == 0) ? -1 : 1);
      when[tt] = 0;
      tt++;
    }
    else*/ b.insert(make_pair(x[i],i));
  }
  cin >> m;
  for (i=0;i<m;i++) {
    cin >> y[i];
/*    if (y[i] == 0 || y[i] == L) {
      nx[tt] = ((y[i] == 0) ? -1 : 1);
      when[tt] = 0;
      tt++;
    }
    else*/ a.insert(make_pair(y[i],i));
  }
  long long shift = 0, cur = 0;
  for (;tt<n+m;tt++)
    if (tt % 2 == 0) {
      bool both = false;
      long long z = (long long)2e9, w = 0;
      it = a.begin();
      if (it != a.end())
        if ((*it).first-shift < z) {
          z = (*it).first-shift;
          w = -1;
        }
      it = b.end();
      if (it != b.begin()) {
        it--;
        long long ft = L-((*it).first+shift);
        if (ft < z) {
          z = ft;
          w = 1;
        } else
        if (ft == z) both = true;
      }
      cur += z;
      shift += z;
      nx[tt] = w;
      when[tt] = cur;
      if (w == -1) {
        a.erase(a.begin());
      } else {
        it = b.end(); it--;
        b.erase(it);
      }
      if (both) {
        tt++;
        nx[tt] = -w;
        when[tt] = cur;
        if (w == 1) {
          a.erase(a.begin());
        } else {
          it = b.end(); it--;
          b.erase(it);
        }
      }
    } else {
      bool both = false;
      long long z = (long long)2e9, w = 0;
      it = b.begin();
      if (it != b.end())
        if ((*it).first+shift < z) {
          z = (*it).first+shift;
          w = -1;
        }
      it = a.end();
      if (it != a.begin()) {
        it--;
        long long ft = L-((*it).first-shift);
        if (ft < z) {
          z = ft;
          w = 1;
        } else
        if (ft == z) both = true;
      }          
      cur += z;
      shift -= z;
      nx[tt] = w;
      when[tt] = cur;
      if (w == -1) {
        b.erase(b.begin());
      } else {
        it = a.end(); it--;
        a.erase(it);
      }
      if (both) {
        tt++;
        nx[tt] = -w;
        when[tt] = cur;
        if (w == 1) {
          b.erase(b.begin());
        } else {
          it = a.end(); it--;
          a.erase(it);
        }
      }
    }
  c.clear();
  for (i=0;i<n;i++) c.push_back(make_pair(x[i],i)), ans1[i] = 0;
  for (i=0;i<m;i++) c.push_back(make_pair(y[i],-i-1)), ans2[i] = 0;
  sort(c.begin(),c.end());
  long long ll = 0, rr = n+m-1;
  for (tt=0;tt<n+m;tt++)
    if (nx[tt] == -1) {
      long long z = c[ll].second;
      if (z >= 0) ans1[z] = when[tt];
      else ans2[-z-1] = when[tt];
      ll++;
    } else {
      long long z = c[rr].second;
      if (z >= 0) ans1[z] = when[tt];
      else ans2[-z-1] = when[tt];
      rr--;
    }
  for (i=0;i<n-1;i++) cout << ans1[i] << " ";
  if (n > 0) cout << ans1[n-1];
  cout << endl;
  for (i=0;i<m-1;i++) cout << ans2[i] << " ";
  if (m > 0) cout << ans2[m-1];
  cout << endl;
  return 0;
}
