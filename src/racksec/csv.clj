(ns racksec.csv
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]))

(defn ^:private transform-row
  "Helper that returns a function to turn a map into a CSV row with
  values that correspond to the positions of keys in the bound 'header'.

  Example:
    header - [:x :y :z :other]
    data - {:z :foo :x :bar :y :baz}

    ((transform-row header) data)
    => [:bar :baz :foo \"\"]"
  [header]
  (fn [row]
    (map #(get row % "") header)))

(defn data->csv
  "Converts a list of maps [map1 ... mapN] with common keys into the
  list [header row1 row2 ... rowN].

  A header can be specified, and the specified key order will be used.

  If a header is not specified, a header will be generated from the list
  of distinct keys present across all the input maps in 'data'. Each
  rowK is a list of data of the same length as the header, where each
  entry rowK[i] corresponds to mapK.get(header[i])."
  ([data]
   (data->csv data (distinct (mapcat keys data))))
  ([data header]
   (let [transform (transform-row header)]
     (if (empty? header)
       header
       (cons header (map transform data))))))

(defn write-csv!
  "Writes formatted data as a CSV to disk.

  Use data->csv to transform a sequence of maps to correctly formatted
  CSV data."
  [filename data]
  (with-open [outfile (io/writer filename)]
    (csv/write-csv outfile data)))
