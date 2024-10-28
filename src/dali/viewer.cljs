(ns dali.viewer
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [promesa.core :as p]
   [reagent.core :as r]
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


(defn viewer2
  [{:keys [viewer-fn transform-fn data] :as dali-spec}]
  (let [a (r/atom nil)
        load-spec (fn [dali-spec]
                    (-> (process dali-spec)
                        (p/then (fn [r]
                                  (info "load and transform complete!")
                                  (reset! a r)))
                        (p/catch (fn [err]
                                   (error "load and transform error: " err)
                                   (reset! a {:error err})))))]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
    (r/create-class
     {:display-name "dali-viewer"
      :reagent-render  (fn [{:keys [viewer-fn transform-fn data]}]
                         (let [{:keys [viewer data error] :as aval} @a]
                           (cond
                             (nil? aval)
                             [:p "loading.."]
                             error
                             [:p "error!"]
                             :else
                             [viewer data])))
      :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             (let [new-argv (rest (r/argv this))
                                   [dali-spec] new-argv]
                               (load-spec dali-spec)))
      :component-did-update (fn [this old-argv]
                              (let [new-argv (rest (r/argv this))
                                    [dali-spec] new-argv]
                                ;(println "component did update: " this "argv: " new-argv)
                                (load-spec dali-spec)))})))