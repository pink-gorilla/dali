(ns dali.plot.hiccup
  (:require
   [dali.spec :refer [create-dali-spec]]))

(defn hiccup
  [h]
  (create-dali-spec
   {:viewer-fn 'dali.viewer.hiccup/hiccup
    :data h}))