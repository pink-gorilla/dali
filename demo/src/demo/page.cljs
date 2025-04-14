(ns demo.page
  (:require
   [reagent.core :as r]
   [frontend.routes :refer [goto!]]
   [dali.viewer :refer [viewer2]]
   ;[dali.cljviewer :refer [clj-viewer]]
   [dali.container :refer [container-dimension]]
   ;[dali.error-boundary :refer [error-boundary]]
   [dali.flowy :refer [dali-flow-viewer dali-task-viewer]]))

(defonce window-a (r/atom nil))

(defn bad-component []
  (throw (js/Exception. "asdf")))

(def saying-delay-a (r/atom 5000))

(def saying-id-a (r/atom 0))

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
   [:a {:on-click #(goto! 'dali.flowy.tap/page)} " [ tap-viewer ] "]

   [:h1 "viewer with edn load"]
   [viewer2
    {:viewer-fn 'demo.person/person
     :transform-fn 'dali.transform.load/load-edn
     :data {:url "/r/person.edn"}}]

   [:h1 "container dimension"]
   [container-dimension
    {:window-a window-a}]

   [:div "container dimension:"
    [:p (pr-str @window-a)]]

   ;[clj-viewer {:fun 'demo.service.saying/saying
;                :args [{:id 5}]}]
   
;(when @window-a
;     [clj-viewer {:fun 'demo.service.saying/saying
                  ;:args [(assoc {:id 3} :window @window-a)]}])
   
   ;[:h1 "the sun:"]
   ;[clj-viewer {:fun 'demo.service.image/sun
                ;:args []}]
   
   [:h1 "dali-task-viewer"]
   [dali-task-viewer 'demo.service.employee/best-employee]

   [:a {:on-click #(swap! saying-id-a inc)} " next saying "]
   [dali-task-viewer 'demo.service.saying/saying {:id @saying-id-a}]
   
   [dali-flow-viewer 'demo.service.counter/counter-fn]
   
   [:a {:on-click #(reset! saying-delay-a 1000)} " fast sayings "]
   [dali-flow-viewer 'demo.service.saying/saying-flow @saying-delay-a]

; error boundary is not yet working
   ;[error-boundary  [bad-component] ]
   ])

