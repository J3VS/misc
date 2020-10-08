(ns misc.screen-lock.combinations-test
  (:require [clojure.test :refer :all]
            [misc.screen-lock.combinations :as c]))

(defn log-base [base number] (/ (Math/log number) (Math/log base)))

(deftest test-total-valid-combinations
  (testing
    "the number of digits required for a pin to exceed
    the number of combinations for the screen lock"
    (let [combinations (c/total-valid-combinations 3 9)]
      (is (= combinations 389488))
      (is (= 6 (->> combinations
                    (log-base 9)
                    (Math/ceil)
                    int)))
      (is (= (< (Math/pow 9 5) combinations (Math/pow 9 6)))))))
