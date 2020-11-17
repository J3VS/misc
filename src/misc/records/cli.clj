(ns misc.records.cli
  (:require [clojure.pprint :as pp]
            [clojure.tools.cli :as cli]
            [misc.records.data.read :as read]
            [misc.records.sort :as sort])
  (:gen-class))

(def cli-options
  [["-f" "--file FILE" "File path"
    :id :file
    :default nil
    :validate [some? "Must be provided"]]
   ["-s" "--sort SORT" "Sort function"
    :id :sort
    :default :gender
    :parse-fn keyword
    :validate [#{:gender :birthdate :name} "Must be gender, birthdate or name "]]])

(defn print-data
  "Prints out the sorted data from the provided file"
  [{{:keys [file sort]} :options}]
  (let [reader (read/->FileReader [file])]
    (->> (read/read-all reader)
         (sort/by-key sort)
         pp/pprint)))

(defn -main [& args]
  "Command line function to print out a parsed file sorted by a provided sort function.
  Executed as follows:
  lein records-cli -f <file-path> -s <sort>
  sort is one of 'gender', 'birthdate' or 'name'"
  (print-data (cli/parse-opts args cli-options)))
