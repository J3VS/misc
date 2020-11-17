(ns misc.records.api.middleware-test
  (:require [clojure.test :refer :all]
            [misc.records.api.middleware :refer :all]
            [ring.util.http-response :refer [ok]]))

(deftest test-wrap-transform-keys-request
  "tests the correct keys are transformed in the request"
  (let [wrapped-handler (wrap-transform-keys
                          (fn [{:keys [body params]}]
                            (mapcat keys [body params])))]
    (is (= ["hello-world" :foo-bar "bloop-toop"]
           (wrapped-handler {:body {"helloWorld" true
                                    :fooBar 123}
                             :params {"bloopToop" "floop"}})))))

(deftest test-wrap-transform-key-response
  "tests the correct keys are transformed in the response"
  (let [wrapped-handler (wrap-transform-keys
                          (fn [_]
                            (ok {"hello-world" true
                                 :foo-bar 123})))]
    (is (= {"helloWorld" true
            :fooBar 123}
           (:body (wrapped-handler nil))))))
