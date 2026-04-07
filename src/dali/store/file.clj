(ns dali.store.file
  (:require
   [nano-id.core :refer [nano-id]]
   [babashka.fs :as fs]
   [dali.store :refer [dali-store]]))

(defmulti write-file
  (fn [format _filename _v]
    format))

(defmulti open-file
  (fn [format _filename]
    format))

(defrecord file-store [fpath rpath sub-path-a]
  dali-store
  (write [_ fmt v]
    (let [id (nano-id 5)
          extension (case fmt :image "png" :transit-json ".transit.json" :else fmt)
          filename (str id "." extension)
          path-full (str fpath @sub-path-a)
          filename-absolute (str path-full filename)
          url (str rpath @sub-path-a filename)]
      (fs/create-dirs path-full)
      (write-file fmt filename-absolute v)
      {:id id
       :url url
       :filename filename
       :fmt fmt}))
  (open [_ {:keys [fmt filename]}]
    (let [path-full (str fpath @sub-path-a)
          filename-absolute (str path-full filename)
          v (open-file fmt filename-absolute)]

      v)))

(defn create-dali-file-store [{:keys [fpath rpath]}]
  (file-store. fpath rpath (atom "/")))

(defn set-sub-path [s sub-path]
  (reset! (:sub-path-a s) sub-path))



