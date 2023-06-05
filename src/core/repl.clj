(ns core.repl
  (:gen-class)
  (:require
   [core.lexer :refer [lexdeez!!]]
   [core.token :refer [tokens]]))

(defn run
  "this is a repl for monkey lang"
  []
  (print ">> ")
  (flush)
  (let [buf (read-line)]
    (loop [[l & lexers] (lexdeez!! tokens buf)]
      (if (not= l nil)
        (do
          (println l)
          (recur lexers))))
    (run)))

(run)
