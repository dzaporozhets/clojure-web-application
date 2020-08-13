(ns sample.views.auth
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.element :refer :all]
            [hiccup.form :refer :all]
            [sample.models.user :as db]
            [sample.helpers :refer :all]
            [struct.core :as st]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

(defn login-page [& [email errors]]
  [:div.login-form
   [:h1 "Login with existing account"]
   (form-to [:post "/login"]
            (anti-forgery-field)
            (input-control text-field "email" "Email" email true (:email errors))
            (input-control password-field "password" "Password" nil true)
            (submit-button {:class "btn btn-success"} "Login"))])

(defn registration-page [& [name email errors]]
  [:div.registration-form
   [:h1 "Let's create an account"]
   (form-to [:post "/register"]
            (anti-forgery-field)
            (input-control text-field "name" "Name" name true (:name errors))
            (input-control text-field "email" "Email" email true (:email errors))
            (input-control password-field "password" "Password" nil true (:password errors))
            (input-control password-field "password-confirmation" "Repeat password" nil true (:password-confirmation errors))
            (submit-button {:class "btn btn-success"} "Create account"))])
