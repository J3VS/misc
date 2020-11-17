(ns misc.records.data.read-test
  (:require [clojure.test :refer :all]
            [misc.records.data.read :refer :all]))

(def comma-test-file
  "smith,david,m,red,1988-02-01\njones,jennifer,f,blue,1998-06-24\npartridge,alan,m,beige,1965-11-29\nbrand,jo,f,black,1959-12-01")

(def pipe-test-file
  "smith|david|m|red|1988-02-01\njones|jennifer|f|blue|1998-06-24\npartridge|alan|m|beige|1965-11-29\nbrand|jo|f|black|1959-12-01")

(def space-test-file
  "smith david m red 1988-02-01\njones jennifer f blue 1998-06-24\npartridge alan m beige 1965-11-29\nbrand jo f black 1959-12-01")

(deftest test-parse-text
  "tests the input data is correctly formatted into a row"
  (is (= [{:first-name "david"
           :last-name "smith"
           :gender "m"
           :favorite-color "red"
           :birthdate "1988-02-01"}
          {:first-name "jennifer"
           :last-name "jones"
           :gender "f"
           :favorite-color "blue"
           :birthdate "1998-06-24"}
          {:first-name "alan"
           :last-name "partridge"
           :gender "m"
           :favorite-color "beige"
           :birthdate "1965-11-29"}
          {:first-name "jo"
           :last-name "brand"
           :gender "f"
           :favorite-color "black"
           :birthdate "1959-12-01"}]
         (parse-text comma-test-file)
         (parse-text pipe-test-file)
         (parse-text space-test-file))))
