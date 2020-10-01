(ns misc.tic-tac-toe)

(defn winner?
  "The row/column/diagonal contains a winner if it contains unique elements.
  Returns the winner"
  [tuple]
  (when (= (-> tuple set count) 1)
    (first tuple)))

(defn flip
  "Flips the board on the diagonal"
  [board]
  (apply map list board))

(defn get-element
  "Takes an element from the board at `row` and `column` indexes"
  [board row column]
  (nth (nth board row) column))

(defn trace
  "Traces across the board, the `f` function takes the indexes (0, 1, 2)
  and should return a tuple (x, y) as an index location to grab from the board.
  X and y are converted mod 3 to ensure they fall inside the board. This means
  mathematical operators are all mod 3
  For example:
  => (trace [[:a :b :c] [:d :e :f] [:g :h :i]] (fn [i] [i (+ i 1)]))
  (:b :f :g)"
  [board f]
  (->> (range 3)
       (map (fn [i]
              (let [[row col] (map #(mod % 3) (f i))]
                (get-element board row col))))))

(def xf
  "Transducer to return the first winner found in one of the possible items"
  (comp
    (map winner?)
    (filter some?)
    (take 1)))

(defn find-winner
  "Determine a winner for a given tic tac toe board"
  [board]
  (let [rows board
        columns (flip board)
        first-diagonal (trace board (fn [i] [i i]))
        second-diagonal (trace board (fn [i] [i (- 2 i)]))]
    (->> (concat rows columns [first-diagonal second-diagonal])
         (into [] xf)
         first)))
