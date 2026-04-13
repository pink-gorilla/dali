(ns demo.page.error
  (:require
   [reagent.core :as r]
   [demo.nav :refer [goto!]]
   [dali.error-boundary :refer [error-boundary error-boundary-reagent]]
   [uix.core :refer [defui $] :as uix]))

(defn bad-component []
  ;(throw (js/Exception. "asdf"))
  (throw "I am a bad reagent component")
  )

(defui erroring-component []
  (throw "I am a bad uix component."))

(defui uix-error-boundary-demo []
  ($ error-boundary
     ($ erroring-component)))

(defn sub-component []
  [:div 
   [:h1 "sub-component title"]
   [error-boundary-reagent [bad-component]]
   [:p "sub component footer"]])

(defn page [_]
  [:div 
   [:h1 "uix error boundary demo"]
   
   [:h2 "uix"]
   ($ uix-error-boundary-demo)
   
   [:h2 "reagent"]
   [error-boundary-reagent  [bad-component] ]

   [:h2 "sub component"]
   [sub-component]
   ])