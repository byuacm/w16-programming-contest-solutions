def buildBlankMatrix(n):
    matrix = []
    for i in range(n):
        matrix.append([])
        for j in range(n):
            matrix[i].append(0)
    return matrix


def buildIdentity(n):
    matrix = buildBlankMatrix(n)
    for i in range(n):
        matrix[i][i] = 1
    return matrix


def buildBackVector(characters):
    vector = []
    for i in range(characters):
        vector.append(1)
    return vector


def buildBackMatrix(characters):
    matrix = buildBlankMatrix(characters)
    for i in range(characters):
        for j in range(characters):
            if i > j and i != characters - 1:
                value = 0
            else:
                value = 1
            matrix[i][j] = value
    return matrix


def buildFrontVector(characters):
    vector = []
    for i in range(2*(characters-1)):
        vector.append((i+1) % 2)
    return vector


def buildFrontMatrix(characters):
    size = (characters - 1) * 2
    matrix = buildBlankMatrix(size)
    for i in range(size - 1):
        matrix[i][i+1] = 1

    for i in range(characters - 1):
        for j in range(2*i):
            matrix[j][2*i] = 1
    return matrix


def makeColMatrix(matrix):
    colMatrix = buildBlankMatrix(len(matrix))
    for i in range(len(matrix)):
        for j in range(len(matrix)):
            colMatrix[i][j] = matrix[j][i]
    return colMatrix


def dotProduct(x,y):
    value = 0
    if(len(x) != len(y)):
        return 0
    
    for i in range(len(x)):
        value += x[i] * y[i]

    return value


def vectorProduct(vector,matrix):
    matrix = makeColMatrix(matrix)
    product = []
    if(len(vector) != len(matrix)):
        return []

    for i in range(len(vector)):
        product.append(dotProduct(vector,matrix[i]))

    return product


def matrixMultiply(matrix,colMatrix):
    newMatrix = []
    for i in range(len(matrix)):
        newMatrix.append([])
        for j in range(len(colMatrix)):
            newMatrix[i].append(0)
            newMatrix[i][j] = dotProduct(matrix[i],colMatrix[j])
    return newMatrix


def matrixPower(matrix, n):
    colMatrix = makeColMatrix(matrix)
    newMatrix = matrix

    if n == 0:
        return buildIdentity(len(matrix))
    for k in range(1,n):
        newMatrix = matrixMultiply(matrix, colMatrix)
        matrix = newMatrix
    return newMatrix


def getTotal(characters, length):
    frontMatrix = buildFrontMatrix(characters)
    backMatrix = buildBackMatrix(characters)
    frontVector = buildFrontVector(characters)
    backVector = buildBackVector(characters)

    value = 0
    for i in range(length+1):
        if(i != 0):
            frontValue = sum(vectorProduct(frontVector, matrixPower(frontMatrix,i-1)))
        else:
            frontValue = 1

        if(i < length-1):
            backValue = sum(vectorProduct(backVector, matrixPower(backMatrix,length - i - 2)))
        else:
            backValue = 1

        value += frontValue * backValue
    return value


data_in = raw_input()
data_split = [x.strip() for x in data_in.split(',')]
maxLetters = int(data_split[0])
maxLength = int(data_split[1])

print(getTotal(maxLetters, maxLength))
