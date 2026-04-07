(ns demo.tap
  (:require
   [dali.plot.exception :refer [exception]]
   [dali.plot.hiccup :refer [hiccup]]
   [dali.plot.collection :refer [collection]]
   [dali.plot.image :refer [image]]
   ; impl
   [dali.store.file :refer [open-file]]
   [demo.service.saying :refer [saying]]
   [demo.service.employee :refer [best-employee]]))

; run: clj -X:jetty
; goto browser on port 8080 and click on tap-viewer

; jack-in to repl on port 9100 
; eval below:

(tap> (saying {:id 1}))
(tap> (saying {:id 2}))

(tap> (best-employee))

; image
(def i (open-file :image "resources/sun.png"))
(def sun
  (image {:alt "sun"} i))

(tap> sun)

(tap> (exception (ex-info "more data needed" {:x 3 :a "test"})))


(tap> (collection
       {:class "grid"
        :style {:border "1px solid black"
              ;  :background-color "blue"
                ;:height "100px"
               ; :width "200px"
                }}
       (hiccup [:h1.title "saying collection"])
       (saying {:id 1})
       (saying {:id 2})
       (saying {:id 5})
       sun
       (hiccup [:p "123"])
       (hiccup [:p "456"])
       (hiccup [:p "789"])))


