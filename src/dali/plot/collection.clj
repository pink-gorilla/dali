(ns dali.plot.collection
  (:require
   [dali.spec :refer [create-dali-spec dali-spec?]]))


(defn collection
  "Renders children inside a dali-collection layout wrapper."
  [opts & children]
  (let [[opts children] (if (dali-spec? opts)
                          [{} (concat [opts] children)]
                          [opts children])]
    (create-dali-spec
     {:viewer-fn 'dali.viewer.hiccup/hiccup
      :data [:div.dali-collection opts]
      :children (into [] children)})))
