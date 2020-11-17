(ns misc.records.data.write
  (:require [clojure.java.io :as io]
            [clojure.spec.alpha :as s]
            [clojure.string :as string]
            [misc.records.data.files :as files]
            [misc.records.data.read :as read]
            [misc.records.data.validation :as v]))

(def order-fields (juxt :last-name :first-name :gender :favorite-color :birthdate))

(defn data->row
  "Converts the data to a string row using the delimiter (adds a newline)"
  [data delimiter]
  (->> data
       order-fields
       (string/join delimiter)
       (str "\n")))

(defn write-to-file
  "Writes the data to the file by determining what the existing delimiter is, and
  appending the data to the end"
  [file data]
  (let [{:keys [delimiter]} (read/file->delimiter-pattern file)
        row (data->row data delimiter)]
    (spit file row :append true)))

(defprotocol Writer
  (write [_ data]))

(defrecord FileWriter [file-reader file-set]
  Writer
  (write [_ data]
    (v/check data)
    (-> (read/smallest-file file-reader)
        (write-to-file data))))

(defn ->resource-file-writer
  "Returns a file writer backed by the resource files inside misc/resources/records"
  []
  (->FileWriter (read/->resources-file-reader) (files/resource-set)))
