(ns misc.records.data.read-test
  (:require [clojure.test :refer :all]
            [misc.records.data.read :refer :all]))

(def comma-test-file
  "smith,david,m,red,02/01/1988\njones,jennifer,f,blue,06/24/1998\npartridge,alan,m,beige,11/29/1965\nbrand,jo,f,black,12/01/1959")

(def pipe-test-file
  "smith|david|m|red|02/01/1988\njones|jennifer|f|blue|06/24/1998\npartridge|alan|m|beige|11/29/1965\nbrand|jo|f|black|12/01/1959")

(def space-test-file
  "smith david m red 02/01/1988\njones jennifer f blue 06/24/1998\npartridge alan m beige 11/29/1965\nbrand jo f black 12/01/1959")

(deftest test-parse-text
  "tests the input data is correctly formatted into a row"
  (is (= [{:first-name "david"
           :last-name "smith"
           :gender "m"
           :favorite-color "red"
           :birthdate "02/01/1988"}
          {:first-name "jennifer"
           :last-name "jones"
           :gender "f"
           :favorite-color "blue"
           :birthdate "06/24/1998"}
          {:first-name "alan"
           :last-name "partridge"
           :gender "m"
           :favorite-color "beige"
           :birthdate "11/29/1965"}
          {:first-name "jo"
           :last-name "brand"
           :gender "f"
           :favorite-color "black"
           :birthdate "12/01/1959"}]
         (parse-text comma-test-file)
         (parse-text pipe-test-file)
         (parse-text space-test-file))))
