(ns demo.service.saying
  (:require
   [missionary.core :as m]
   [dali.plot.hiccup :refer [hiccup]]))

(defn saying [{:keys [id] :as opts}]
  (println "serving saying id: " id " opts: " opts)
  (let [s (case id
            0 "Peace to the world"
            1 "The hottentotten are here"
            2 "The sky is always blue"
            3 "Biene Maya and Willy"
            4 "Quot erum demunstrandum"
            5 "The early bird catches the worm"
            6 "Six Six Six"
            7 "Seven Angles are here"
            8 "8 means infinity "
            9 "what is the secret of nine?"
            nil)]
    (hiccup [:div.bg-blue-300.w-100.h-100
             [:h1 "Sayings Response"]
             [:p "You entered: " id]
             [:hr]
             (if s
               [:p.bg-green-500.p-5 s]
               [:p.bg-red-500.p-5 "This saying does not exist: " id])])))


(defn forever [task]
  (m/ap (m/? (m/?> (m/seed (repeat task))))))

(defn saying-flow [saying-delay-ms]
  (let [random-saying-t (m/sp 
                         (m/? (m/sleep saying-delay-ms))
                         (saying {:id (rand-int 10)}))]
    (forever random-saying-t)))