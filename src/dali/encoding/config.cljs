(ns dali.encoding.config
  (:require
   [ednx.tick.edn :refer [add-tick-edn-handlers!]]
   [dali.spec :refer [add-spec-edn-handlers! add-spec-transit-handlers!]]))

(defn start-encoding [_config]
  (println "adding dali encoding..")
  (add-tick-edn-handlers!)
  (add-spec-edn-handlers!)
  (add-spec-transit-handlers!)
  nil ; we dont wait on this.
  )