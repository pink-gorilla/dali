(ns dali.plot.hiccup
  (:require
   [dali.spec :refer [create-dali-spec]]))

(defn hiccup
  [h]
  (create-dali-spec
   {:viewer-fn 'dali.viewer.hiccup/hiccup
    :data h}))

(defn text
  [{:keys [_class _style _text] :as data}]
  (create-dali-spec
   {:viewer-fn 'dali.viewer.exception/text
    :data data}))