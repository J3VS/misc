(ns misc.records.data.validation-test
  (:require [clojure.test :refer :all]
            [misc.records.data.validation :refer :all])
  (:import (clojure.lang ExceptionInfo)))

(defmacro throws-exception-info
  [msg data & forms]
  `(try
     ~@forms
     (catch ExceptionInfo e#
       (is (= ~msg (.getMessage e#)))
       (is (= ~data (ex-data e#))))))


(deftest test-missing-keys
  "tests validation fails for missing keys"
  (throws-exception-info "Validation Error"
                         {:missing-fields [:favorite-color :birthdate]}
                         (check {:first-name "peter"
                                 :last-name "parker"
                                 :gender "m"})))

(deftest test-invalid-fields
  "test invalid fields"
  (throws-exception-info "Validation Error"
                         {:first-name "must be a string"
                          :last-name "must be a string"
                          :gender "must be 'm' or 'f'"
                          :favorite-color "must be a string"
                          :birthdate "must be of the form YYYY-mm-dd"}
                         (check {:first-name 1
                                 :last-name 3
                                 :gender "b"
                                 :favorite-color 4.5
                                 :birthdate "24-01-1988"})))

(deftest test-valid-fields
  "test valid fields"
  (is (nil? (check {:first-name "peter"
                    :last-name "parker"
                    :gender "m"
                    :favorite-color "red"
                    :birthdate "2020-01-24"}))))
