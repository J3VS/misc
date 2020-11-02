(ns misc.word-search-test
  (:require [clojure.test :refer :all]
            [misc.word-search :as ws]))

(def mat
  [["A" "O" "T" "D" "L" "R" "O" "W"]
   ["L" "C" "B" "M" "U" "M" "L" "U"]
   ["D" "R" "U" "J" "D" "B" "L" "J"]
   ["P" "A" "Z" "H" "Z" "Z" "E" "F"]
   ["B" "C" "Z" "E" "L" "F" "H" "W"]
   ["R" "K" "U" "L" "V" "P" "P" "G"]
   ["A" "L" "B" "L" "P" "O" "P" "Q"]
   ["B" "E" "M" "O" "P" "P" "J" "Y"]])

(deftest test-word-search
  "test the word search function"
  (is (= 2 (ws/count-words-in-matrix mat "HELLO")))
  (is (= 1 (ws/count-words-in-matrix mat "WORLD")))
  (is (= 2 (ws/count-words-in-matrix mat "BUZZ")))
  (is (= 0 (ws/count-words-in-matrix mat "CLOJURE")))
  (is (= 0 (ws/count-words-in-matrix mat "COWABUNGA")))
  (is (= 4 (ws/count-words-in-matrix mat "Z"))))
