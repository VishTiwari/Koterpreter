fun eval(accessPair: Pair < Any, TokenType > , env: MutableMap < String, Any > = global_env): Any {
  //If the tokentype is LIST then just return the list. This type of checking will be used throughout eval. 
    if (accessPair.second.equals(TokenType.LIST)) {
        return accessPair.first
    }
    if (accessPair.second.equals(TokenType.EXPR)) { 
        if (accessPair.first is List < * > ) {
            val exp = accessPair.first as List < Pair < Any, TokenType >>
                var check = exp[0]
            // try (define a 5) 
            // then (display a)
            //Quite similar to Norvig's python. 
            if (check.first.equals("define")) {
                val(_, argument, body) = exp
                //If it is arguments are just a symbol then save it in the environment as a string 
                if(argument.second.equals(TokenType.SYMBOL)) {
                     env[argument.first as String] = eval(body, env)
                }
                //Otherwise chop it into names and parameters.
                //Eventually it will be a procedure 
                if(argument.second.equals(TokenType.EXPR)) {
                        val name = (argument.first as List < Pair < Any, TokenType >> )[0].first as String
                        val parms = (argument.first as List < Pair < Any, TokenType >> ).drop(1)
                        env[name] = Proc(parms, body, env)
                    }
                    return ""
                }
            // try ((lambda (a b) (+ (* 2 a) b)) 5 6)
            if (check.first.equals("lambda")) {
                val(_, parms, body) = exp
                //Lambda will alsp return a procedure
                return Proc(parms.first as List < Pair < Any, TokenType >> , body, env)
            }
            //try (set! a 6) 
            //(display a)
            //Set bang will allow for mutability. 
            if (check.first.equals("set!")) {
                val(_, input, value) = exp
                val mutate = input.first
                //look it up in our current environment
                if (mutate in env) {
                    //the change it
                    env[mutate.toString()] = eval(value, env)
                    return ""
                }
            }
            // try (and 3 (< 3 4) (= 6 6))
            if (check.first.equals("and")) {
                for (things in exp.drop(1)) {
                  //check if the inputs to "and" are true
                    if (eval(things, env) == false) {
                        return false
                    }
                }
                return true
            }
            // try (or 3 (< 3 4) (= 5 6))
            if (check.first.equals("or")) {
                for (things in exp.drop(1)) {
                  //check if the inputs to "or" are true
                    if (eval(things, env) == true) {
                        return true
                    }
                }
                return false
            }
            // try (if (> 3 2) "yes" "no") 
            if (check.first.equals("if")) {
                val(_, test, conseq, alt) = exp
                //if the statement is true execute that conseq or else execute the else part  
                val exp = if (eval(test, env) as Boolean) conseq else alt
                return eval(exp, env)
            }
            if (check.first.equals("cond")) {
                for (things in exp.drop(1)) {
                    val(test, conseq) = things as List < Any >
                        if (eval(test as Pair < Any, TokenType > , env) as Boolean) {
                            return eval(conseq as Pair < Any, TokenType > , env)
                        }
                }
                return ""
            }
            //try (let ((x 1))(let ((y 2))(+ x y)))
            if (check.first.equals("let")) {
                val(_, things, body) = exp
                //Here you will be taking in parameters and expressions as lists (mutable with pairs of course)
                val parm: MutableList < Pair < Any, TokenType >> = mutableListOf()
                val exp: MutableList < Pair < Any, TokenType >> = mutableListOf()
                 //chop/save those things in a list of pairs and do all that type checking stuff
                for (par in things.first as List < Pair < Any, TokenType >> ) {
                    parm.add((par.first as List < Pair < Any, TokenType >> )[0])
                    exp.add((par.first as List < Pair < Any, TokenType >> )[1])
                }
                //Now put let together by deriving the lambda form we all know and love.
                val lamb: MutableList < Any > = mutableListOf(Pair(listOf(Pair("lambda", TokenType.SYMBOL), Pair(parm, TokenType.EXPR), body), TokenType.EXPR))
                for (things in exp) {
                    lamb.add(things)
                }
                return eval(Pair(lamb, TokenType.EXPR), env)
            }
            // Just checking for order of evaluation
            if (check.first is List < * > ) {
                val proc = eval(exp[0], env) as Proc
                val args = exp.drop(1).map {
                    eval(it, env)
                }
                return proc(args)
            }
           //first check if our input has already been defined
            if (check.first in env) {
                val args = exp.drop(1).map {
                    eval(it, env)
                }
                //If it is a new definition the we can keep it as a procedure or else we can store it as a lambda
                return if (env[exp[0].first.toString()] is Proc) {
                        val proc = env[exp[0].first.toString()] as Proc
                        proc(args)
                    } else {
                        val proc: (List < Any > ) -> Any = env[exp[0].first] as(List < Any > ) -> Any
                        proc(args)
                    }
             }
        }
    }

    //STRINGS, SYMBOLS, and NUMBERS are the easy cases to handle.
    if (accessPair.second.equals(TokenType.STRING)) {
      //Note: I think Lispy2 has a more sophisticated way to handle stings but for now our stings are pretty much one word long with no whitespace. 
        return accessPair.first
    }
    if (accessPair.second.equals(TokenType.SYMBOL)) {
        return if (accessPair.first in env) {
                env[accessPair.first]?:"null"
            } else {
                throw Exception("Slow down buddy boy")
            }
    }
    if (accessPair.second.equals(TokenType.NUMBER)) {
        return accessPair.first
    }
    //If it reaches here then it is just unsupported
    return ""
}