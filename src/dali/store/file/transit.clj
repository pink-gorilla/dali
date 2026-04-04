(ns dali.store.file.transit
  (:require
   [transit.io :refer [encode decode]]
   [dali.store.file :refer [write-file open-file]]))

(defmethod write-file :transit-json [_ filename data]
  (spit filename (encode data)))

(defmethod open-file :transit-json [_ filename]
  (-> (slurp filename) (decode)))

