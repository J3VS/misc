(ns misc.records.api.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [misc.records.api.response :as response]
            [misc.records.data.read :as read]
            [misc.records.data.write :as write]
            [misc.records.sort :as sort]
            [ring.util.http-response :refer [ok not-found]])
  (:import (clojure.lang ExceptionInfo)))

(defn insert-record-handler
  "Handler for inserting a record"
  [request]
  (try
    (let [file-writer (write/->resource-file-writer)]
      (write/write file-writer (:body request)))
    (ok)
    (catch ExceptionInfo e
      (-> e ex-data response/ex-data->response))))

(defn sorted-data-handler
  "Accepts a sort key, and returns a handler that reads all
  the data from the resource files and sorts it appropriately"
  [sort-key]
  (let [reader (read/->resources-file-reader)]
    (fn [_]
      (try
        (->> (read/read-all reader)
             (sort/by-key sort-key)
             ok)
        (catch IllegalArgumentException e
          (not-found "Not found"))))))

(defroutes records-routes
  (POST "/records" [] insert-record-handler)
  (GET "/records/:sort" [sort] (-> sort keyword sorted-data-handler))
  (route/not-found "Not found"))
