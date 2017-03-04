# 0.1.0

- Provides `data->csv`, which converts a list of maps `[map1 ... mapN]` with
  common keys into the list `[header row1 row2 ... rowN]`. A custom header can
  be optionally specified.
- Provides `write-csv!`, which wraps `clojure.data.csv` to write properly
  formatted data to the file corresponding to the provided filename.
