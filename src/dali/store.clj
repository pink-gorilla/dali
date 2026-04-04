(ns dali.store)

(defprotocol dali-store
  (write [this opts v])
  (open [this opts]))

(defn store-data [s dali-spec]
  (let [format (:dali.store/format dali-spec)
        set-url (:dali.store/set-url dali-spec)
        store-data (:dali.store/data dali-spec)
        path (or (:dali.store/path dali-spec) "/")]
    (if (and format set-url store-data)
      (let [{:keys [url]} (write s {:fmt format :path path} store-data)]
        (-> dali-spec
            (update :data set-url url)
            (dissoc :dali.store/format :dali.store/data :dali.store/set-url
                    :dali.store/path)))
      dali-spec)))

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


