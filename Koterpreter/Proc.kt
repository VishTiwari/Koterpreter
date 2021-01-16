//Proc takes 3 arguments he parameters, body, and the environment. This is more of a helper class to environment and eval then its own stand alone class. 
class Proc(val parms: List < Pair < Any, TokenType >> , val body: Pair < Any, TokenType > , val env: MutableMap < String, Any > ) {
    operator fun invoke(args: List < Any > ): Any {
        val newZip: MutableMap < String, Any > = mutableMapOf()
        //Zip is really simple and just pairs items together. Here the parms are combined to the arguments
        parms.indices.forEach {
            newZip[parms[it].first.toString()] = args[it]
        }
        //Combine it with the environment
        val procedure = (env + newZip).toMutableMap()
        return eval(body, procedure)
    }
}

