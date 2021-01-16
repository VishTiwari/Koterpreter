/* Welcome to Koterpreter designed by Vish. Here are some sample programs to get you started! They are also repeated in the code with reference to where they were implemented.
(define a 5) 
(display a)
((lambda (a b) (+ (* 2 a) b)) 5 6)
(set! a 6) 
(display a)
(and 3 (< 3 4) (= 6 6))
(or 3 (< 3 4) (= 5 6))
(if (> 3 2) "yes" "no")
(let ((x 1))(let ((y 2))(+ x y)))
(cons 13 4)
(car (cons 13 4))
(cdr (cons 13 4))
(modulo 13 4) */

//Program starts here
fun main() {
    repl()
}
fun repl() {
    //var as oppose to val in Kotlin allows for mutability 
    var go = true
    while (go) {
        val read = readLine()
        if (read.isNullOrEmpty()) {
            continue
        }
        val line = read.toString() //Reads input 
        when(line) {
           //Unless you say the magic words it won't stop reading
            "Vish says stop" -> go = false 
            else -> {
               println(read(tokenize(read)) as Pair < Any, TokenType >)
           //I prefered avoiding the "hacky" parser and having the string be read and parsed below
                println(listCheck(eval(read(tokenize(read)) as Pair < Any, TokenType >))) 
                println()
                read
            }
        }
    }
}
fun listCheck(evalResult: Any): String {
  //So this will print the list out with parens. '*' is just saying you can have any type of list similar to 'Any' but with '*' you can only have one datatype in the list after it has been inputted. It will not allow for a list of strings and integers.   
    return if (evalResult is List < * > ) {
            //joinToString() can take specified parameters to change a string!
            evalResult.joinToString(prefix = "(", postfix = ")")
        } else {
            evalResult.toString()
        }
}

