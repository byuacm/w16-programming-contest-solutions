data_in = raw_input()
data_split = [x.strip() for x in data_in.split(',')]
maxLetters = data_split[0]
variable = data_split[1]

x = variable[0]
y = variable[0]
w = variable[0]
value = True
seenLast = False
violationCount = -1
lastChar = chr(int(maxLetters) - 1 + ord('A'))
for y in variable:
    violationCount += 1
    seenLast = seenLast or x == lastChar
    if ord(y) > ord(lastChar):
        value = False
    if x > y and x != lastChar:
        value = False

    if not(seenLast) and w == x and x == y and violationCount >= 2:
        value = False

    if not(value):
        break
    w = x
    x = y

if value:
    print(-1)
else:
    print(violationCount)
