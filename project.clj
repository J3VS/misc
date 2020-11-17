(defproject misc "0.1.0-SNAPSHOT"
  :description "Miscellanous"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "1.0.194"]
                 [camel-snake-kebab "0.4.0"]
                 [clj-time "0.15.2"]
                 [compojure "1.6.2"]
                 [metosin/ring-http-response "0.9.1"]
                 [ring-middleware-case-format "0.2.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.5.0"]]
  :target-path "target/%s"
  :profiles {:dev {:resource-paths ["test/resources"]}
             :uberjar {:aot :all}
             :records-api {:plugins [[lein-ring "0.12.5"]]
                           :ring {:handler misc.records.api.handler/app
                                  :nrepl {:start? true
                                          :port 9998}}}
             :records-cli {:main misc.records.cli}}
  :aliases {"records-api" ["with-profile" "+records-api" "ring" "server-headless"]
            "records-cli" ["with-profile" "+records-cli" "run"]})
