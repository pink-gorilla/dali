(ns demo.viewer)

(defn employee [data]
  [:div
   "I am the employee viewer"
   [:p "data:"
    (pr-str data)]])
