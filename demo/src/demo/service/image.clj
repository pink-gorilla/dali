(ns demo.service.image
  (:require
   [dali.plot.image :refer [image]]
   [dali.store.file :refer [open-file]]))

(def i (open-file :image "resources/sun.png"))

i

(defn sun []
  (image {:alt "sun"} i))