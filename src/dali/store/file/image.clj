(ns dali.store.file.image
  (:require
   [clojure.java.io :as io]
   [dali.store.file :refer [write-file open-file]])
  (:import
   java.io.File
   java.awt.image.BufferedImage
   javax.imageio.ImageIO))

(defmethod write-file :image [_ filename ^BufferedImage buffered-image]
  (ImageIO/write buffered-image
                 "png"
                 ^java.io.File (io/file filename)))

(defmethod open-file :image [_ filename]
  (ImageIO/read (io/file filename)))

