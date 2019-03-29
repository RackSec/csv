[![codecov](https://codecov.io/gh/RackSec/csv/branch/master/graph/badge.svg)](https://codecov.io/gh/RackSec/csv)
[![Clojars Project](https://img.shields.io/clojars/v/racksec/csv.svg)](https://clojars.org/racksec/csv)

# racksec/csv

Utilities for writing lists of maps to CSV files.

## Usage

How to use this library is best illustrated with some examples.

```clojure
(def source-data
  [{"a" "foo" "b" "bar" "c" "baz"}
   {"a" "x" "b" "y" "c" "z"}])

(def formatted-data (racksec.csv/data->csv source-data))
formatted-data
=> [["a" "b" "c"]
    ["foo" "bar" "baz"]
    ["x" "y" "z"]]

(def custom-header ["c" "b" "a"])

(racksec.csv/data->csv source-data custom-header)
=> [["c" "b" "a"]
    ["baz" "bar" "foo"]
    ["z" "y" "x"]]

(racksec.csv/write-csv! "mydata.csv" formatted-data)
(slurp "mydata.csv")
=> "a,b,c\nfoo,bar,baz\nx,y,z\n"
```

## License

Copyright © 2016 Rackspace Hosting, Inc.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
