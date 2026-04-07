(ns dali.plot.exception
  (:require
   [dali.spec :refer [create-dali-spec]]
   [clj-service.response :refer [convert-ex]]))

(defn exception
  "returns a plot specification {:render-fn :spec :data}. 
   The ui shows the exception."
  [ex]
  (create-dali-spec
   {:viewer-fn 'dali.viewer.exception/exception-viewer
    :data (convert-ex ex)}))

(comment
  (def ex (ex-info "asdf" {:y 3}))

  (exception ex)
  (require '[clojure.stacktrace :as stack])
  (stack/print-stack-trace ex)
  (stack/print-cause-trace ex)
  (stack/root-cause ex)

;
  )