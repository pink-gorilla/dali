(ns dali.store)

(defprotocol dali-store
  (write [this fmt v])
  (open [this opts]))

