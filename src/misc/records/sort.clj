(ns misc.records.sort)

;; Multimethod to provide a key based sort function for record data
(defmulti by-key (fn [key _] key))

(defmethod by-key :gender
  [_ data]
  (sort-by (juxt :gender :last-name) data))

(defmethod by-key :birthdate
  [_ data]
  (sort-by :birthdate data))

(defmethod by-key :name
  [_ data]
  (->> data
       (sort-by :last-name)
       reverse))
