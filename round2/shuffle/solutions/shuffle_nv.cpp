#include <cstdio>
#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

struct node 
{
    int cnt, prio, value;
    node *l, *r;
};

vector<node> treap;
int t_cnt;
node *root;

int cnt(node *p) 
{
    return p ? p->cnt : 0;
}

void update(node *p) 
{
    p->cnt = 1 + cnt(p->l) + cnt(p->r);
}

void merge(node *&t, node *l, node *r) 
{
    if (!l) t = r; 
	else if (!r)  t = l;
		 else if (l->prio < r->prio) merge(l->r, l->r, r), t = l;
			else merge(r->l, l, r->l), t = r;
    update(t);
}

void split(node *t, node *&l, node *&r, int key) 
{
    if (!t) 
	{
        l = r = NULL;
        return;
    } 
	else if (key <= cnt(t->l)) split(t->l, l, t->l, key), r = t;
		else split(t->r, t->r, r, key - cnt(t->l) - 1), l = t;
    update(t);
}

int get(node *&t, int index) 
{
    if (index < cnt(t->l)) return get(t->l, index);
    else if (index > cnt(t->l)) return get(t->r, index - cnt(t->l) - 1);
    return t->value;
}

int get(int index) 
{
    return get(root, index);
}

void add(node *&t, node *it, int index) 
{
    if (!t) t = it;
    else if (it->prio < t->prio) split(t, it->l, it->r, index), t = it;
		else if (index <= cnt(t->l)) add(t->l, it, index);
			else add(t->r, it, index - cnt(t->l) - 1);
    update(t);
}

void add(int index, int value) 
{
    node *it = &treap[t_cnt];
    it->value = value;
    it->prio = (rand()<<15) + rand();
    ++t_cnt;
    add(root, it, index);
}

void buildTree(vector<int> b)
{
	treap.clear();
	treap.resize(b.size() + 3);
	t_cnt = 0;
	root = NULL;
	for (int i = 0; i < b.size(); i++)
		add(i, b[i]);
}


void move_to_back(int l, int r)
{
	node *t1, *t2, *t3;
	split(root, t1, t2, l - 1);
	split(t2, t2, t3, r - l + 1);
	merge(root, t1, t3);
	merge(root, root, t2);
}

struct Counter 
{
	vector<int> head, next, prev, key;
	int sum, max;
	Counter() {};
	Counter(vector<int> keys) 
	{
		key = keys;
        sum = 0;
		for (int i = 0; i < key.size(); i++) 
			sum += key[i];
		head.assign(key.size(), -1);
		next.assign(key.size(), -1);
		prev.assign(key.size(), -1);
        for (int i = 0; i < key.size(); i++) 
		{
			next[i] = head[key[i]];
            head[key[i]] = i;
        }
        for (int i = 0; i < key.size(); i++) 
			if (next[i] != -1) prev[next[i]] = i;
		max = head.size() - 1;
	}
	void distroy()
	{
		head.clear();
		next.clear();
		prev.clear();
		key.clear();
		sum = max = 0;
	}
    int getMaxCount() 
	{
		while (max >= 0 && head[max] == -1) max--;
        return max;
    }

    int getMaxKey() 
	{
		int maxCount = getMaxCount();
        if (maxCount == -1) return -1;
        return head[maxCount];
    }

    int getSum() 
	{
		return sum;
    }

    void decCount(int keys) 
	{
		if (next[keys] != -1) prev[next[keys]] = prev[keys];
        if (prev[keys] != -1) next[prev[keys]] = next[keys];
        if (head[key[keys]] == keys) head[key[keys]] = next[keys];
        key[keys]--;
        next[keys] = head[key[keys]];
        prev[keys] = -1;
        head[key[keys]] = keys;
        if (next[keys] != -1) prev[next[keys]] = keys;
        sum--;
    }
};

vector< pair<int, int> > moves;

void move(int l, int r)
{
	moves.push_back(make_pair(l, r));
	move_to_back(l + 1, r);
}

