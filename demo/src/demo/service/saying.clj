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
    (if s
      (hiccup [:div
               [:h1 "Saying " id]
               [:blockquote
                {:style {:fontSize "1.4rem"
                         :fontFamily "cursive, Georgia, serif"
                         :margin "0.75rem 0 0 0"
                         :padding "1rem 1.25rem"
                         :borderLeft "4px solid #6b9bd1"
                         :backgroundColor "#eef6fc"
                         :borderRadius "0 0.375rem 0.375rem 0"
                         :lineHeight 1.5}}
                s]])
      (throw (ex-info "saying does not exist" {:id id})))))

(defn forever [task]
  (m/ap (m/? (m/?> (m/seed (repeat task))))))

(defn saying-flow [saying-delay-ms]
  (let [random-saying-t (m/sp
                         (m/? (m/sleep saying-delay-ms))
                         (saying {:id (rand-int 10)}))]
    (forever random-saying-t)))