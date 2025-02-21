(ns demo.page
  (:require
   [reagent.core :as r]
   [dali.viewer :refer [viewer2]]
   [dali.cljviewer :refer [clj-viewer]]
   [dali.container :refer [container-dimension]]
   ;[dali.error-boundary :refer [error-boundary]]
   ))

(defonce window-a (r/atom nil))

(defn bad-component []
  (throw (js/Exception. "asdf")))

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
   [clj-viewer {:fun 'demo.service.saying/saying
                :args [{:id 5}]}]

   [container-dimension
    {:window-a window-a}]

   [:div "container dimension:"
    [:p (pr-str @window-a)]]
   
   (when @window-a
     [clj-viewer {:fun 'demo.service.saying/saying
                  :args [(assoc {:id 3} :window @window-a)]}])

   [:h1 "the sun:"]
   [clj-viewer {:fun 'demo.service.image/sun
                :args []}]

   [:h1 "viewer with edn load"]
   [viewer2
    {:viewer-fn 'demo.person/person
     :transform-fn 'dali.transform.load/load-edn
     :data {:url "/r/person.edn"}}]

; error boundary is not yet working
   ;[error-boundary  [bad-component] ]
   ])

