(ns misc.records.utils
  (:require [clj-time.format :as f]))

(def birthdate-formatter (f/formatter "MM/dd/yyyy"))
(defn parse-birthdate [birthdate] (f/parse birthdate-formatter birthdate))
