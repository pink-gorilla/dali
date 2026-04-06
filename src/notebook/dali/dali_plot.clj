(ns notebook.dali.dali-plot
  (:require
   [clojure.java.io :as io]
   [dali.plot.exception :refer [exception]]
   [dali.plot.hiccup :refer [hiccup]]
   [dali.plot.image :refer [image]]
   [dali.plot.collection :refer [collection]])
  (:import
   javax.imageio.ImageIO))

(def sun-img
  (ImageIO/read (io/resource "demodata/sun.png")))

(collection
 {:class "grid"
  :style {:border "1px solid black"
              ;  :background-color "blue"
                ;:height "100px"
               ; :width "200px"
          }}
 (hiccup [:h1.title "demo grid collection"])

 (image {:alt "sun"} sun-img)

 (hiccup [:p "123"])
 (hiccup [:p "456"])
 (hiccup [:p "789"])
 (hiccup [:p "123"])
 (hiccup [:p "456"])
 (hiccup [:p "789"]))

(exception (ex-info "more data needed" {:x 3 :a "test"}))

