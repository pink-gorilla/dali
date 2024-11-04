(ns dali.spec)

(defn dali-spec? [{:keys [viewer-fn] :as dali-spec}]
  (and (map? dali-spec)
       viewer-fn
       (symbol? viewer-fn)))

(defn create-dali-spec [{:keys [viewer-fn transform-fn data] :as dali-spec}]
  (assert viewer-fn "viewer-fn is a required parameter")
  (assert (symbol? viewer-fn) "viewer-fn needs to be a symbol")
  (when transform-fn
    (assert (symbol? transform-fn) "transform-fn (if specified) needs to be a symbol"))
  (with-meta
    dali-spec
    {:dali true}))