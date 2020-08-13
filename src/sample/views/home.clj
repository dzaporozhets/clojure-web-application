(ns sample.views.home
  (:require [hiccup.element :refer :all]))

(defn home [user]
  [:div
   [:h1 "Hello " (:name user)]])
