(ns dali.store)

(defprotocol dali-store
  (write [this opts v])
  (open [this opts]))

(declare store-data-children)

(defn store-data [s dali-spec]
  (let [format (:store-format dali-spec)
        set-url (:store-set-url dali-spec)
        store-data (:store-data dali-spec)
        path (or (:store-path dali-spec) "/")
        dali-spec (if (and format set-url store-data)
                        ; needs store
                    (let [{:keys [url]} (write s {:fmt format :path path} store-data)]
                      (-> dali-spec
                          (update :data set-url url)
                          (dissoc :store-format :store-data :store-set-url
                                  :store-path)))
                    dali-spec)]
    ; finally store for all children.
    (store-data-children s dali-spec)))

(defn store-data-children [s dali-spec]
  (if-let [children (:children dali-spec)]
    (assoc dali-spec
           :children
           (->> children
                (map #(store-data s %))
                (into [])))
    dali-spec))


(comment

  (require '[dali.store.file :refer [open-file create-dali-file-store]])
  (def i (open-file "png" "resources/sun.png"))
  (require '[dali.plot.image :refer [image]])
  (def sun  (image {:alt "sun"} i))
  sun
  (keys sun)
  (def s (create-dali-file-store {:fpath ".gorilla/public/dali-tap"
                                  :rpath "/r/dali-tap"}))
  (require '[dali.store.file.image])
  ; side effects

  (store-data s sun)

; 
  )