Counter conflictsCounter;
bool solveIfBasic(int length) 
{
	int sourceLength = length;
	int l = -1;
    int r = -1;
    bool tooManyMaxColor = false;
    int maxColor = conflictsCounter.getMaxKey();
    int maxColorCount = conflictsCounter.getMaxCount();
    int totalConflicts = conflictsCounter.getSum();
	if (maxColorCount > (totalConflicts + 1) / 2) tooManyMaxColor = true;
    if (maxColorCount < (totalConflicts + 1) / 2) return false;
	conflictsCounter.distroy();

    for (int i = 0; i < length; i++)
		if (get(i) == maxColor)
		{
			if (l == -1) l = i;
            r = i;
        }

    int remainVirtual = 0;
    if (tooManyMaxColor) 
	{
		int fatConflicts = maxColorCount;
		int otherConflicts = totalConflicts - fatConflicts;
		remainVirtual = fatConflicts - otherConflicts;
    }

    bool wasC = r < length - 1;
    bool found = true;
    while (found) 
	{
		found = false;
        for (int i = r + 1; i < length - 1; i++) 
			if (get(i) == get(i + 1) || remainVirtual > 0) 
			{
				if (get(i) != get(i + 1)) remainVirtual--;
                move(r, i + 1);
                length -= i + 1 - r;
                r--;
                found = true;
                break;
            }
	}

    if (tooManyMaxColor && l > 0 && wasC && length % 2 == 1 && maxColorCount + 1 == (sourceLength + 1) / 2 && remainVirtual > 0) 
	{
		move(l, l + 1);
        r--;
        length--;
        remainVirtual--;
    }

    found = true;
    while (found) 
	{
		found = false;
        for (int i = l - 2; i >= -1; i--)
			if ((i >= 0 && get(i) == get(i + 1)) || remainVirtual > 0) 
			{
				if (i >= 0 && get(i) != get(i + 1)) remainVirtual--;
                move(i + 1, l + 1);
                length -= l - i;
                int shift = l - i;
                l = l - shift + 1;
                r = r - shift;
				found = true;
                break;
            }
	}
	if (l < r) move(0, r);
    return true;
}

void solveOne()
{
	moves.clear();
    int n;
	cin >> n;
    vector<int> b(n);
    int cur = -1;
    int val = 0;
    for (int i = 0; i < n; i++) 
	{
		int x;
		cin >> x;
        if (x > cur) 
		{
			cur = x;
            val++;
        }
        b[i] = val;
    }
    buildTree(b);
    vector<int> numberCount(n+1);
    for (int i = 0; i < b.size(); i++)
		numberCount[b[i]]++;
     
    for (int i = 0; i < numberCount.size(); i++) 
		if (numberCount[i] > (n + 1) / 2) 
		{
			cout << "-1\n";
            return;
        }

    vector<int> conflictsCount(b.size());
    for (int i = 0; i < b.size() - 1; i++)
		if (b[i] == b[i + 1]) conflictsCount[b[i]]++;

    conflictsCounter = Counter(conflictsCount);
    int length = n;
    int lastPos = length - 2;
    while (true) 
	{
		if (solveIfBasic(length)) break;
        
        while (lastPos >= length - 1 || get(lastPos) != get(lastPos + 1) || get(lastPos - 1) == get(lastPos))
			lastPos--;
        
        int otherPos = lastPos - 1;
        while (get(otherPos) != get(otherPos - 1)) otherPos--;
            
		conflictsCounter.decCount(get(otherPos));
        conflictsCounter.decCount(get(lastPos));
        move(otherPos, lastPos + 1);
        length -= lastPos - otherPos + 1;
        lastPos = otherPos + 1;    
	}
    cout << moves.size() << endl;
    for (int i = 0; i < moves.size(); i++)
		cout << moves[i].first + 1 << " " << moves[i].second << "\n";
}


int main() 
{
	int n;
	cin >> n;
	for (int i = 0; i < n; i++)
		solveOne();
}