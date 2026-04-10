(ns dali.error-boundary
  (:require
   [reagent.core :as r]
   [uix.core :refer [$] :as uix]))

(def error-boundary
  (uix/create-error-boundary
   {:derive-error-state (fn [error] {:error error}) ;; maps JS Error into component's state
    :did-catch (fn [error info] (println error))} ;; for side effects e.g. logging an error into your tracing system etc
   (fn [[{:keys [error]} set-state] {:keys [children]}] ;; signature ([state set-state] props)
     (if error
       ($ :div {:style {:color :red}}
          ($ :h1 "an error has occured!")
          error)
       children))))

(defn error-boundary-reagent [child]
  [:<>
   ($ error-boundary
      (r/as-element [:<> child]))])


