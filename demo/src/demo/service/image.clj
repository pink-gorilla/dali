(ns demo.service.image
  (:require
   [dali.plot.image :refer [image]]
   [dali.store.file :refer [create-dali-file-store open-file]]))

(def env
  {:dali-store (create-dali-file-store
                {:fpath ".data/public/dali"
                 :rpath "/r/dali"})})

(def i (open-file "png" "resources/sun.png"))

(defn sun []
  (image env {} i))