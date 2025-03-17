(ns dali.flowy.tap
  (:require
   [dali.flowy :refer [dali-flow-viewer]]))

(defn page [& _args]
  [:div {:style {:height "100vh"
                 :width "100vw"
                 :top "0"
                 :left "0"
                 :margin "0"
                 :padding "0"}}
   [:link {:rel "stylesheet" :href "/r/reset.css"}]
   [dali-flow-viewer 'dali.flowy.tap/listen]])
