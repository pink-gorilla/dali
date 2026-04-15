(ns dali.type.util
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.type.converter :refer [type->dali]]))

(defn simplevalue->dali
  [v class]
  (create-dali-spec
   {:viewer-fn 'dali.viewer.hiccup/hiccup
    :data [:span {:class class}
           (pr-str v)]}))

;; list

(defn- box [{:keys [class open close children] :as data}]
  (create-dali-spec
   {:viewer-fn 'reval.dali.viewer.list/list-view
    :data data}))

(defn list->dali
  [{:keys [class open close separator] :as opts} list]
  (box (assoc opts
              :children (map type->dali list))))

(defn map->dali [opts m]
  (box (assoc opts
              :children (interleave
                         (map type->dali (keys m))
                         (map type->dali (vals m))))))

(comment
  (list->dali
   {:class "clj-lazy-seq"
    :open "("
    :close ")"
    :separator " "}
   [1 "test" 5.3 nil :super])

  (map->dali
   {:class "clj-map"
    :open "{"
    :close "}"
    :separator " "}
   {:a 1
    :b "test"
    :c 5.3
    :d nil
    :e :super})

;  
  )
