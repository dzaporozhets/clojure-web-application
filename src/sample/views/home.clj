(ns sample.views.home
  (:require [hiccup.element :refer :all]
            [noir.session :as session]))

(defn home []
  [:div
   [:h1 "Hello " (session/get :user-name)]])
