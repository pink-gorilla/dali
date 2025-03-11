(ns dali.store.cache
  (:require
   [nano-id.core :refer [nano-id]]
   [ring.util.response :as response]
   [dali.store :refer [dali-store]]))

(defonce cache (atom {}))

(defn store-once
  ([data id]
   (swap! cache assoc id data)))

(defn load-once [id]
  (let [data (get @cache id)]
    (println "dali load-once data: " data)
    (swap! cache dissoc id)
    data))

(defn data-handler [{:keys [#_route-params path-params] :as req}]
  (let [;id (:id route-params) ; bidi
        id (:id path-params) ; reitit
        ;_ (println "DS HANDLER ID: " id)
        ;_ (println "DS HANDLER path-params:" path-params)
        data (when id (load-once id))]
    (if data
      (response/response data)
      (response/not-found nil))))

(defrecord cache-store []
  dali-store
  (write [_ fmt v]
    (let [id (nano-id 5)
          filename (str id ".json")
          url (str "/dali/" filename)]
      (println (str "dali cache storing " id))
      (store-once v id)
      {:id id
       :url url
       :filename filename
       :fmt fmt}))
  (open [_ {:keys [fmt id filename]}]
    (load-once id)))

(def dali-cache-store
  (cache-store.))