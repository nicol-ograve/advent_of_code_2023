 package day1

import utils.getDataLines


 val digitsMap: Map<String, Int> = mapOf(
     "zero" to 0,
     "one" to 1,
     "two" to 2,
     "three" to 3,
     "four" to 4,
     "five" to 5,
     "six" to 6,
     "seven" to 7,
     "eight" to 8,
     "nine" to 9
 )

fun main(args: Array<String>) {
    val isDemo = false
    val data = getDataLines(1, if (isDemo) arrayOf("demo") else emptyArray())



    val result = data.fold(0) { acc: Int, line: String ->
        var firstDigit: Int? = null
        var lastDigit: Int? = null

        line.forEachIndexed { index, _ ->
            val digitValue = getDigitValue(index, line)
            if (digitValue != null) {
                if (firstDigit == null) {
                    firstDigit = digitValue
                }
                lastDigit = digitValue
            }
        }
        val digitsSum =firstDigit!! * 10 + lastDigit!!
        acc + digitsSum
    }

    println(result)

}



 fun getDigitValue(index: Int, string: String): Int? {
     val char = string[index]
     return if (char.isDigit()) {
         char.toString().toInt()
     } else {
         digitsMap.keys.forEach { key ->
             if(string.length >= index + key.length && string.substring(index until index+key.length) == key){
                 return digitsMap[key]
             }
         }
         null
     }
 }