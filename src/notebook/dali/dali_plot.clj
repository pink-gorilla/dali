(ns notebook.dali.dali-plot
  (:require
   [clojure.java.io :as io]
   [dali.plot.exception :refer [exception]]
   [dali.plot.hiccup :refer [hiccup]]
   [dali.plot.image :refer [image]]
   [dali.plot.text :refer [text]]
   [dali.plot.collection :refer [collection]])
  (:import
   javax.imageio.ImageIO))


(text {:style {:color "blue"
               :backgroundColor "yellow"
               :margin "10px"
               :border "1px solid black"}
       :text "Hello, \r\nworld!"})

;; 
;; test plotting an image
;; 

(def sun-img
  (ImageIO/read (io/resource "demodata/sun.png")))

(image {:alt "sun"} sun-img)

;;
;; plot a collection of dali-specs
;;


;; this deos not work, as the children are not dali-specs.
;; however, it should be catched by the error-boundary and shown as an error.
;; this does not work either; why?
#_(collection 
 {:style {:border "1px solid green"
          :margin "10px"
          :display "grid"
          :gridTemplateColumns "1fr 1fr 1fr 1fr 1fr"}
  :children (range 10)
  })


(collection
 {:class "grid" ; grid will show 1-3 columns depending on the width of the notebook.
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

;;
;; lets plot an exception
;;

(exception (ex-info "more data needed" {:x 3 :a "test"}))

