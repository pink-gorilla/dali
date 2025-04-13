(ns dali.flowy
  (:require
   [missionary.core :as m]
   [reagent.core :as r]
   [flowy.reflower :refer [task flow]]
   [flowy.reagent :refer [#_flow->ratom task->ratom]]
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

(defn dali-task-viewer [fun & args]
  (r/with-let [t (apply task fun args)
               [a dispose!] (task->ratom t :waiting)]
    (if (= :waiting @a)
      [:div "dali-task-viewer waiting for data"]
      [viewer2 @a])
    (finally
      (println "Cleanup: stopping dali-task-viewer!")
      (dispose!))))