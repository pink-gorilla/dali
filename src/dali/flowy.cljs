(ns dali.flowy
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [missionary.core :as m]
   [reagent.core :as r]
   [flowy.reflower :refer [task flow]]
   [dali.viewer :refer [viewer2]]
   [dali.viewer.exception :refer [exception-viewer]]))

(defn dali-flow-viewer [& fun-args]
  (r/with-let [a (r/atom :waiting)
               dispose-a (atom nil)
               fun-args-a (atom nil)
               dispose! (fn []
                          (when @dispose-a
                            (info "disposing old flow.")
                            (@dispose-a)))]
    (when-not (= fun-args @fun-args-a)
      (reset! fun-args-a fun-args)
      (dispose!)
      (let [f (apply flow fun-args)
            _ (info "flow->ratom SUBSCRIBE " fun-args)
            task (m/reduce (fn [_r v]
                             ;(println "flow->ratom VALUE: " v)
                             (reset! a v)
                             v) :waiting f)
            dispose! (task
                      #(info "flow->ratom completed:  " %)
                      #(info "flow->ratom error: " %))]
        (reset! dispose-a dispose!)))
    (if (= :waiting @a)
      [:div "dali-flow-viewer waiting for data"]
      [viewer2 @a])
    (finally
      (info "Cleanup: stopping flow!" fun-args)
      (dispose!))))

(defn dali-task-viewer [& fun-args]
  (r/with-let [a (r/atom :waiting)
               fun-args-a (atom nil)
               dispose-a (atom nil)
               dispose! (fn []
                          (when @dispose-a
                            (info "disposing old task.")
                            (@dispose-a)))]
    (when-not (= fun-args @fun-args-a)
      (reset! fun-args-a fun-args)
      (dispose!)
      (let [t (apply task fun-args)
            _ (info "task->ratom exec" fun-args)
            task (m/sp
                  (let [v (m/? t)]
                    (info "task->ratom VALUE: " v)
                    (reset! a {:data v})
                    v))
            dispose! (task
                      #(info "task->ratom completed. value: " %)
                      (fn [err]
                        (info "task->ratom crashed: " err)
                        (reset! a {:error err})))]
        (reset! dispose-a dispose!)))
      ; ui      
    (cond
      (= :waiting @a)
      [:div "dali-task-viewer waiting for data"]
      (:error @a)
      [exception-viewer (:error @a)]
      :else
      [viewer2 (:data @a)])
    (finally
      (info "Cleanup: stopping dali-task-viewer!")
      (dispose!))))