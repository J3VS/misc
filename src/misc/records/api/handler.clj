(ns misc.records.api.handler
  (:require [compojure.core :refer [routes]]
            [misc.records.api.middleware :as middleware]
            [misc.records.api.routes :refer [records-routes]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

(def app
  (-> (routes records-routes)
      (middleware/wrap-transform-keys)
      (wrap-json-response)
      (wrap-json-body {:keywords? true})
      (wrap-defaults api-defaults)))
