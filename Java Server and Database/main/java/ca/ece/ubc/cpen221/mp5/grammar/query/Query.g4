grammar Query;

WS : [ \t\n\r] + -> skip ;

OR : '||'  ;
AND : '&&'  ;

GT : '>' ;
GTE : '>=' ;
LT : '<' ;
LTE : '<=' ;
EQ : '=' ;

NUM : [1-5] ;
STRING : [a-zA-Z0-9_\-]+(' '+[a-zA-Z0-9_\-]+)* ;

LPAREN : '(' ;
RPAREN : ')' ;

query : orexpr ;

orexpr : andexpr(OR andexpr)* ;
andexpr : atom(AND atom)* ;
atom : in | category | rating | price | name | LPAREN orexpr RPAREN ;
ineq : GT | GTE | LT | LTE | EQ ;

in : 'in'  LPAREN STRING RPAREN  ;
category : 'category' LPAREN STRING RPAREN  ;
name : 'name'  LPAREN STRING RPAREN ;
rating : 'rating' ineq NUM ;
price : 'price' ineq NUM ;
