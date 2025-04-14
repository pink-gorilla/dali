(ns dali.flowy
  (:require
   [missionary.core :as m]
   [reagent.core :as r]
   [flowy.reflower :refer [task flow]]
   [dali.viewer :refer [viewer2]]))

(defn dali-flow-viewer [& fun-args]
  (r/with-let [a (r/atom :waiting)
               dispose-a (atom nil)
               fun-args-a (atom nil)
               dispose! (fn []
                         (when @dispose-a
                           (println "disposing old flow.")
                           (@dispose-a)))]
    (when-not (= fun-args @fun-args-a)
      (reset! fun-args-a fun-args)
      (dispose!)
      (let [f (apply flow fun-args)
            _ (println "flow->ratom SUBSCRIBE " fun-args)
            task (m/reduce (fn [_r v]
                             ;(println "flow->ratom VALUE: " v)
                             (reset! a v)
                             v) :waiting f)
            dispose! (task
                      #(println "flow->ratom completed:  " %)
                      #(println "flow->ratom error: " %))]
        (reset! dispose-a dispose!)))
    (if (= :waiting @a)
      [:div "dali-flow-viewer waiting for data"]
      [viewer2 @a])
    (finally
      (println "Cleanup: stopping flow!")
      (dispose!))))

(defn dali-task-viewer [& fun-args]
  (r/with-let [a (r/atom :waiting)
               fun-args-a (atom nil)
               dispose-a (atom nil)
               dispose! (fn []
                          (when @dispose-a
                            (println "disposing old task.")
                            (@dispose-a)))]
    (when-not (= fun-args @fun-args-a)
      (reset! fun-args-a fun-args)
      (dispose!)
      (let [t (apply task fun-args)
            _ (println "task->ratom exec" fun-args) 
            task (m/sp
                  (let [v (m/? t)]
                    (println "task->ratom VALUE: " v)
                    (reset! a v)
                    v))
            dispose! (task
                      #(println "task->ratom completed. value: " %)
                      #(println "task->ratom crashed: " %))]
        (reset! dispose-a dispose!)))
      ; ui      
      (if (= :waiting @a)
        [:div "dali-task-viewer waiting for data"]
        [viewer2 @a])
      (finally
        (println "Cleanup: stopping dali-task-viewer!")
        (dispose!))))