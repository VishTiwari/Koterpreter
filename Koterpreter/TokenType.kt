//Using an enumerated class to make custom types
enum class TokenType {
    EXPR,
    //EXPR = (Atom, List)  
    SYMBOL,
    NUMBER,
    //ATOM = (SYMBOL, NUMBER) 
    STRING,
    LIST
   //Env  = dict    
}