(ns dali.viewer.text
  (:require
   [clojure.string :as str]))

(defn line-with-br [t]
  [:div
   [:span.font-mono.text-lg.whitespace-pre t]
   [:br]])

(defn text
  "Render text (as string) to html
   works with \\n (newlines)
   Needed because \\n is meaningless in html"
  [{:keys [class style text]}]
  (let [lines (str/split text #"\n")]
    (into [:div {:class class
                 :style style}]
          (map line-with-br lines))))

(defn text-exception [data]
  [text {:style {:backgroundColor "red"
                 :width "100%"}
         :text data}])
