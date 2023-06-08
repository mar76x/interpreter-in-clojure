(ns core.lexer
  (:require
   [clojure.java.io :refer [reader resource]]
   [clojure.string :refer [join]]
   [core.token :refer [tokens]]))

(def allowed-words "[A-Za-z0-9]+")
(def allowed-numbers "[0-9]+")
(def allowed-symbols "[!+={},;()<>*/-]|\\s")

(defn build-filter
  [& args]
  (str (join "|" args)))

(defn new-token-char-tuple
  [token-col ch]
  (let [k (get token-col ch)]
    (if (= k nil)
      (list :IDENT ch)
      (list k ch))))

(defn read-chars
  [token-col input]
  (loop [[f & rest] input result []]
    (let [s (first rest)] ;; s is still in rest
      (cond
        (= f nil) (conj result (list :EOF nil))

        (= f " ") (recur rest result)

        (and (= f "=") (= s "="))
        (recur (drop 1 rest) (conj result (list :EQ "==")))

        (and (= f "!") (= s "="))
        (recur (drop 1 rest) (conj result (list :NOT_EQ "!=")))

        (re-matches (re-pattern allowed-numbers) f)
        (recur rest (conj result (list :INT f)))

        (re-matches (re-pattern allowed-words) f)
        (recur rest (conj result (new-token-char-tuple token-col f)))

        (re-matches (re-pattern allowed-symbols) f)
        (recur rest (conj result (new-token-char-tuple token-col f)))

        :else (recur rest (conj result (list :ILLEGAL f)))))))

;; main lexer function
(defn lexdeez!!
  "[token-col s]
  given a collection of valid tokens and a string, splits the string into a list of words and then
  for each element generates a list of (token, literal) tuples using the token-col to validate."
  [token_col input]
  (->> (or (re-seq (re-pattern (build-filter allowed-words allowed-numbers allowed-symbols)) input)
           (not (re-seq (re-pattern (build-filter allowed-words allowed-numbers allowed-symbols)) input)))
       (read-chars token_col)))

(comment
  (defn read-txt [file-path]
    (with-open [r (reader (resource file-path))]
      (apply str (line-seq r))))

  (lexdeez!! tokens (read-txt "input_2.txt")))
