#!/usr/bin/python

minimal_group_size = int(raw_input())
num_pairs = int(raw_input())
data = []
for x in range(0, num_pairs):
  line = raw_input().replace('\r', '').split(' - ')
  data.append(line)

n = {}
for a, b in data:
  if a not in n: n[a] = set()
  if b not in n: n[b] = set()
  n[a].add(b)
  n[b].add(a)

answers = []
def bk(r, p, x):
  if not p and not x:
    if len(r) >= minimal_group_size:
      answers.append(tuple(sorted(list(r))))
  for v in p:
    bk(r | set([v]), p & n[v], x & n[v])
    p = p - set([v])
    x = x | set([v])


bk(set(), set(n.keys()), set())
answers.sort()
for x in answers:
  my_str = ''
  for y in x:
    my_str += y + ', '
  print(my_str[:-2])
