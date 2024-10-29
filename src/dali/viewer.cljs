(ns dali.viewer
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [promesa.core :as p]
   [reagent.core :as r]
   ["react" :as react]
   [dali.util.resolve :refer [resolve-symbol]]))

(defn transform-data [transform-fn data]
  (info "transforming: " transform-fn " data: " data)
  (-> (resolve-symbol transform-fn)
      (p/then (fn [transform]
                (info "transform-fn resolve success. Now transforming.")
                (transform data)))))

(defn process [{:keys [viewer-fn transform-fn data] :as _dali-spec}]
  (let [viewer-p (resolve-symbol viewer-fn)]
    (if transform-fn
      (-> (p/all [viewer-p
                  (transform-data transform-fn data)])
          (p/then (fn [[viewer data]]
                    (info "data successfully transformed.")
                    ;(info "data successfully transformed to:  " data)
                    {:viewer viewer
                     :data data})))
      (-> viewer-p
          (p/then (fn [viewer]
                    {:viewer viewer
                     :data data}))))))


(defn viewer [{:keys [viewer-fn transform-fn data] :as dali-spec}]
  ; this viewer has the problem that it does not work when the dali-spec changes
  (let [a (r/atom nil)]
    (info "viewer viewer-fn: " viewer-fn " transform-fn: " transform-fn)
    (-> (process dali-spec)
        (p/then (fn [r]
                  (info "load and transform complete!")
                  (reset! a r)))
        (p/catch (fn [err]
                   (error "load and transform error: " err)
                   (reset! a {:error err}))))
    (fn [{:keys [viewer-fn transform-fn data]}]
      (let [{:keys [viewer data error] :as aval} @a]
        (cond
          (nil? aval)
          [:p "loading.."]
          error
          [:p "error!"]
          :else
          [viewer data])))))

(defn viewer-impl [dali-spec dali-spec-js]
  (let [[result set-result] (react/useState nil)
        [error set-error] (react/useState false)]
    (react/useEffect
     (fn []
       (set-result nil)
       (info "processing dali-spec: " dali-spec)
       (->  (process dali-spec)
            (p/then (fn [result]
                      (info "processing dali-spec success!")
                      (set-result result)))
            (p/catch (fn [err]
                       (info "processing dali-spec error!")
                       (set-error err))))
       (fn []
         (info "processing cleanup ..")))
      dali-spec-js) ; this fails with object.is identity check
    
    (react/useMemo 
     (fn []
      (r/as-element
       (cond
         result
         (let [{:keys [viewer data error]} result]
           (if data
             [viewer data]
             [:p "error!"]))
         error
         [:p "error!"]
         :else
         [:p "loading.."]))
       )
     #js [result])))

(defn viewer2 [dali-spec]
  (let [dali-spec-js #js [dali-spec]]
  [:f> viewer-impl dali-spec dali-spec-js]))

#_(defn viewer2 [dali-spec]
   [:> viewer-impl dali-spec])


; reagent.core/as-element function creates a React element from a Hiccup form.
;  some React features, like Hooks, only work with Functional components. There are several ways to use functions as components with Reagent:

; 1. Calling r/create-element directly with a ClojureScript function doesn't wrap the component in any Reagent wrappers, and will create functional components. 
; In this case you need to use r/as-element inside the function to convert Hiccup-style markup to elements, 
; or just return React Elements yourself. You also can't use Ratoms here, as Ratom implementation requires that the component is wrapped by Reagent.

; 2. :r> shortcut can be used to create components similar to r/create-element, and the children Hiccup forms are converted to React
;  element automatically.

; Using adapt-react-class or :> is also calls create-element, but that also does automatic conversion of ClojureScript parameters 
; to JS objects, which isn't usually desired if the component is ClojureScript function.


; :f> shortcut can be used to create function components from Reagent components (functions), where both RAtoms and Hooks work.