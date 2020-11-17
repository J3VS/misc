(ns misc.records.sort-test
  (:require [clojure.test :refer :all]
            [misc.records.sort :refer :all]))

(def test-data
  [{:first-name "dave"
    :last-name "jones"
    :gender "m"
    :favorite-color "red"
    :birthdate "1965-01-01"}
   {:first-name "mary"
    :last-name "berry"
    :gender "f"
    :favorite-color "white"
    :birthdate "1932-08-24"}
   {:first-name "mad"
    :last-name "max"
    :gender "m"
    :favorite-color "black"
    :birthdate "1955-06-03"}
   {:first-name "gemma"
    :last-name "davis"
    :gender "f"
    :favorite-color "yellow"
    :birthdate "1994-12-25"}
   {:first-name "sophie"
    :last-name "williams"
    :gender "f"
    :favorite-color "blue"
    :birthdate "1979-10-15"}])

(deftest test-gender-sorter
  "tests the sorter that sorts by gender"
  (is (= [{:first-name "mary"
           :last-name "berry"
           :gender "f"
           :favorite-color "white"
           :birthdate "1932-08-24"}
          {:first-name "gemma"
           :last-name "davis"
           :gender "f"
           :favorite-color "yellow"
           :birthdate "1994-12-25"}
          {:first-name "sophie"
           :last-name "williams"
           :gender "f"
           :favorite-color "blue"
           :birthdate "1979-10-15"}
          {:first-name "dave"
           :last-name "jones"
           :gender "m"
           :favorite-color "red"
           :birthdate "1965-01-01"}
          {:first-name "mad"
           :last-name "max"
           :gender "m"
           :favorite-color "black"
           :birthdate "1955-06-03"}]
         (by-key :gender test-data))))

(deftest test-birthdate-sorter
  "tests the sorter that sorts by date of birth"
  (is (= [{:first-name "mary"
           :last-name "berry"
           :gender "f"
           :favorite-color "white"
           :birthdate "1932-08-24"}
          {:first-name "mad"
           :last-name "max"
           :gender "m"
           :favorite-color "black"
           :birthdate "1955-06-03"}
          {:first-name "dave"
           :last-name "jones"
           :gender "m"
           :favorite-color "red"
           :birthdate "1965-01-01"}
          {:first-name "sophie"
           :last-name "williams"
           :gender "f"
           :favorite-color "blue"
           :birthdate "1979-10-15"}
          {:first-name "gemma"
           :last-name "davis"
           :gender "f"
           :favorite-color "yellow"
           :birthdate "1994-12-25"}]
         (by-key :birthdate test-data))))

(deftest test-last-name-sorter
  "tests the sorter that sorts by last name"
  (is (= [{:first-name "sophie"
           :last-name "williams"
           :gender "f"
           :favorite-color "blue"
           :birthdate "1979-10-15"}
          {:first-name "mad"
           :last-name "max"
           :gender "m"
           :favorite-color "black"
           :birthdate "1955-06-03"}
          {:first-name "dave"
           :last-name "jones"
           :gender "m"
           :favorite-color "red"
           :birthdate "1965-01-01"}
          {:first-name "gemma"
           :last-name "davis"
           :gender "f"
           :favorite-color "yellow"
           :birthdate "1994-12-25"}
          {:first-name "mary"
           :last-name "berry"
           :gender "f"
           :favorite-color "white"
           :birthdate "1932-08-24"}]
         (by-key :name test-data))))
