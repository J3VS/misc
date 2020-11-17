(ns misc.records.api.response-test
  (:require [clojure.test :refer :all]
            [misc.records.api.response :refer :all]))

(deftest test-ex-data->response
  "testing conversion of validation exception data"
  (is (= {:status 400
          :headers {}
          :body "Missing fields: lastName, birthdate\nThe value for firstName must be a string\nThe value for gender must be 'm' or 'f'"}
         (ex-data->response {:missing-fields [:last-name :birthdate]
                             :first-name "must be a string"
                             :gender "must be 'm' or 'f'"}))))
