(ns dali.plot.image
  "Render BufferedImage objects"
  (:require
   [clojure.data.codec.base64 :as b64]
   [dali.store.file.image] ; side effects 
   [dali.spec :refer [create-dali-spec]])
  (:import
   [java.awt Image]
   [java.awt.image BufferedImage]
   [java.io ByteArrayOutputStream]
   [javax.imageio ImageIO]))

(defn image-to-bytes [^Image image ^String type width height]
  (let [bi (BufferedImage. width height (if (#{"png" "gif"} type)
                                          BufferedImage/TYPE_INT_ARGB
                                          BufferedImage/TYPE_INT_RGB))
        baos (ByteArrayOutputStream.)]
    (doto (.getGraphics bi) (.drawImage image 0 0 width height nil))
    (ImageIO/write bi type baos)
    (.toByteArray baos)))

;; inline image

(defn image-inline [^BufferedImage image {:keys [alt type width height]
                                          :or {alt ""
                                               type "png"}}]
  (let [iw (.getWidth image)
        ih (.getHeight image)
        [w h] (cond
                (and width height) [(int width) (int height)]
                width [(int width) (int (* (/ width iw) ih))]
                height [(int (* (/ height ih) iw)) (int height)]
                :else [iw ih])
        b64-img (String. (b64/encode (image-to-bytes image type w h)))
        src (format "data:image/%1$s;base64,%2$s" type b64-img)]
    (create-dali-spec
     {:viewer-fn 'dali.viewer.hiccup/hiccup
      :data [:img {:src src
                   :width w
                   :height h
                   :alt alt}]})))

(defn set-url [data url]
  (update data 1 assoc :src url))

(defn image [{:keys [alt type width height]
              :or {alt ""
                   type "png"}
              :as _opts}
             ^BufferedImage image]
  (let [iw (.getWidth image)
        ih (.getHeight image)
        [w h] (cond
                (and width height) [(int width) (int height)]
                width [(int width) (int (* (/ width iw) ih))]
                height [(int (* (/ height ih) iw)) (int height)]
                :else [iw ih])]
    (create-dali-spec
     {:viewer-fn 'dali.viewer.hiccup/hiccup
      :data [:img {:width w
                   :height h
                   :alt alt}]
      :dali.store/format :image
      :dali.store/data image
      :dali.store/set-url set-url})))

(comment
  (set-url [:img {:width 100 :height 100}]
           "https://example.com/image.png")

; 
  )