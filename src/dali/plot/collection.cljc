(ns dali.plot.collection
  (:require
   [dali.spec :refer [create-dali-spec dali-spec?]]
   [dali.type.converter :refer [type->dali]]))

(defn default-style [{:keys [width height overflow-y] :as style}]
  (merge
   style
   {:width (or width "100%")
    ;:height (or height "600px")
    ;:overflow-y (or overflow-y "scroll")
    }))

(defn collection
  "Renders children inside a dali-collection layout wrapper.
   children can be passed either via :childrens in opts or
   as additional parameters children2). priority: children"
  [{:keys [children style _class] :as opts} & children2]
  (let [[opts children2] (if (dali-spec? opts)
                           [{} (concat [opts] children2)]
                           [opts children2])
        children (or children children2)
        children (map type->dali children)]
    (create-dali-spec
     {:viewer-fn 'dali.viewer.hiccup/hiccup
      :data [:div.dali-collection (assoc opts :style (default-style style))]
      :children (into [] children)})))
