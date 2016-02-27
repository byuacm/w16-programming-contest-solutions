import heapq

num_buildings = int(raw_input())
pbuildings = []
for x in range(0, num_buildings):
    line = map(int, raw_input().split())
    pbuildings.append(line)

def getSkyline(buildings):
    events = sorted([(L, -H, R) for L, R, H in buildings] + list({(R, 0, None) for _, R, _ in buildings}))
    res, hp = [[0, 0]], [(0, float("inf"))]
    for x, negH, R in events:
        while x >= hp[0][1]: 
            heapq.heappop(hp)
        if negH: 
            heapq.heappush(hp, (negH, R))
        if res[-1][1] + hp[0][0]:
            res += [x, -hp[0][0]],
    return res[1:]


thing = getSkyline(pbuildings)
for x in thing:
    print('%d, %d' % (x[0], x[1]))