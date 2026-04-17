(ns dali.type.error
  (:require
   [dali.spec :refer [create-dali-spec]]))

(defn unknown-type [v]
  (let [type-as-str (-> v type str)]
    (create-dali-spec
     {:viewer-fn 'dali.viewer.hiccup/hiccup
      :data  [:div {:style {:border "solid 1px red"
                            :padding "4px"}}
              [:p {:style {:color "red"}} "unknown type: " type-as-str]
              [:span (pr-str v)]]})))

(defn type-convert-err [ex-message]
  (create-dali-spec
   {:viewer-fn 'dali.viewer.hiccup/hiccup
    :data  [:div {:style {:border "solid 1px red"
                          :padding "4px"}}
            [:p {:style {:color "red"}}
             "type convert error: " ex-message]]}))