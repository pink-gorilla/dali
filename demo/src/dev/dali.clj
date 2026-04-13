(ns dev.dali
  (:require
   [dali.spec :refer [dali-spec?]]
   [dali.type.converter :refer [type->dali]]
   [dali.type.util :refer [list->dali]]))

(type->dali 1)
(type->dali nil)

(type->dali "asdf")

(type->dali [3 4])
(type->dali '(3 4))

(type->dali 'notebook.study.movies/more-movies)

(list->dali  {:separator ""} [1 :yes "a"])
(list->dali  {} {:a 1 :b "BB"})

(dali-spec? {:a 1 :b "BB"})



