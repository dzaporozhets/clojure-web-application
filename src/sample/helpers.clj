(ns sample.helpers
  (:require [compojure.core :refer :all]
            [sample.models.user :as user-db]
            [hiccup.form :refer :all]
            [hiccup.element :refer :all]
            [noir.session :as session]))

(defn current-user-id []
  (Integer. (session/get :user-id)))

(defn current-user []
  (user-db/get-user-by-id (current-user-id)))

(defn input-control [type id name & [value]]
  [:div.form-group
   (list
     (label id name)
     (type {:class "form-control"} id value))])
