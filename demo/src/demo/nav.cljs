(ns demo.nav
  (:require
   [reitit.frontend.easy :as rfe]))

(defn link-fn [fun text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:on-click fun} text])

(defn link-href [href text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:href href} text])

(defn link-goto
  ([fun opts text]
   (link-fn #(rfe/navigate fun opts) text))
  ([fun text]
   (link-fn #(rfe/navigate fun) text)))

(defn goto!
  ([page-fn]
   (rfe/navigate page-fn)))