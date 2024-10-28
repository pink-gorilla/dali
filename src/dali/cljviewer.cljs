(ns dali.cljviewer
  (:require
   [reagent.core :as r]
   [promesa.core :as p]
   [taoensso.timbre :refer-macros [info warn error]]
   [goldly.service.core :refer [clj]]
   [dali.viewer :refer [viewer]]))

(defn load-to-atom-once [a fun args]
  (info "loading clj fun: " fun " args: " args)
  (swap! a assoc :current [fun args] :status :loading)
  (let [rp (apply clj fun args)]
    (p/then rp (fn [r] (swap! a assoc :status :data :data r)))
    (p/catch rp (fn [r] (swap! a assoc :status :error :error r)))
    nil))

(defn clj-viewer
  "clj-viewer executes clj fn fun with args args.
   result is expected to be a dali-spec which is passed
   to dali/viewer when the result is received."
  [{:keys [fun args]
    :or {args []}
    :as opts
    }]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
  (let [a (r/atom {:data nil :error nil})]
    (r/create-class
     {:display-name "clj-viewer"
      :reagent-render (fn [{:keys [fun args]
                            :or {args []}}] ;; remember to repeat parameters
                        (let [{:keys [data error]} @a
                              ]
                          (cond 
                            data [viewer data]
                            error [:div "clj-exec error"]
                            :else [:div "executing clj"])))
      :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             (let [argv (rest (r/argv this))
                                   [arg1] argv
                                   ]
                             ;(println "c-d-m: " this)
                             (println "mount: arg:" arg1 " opts " opts)
                             (load-to-atom-once a fun args)
                             ))
      :component-did-update (fn [this old-argv]
                              (let [new-argv (rest (r/argv this))
                                    [arg1] new-argv
                                    {:keys [fun args]} arg1]
                                (println "update: new-arg:"  arg1)
                                ;(load-to-atom-once a fun args)
                                ))})))



