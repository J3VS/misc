(ns misc.records.sort-test
  (:require [clojure.test :refer :all]
            [misc.records.sort :refer :all]))

(def test-data
  [{:first-name "dave"
    :last-name "jones"
    :gender "m"
    :favorite-color "red"
    :birthdate "01/01/1965"}
   {:first-name "mary"
    :last-name "berry"
    :gender "f"
    :favorite-color "white"
    :birthdate "08/24/1932"}
   {:first-name "mad"
    :last-name "max"
    :gender "m"
    :favorite-color "black"
    :birthdate "06/03/1955"}
   {:first-name "gemma"
    :last-name "davis"
    :gender "f"
    :favorite-color "yellow"
    :birthdate "12/25/1994"}
   {:first-name "sophie"
    :last-name "williams"
    :gender "f"
    :favorite-color "blue"
    :birthdate "12/26/1994"}])

(deftest test-gender-sorter
  "tests the sorter that sorts by gender"
  (is (= [{:first-name "mary"
           :last-name "berry"
           :gender "f"
           :favorite-color "white"
           :birthdate "08/24/1932"}
          {:first-name "gemma"
           :last-name "davis"
           :gender "f"
           :favorite-color "yellow"
           :birthdate "12/25/1994"}
          {:first-name "sophie"
           :last-name "williams"
           :gender "f"
           :favorite-color "blue"
           :birthdate "12/26/1994"}
          {:first-name "dave"
           :last-name "jones"
           :gender "m"
           :favorite-color "red"
           :birthdate "01/01/1965"}
          {:first-name "mad"
           :last-name "max"
           :gender "m"
           :favorite-color "black"
           :birthdate "06/03/1955"}]
         (by-key :gender test-data))))

(deftest test-birthdate-sorter
  "tests the sorter that sorts by date of birth"
  (is (= [{:first-name "mary"
           :last-name "berry"
           :gender "f"
           :favorite-color "white"
           :birthdate "08/24/1932"}
          {:first-name "mad"
           :last-name "max"
           :gender "m"
           :favorite-color "black"
           :birthdate "06/03/1955"}
          {:first-name "dave"
           :last-name "jones"
           :gender "m"
           :favorite-color "red"
           :birthdate "01/01/1965"}
          {:first-name "gemma"
           :last-name "davis"
           :gender "f"
           :favorite-color "yellow"
           :birthdate "12/25/1994"}
          {:first-name "sophie"
           :last-name "williams"
           :gender "f"
           :favorite-color "blue"
           :birthdate "12/26/1994"}]
         (by-key :birthdate test-data))))

(deftest test-last-name-sorter
  "tests the sorter that sorts by last name"
  (is (= [{:first-name "sophie"
           :last-name "williams"
           :gender "f"
           :favorite-color "blue"
           :birthdate "12/26/1994"}
          {:first-name "mad"
           :last-name "max"
           :gender "m"
           :favorite-color "black"
           :birthdate "06/03/1955"}
          {:first-name "dave"
           :last-name "jones"
           :gender "m"
           :favorite-color "red"
           :birthdate "01/01/1965"}
          {:first-name "gemma"
           :last-name "davis"
           :gender "f"
           :favorite-color "yellow"
           :birthdate "12/25/1994"}
          {:first-name "mary"
           :last-name "berry"
           :gender "f"
           :favorite-color "white"
           :birthdate "08/24/1932"}]
         (by-key :name test-data))))
