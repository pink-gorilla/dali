(ns demo.tap
  (:require
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
