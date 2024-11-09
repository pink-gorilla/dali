(ns dali.transform.load
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [promesa.core :as p]
   [ajax.core :as ajax]
   [clojure.edn :as edn]))

(defn wrap-promise
  [AJAX-TYPE url params]
  (p/create
   (fn [resolve reject]
     (AJAX-TYPE url
                (merge params
                       {:handler (fn [response]
                                   (resolve response))
                        :error-handler (fn [error]
                                         (reject error))})))))

(defn GET
  ([url] (GET url {}))
  ([url params] (wrap-promise ajax/GET url params)))

(defn parse-edn [s]
  (if (string? s)
    (edn/read-string s)
    s))

(defn load-edn [{:keys [url] :as opts}]
  (info "loading edn from url: " url " opts: " opts)
  (let [load-promise (GET url)]
    (-> load-promise
        (p/then (fn [data]
                  (info "url " url " loaded successfully. ")
                  (parse-edn data)))
        (p/catch (fn [err]
                   (error "could not load edn from url " url " err: " err))))))

(defn parse-json [s]
  (-> s js/JSON.parse js->clj))

(defn load-json [{:keys [url] :as opts}]
  (info "loading json from url: " url " opts: " opts)
  (let [load-promise (GET url)]
    (-> load-promise
        (p/then (fn [data]
                  (info "url " url " loaded successfully. ")
                  (parse-json data)))
        (p/catch (fn [err]
                   (error "could not load json from url " url " err: " err))))))


