(ns misc.screen-lock-test
  (:require [clojure.test :refer :all]
            [misc.screen-lock :as sl]))

(deftest test-valid-path
  (testing "screen lock valid-path"
    (is (true? (sl/valid-path [1 6 7 4])))
    (is (true? (sl/valid-path [2 1 3])))
    (is (false? (sl/valid-path [1 3 2])))
    (is (false? (sl/valid-path [1 9])))
    (is (false? (sl/valid-path [1 2 3 2 1])))
    (is (false? (sl/valid-path [0 1 2 3])))))
