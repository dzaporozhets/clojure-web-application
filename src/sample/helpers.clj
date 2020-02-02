(ns sample.helpers
  (:require [compojure.core :refer :all]
            [sample.models.user :as user-db]
            [hiccup.form :refer :all]
            [hiccup.element :refer :all]
            [noir.validation :as vali]
            [noir.session :as session]))

(defn current-user-id []
  (Integer. (session/get :user-id)))

(defn current-user []
  (user-db/get-user-by-id (current-user-id)))

(defn error-item [[error]]
  [:div.text-danger error])

(defn input-control [type id name & [value required]]
  [:div.form-group
   (list
     (label id name)
     (vali/on-error (keyword id) error-item)
     (type {:class "form-control" :required required} id value))])
