(ns dali.error-boundary
  (:require
   ["react" :as react]
   [applied-science.js-interop :as j]
   [clojure.set :as set]
   [clojure.string :as str]
   [goog.object]
   [goog.string :as gstring]
   [reagent.core :as r]
   [shadow.cljs.modern :refer [defclass]]
   [dali.hooks :as hooks]))

(defn error-badge [& content]
  [:div.bg-red-50.rounded-sm.text-xs.text-red-400.px-2.py-1.items-center.sans-serif.inline-flex
   [:svg.h-4.w-4.text-red-400 {:xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 20 20" :fill "currentColor" :aria-hidden "true"}
    [:path {:fill-rule "evenodd" :d "M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" :clip-rule "evenodd"}]]
   (into [:div.ml-2.font-bold] content)])

(defn error-view [error]
  (let [!stack-expanded (hooks/use-state false)]
    [:div.bg-red-100.dark:bg-gray-800.px-6.py-4.rounded-md.text-xs.dark:border-2.dark:border-red-300.not-prose
     [:p.font-mono.text-red-600.dark:text-red-300.font-bold (or (:message error) (.-message error))]
     #_(when-let [data (or (:data error) (.-data error))]
         [:<>
          (when-let [extra-view (::extra-view data)]
            [:div.mt-2.overflow-auto [inspect extra-view]])
          [:div.mt-2.overflow-auto [inspect (dissoc data ::extra-view)]]])
     (when-let [stack (try
                        (->> (or (:stack error) (.-stack error))
                             str/split-lines
                             (drop 1)
                             (mapv str/trim))
                        (catch js/Error _ nil))]
       [:pre.text-red-600.dark:text-red-300.w-full.overflow-auto.mt-2 {:class "text-[11px] max-h-[155px]"}
        [:span.underline.cursor-pointer {:on-click #(swap! !stack-expanded not)}
         (if @!stack-expanded "Hide" "Show")
         " Stacktrace (" (count stack) " lines)\n"]
        (when @!stack-expanded
          (str/join "\n" stack))])]))

(defclass ErrorBoundary
  (extends react/Component)
  (field handle-error)
  (field hash)
  (constructor [this ^js props]
               (super props)
               (set! (.-state this) #js {:error nil :hash (j/get props :hash)})
               (set! hash (j/get props :hash))
               (set! handle-error (fn [error]
                                    (set! (.-state this) #js {:error error}))))

  Object
  (render [this ^js props]
          (j/let [^js {{:keys [error]} :state
                       {:keys [children]} :props} this]
            (if error
              (r/as-element [error-view error])
              children))))

(j/!set ErrorBoundary
        :getDerivedStateFromError (fn [error] #js {:error error})
        :getDerivedStateFromProps (fn [props state]
                                    (when (not= (j/get props :hash)
                                                (j/get state :hash))
                                      #js {:hash (j/get props :hash) :error nil})))

(defn error-boundary [child]
  [:f> ErrorBoundary {:hash 15}
   [:f> child]])


