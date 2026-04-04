(ns dali.viewer.hiccup)

(defn hiccup
  ([h]
   h)
  ([h children]
   (into h children)))
