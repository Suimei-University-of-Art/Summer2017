#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include "testlib.h"
#define Maxn 1000001
#define Maxm 2000001

using namespace std;

typedef long long int int64;
typedef struct{
  int64 x,y;
  int id;
}Point;

typedef struct{
  int a,b;
}PPair;

Point P[Maxn];

int Turn(Point P0,Point P1,Point P2){
   int64 crossp=(P1.x-P0.x)*(P2.y-P0.y)-(P2.x-P0.x)*(P1.y-P0.y);
   if (crossp<0)
      return -1;
   else if (crossp>0)
      return 1;
   else
      return 0;
}
bool Between(Point P1,Point P2,Point Q){
//In: P1-P2-Q colinear
//Out: P1-Q-P2
   return   (abs(P1.x-Q.x)<=abs(P2.x-P1.x)) &&
            (abs(P2.x-Q.x)<=abs(P2.x-P1.x)) &&
            (abs(P1.y-Q.y)<=abs(P2.y-P1.y)) &&
            (abs(P2.y-Q.y)<=abs(P2.y-P1.y)) ;
}
bool Cross(Point p1, Point p2, Point q1, Point q2){
	int fpq1 = Turn(p1, p2, q1);
  	int fpq2 = Turn(p1, p2, q2);
  	int fqp1 = Turn(q1, q2, p1);
  	int fqp2 = Turn(q1, q2, p2);

  	return  (fpq1 * fpq2 < 0) && (fqp1 * fqp2 < 0) ||
         fpq1 == 0 && Between(p1, p2, q1) ||
         fpq2 == 0 && Between(p1, p2, q2) ||
         fqp1 == 0 && Between(q1, q2, p1) ||
         fqp2 == 0 && Between(q1, q2, p2);
}
bool SIntree(Point a, Point b, Point c, Point s){
   return Turn(a,b,s)>0 && Turn(b,c,s)>0 && Turn(c,a,s)>0 ;
}
bool Intree(Point a, Point b, Point c, Point s){
   return Turn(a,b,s)>=0 && Turn(b,c,s)>=0 && Turn(c,a,s)>=0 ;
}

int main(int argc, char **argv) {
  registerTestlibCmd(argc, argv);

  int n = inf.readInt();
  int a = inf.readInt();
  Point Q;
  Q.x = inf.readInt();
  Q.y = inf.readInt();
  for (int i = 1; i <= n; ++i) {
    P[i].x = inf.readInt();
    P[i].y = inf.readInt();
  }

  int b = ouf.readInt();
  int c = ouf.readInt();

  int b0 = ans.readInt();
  int c0 = ans.readInt();

  if (b == 0 && c == 0) {
    if (b0 == 0 && c0 == 0) {
      quitf(_ok, "OK");
    } else {
      quitf(_wa, "jury has a solution while participant not.");
    }
  }
  if (b < 1 || b > n || c < 1 || c > n || a == b || a == c || b == c) {
    quitf(_wa, "index range is not correct");
  }
  if (!SIntree(P[a], P[b], P[c], Q)) {
    quitf(_wa, "new tree is not inside the triangle.");
  }
  for (int i = 1; i <= n; ++i) {
    if (a != i && b != i && c != i && Intree(P[a], P[b], P[c], P[i])) {
      quitf(_wa, "old tree #%d is inside the triangle or on the border of the triangle", i);
    }
  }
  if (b0 == 0 && c0 == 0) {
    quitf(_fail, "participant has a solution while jury not.");
  }
  quitf(_ok, "OK");
  return 0;
}
