Acknowledgment 
    I am pleased to acknowledge Prof. Keith O’Hara for his invaluable guidance during the course of this project work. Samuel Furr was also instrumental in this project as he had some key insights. 
    
December, 2020

   The program utilizes Kotlin to create an interpreter for lisp. On a fundamental level, the interpreter takes in a program, breaks the program into tokens, parses the tokens giving us an AST, evaluates the AST, and finally prints a result.

Norvig represents this process with the following diagram: 

   All users have to do is import the project onto intelliJ and type in their favorite scheme expressions to produce an output. They can type in their programs once the console has fully compiled which it takes a minute or so to do. You may be presented with warning messages generated but nothing detrimental to the performance of our interpreter. Most of these warnings are Kotlin being unnecessarily worried. 

   An important point to note is that Koterpreter was mainly inspired by Jscheme and Lispy both written by Norvig. Sure you may come across elements of SICP but norvig’s programs were the main sources of inspiration. The only slight deviation from these brilliant interpreters Norvig wrote was in the parser method which was scrapped due to its “hacky” nature. 

   I was confident I could create this interpreter, but I knew it would be a difficult task. Kotlin is a relatively new language (released in 2016) and besides from a few stackoverflow sites the only real source of examples was from Jetbrain's website and obscure forums/blogs. 


Scope for further investigation:
- Fix a few of the warnings to make sure it is completely type safe
- Implement lazy eval
- Implement continuations
- Look into implementing lispy2 
http://norvig.com/lispy2.html




