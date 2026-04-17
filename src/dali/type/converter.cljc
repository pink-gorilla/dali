(ns dali.type.converter
  (:require
   [dali.type.protocol :refer [dali-convertable to-dali]]
   [dali.type.error :refer [unknown-type type-convert-err]]))

#?(:clj
   (defn type->dali [v] ; here env is first
     (try
       (if (satisfies? dali-convertable v)
         (to-dali v)
         (unknown-type v))
       (catch Exception ex
         (type-convert-err (ex-message ex)))
       (catch Throwable t
         (println "T"))))

   :cljs
   (defn type->dali [v]
     (try
       (to-dali v)
       (catch js/Exception _
         (unknown-type v)))))

(comment
  (require '[tablecloth.api :as tc])
  (def ds (tc/dataset {:a [1 2 3] :b [4 5 6]}))

  (type->dali (:a ds))
  (type->dali ds)

  (keys ds)
  (vals ds)

  ;; ds satisfies dali-convertable. dont know why.
  (satisfies? dali-convertable ds)
  (satisfies? dali-convertable (:a ds))

  (satisfies? dali-convertable (type ds))
  (satisfies? dali-convertable (type (:a ds)))

  (satisfies? dali-convertable (type 54))

  ;(extends? dali-convertable (type ds))
  ;(extends? dali-convertable (type (:a ds)))
;  (extends? dali-convertable (type 44))

  (type ds)

  (.getInterfaces (type ds))
 ; 
  )