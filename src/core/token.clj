(ns core.token)

;; tokens is a hashmap where the keys are type string and value are type :key
;; this allow to direct extract the name of the char
(def tokens {"=" :ASSIGN
             "+" :PLUS
             "," :COMMA
             ";" :SEMICOLON
             "(" :LPAREN
             ")" :RPAREN
             "{" :LBRACE
             "}" :RBRACE
             "fn" :FUNCTION
             "let" :LET
             "!" :BANG
             "-" :MINUS
             "/" :SLASH
             "*" :ASTERISK
             "<" :LT
             ">" :GT
             "return" :RETURN
             "if" :IF
             "else" :ELSE
             "true" :TRUE
             "false" :FALSE})


