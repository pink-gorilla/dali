(ns demo.plot
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects 
   ))

(defn employee [v]
  (create-dali-spec
   {:viewer-fn 'demo.viewer/employee
    :data v}))

(defn bad []
  (create-dali-spec
   {:viewer-fn 'demo.error/bad-component
    :data 42}))

(comment

  (require '[dali.store.file :refer [create-dali-file-store]])
  (def s (create-dali-file-store {:fpath ".gorilla/public/dali-tap"
                                  :rpath "/r/dali-tap"}))
  (def e (employee {:name "Walter" :salary 5000}))
  e
  (type e)

  (require '[dali.store :refer [store-data open]])
  (def e2 (store-data s e))
  (type e2)
  e2
  "/r/dali-tap/yvZJO..transit.json"
  (open s {:fmt :transit-json
           :filename "/yvZJO..transit.json"})

; .gorilla/public/dali-tap/r/dali-tap/yvZJO..transit.json 
 ;
  )



