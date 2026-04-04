(ns notebook.dali.dali-plot
  (:require
   [dali.plot.exception :refer [exception]]
   [dali.plot.hiccup :refer [hiccup]]
   [dali.plot.collection :refer [collection]]))

(collection
 {:class "grid"
  :style {:border "1px solid black"
              ;  :background-color "blue"
                ;:height "100px"
               ; :width "200px"
          }}
 (hiccup [:h1.title "demo grid collection"])
 sun
 (hiccup [:p "123"])
 (hiccup [:p "456"])
 (hiccup [:p "789"])
 (hiccup [:p "123"])
 (hiccup [:p "456"])
 (hiccup [:p "789"]))

(exception (ex-info "more data needed" {:x 3 :a "test"}))

