import BinomialMath.nCk
import BinomialMath.nCk_memo
import kotlin.system.measureNanoTime

fun main() {
    val rows = 30
    var triangle: Array<Array<Int>>

    var elapsed = measureNanoTime {
        triangle = pascalA(rows)
    }
    printPascalsTriangle(triangle)
    println("Took ${elapsed / 1000000.0f} ms\n")

    elapsed = measureNanoTime {
        triangle = pascalB(rows)
    }
    printPascalsTriangle(triangle)
    println("Took ${elapsed / 1000000.0f} ms\n")

    elapsed = measureNanoTime {
        triangle = pascalC(rows)
    }
    printPascalsTriangle(triangle)
    println("Took ${elapsed / 1000000.0f} ms\n")

    elapsed = measureNanoTime {
        triangle = pascalD(rows)
    }
    printPascalsTriangle(triangle)
    println("Took ${elapsed / 1000000.0f} ms\n")
}

fun printPascalsTriangle(triangle: Array<Array<Int>>) {
    // This inner function will print a value with left and right padding
    fun valuePrint(value: Int, cellWidth: Int) {
        val valueWidth = value.toString().length
        val leftPaddingString = " ".repeat((cellWidth - valueWidth) / 2)
        val rightPaddingString = " ".repeat(cellWidth - valueWidth - leftPaddingString.length)
        print("$leftPaddingString$value$rightPaddingString")
    }

    // Get the largest value from the triangle
    // Then get the number of rows we're printing
    val cellWidth = triangle.last().max().toString().length + 1
    val numRows = triangle.size

    // A single instance of an empty cell for padding (half a full cell)
    val emptyCell = " ".repeat(cellWidth / 2)

    // Print each row, adding padding between
    for (rowIndex in 0 until numRows) {
        // Calculate how much padding to add and print them
        repeat(numRows - rowIndex - 1) {
            print(emptyCell)
        }

        for (value in triangle[rowIndex]) {
            valuePrint(value, cellWidth)
        }
        println()
    }
}

fun pascalA(rows: Int): Array<Array<Int>> {
    // Handle the base cases of 1 or 2 or less than 1 rows
    when (rows) {
        in Int.MIN_VALUE..0 -> arrayOf<Int>()
        1 -> arrayOf(arrayOf(1))
        2 -> arrayOf(arrayOf(1), arrayOf(1, 1))
    }

    // We'll store the whole triangle here
    val answer = arrayListOf(arrayOf(1), arrayOf(1, 1))

    // We keep a record of the previous and current row
    var previousRow = arrayListOf(1, 1)
    val currentRow = arrayListOf<Int>()

    for (i in 2 until rows) {
        // Clear the current row
        currentRow.clear()
        currentRow.add(1)

        // Fill the current row with the sum of the above row's cells
        for (prevRowIndex in 0 until previousRow.size - 1) {
            currentRow.add(previousRow[prevRowIndex] + previousRow[prevRowIndex + 1])
        }
        currentRow.add(1)

        // Add the current list to the answer
        answer.add(currentRow.toTypedArray())

        // Set the previous row to the now current row
        previousRow = ArrayList(currentRow)
    }

    return answer.toTypedArray()
}

fun pascalB(rows: Int): Array<Array<Int>> {
    // Handle the base cases of 1 or 2 or less than 1 rows
    when (rows) {
        in Int.MIN_VALUE..0 -> arrayOf<Int>()
        1 -> arrayOf(arrayOf(1))
        2 -> arrayOf(arrayOf(1), arrayOf(1, 1))
    }

    // We'll store the whole triangle here
    val answer = arrayListOf(arrayOf(1), arrayOf(1, 1))

    // We keep a record of the current row
    val currentRow = arrayListOf<Int>()

    for (rowNumber in 2 until rows) {
        // Clear the current row
        currentRow.clear()

        // Fill the current row with the sum of the above row's cells
        for (rowPosition in 0..rowNumber) {
            currentRow.add(nCk(rowNumber, rowPosition))
        }

        // Add the current list to the answer
        answer.add(currentRow.toTypedArray())
    }

    return answer.toTypedArray()
}

fun pascalC(rows: Int): Array<Array<Int>> {
    // Handle the base cases of 1 or 2 or less than 1 rows
    when (rows) {
        in Int.MIN_VALUE..0 -> arrayOf<Int>()
        1 -> arrayOf(arrayOf(1))
        2 -> arrayOf(arrayOf(1), arrayOf(1, 1))
    }

    // We'll store the whole triangle here
    val answer = arrayListOf(arrayOf(1), arrayOf(1, 1))

    // We keep a record of the current row
    val currentRow = arrayListOf<Int>()

    for (rowNumber in 2 until rows) {
        // Clear the current row
        currentRow.clear()

        // Fill the current row with the sum of the above row's cells
        for (rowPosition in 0..rowNumber) {
            currentRow.add(nCk_memo(rowNumber, rowPosition))
        }

        // Add the current list to the answer
        answer.add(currentRow.toTypedArray())
    }

    return answer.toTypedArray()
}

fun pascalD(rows: Int): Array<Array<Int>> {
    // Handle the base cases of 1 or 2 or less than 1 rows
    when (rows) {
        in Int.MIN_VALUE..0 -> arrayOf<Int>()
        1 -> arrayOf(arrayOf(1))
        2 -> arrayOf(arrayOf(1), arrayOf(1, 1))
    }

    // We'll store the whole triangle here
    val answer = arrayListOf(arrayOf(1), arrayOf(1, 1))

    // We keep a record of the current row
    val currentRow = arrayListOf<Int>()

    // This is the inserted value which we will mutate
    var value: Int

    for (rowNumber in 2 until rows) {
        // Clear the current row, reset value
        currentRow.clear()
        value = 1
        currentRow.add(value)

        // Fill the current row with the sum of the above row's cells
        for (rowPosition in 1 .. rowNumber) {
            value = (value * (rowNumber + 1 - rowPosition)) / (rowPosition)
            currentRow.add(value)
        }

        // Add the current list to the answer
        answer.add(currentRow.toTypedArray())
    }

    return answer.toTypedArray()
}