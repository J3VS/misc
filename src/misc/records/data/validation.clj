(ns misc.records.data.validation
  (:require [clojure.spec.alpha :as s]
            [clj-time.format :as f]))

(def formatter (f/formatter "yyyy-MM-dd"))
(defn valid-date? [s] (try (f/parse formatter s) (catch IllegalArgumentException _ false)))

(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::gender #{"f" "m"})
(s/def ::favorite-color string?)
(s/def ::birthdate valid-date?)
(s/def ::data (s/keys :req-un [::first-name ::last-name ::gender ::favorite-color ::birthdate]))

(defn problem->missing-field
  "Plucks the issing field out of the spec predicate"
  [problem]
  (-> problem
      :pred
      (nth 2)
      last))

(defn in->message
  "Hardcoded string messages describing what format each field should be in"
  [in]
  (case in
    (:first-name :last-name :favorite-color) "must be a string"
    :gender "must be 'm' or 'f'"
    :birthdate "must be of the form YYYY-mm-dd"))

(defn transform-problem
  [[in problems]]
  (if in
    [in (in->message in)]
    [:missing-fields (map problem->missing-field problems)]))

(defn problems->ex-data
  "Groups the problems by the :in key, if this value is empty, it indicates
  an issue with the map itself (a missing key), otherwise we can refer to in->message
  to find the issue based on the provided key in the :in"
  [problems]
  (->> problems
       (group-by (comp first :in))
       (map transform-problem)
       (into {})))

(defn check
  "Validates the provided data, and returns a bespoke exception info containing
  a description of what failed"
  [data]
  (when-let [{::s/keys [problems]} (s/explain-data ::data data)]
    (when problems
      (throw (ex-info "Validation Error" (problems->ex-data problems))))))
