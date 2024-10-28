(ns dali.viewer.exception
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
  [{:keys [class style text]
    :or {style {:overflow-y "scroll"}
         class "w-full h-full"}}]
  (let [lines (str/split text #"\n")]
    (into [:div {:class class
                 :style style}]
          (map line-with-br lines))))

(defn exception [data]
  [text {:class "bg-red-200 w-full h-full"
         :text data}])
