(ns core.lexer
  (:require
   [clojure.string :refer [join]]))

(defn new-lexer
  "given a char and a token collection, 
  it uses the char as key to get the correspondant 
  token from col (default is :IDENT); finally returns a lexer like a key value pair"
  [ch token-col]
  (let [k (get token-col ch)]
    (if k
      (list k ch)
      (list :IDENT ch))))

(def allowed-words "[A-Za-z]+")
(def allowed-numbers "[0-9]+")
(def allowed-symbols "[!+={}(),;<>*/-]")
(def not-allowd-symbols (str "[^" allowed-words allowed-numbers allowed-symbols "\\s]"))

(defn custom-filter
  "builds a string that will be use as regex pattern to split the code into valid, 
  and more useful strings"
  [& args]
  (str (join "|" args)))

(defn chars-to-lexers
  "it loops recursively over a string input and a result collection (first empty), 
  reading two chars at a time; adds at least one lexer to result col and when a nil 
  is found returns the result col"
  [token-col input]
  (loop [[f & rest] input result []]
    (let [s (first rest)] ;; not removing s yet
      (cond
        (= f nil) (conj result '(:EOF nil))
        (and (= f "=") (= s "=")) (recur (drop 1 rest) (conj result (list :EQ "==")))
        (and (= f "!") (= s "=")) (recur (drop 1 rest) (conj result (list :NOT_EQ "!=")))
        (re-matches (re-pattern allowed-numbers) f) (recur rest (conj result (list :INT f)))
        (re-matches (re-pattern allowed-words) f) (recur rest (conj result (new-lexer f token-col)))
        (re-matches (re-pattern allowed-symbols) f) (recur rest (conj result (new-lexer f token-col)))
        :else (recur rest (conj result (list :ILLEGAL f)))))))

;; main lexer function 
(defn lexdeez!!
  "receives a string input, converts it into a vector of 'words' that follow a specific
  regex pattern then creates a lexer for every element of the vector and returns it."
  [token_col input]
  (->>
   input
   (re-seq (re-pattern (custom-filter allowed-words allowed-numbers allowed-symbols not-allowd-symbols)))
   (chars-to-lexers token_col)))
