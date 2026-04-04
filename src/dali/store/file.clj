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

(defrecord file-store [fpath rpath]
  dali-store
  (write [_ {:keys [fmt path]
             :or {path "/"}} v]
    (let [id (nano-id 5)
          extension (case fmt :image "png" :transit-json ".transit.json" :else fmt)
          filename (str id "." extension)
          filename-absolute (str fpath path filename)
          url (str rpath path filename)]
      (fs/create-dirs fpath)
      (write-file fmt filename-absolute v)
      {:id id
       :url url
       :filename filename
       :fmt fmt}))
  (open [_ {:keys [fmt filename]}]
    (let [filename-absolute (str fpath "/" filename)
          v (open-file fmt filename-absolute)]

      v)))

(defn create-dali-file-store [{:keys [fpath rpath]}]
  (file-store. fpath rpath))





