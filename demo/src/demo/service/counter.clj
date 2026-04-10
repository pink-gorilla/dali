(ns demo.service.counter
  (:require
   [missionary.core :as m]
   [dali.plot.hiccup :refer [hiccup]]))

(def counter
  (m/stream
   (m/ap
    (loop [i 0]
      (m/amb
       (m/? (m/sleep 5000 i))
       (recur (inc i)))))))

(defn dalify-value [v]
  (hiccup [:div.bg-blue-300.w-100.h-100
           [:h1 "Counter"]
           [:hr]
           [:p (str v)]]))

(def dali-counter
  (m/eduction
   (map dalify-value) counter))

(defn counter-fn []
  ;counter
  dali-counter)
