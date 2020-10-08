(ns misc.screen-lock)

(defn every-number-on?
  "Given a function `f`, a grid-size, and a series of numbers. Returns whether
  the provided numbers all have the same value when the function is applied."
  [f grid-size & args]
  (->> args
       (map dec)
       (map (partial f grid-size))
       (apply =)))

;; For numbers on the same positive diagonal, the difference between the mod
;; ;;and the quotient is consistent
(defn positive-diagonal [grid-size x] (- (mod x grid-size) (quot x grid-size)))
;; For numbers on the same negative diagonal, the sum of the mod and the quotient is consistent
(defn negative-diagonal [grid-size x] (+ (mod x grid-size) (quot x grid-size)))
;; For numbers on the same row, the quotient is consistent
(defn row [grid-size x] (quot x grid-size))
;; For numbers in the same column, the mod is consistent
(defn column [grid-size x] (mod x grid-size))

(defn get-skip-step
  "For two numbers in the grid, if they fall on either a diagonal, a row, or
  a column, this function returns the exact skip between numbers in the grid
  that traces out that diagonal, row or column."
  [grid-size x y]
  (cond
    (every-number-on? positive-diagonal grid-size x y)
    (+ grid-size 1)
    (every-number-on? negative-diagonal grid-size x y)
    (- grid-size 1)
    (every-number-on? row grid-size x y)
    1
    (every-number-on? column grid-size x y)
    grid-size))

(defn skipped-nodes
  "Returns the skipped nodes by figuring out whether there is an exact skip
  step on the grid, and if there is, returning every skipped node"
  [grid-size x y]
  (let [[x y] (sort [x y])]
   (some->> (get-skip-step grid-size x y)
            (range x y)
            rest
            seq)))

(defn inside-grid?
  "Determines if the number is inside the grid"
  [grid-size x]
  (<= 1 x (* grid-size grid-size)))

(defn already-visited-skipped-nodes?
  "Finds any skipped nodes between the previous and current,
  returns true if there are no skipped nodes, or if all the skipped
  nodes have previously been visited"
  [grid-size visited-nodes previous current]
  (->> (skipped-nodes grid-size previous current)
       (every? visited-nodes)))

(defn valid-transition?
  "Determines whether the transition from previous to current
  is valid, given the grid-size and the previously visited nodes"
  [grid-size visited-nodes previous current]
  (and
    (inside-grid? grid-size current)
    (not (visited-nodes current))
    (already-visited-skipped-nodes? grid-size visited-nodes previous current)))

(defn no-repetitions?
  [path]
  (apply distinct? path))

(defn valid-path-general
  "A general solution to find whether a path is valid given a grid size"
  [grid-size path]
  (let [initial (first path)]
    (if (and (no-repetitions? path) (inside-grid? grid-size initial))
      (->> path
           (partition 2 1)
           (reduce (fn [acc [previous current]]
                     (if (valid-transition? grid-size acc previous current)
                       (conj acc current)
                       (reduced false)))
                   #{initial})
           boolean)
      false)))

;; Special case for grid size of 3x3
(def valid-path (partial valid-path-general 3))
