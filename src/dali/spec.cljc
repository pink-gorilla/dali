(ns dali.spec
  (:require
   [ednx.handler :refer [add-edn-handlers!]]
   [transit.handler :refer [add-transit-io-handlers!]]
   [cognitect.transit :as transit]))

(defrecord DaliSpec [viewer-fn transform-fn data
                     children
                     store-format
                     store-data
                     store-set-url])

(defn dali-spec? [dali-spec]
  (instance? DaliSpec dali-spec))

(defn create-dali-spec
  [{:keys [viewer-fn transform-fn _data
           children
           store-format _store-data store-set-url] :as m}]
  ; viewer-fn
  (assert viewer-fn ":viewer-fn is required")
  (assert (symbol? viewer-fn) ":viewer-fn must be a symbol")
  ; transform-fn
  (assert (or (not transform-fn)
              (symbol? transform-fn)) ":transform-fn must be a symbol")
  ; children
  (assert (or (not children)
              (seq? children)
              (vector? children)) ":children must be a seq/vector")
  ; store-format
  (assert (or (not store-format)
              (symbol? store-format)) ":store-format must be a symbol")
  ; store-set-url
  (assert (or (nil? store-set-url)
              (fn? store-set-url)) ":store-set-url must be a function")
  (map->DaliSpec m))

;; EDN

(defn read-dali-spec
  "EDN tagged-literal reader for dali.spec.DaliSpec.
   Expects a map and returns a validated DaliSpec record."
  [m]
  (assert (map? m) "DaliSpec EDN reader expects a map")
  (create-dali-spec m))

(defn add-spec-edn-handlers! []
  (add-edn-handlers! {'dali.spec.DaliSpec dali.spec/read-dali-spec}))

;; TRANSIT

(def dali-spec-tag "dali.spec/DaliSpec")

(defn prepare [x]
  ; store is excluded. 
  (select-keys x [:viewer-fn :transform-fn :data :children]))

(def spec-serialization-handlers
  {dali.spec.DaliSpec
   (transit/write-handler
    (constantly dali-spec-tag)
    prepare)})

(def spec-deserialization-handlers
  {dali-spec-tag (transit/read-handler create-dali-spec)})

(defn add-spec-transit-handlers! []
  (add-transit-io-handlers!
   spec-deserialization-handlers
   spec-serialization-handlers))

; this is the side effect that we want to happen.
(add-spec-transit-handlers!)

(comment
  (def s (create-dali-spec {:viewer-fn 'dali.viewer.hiccup/hiccup
                            :data [:p "123"]}))
  s

  ; test edn encoding
  (require '[ednx.edn :refer [read-edn]])
  (-> s pr-str read-edn)

;; test transit encoding
  (require '[transit.io :refer [encode decode]])
  (-> s encode decode)

; 
  )


