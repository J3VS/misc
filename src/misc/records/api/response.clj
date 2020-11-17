(ns misc.records.api.response
  (:require [clojure.string :as string]
            [camel-snake-kebab.core :as csk]
            [ring.util.http-response :refer [bad-request]]))

(defn externalize-key
  "Converts a key from :key-word to keyWord"
  [key]
  (-> key name csk/->camelCase))

(defn externalize-keys
  "Converts a list of [:some-key :words-here] to
  a string \"someKey, wordsHere\""
  [keys]
  (->> keys
       (map externalize-key)
       (string/join ", ")))

(defn- problem->message
  "Takes the key and value in the ex-data map
  and converts it into a string message to send to the user"
  [[type value]]
  (case type
    :missing-fields (str "Missing fields: " (externalize-keys value))
    (str "The value for " (externalize-key type) " " value)))

(defn ex-data->response
  "Converts the ex-data response from a map with internalized keys
  to a bad request response with a human readable message"
  [ex-data]
  (->> ex-data
       (map problem->message)
       (string/join "\n")
       bad-request))
