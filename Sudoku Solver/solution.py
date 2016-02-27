# python 3

inValue = input()  #".2..9.5.78.9.......7.4...29..4.....63....4..5....5941.2....7.....7..1.325.132..7."


def printPuzzle(puzzle):
    countout = 0
    for i in puzzle:
        countout += 1
        count = 0
        for j in i:
            count += 1
            print(j, end=" ")
            if count % 3 == 0 and count != 9:
                print("|", end="")
        print("")
        if countout % 3 == 0 and countout != 9:
            print("---------------------")


def outPuzzle(puzzle):
    final = ""
    for i in puzzle:
        for j in i:
            final += j
    return final


def checkPuzzle(puzzle):
    for i in range(0, 9):
        rowValues = []
        colValues = []
        sqValues = []
        for j in range(0,9):
            row = puzzle[i][j]
            col = puzzle[j][i]
            sq = puzzle[(i % 3)*3 + int(j/3)][j % 3 + i - i % 3]
            if (row != '.' and row in rowValues) or (col != '.' and col in colValues) or (sq != '.' and sq in sqValues):
                return False
            rowValues.append(row)
            colValues.append(col)
            sqValues.append(sq)

    return True


def getDots(puzzle):
    dots = []
    for i in range(0, 9):
        for j in range(0, 9):
            if puzzle[i][j] == '.':
                dots.append([i, j])
    return dots


def isFinished(puzzle):
    for i in puzzle:
        for j in i:
            if j == '.':
                return False

    return checkPuzzle(puzzle)


puzzle = []
puzzle.append
count = 0
for i in inValue:
    if count % 9 == 0:
        puzzle.append([])
    puzzle[int(count / 9)].append(i)
    count += 1

dots = getDots(puzzle)

count = 0
while not(isFinished(puzzle)):
    dot = dots[count]
    if puzzle[dot[0]][dot[1]] == '9':
        puzzle[dot[0]][dot[1]] = '.'
        count -= 1
        continue
    elif puzzle[dot[0]][dot[1]] == '.':
        puzzle[dot[0]][dot[1]] = '1'
    else:
        puzzle[dot[0]][dot[1]] = str(int(puzzle[dot[0]][dot[1]]) + 1)

    if checkPuzzle(puzzle):
        count += 1

print(outPuzzle(puzzle))
