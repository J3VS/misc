(ns misc.tic-tac-toe-test
  (:require [clojure.test :refer :all]
            [misc.tic-tac-toe :as ttt]))

(deftest test-find-winner
  (testing "Tic Tac Toe winner"
    (is (= :x (ttt/find-winner [[:x :o :x] [:x :o :o] [:x :x :o]])))
    (is (= :o (ttt/find-winner [[:o :x :x] [:x :o :x] [:x :o :o]])))
    (is (nil? (ttt/find-winner [[:x :o :x] [:x :o :x] [:o :x :o]])))))
