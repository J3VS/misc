(ns misc.word-search
  (:require [clojure.string :as string]))

(defn row-strings
  "Takes the grid and returns a list of strings which represent the
  rows as concatenated strings"
  [grid]
  (map string/join grid))

(defn col-strings
  "Takes the grid and returns a list of strings which represent the
  columns as concatenated strings"
  [grid]
  (apply map (comp string/join list) grid))

(defn all-strings
  "Takes the grid and returns a list of strings which represent the
  columns AND rows as concatenated strings"
  [grid]
  (concat (row-strings grid) (col-strings grid)))

(defn matches
  "Finds the number of times a word appears within a string"
  [word s]
  (-> (re-pattern word)
      (re-seq s)
      count))

(defn- concat-with-reverse
  "Concatenates to the provided collection, the reverse
  of every element in the collection"
  [col]
  (->> (map string/reverse col)
       (concat col)))

(defn find-letter
  "Finds how many times a single letter appears in the entire grid"
  [grid letter]
  (-> (flatten grid)
      frequencies
      (get letter 0)))

(defn find-word
  "Finds how many times the word appears in the entire grid, the word
  must be greater than a single letter"
  [grid word]
  {:pre [(> (count word) 1)]}
  (->> (all-strings grid)
       concat-with-reverse
       (reduce (fn [acc s]
                 (+ acc (matches word s))) 0)))

(defn count-words-in-matrix
  "Counts the number of times the word is found in the matrix"
  [grid word]
  (if (= (count word) 1)
    (find-letter grid word)
    (find-word grid word)))
