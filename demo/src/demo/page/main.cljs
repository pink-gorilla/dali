(ns demo.page.main
  (:require
   [reagent.core :as r]
   [demo.nav :refer [goto!]]
   [dali.viewer :refer [viewer2]]
   [dali.cljviewer :refer [clj-viewer]]
   [dali.container :refer [container-dimension]]
   [dali.flowy :refer [dali-flow-viewer dali-task-viewer]]))

(defonce window-a (r/atom nil))

(def saying-delay-a (r/atom 5000))

(def saying-id-a (r/atom 0))

(defn page [_]
  (let [page-style {:height "100vh"
                    :width "100vw"
                    :boxSizing "border-box"
                    :backgroundColor "#dbeafe"
                    :overflow "hidden"
                    :padding "0px"
                    :margin "0px"
                    :display "grid"
                    :left "0px"
                    :top "0px"
                    :right "0px"
                    :bottom "0px"
                    :position "absolute"
                    :gridTemplateColumns "1fr 1fr"
                    :gap "8px"}

;; avoid grid overflow; minWidth 0 lets flex/grid children shrink
        cell {:minWidth 0
              :border "1px solid #93c5fd"
              :borderRadius "4px"
              :padding "4px"}
        cell-stack (merge cell {:display "flex"
                                :flexDirection "column"
                                :gap "2px"})
        span-both (merge cell {:gridColumn "1 / -1"})
        button-style {:cursor "pointer"
                      :padding "4px 4px"
                      :border "1px solid #93c5fd"
                      :borderRadius "4px"
                      :background "#ffffff"
                      :font "inherit"
                      :color "#1e40af"}]
    [:div {:style page-style}

     [:div {:style span-both}
      [:a {:on-click #(goto! 'dali.flowy.tap/page)} " [ tap-viewer ] "]
      [:a {:on-click #(goto! 'demo.page.error/page)} " [ error ] "]]

     [:div {:style cell-stack}
      [:h1 "viewer with edn load"]
      [viewer2
       {:viewer-fn 'demo.person/person
        :transform-fn 'dali.transform.load/load-edn
        :data {:url "/r/person.edn"}}]]

     [:div {:style cell-stack}
      [:h1 "container dimension"]
      [container-dimension
       {:window-a window-a}]
      [:div "container dimension:"
       [:p (pr-str @window-a)]]]

     [:div {:style cell}
      [clj-viewer {:fun 'demo.service.saying/saying
                   :args [{:id 5}]}]]

;(when @window-a
;     [clj-viewer {:fun 'demo.service.saying/saying
                  ;:args [(assoc {:id 3} :window @window-a)]}])

     [:div {:style cell-stack}
      [:h1 "dali-task-viewer (best employee)"]
      [dali-task-viewer 'demo.service.employee/best-employee]]

     [:div {:style cell-stack}
      [:h1 "saying task result (after 10 throws exception)"]
      [:button {:type "button"
                :style button-style
                :on-click #(swap! saying-id-a inc)}
       " next saying "]
      [dali-task-viewer 'demo.service.saying/saying {:id @saying-id-a}]]

     [:div {:style cell}
      [dali-flow-viewer 'demo.service.counter/counter-fn]]

     [:div {:style cell-stack}
      [:a {:on-click #(reset! saying-delay-a 1000)} " fast sayings "]
      [dali-flow-viewer 'demo.service.saying/saying-flow @saying-delay-a]]]))

