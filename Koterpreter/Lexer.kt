fun read(token: MutableList < String > ): Pair < Any, TokenType > {
    //Assigns a type to the input depending on what the input starts with
    val pop = token.removeAt(0)
    if (pop.startsWith("'")) {
        return read(token) to TokenType.LIST
    }
    // If you see a paren then just read its add it as an expression and stop at the left paren
    if (pop.startsWith("(")) {
        val Mlist: MutableList < Pair < Any, TokenType >> = mutableListOf()
        while (token[0] != ")") {
            Mlist.add(read(token))
        }
        token.removeAt(0)
        return Mlist to TokenType.EXPR
    }
    if (pop.startsWith("\"")) {
        return pop to TokenType.STRING
    } else {
        return try {
          // All else fails then it has to be a number or symbol
            pop.toDouble() to TokenType.NUMBER
        } catch (e: Exception) {
            pop to TokenType.SYMBOL
        }
    }
}
fun tokenize(s: String): MutableList < String > {
    return s.replace("(", " ( ").replace(")", " ) ").split(" ").filter {
        it != ""
    }.toMutableList()
}
