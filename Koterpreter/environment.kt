 val environment: MutableMap < String, Any > = mutableMapOf(
  //Now we have to set each string to our desired action and we will use a list of <Any> to help
    "display"
    to {
        input: List < Any > ->
            input[0]
    },
    // try (cons 13 4)
    "cons"
    to {
        input: List < Any > ->
            listOf(input[0], input[1])
    },
    "car"
    // try (car (cons 13 4))
    to {
        input: List < Any > ->
            (input[0] as List < * > )[0]
    },
    //try (cdr (cons 13 4))
    "cdr"
    to {
        input: List < Any > ->
            (input[0] as List < * > ).drop(1)
    },
    "+"
    to {
        input: List < Any > ->
        var sum = input[0] as Double
        var droppedList = input.drop(1)
        droppedList.forEach {
            sum = sum.plus(it as Double)
        }
        sum
    },
    "-"
    to {
        input: List < Any > ->
        var sub = input[0] as Double
        var droppedList = input.drop(1)
        droppedList.forEach {
            sub = sub.minus(it as Double)
        }
        sub
    },
    "*"
    to {
        input: List < Any > ->
          var mul = input[0] as Double
        var droppedList = input.drop(1)
        droppedList.forEach {
            mul = mul.times(it as Double)
        }
        mul
    },
    "/"
    to {
        input: List < Any > ->
\        var div = input[0] as Double
        var droppedList = input.drop(1)
        droppedList.forEach {
            div = div.div(it as Double)
        }
        div
    },
    //Try (modulo 13 4) 
    "modulo"
    to {
        input: List < Any > ->
        var mod = input[0] as Double
        var droppedList = input.drop(1)
        droppedList.forEach {
          //.rem just stands for remainder 
            mod = mod.rem(it as Double)
        }
        mod
    },
    "="
    to {
        input: List < Any > ->
        val eq = input[0] as Double
        var droppedList = input.drop(1)
        var equality = true
        droppedList.forEach {
        //.compareTo returns -1,0, or 1
            equality = eq.compareTo(it as Double) == 0
        }
        equality
    },
    "<"
    to {
        input: List < Any > ->
        val less = input[0] as Double
        var equality = true
        var droppedList = input.drop(1)
        //Now we check to see if the funciton returns -1
        droppedList.forEach {
            equality = less.compareTo(it as Double) == -1
        }
        equality
    },
    ">"
    to {
        input: List < Any > ->
        val more = input[0] as Double
        var equality = true
        var droppedList = input.drop(1)
        //Similarly check for 1
        droppedList.forEach {
            equality = more.compareTo(it as Double) == 1
        }
        equality
    }
)
//Intialize the environment 
val global_env = environment
