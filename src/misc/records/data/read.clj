(ns misc.records.data.read
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [misc.records.data.files :as files]))

(def delimiter-patterns
  "The list of all possible delimiters/patterns that can be
  used to split/join the data"
  [{:delimiter ","
    :pattern #","}
   {:delimiter "|"
    :pattern #"\|"}
   {:delimiter " "
    :pattern #" "}])

(defn- has-delimiter?
  "Determines whether a given delimiter/pattern is present in
  the provided lines. Note: only the first line is checked"
  [line {:keys [pattern]}]
  (->> line
       (re-find pattern)
       boolean))

(defn- lines->delimiter-pattern
  "Determines which delimiter/pattern is used in the provided lines.
  If the file contains no lines, a random delimiter is selected"
  [lines]
  (if (seq lines)
    (->> delimiter-patterns
         (filter (partial has-delimiter? (first lines)))
         first)
    (rand-nth delimiter-patterns)))

(defn file->delimiter-pattern
  "Determines which delimiter/pattern is used in the file"
  [file]
  (-> file
      slurp
      string/split-lines
      lines->delimiter-pattern))

(defn transform-row
  "Converts a row obtained by splitting on a delimiter into a keyed map"
  [[last-name first-name gender color birthdate]]
  {:last-name last-name
   :first-name first-name
   :gender gender
   :favorite-color color
   :birthdate birthdate})

(defn read-lines
  "Reads the lines from a file"
  [file]
  (-> file
      slurp
      string/split-lines))

(defn count-rows
  "Counts the number of lines in a file"
  [file]
  (-> (read-lines file) count))

(defn parse-text
  "Converts the raw text inside a file into a list of maps
  with descriptive keys"
  [text]
  (let [lines (string/split-lines text)]
    (when-let [{:keys [pattern]} (lines->delimiter-pattern lines)]
      (->> lines
           (map #(string/split % pattern))
           (map transform-row)))))

(defn read-file
  [file]
  "Converts a file into a list of maps with descriptive keys"
  (-> file
      slurp
      parse-text))

(defprotocol Reader
  (smallest-file [_])
  (read-all [_]))

(defrecord FileReader [file-set]
  Reader
  (smallest-file [_]
    (->> file-set
         (sort-by count-rows)
         first))
  (read-all [_]
    (mapcat read-file file-set)))

(defn ->resources-file-reader
  "Returns a file reader backed by the resource files inside misc/resources/records"
  []
  (->FileReader (files/resource-set)))
