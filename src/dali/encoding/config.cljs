(ns dali.encoding.config
  (:require
   [taoensso.timbre :refer-macros [debug info warn error]]
   [ednx.tick.edn :refer [add-tick-edn-handlers!]]
   [dali.spec :refer [add-spec-edn-handlers! add-spec-transit-handlers!]]))

(defn start-encoding [_config]
  (info "adding dali-spec edn/transit encoding..")
  (add-tick-edn-handlers!)
  (add-spec-edn-handlers!)
  (add-spec-transit-handlers!)
  nil ; we dont wait on this.
  )