(ns demo.plot
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects 
   ))


(defn set-url [data url]
  (let [data (or data {})]
    (assoc data :load {:url url})))
  

(defn employee [v]
  (create-dali-spec
   {:viewer-fn 'demo.viewer/employee
    :transform-fn 'dali.transform.load/load-transit
    :store-format :transit-json
    :store-data v
    :store-set-url set-url}))

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



