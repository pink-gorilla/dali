(ns dali.viewer
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [promesa.core :as p]
   [reagent.core :as r]
   ["react" :as react]
   [dali.util.resolve :refer [resolve-symbol]]))

(defn transform-data [transform-fn data]
  (info "transforming: " transform-fn " data: " data)
  (-> (resolve-symbol transform-fn)
      (p/then (fn [transform]
                (info "transform-fn resolve success. Now transforming.")
                (info "transform: " transform)
                (let [r (transform data)]
                  (info "data successfully transformed to:  " r)
                  r)))))


(defn process [{:keys [viewer-fn transform-fn data] :as _dali-spec}]
  (let [viewer-p (resolve-symbol viewer-fn)]
    (if transform-fn
      (-> (p/all [viewer-p
                  (transform-data transform-fn data)])
          (p/then (fn [[viewer data]]
                    {:viewer viewer
                     :data data})))
      (-> viewer-p
          (p/then (fn [viewer]
                    {:viewer viewer
                     :data data}))))))


(defn viewer [{:keys [viewer-fn transform-fn data] :as dali-spec}]
  ; this viewer has the problem that it does not work when the dali-spec changes
  (let [a (r/atom nil)]
    (info "viewer viewer-fn: " viewer-fn " transform-fn: " transform-fn)
    (-> (process dali-spec)
        (p/then (fn [r]
                  (info "load and transform complete!")
                  (reset! a r)))
        (p/catch (fn [err]
                   (error "load and transform error: " err)
                   (reset! a {:error err}))))
    (fn [{:keys [viewer-fn transform-fn data]}]
      (let [{:keys [viewer data error] :as aval} @a]
        (cond
          (nil? aval)
          [:p "loading.."]
          error
          [:p "error!"]
          :else
          [viewer data])))))

(defn viewer-impl [dali-spec]
  (let [[data set-data] (react/useState nil)
        [error set-error] (react/useState false)]
    (react/useEffect
     (fn []
       (let [ignore (atom false)]
         (set-data nil)
         (println "processing dali-spec..")
         (->  (process dali-spec)
              (p/then (fn [result]
                        (println "processing dali-spec success!")
                        (when-not @ignore
                          (set-data result))))
              (p/catch (fn [err]
                         (println "processing dali-spec error!")
                         (set-error err))))
         (fn []
           (reset! ignore true))))
     (clj->js [dali-spec]))
    (cond
      data
      [viewer data]
      error
      [:p "error!"]
      :else
      [:p "loading.."])))


(defn viewer2 [dali-spec]
  [:f> viewer-impl dali-spec])

