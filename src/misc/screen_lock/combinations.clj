(ns misc.screen-lock.combinations
  (:require [misc.screen-lock :as sl]
            [clojure.set :as set]))

(defn all-grid-numbers
  "A list of all numbers in the grid"
  [grid-size]
  (range 1 (inc (* grid-size grid-size))))

(defn append-node
  "Explodes the path by generating up to 9 new combinations
  from each by adding the numbers 1 to 9 to each combination, provided that
  number isn't already in the combination. In other words [1] becomes
  [[1 2] [1 3] [1 4] ... ]"
  [grid-size path]
  (let [not-in-path (set/difference
                      (set (all-grid-numbers grid-size))
                      (set path))]
    (map #(conj path %) not-in-path)))

(defn initial-valid-combinations
  "Returns the smallest possible combinations (with only 2 nodes)"
  [grid-size]
  (for [i (all-grid-numbers grid-size)
        j (all-grid-numbers grid-size)
        :when (sl/valid-path-general grid-size [i j])]
    [i j]))

(defn count-paths
  "A recursive function that counts the number of valid paths,
  starting with `path` up to size `max-length`, if the provided
  path is invalid, this will return 0"
  [grid-size max-length path]
  (if (sl/valid-path-general grid-size path)
    (if (< (count path) max-length)
      (->> (append-node grid-size path)
           (map (partial count-paths grid-size max-length))
           (apply +)
           inc)
      1)
    0))

(defn total-valid-combinations
  "Gets all possible valid paths in the grid from size 2 to `max-length`"
  [grid-size max-length]
  (->> (initial-valid-combinations grid-size)
       (reduce (fn [acc path]
                 (+ acc (count-paths grid-size max-length path))) 0)))
