(ns demo.app
  (:require
   [frontend.css :refer [css-loader]]
   [shadowx.core :refer [get-resource-path]]))

(defn wrap [page match]
  [:div
   [css-loader (get-resource-path)]
   [page match]])

(def routes
  [["/" {:name 'demo.page/page}]
   ])

