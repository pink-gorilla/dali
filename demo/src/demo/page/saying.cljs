(ns demo.page.saying
  (:require
   [reagent.core :as r]
   [dali.viewer :refer [viewer2]]
   [dali.cljviewer :refer [clj-viewer]]
   [dali.container :refer [container-dimension]]))

(defonce window-a (r/atom nil))

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
   [clj-viewer {:fun 'demo.service.saying/saying
                :args [{:id 5}]}]

   [container-dimension
    {:window-a window-a}]

   (when @window-a
     [clj-viewer {:fun 'demo.service.saying/saying
                  :args [(assoc {:id 3} :window @window-a)]}])])

