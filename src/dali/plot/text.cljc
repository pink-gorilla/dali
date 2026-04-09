(ns dali.plot.text
  (:require
   [dali.spec :refer [create-dali-spec]]))


(defn default-style [{:keys [width height overflow-y] :as style}]
  (merge
   style
   {:width (or width "100%")
    ;:height (or height "600px")
    ;:overflow-y (or overflow-y "scroll")
    }))

(defn text
  [{:keys [class style text] :as data}]
  (create-dali-spec
   {:viewer-fn 'dali.viewer.text/text
    :data (merge {:style (default-style style)
                  :text (or text "no text provided")
                  :class (or class "")}
                 data)}))