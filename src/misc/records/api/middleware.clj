(ns misc.records.api.middleware
  (:require [ring.middleware.case-format :as cf]))

(defn wrap-transform-keys [handler]
  "Middleware to ensure request keys are transformed to kebab case,
  and that response keys are transformed to camel case"
  (fn [request]
    (-> (cf/->kebab request)
        handler
        cf/->camel)))
