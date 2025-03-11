(ns demo.service.employee
  (:require
   [demo.plot :refer [employee]]))

(defn best-employee [dali-store]
  (let [dali-spec (employee dali-store {:name "Walter" :salary 5000})]
    (println "best employee dali spec: " dali-spec)
    dali-spec))