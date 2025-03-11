(ns demo.page
  (:require
   [reagent.core :as r]
   [dali.viewer :refer [viewer2]]
   ;[dali.cljviewer :refer [clj-viewer]]
   [dali.container :refer [container-dimension]]
   ;[dali.error-boundary :refer [error-boundary]]
   [dali.flowy :refer [dali-flow-viewer dali-task-viewer]]))

(defonce window-a (r/atom nil))

(defn bad-component []
  (throw (js/Exception. "asdf")))

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
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
   [dali-task-viewer 'demo.service.saying/saying {:id 5}]

   [:h1 "dali-flow-viewer"]
   [dali-flow-viewer 'demo.service.counter/counter-fn]

; error boundary is not yet working
   ;[error-boundary  [bad-component] ]
   ])

