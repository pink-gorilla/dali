(ns dali.flowy
  (:require
   [reagent.core :as r]
   [flowy.reflower :refer [task flow]]
   [flowy.reagent :refer [flow->ratom]]
   [dali.viewer :refer [viewer2]]))

(defn dali-flow-viewer [fun & args]
  (r/with-let [f (apply flow fun args)
               [a dispose!] (flow->ratom f :waiting)]
    (if (= :waiting @a)
      [:div "dali-flow-viewer waiting for data"]
      [viewer2 @a])
    (finally
      (println "Cleanup: stopping flow!")
      (dispose!))))
