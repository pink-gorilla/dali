(ns demo.error)

(defn bad-component []
  ;(throw (js/Exception. "asdf"))
  (throw "I am a bad reagent component"))