(ns dali.container
  (:require
   [reagent.core :as r]
   [reagent.dom]))

; https://www.geeksforgeeks.org/how-to-get-the-elements-actual-width-and-height-in-javascript/
; https://react.dev/reference/react-dom/findDOMNode#alternatives

(defn container-dimension
  [{:keys [window-a]}]
  (let [my-ref (atom nil)]
  (r/create-class
   {:display-name "container-dimension"
    :reagent-render (fn [] ;; remember to repeat parameters
                      [:div.container-dimension
                       {:style {:display "none"}
                        :ref (fn [el] (reset! my-ref el))}])
    :component-did-mount (fn [this] ; oldprops oldstate snapshot
                           (let [node @my-ref
                                 parent (.-parentElement node)
                                 width (.-offsetWidth parent)
                                 height (.-offsetHeight parent)
                                 window {:width width :height height}]
                             (when-not (= window @window-a)
                               (println "window: " window)
                               (reset! window-a window))))})))

