(ns dali.viewer.nom)

(defn anomaly [data]
  [:div {:class "bg-blue-500"}
   [:h1.bg-red-500 "error!"]
   [:p
    (pr-str data)]])
