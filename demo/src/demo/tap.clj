(ns demo.tap
  (:require
   [dali.plot.exception :refer [exception]]
   [dali.plot.hiccup :refer [hiccup]]
   [dali.plot.collection :refer [collection]]
   [demo.service.saying :refer [saying]]
   [demo.service.employee :refer [best-employee]]
   [demo.service.image :refer [sun]]))

; run: clj -X:jetty
; goto browser on port 8080 and click on tap-viewer

; jack-in to repl on port 9100 
; eval below:

(tap> (saying {:id 1}))

(tap> (saying {:id 2}))

(tap> (saying {:id 5}))

(tap> (best-employee))

(tap> (sun))

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
       (hiccup [:p "123"])
       (hiccup [:p "456"])
       (hiccup [:p "789"])))

(tap> (exception (ex-info "more data needed" {:x 3 :a "test"})))
