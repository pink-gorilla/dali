(ns dali.flowy.tap
  (:require
   [missionary.core :as m]
   [taoensso.timbre :refer [info]]
   [dali.spec :refer [dali-spec?]]
   [dali.store.file :refer [create-dali-file-store]]
   [dali.store :refer [store-data]]
   [dali.type.converter :refer [type->dali]]
   ))

(def tap-flow
  (m/stream
   (m/observe
    (fn [!]
      (info "dali tap viewer is listening to clojure tap events..")
      (add-tap !)
      (fn []
        (info "dali tap viewer stopped (unlisten to clojure tap events).")
        (remove-tap !))))))

(def s (create-dali-file-store {:fpath ".gorilla/public/dali-tap"
                                :rpath "/r/dali-tap"}))

(defn listen []
  (m/eduction
   ;(filter dali-spec?)
   (map type->dali)
   (map (fn [dali-spec] (store-data s dali-spec)))
   tap-flow))

