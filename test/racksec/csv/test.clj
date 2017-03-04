(ns racksec.csv.test
  (:require [clojure.test :refer :all]
            [me.raynes.fs :refer [delete exists?]]
            [racksec.csv :refer :all]))

(deftest transform-row-tests
  (testing "1-1 match for header and row"
    (let [header ["a" "b" "c"]
          row {"a" "foo" "b" "bar" "c" "baz"}
          transform-row (#'racksec.csv/transform-row header)
          output (transform-row row)
          expected ["foo" "bar" "baz"]]
      (is (= expected output))))
  (testing "fills in optional fields as null string"
    (let [header ["lots" "of" "fields"]
          row {"lots" "data"}
          transform-row (#'racksec.csv/transform-row header)
          output (transform-row row)
          expected ["data" "" ""]]
      (is (= expected output))))
  (testing "fills in optional fields positionally"
    (let [header ["lots" "of" "fields"]
          row {"fields" "data"}
          transform-row (#'racksec.csv/transform-row header)
          output (transform-row row)
          expected ["" "" "data"]]
      (is (= expected output)))))

(deftest data->csv-tests
  (testing "does the right thing for empty case"
    (is (empty? (data->csv []))))
  (testing "does the right thing for small case"
    (let [source-data [{"a" "foo" "b" "bar" "c" "baz"}]
          header ["a" "b" "c"]
          data ["foo" "bar" "baz"]
          output (data->csv source-data)
          expected [header data]]
      (is (= expected output))))
  (testing "does the right thing for variable size row case"
    (let [source-data [{"a" "foo" "b" "bar" "c" "baz"}
                       {"x" "lol" "y" "olo" "z" "wat"}]
          header ["a" "b" "c" "x" "y" "z"]
          data [["foo" "bar" "baz" "" "" ""]
                ["" "" "" "lol" "olo" "wat"]]
          output (data->csv source-data)
          expected (cons header data)]
      (is (= expected output))))
  (testing "allows user to specify their own header"
    (let [source-data [{"a" "foo" "b" "bar" "c" "baz"}
                       {"c" "lol" "b" "olo" "z" "wat"}]
          header ["b" "a" "c"]
          data [["bar" "foo" "baz"]
                ["olo" "" "lol"]]
          output (data->csv source-data header)
          expected (cons header data)]
      (is (= expected output)))))

(deftest write-csv!-tests
  (testing "writes data accurately to the correct filename"
    (let [filename "write-csv-test.csv"
          data [["a" "b" "c" "x" "y" "z"]
                ["foo" "bar" "baz" "" "" ""]
                ["" "" "" "lol" "olo" "wat"]]
          expected "a,b,c,x,y,z\nfoo,bar,baz,,,\n,,,lol,olo,wat\n"]
      (write-csv! filename data)
      (is (exists? filename))
      (is (= expected (slurp filename)))
      (delete filename))))
