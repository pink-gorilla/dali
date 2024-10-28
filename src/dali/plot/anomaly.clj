(ns dali.plot.anomaly
  (:require
   [de.otto.nom.core :as nom]
   [dali.spec :refer [create-dali-spec]]
   ))

(defn remove-ex [[type category data-map]]
  ; [::nom/anomaly ::my-category {:some data}]
  [type category (dissoc data-map :ex)])

(defn anomaly
  "returns a plot specification {:render-fn :spec :data}. 
   The ui shows the exception."
  [nom-anomaly]
  (create-dali-spec
   {:viewer-fn 'dali.viewer.nom/anomaly
    :data  (remove-ex nom-anomaly)}))