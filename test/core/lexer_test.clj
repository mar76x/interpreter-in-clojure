(ns core.lexer-test
  (:require
   [clojure.java.io :refer [resource reader]]
   [clojure.test :refer [deftest is testing]]
   [core.lexer :refer [lexdeez!!]]
   [core.token :refer [tokens]]))

(defn read-txt [file-path]
  (with-open [r (reader (resource file-path))]
    (apply str (line-seq r))))

(deftest lexer-test-1
  (testing "=+(){},;"
    (let [result ['(:ASSIGN "=")
                  '(:PLUS "+")
                  '(:LPAREN "(")
                  '(:RPAREN ")")
                  '(:LBRACE "{")
                  '(:RBRACE "}")
                  '(:COMMA ",")
                  '(:SEMICOLON ";")
                  '(:EOF nil)]]
      (is (= (lexdeez!! tokens "=+(){},;") result)))))

(deftest lexer-test-2
  (testing "input_1.txt"
    (let [result ['(:LET "let")
                  '(:IDENT "five")
                  '(:ASSIGN "=")
                  '(:INT "5")
                  '(:SEMICOLON ";")
                  '(:LET "let")
                  '(:IDENT "ten")
                  '(:ASSIGN "=")
                  '(:INT "10")
                  '(:SEMICOLON ";")
                  '(:LET "let")
                  '(:IDENT "add")
                  '(:ASSIGN "=")
                  '(:FUNCTION "fn")
                  '(:LPAREN "(")
                  '(:IDENT "x")
                  '(:COMMA ",")
                  '(:IDENT "y")
                  '(:RPAREN ")")
                  '(:LBRACE "{")
                  '(:IDENT "x")
                  '(:PLUS "+")
                  '(:IDENT "y")
                  '(:SEMICOLON ";")
                  '(:RBRACE "}")
                  '(:SEMICOLON ";")
                  '(:LET "let")
                  '(:IDENT "result")
                  '(:ASSIGN "=")
                  '(:IDENT "add")
                  '(:LPAREN "(")
                  '(:IDENT "five")
                  '(:COMMA ",")
                  '(:IDENT "ten")
                  '(:RPAREN ")")
                  '(:SEMICOLON ";")
                  '(:EOF nil)]]
      (is (= (lexdeez!! tokens (read-txt "input_1.txt")) result)))))

(deftest lexer-test-3
  (testing "input_2.txt"
    (let [result ['(:LET "let")
                  '(:IDENT "five")
                  '(:ASSIGN "=")
                  '(:INT "5")
                  '(:SEMICOLON ";")
                  '(:LET "let")
                  '(:IDENT "ten")
                  '(:ASSIGN "=")
                  '(:INT "10")
                  '(:SEMICOLON ";")
                  '(:LET "let")
                  '(:IDENT "add")
                  '(:ASSIGN "=")
                  '(:FUNCTION "fn")
                  '(:LPAREN "(")
                  '(:IDENT "x")
                  '(:COMMA ",")
                  '(:IDENT "y")
                  '(:RPAREN ")")
                  '(:LBRACE "{")
                  '(:IDENT "x")
                  '(:PLUS "+")
                  '(:IDENT "y")
                  '(:SEMICOLON ";")
                  '(:RBRACE "}")
                  '(:SEMICOLON ";")
                  '(:LET "let")
                  '(:IDENT "result")
                  '(:ASSIGN "=")
                  '(:IDENT "add")
                  '(:LPAREN "(")
                  '(:IDENT "five")
                  '(:COMMA ",")
                  '(:IDENT "ten")
                  '(:RPAREN ")")
                  '(:SEMICOLON ";")
                  '(:BANG "!")
                  '(:MINUS "-")
                  '(:SLASH "/")
                  '(:ASTERISK "*")
                  '(:INT "5")
                  '(:SEMICOLON ";")
                  '(:INT "5")
                  '(:LT "<")
                  '(:INT "10")
                  '(:GT ">")
                  '(:INT "5")
                  '(:SEMICOLON ";")
                  '(:IF "if")
                  '(:LPAREN "(")
                  '(:INT "5")
                  '(:LT "<")
                  '(:INT "10")
                  '(:RPAREN ")")
                  '(:LBRACE "{")
                  '(:RETURN "return")
                  '(:TRUE "true")
                  '(:SEMICOLON ";")
                  '(:RBRACE "}")
                  '(:ELSE "else")
                  '(:LBRACE "{")
                  '(:RETURN "return")
                  '(:FALSE "false")
                  '(:SEMICOLON ";")
                  '(:RBRACE "}")
                  '(:INT "10")
                  '(:EQ "==")
                  '(:INT "10")
                  '(:SEMICOLON ";")
                  '(:INT "10")
                  '(:NOT_EQ "!=")
                  '(:INT "9")
                  '(:SEMICOLON ";")
                  '(:EOF nil)]]
      (is (= (lexdeez!! tokens (read-txt "input_2.txt")) result)))))
