(ns dali.flowy.tap
  (:require
   [missionary.core :as m]
   [taoensso.timbre :refer [info]]
   [dali.spec :refer [dali-spec?]]))

(def tap-flow
  (m/stream
   (m/observe
    (fn [!]
      (info "dali tap viewer is listening to clojure tap events..")
      (add-tap !)
      (fn []
        (info "dali tap viewer stopped (unlisten to clojure tap events).")
        (remove-tap !))))))

(defn listen []
  (m/eduction
   (filter dali-spec?)
   tap-flow))

