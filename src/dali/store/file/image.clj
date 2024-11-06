(ns dali.store.file.image
  (:refer-clojure :exclude [read])
  (:require
   [clojure.java.io :as io]
   [dali.store.file :refer [write-file open-file]])
  (:import
   java.io.File
   java.awt.image.BufferedImage
   javax.imageio.ImageIO))

(defmethod write-file "png" [_ filename ^BufferedImage buffered-image]
  (ImageIO/write buffered-image
                 "png"
                 ^java.io.File (io/file filename)))

(defmethod open-file "png" [_ filename]
  (ImageIO/read (io/file filename)))

