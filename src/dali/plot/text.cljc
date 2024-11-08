(ns dali.plot.text
  (:require
   [dali.spec :refer [create-dali-spec]]))

(defn text
  [{:keys [_class _style _text] :as data}]
  (create-dali-spec
   {:viewer-fn 'dali.viewer.text/text
    :data data}))