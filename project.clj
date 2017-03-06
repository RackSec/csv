(defproject racksec/csv "0.1.1-SNAPSHOT"
  :description "Clojure utilities for writing lists of maps to CSV files"
  :url "http://github.com/RackSec/csv-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.3"]]
  :plugins [[lein-cljfmt "0.3.0"]
            [lein-environ "1.0.0"]
            [lein-cloverage "1.0.9"]
            [jonase/eastwood "0.2.3"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :test {:dependencies [[me.raynes/fs "1.4.6"]]}})
