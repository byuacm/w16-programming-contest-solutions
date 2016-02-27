import math


def getQuads(n):
    quads = set()
    second = set()
    combinations = createCombo(n)
    for x in range(int(n/2)+1):
        y = n - x
        if x in combinations and y in combinations:
            second.add(x)
            xSquares = getSquares(x)
            ySquares = getSquares(y)
            for xs in xSquares:
                for ys in ySquares:
                    quads.add(frozenset(xs+ys))
    return quads


def getSquares(n):
    squares = []
    x = int(math.sqrt(n))
    y = int(math.sqrt(n-x*x))
    while y <= x:
        if x*x + y*y == n:
            squares.append([x, y])
        if x == 0:
            break
        x -= 1
        y = int(math.sqrt(n-x*x))
    return squares


def createCombo(n):
    combo = set()
    x = int(math.sqrt(n)) + 1
    for i in range(x):
        for j in range(int(math.sqrt(n-i*i))+1):
            combo.add(i*i + j*j)
    return combo

print(len(getQuads(int(raw_input()))))
