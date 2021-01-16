Acknowledgment 
    I am pleased to acknowledge Prof. Keith O’Hara for his invaluable guidance during the course of this project work. Samuel Furr was also instrumental in this project as he had some key insights. 
    
December, 2020

Principle
   The program utilizes Kotlin to create an interpreter for lisp. On a fundamental level, the interpreter takes in a program, breaks the program into tokens, parses the tokens giving us an AST, evaluates the AST, and finally prints a result.

Norvig represents this process with the following diagram: 


   All users have to do is click “run” on the repl program and type in their favorite scheme expressions to produce an output. They can type in their programs once the console has fully compiled which it takes a minute or so to do. You may be presented with warning messages generated but nothing detrimental to the performance of our interpreter. Most of these warnings are Kotlin being unnecessarily worried. 

   An important point to note is that Koterpreter was mainly inspired by Jscheme and Lispy both written by Norvig. Sure you may come across elements of SICP but norvig’s programs were the main sources of inspiration. The only slight deviation from these brilliant interpreters Norvig wrote was in the parser method which was scrapped due to its “hacky” nature. 

   I was confident I could create this interpreter, but I knew it would be a difficult task. Kotlin is a relatively new language (released in 2016) and besides from a few stackoverflow sites the only real source of examples was from Jetbrain's website and obscure forums/blogs. 



Implementation
	I closely followed Norvig’s python interpreter so I will just step through each .kt class and talk a little bit about it. However, there are comments in the code to also help you along with understanding this program. The key structures used are maps and lists (mutable ones to be specific) with pairs.

Main.kt - The main class contains a few fun programs to try out. Those same programs are also available to test in Environment and Eval depending on where a certain program’s functionality was implemented. The main program also has the method listCheck() which just checks if our result is a list and if so wrap it in parentheses. The main repl() prints out both the “AST” and allows for us to avoid using a parser as all the gory details of implementing the lexer are just done in the repl(). Refer to the code snippet below:
val line = read.toString() //Reads input
       when(line) {
           "Vish says stop" -> go = false
           else -> {
              println(read(tokenize(read)) as Pair < Any, TokenType >)
               println(listCheck(eval(read(tokenize(read)) 
                as Pair < Any, TokenType >)))
               println()
               read
           }
You may come across var and val which are just mutable and immutable variables. Also note '*' in Kotlin means you can have any type of list similar to 'Any' but with '*' you can only expect one datatype to be consistently entered. These fun nuances make Kotlin’s type checking a bit simpler to work with. 
TokenType.kt - This enumerated class is very straightforward and just defines our types. There are no atoms explicitly declared but there are expressions, symbols, numbers, strings, and lists. So all input our program will receive will boil down to one of these types. Refer to code snippet below:
enum class TokenType {
   	EXPR,
   	SYMBOL,
   	NUMBER,
   	STRING,
   	LIST
}

Lexer.kt - The lexer just assigns whatever input it receives to a specific type in TokenType. For example:
if (pop.startsWith("'")) {
       return read(token) to TokenType.LIST
  	 }
It also contains the tokenize() method that breaks the string into tokens by adding space and filtering. It is also where a string is returned as a mutableList to main.kt. This Lexer class along with the repl() function implements the first part of our diagram on its own. 
Environment.kt - Environment uses a mutableMap() of pairs which is similar to the dictionary in python where you just look up values. Pairs are quite cleanly represented in Kotlin. Where the first input is a string/key and the second input is “Any”/value. Again, “Any” allows Kotlin the ability to take any data type as input without worrying about it having being defined at runtime. In eval I have implemented the following: display, cons, car, cdr, +, -, inequalities, etc. There is a technique that I use here to implement operations like division and addition that is worth mentioning. It also appears in other places in the program. The technique essentially consists of taking the input as a list and saving the first value of that list in a var. Then iterating through (using a forEach loop) that list skipping/dropping the first input and working with the remaining inputs. There might be a cleaner way of implementing this but from researching online this seemed to be the standard way to go about it. We also have to be constantly type checking so you may notice everything being cast “as Double” for example. Here is an example of subtraction’s implementation for reference:
"-"
   to {
       input: List < Any > ->
       //Same technique as before but with subtraction
       var sub = input[0] as Double
       var droppedList = input.drop(1)
       droppedList.forEach {
           sub = sub.minus(it as Double)
       }
       sub
   },
Eval.kt - Strings, lists and numbers are pretty much returned as themselves without much altercation. However, expressions have to be looked at a little more closely. This is also where most of the good stuff lies and it is all implemented using if statements. I tried to use a sealed class here and when statements but there were too many errors with doing that that I decided to stick with plain old if statements. Under expressions I define if, or, cond, let, lambda, and, set!, Etc. I do so very very similarly to how Norvig defines these things and how we saw they were defined in Lab 4. Val here can be used to store multiple things at once just like python allows with tuples which is also pretty cool as it was hard to do this in Java. Proc.kt also helps here with certain operations like define and lambda. This is because we want the global environment to persist even after we complete the evaluation for example: 
(define x 3)
((lambda (a) (+ a 2)) x)
 
      These two expressions only really work independently if there is a global                environment that maintains a state between evaluations and expression. Proc helps in this as it takes in the parameters separately and combines them with the list of arguments maintaining this state once paired with the environment. If we dig into the guts of “define” we see that if it is a symbol then we save it as such otherwise we remember the name and its parameters and it is sent to proc in three parts. Here is an quick code snippet from this class
           if (check.first.equals("define")) {
               val(_, argument, body) = exp
               if(argument.second.equals(TokenType.SYMBOL)) {
                    env[argument.first as String] = eval(body, env)
               }
if(argument.second.equals(TokenType.EXPR)) {
val name = (argument.first as List < Pair < Any, TokenType >> )[0].first as String
val parms = (argument.first as List < Pair < Any, TokenType >> ).drop(1)
                       env[name] = Proc(parms, body, env)
                   }


  5.5 Proc.kt - We have talked about Proc.kt separately and it is really part of the environment as Norvig mentions. The implementation details of Proc aren’t too important and they do follow very closely to the Norvig approach. This is really just where the parameters, body, and the environment come together and the parameters and arguments get combined with reference to the environment.
Evaluation
	One thing I could have adjusted is not using Repl.it. I think the platform makes the program slow when it is initially starting and intelliJ would definitely be a better program to use. It would have saved me a lot of downtime just waiting for the program to compile. So if I had to do the project again I would definitely not use Repl.it even though it is an amazing environment to program in. If I did have to restart this project I would also start writing/documenting this report alongside coding my project just to put all my thoughts together and not get lost in the weeds of it. 

Overall, the program works well and I am glad it all came together in the end. At its current state there is not too much I would want to fix. There are elements I would add to it and I will definitely keep updating and adding to it for my own personal satisfaction. I have always been interested in how programming languages were designed. I am glad I got to gain a much deeper understanding of programming languages this semester.


Scope for further investigation:
Be able to handle strings
Fix a few of the warnings to make sure it is completely type safe
Implement lazy eval
Implement continuations
Look into implementing lispy2 
http://norvig.com/lispy2.html




