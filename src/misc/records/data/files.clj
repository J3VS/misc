(ns misc.records.data.files
  (:require [clojure.java.io :as io]))

(def resources ["records/comma_delimited.txt"
                "records/pipe_delimited.txt"
                "records/space_delimited.txt"])

(defn resource-set [] (map io/resource resources))
