(ns dali.store.file
  (:require
   [nano-id.core :refer [nano-id]]
   [babashka.fs :as fs]
   [taoensso.telemere :as tm]
   [dali.store :refer [dali-store]]))

(defmulti write-file
  (fn [format _filename _v]
    format))

(defmulti open-file
  (fn [format _filename]
    format))

(defrecord file-store [fpath rpath]
  dali-store
  (write [_ fmt v]
    (let [id (nano-id 5)
          filename (str id "." fmt)
          filename-absolute (str fpath "/" filename)
          url (str rpath "/" filename)]
      (tm/log! (str "writing " filename-absolute))
      (fs/create-dirs fpath)
      (write-file fmt filename-absolute v)
      (tm/log! "writing done!")
      {:id id
       :url url
       :filename filename
       :fmt fmt}))
  (open [_ {:keys [fmt filename]}]
    (let [filename-absolute (str fpath "/" filename)]
      (tm/log! (str "opening " filename-absolute))
      (let [v (open-file fmt filename-absolute)]
        (tm/log! "open done!")
        v))))

(defn create-dali-file-store [{:keys [fpath rpath]}]
  (file-store. fpath rpath))




