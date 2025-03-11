(ns demo.plot
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store :refer [write]]))

(defn employee
  [dali-store v]
  (create-dali-spec
   {:viewer-fn 'demo.viewer/employee
    :transform-fn 'dali.transform.load/load-transit
    :data (write dali-store "transit-json" v)}))



