(ns demo.service.employee
  (:require
   [dali.store.cache :refer [dali-cache-store]]
   [demo.plot :refer [employee]]))

(defn best-employee
  ([]
   (best-employee dali-cache-store))
  ([dali-store]
   (let [dali-spec (employee dali-store {:name "Walter" :salary 5000})]
     (println "best employee dali spec: " dali-spec)
     dali-spec)))