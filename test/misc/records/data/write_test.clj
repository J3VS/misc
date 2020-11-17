(ns misc.records.data.write-test
  (:require [clojure.test :refer :all]
            [misc.records.data.write :refer :all]
            [misc.records.data.read :as read]
            [clojure.java.io :as io]
            [misc.records.data.write :as write]))

(def comma-file (io/resource "records/test_comma_separated.txt"))
(def pipe-file (io/resource "records/test_pipe_separated.txt"))
(def space-file (io/resource "records/test_space_separated.txt"))
(def file-set [comma-file pipe-file space-file])

(defn set-up-files
  []
  (spit comma-file "smith,daniel,m,red,08/12/1974\nwhite,betty,f,white,11/10/1932\njones,gabby,f,blue,01/01/2000" :append false)
  (spit pipe-file "gibson|maggie|f|blue|02/16/1993\nbolton|james|m|yellow|11/21/1987" :append false)
  (spit space-file "jenkins barry m turquoise 02/25/1955" :append false))

(defn clear-files
  []
  (spit comma-file nil :append false)
  (spit pipe-file nil :append false)
  (spit space-file nil :append false))

(defn file-text-fixture [f]
  (set-up-files)
  (f)
  (clear-files))

(use-fixtures :once file-text-fixture)

(deftest test-write-data
  "tests writing data to a file"
  (let [reader (read/->FileReader file-set)
        writer (write/->FileWriter reader file-set)
        space-reader (read/->FileReader [space-file])]
    (is (= [{:last-name "jenkins"
             :first-name "barry"
             :gender "m"
             :favorite-color "turquoise"
             :birthdate "02/25/1955"}]
           (read/read-all space-reader)))
    (is (= [{:last-name "smith"
             :first-name "daniel"
             :gender "m"
             :favorite-color "red"
             :birthdate "08/12/1974"}
            {:last-name "white"
             :first-name "betty"
             :gender "f"
             :favorite-color "white"
             :birthdate "11/10/1932"}
            {:last-name "jones"
             :first-name "gabby"
             :gender "f"
             :favorite-color "blue"
             :birthdate "01/01/2000"}
            {:last-name "gibson"
             :first-name "maggie"
             :gender "f"
             :favorite-color "blue"
             :birthdate "02/16/1993"}
            {:last-name "bolton"
             :first-name "james"
             :gender "m"
             :favorite-color "yellow"
             :birthdate "11/21/1987"}
            {:last-name "jenkins"
             :first-name "barry"
             :gender "m"
             :favorite-color "turquoise"
             :birthdate "02/25/1955"}]
           (read/read-all reader)))
    (write/write writer {:first-name "jessica"
                         :last-name "maple"
                         :gender "f"
                         :favorite-color "taupe"
                         :birthdate "11/26/1972"})

    (is (= [{:last-name "smith"
             :first-name "daniel"
             :gender "m"
             :favorite-color "red"
             :birthdate "08/12/1974"}
            {:last-name "white"
             :first-name "betty"
             :gender "f"
             :favorite-color "white"
             :birthdate "11/10/1932"}
            {:last-name "jones"
             :first-name "gabby"
             :gender "f"
             :favorite-color "blue"
             :birthdate "01/01/2000"}
            {:last-name "gibson"
             :first-name "maggie"
             :gender "f"
             :favorite-color "blue"
             :birthdate "02/16/1993"}
            {:last-name "bolton"
             :first-name "james"
             :gender "m"
             :favorite-color "yellow"
             :birthdate "11/21/1987"}
            {:last-name "jenkins"
             :first-name "barry"
             :gender "m"
             :favorite-color "turquoise"
             :birthdate "02/25/1955"}
            {:first-name "jessica"
             :last-name "maple"
             :gender "f"
             :favorite-color "taupe"
             :birthdate "11/26/1972"}]
           (read/read-all reader)))
    (is (= [{:last-name "jenkins"
             :first-name "barry"
             :gender "m"
             :favorite-color "turquoise"
             :birthdate "02/25/1955"}
            {:first-name "jessica"
             :last-name "maple"
             :gender "f"
             :favorite-color "taupe"
             :birthdate "11/26/1972"}]
           (read/read-all space-reader)))))

