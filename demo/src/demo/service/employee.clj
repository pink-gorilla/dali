(ns demo.service.employee
  (:require
   [demo.plot :refer [employee]]))

(defn best-employee []
  (let [dali-spec (employee {:name "Walter" :salary 5000})]
    (println "best employee dali spec: " dali-spec)
    dali-spec))