FOL Solver permet à l'utilisateur d'entrer une formule logique du premier ordre, obéissant à une syntaxe particulière, et de lancer l'algorithme de résolution sur cette formule.

SYNTAXE:

La formule entrée par l'utilisateur doit respecter les règles syntaxiques suivantes.

terme  ::= var | fun | fun termes
termes ::= "(" terme ")" | "(" terme "," termes ")"
formule  ::=   predicat
          | predicat termes
          | "!" formule
          | "(" formule "&" formule ")"
          | "(" formule "|" formule ")"
          | "(" formule "->" formule ")"
          | "exists " var "." formule
          | "forall " var "." form
predicat ::= bottom | top | [a-z]+
var ::= [A-Z]{1}
fun ::= (?!f$[0-9]+\b)[a-z]+

- Tout opérateur binaire (&, | et ->) et ses opérandes doivent être entourés de parenthèses pour que la formule soit bien formée.
- De fait, la mise en série d'opérateurs n'est pas possible. Par exemple, on ne peut pas écrire (p(X) | p(Y) | p(Z)), mais on doit écrire
((p(X) | p(Y)) | p(Z)). En effet, la syntaxe en est un peu alourdie.
- Pour avoir une formule bien formée, les variables doivent être en majuscules (X, Y, Z...), et les prédicats et fonctions en minuscules (p(X), p(f(Y))).
- Le nom des fonctions ne doit pas être de la forme f$ suivi d'un entier, car ce sont des symboles réservés à la production aléatoire de fonctions 
lors de l'herbrandisation ou de la skolémisation d'une formule.

LANCEMENT DU LOGICIEL:

