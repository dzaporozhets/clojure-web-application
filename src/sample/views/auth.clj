(ns sample.views.auth
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.element :refer :all]
            [hiccup.form :refer :all]
            [sample.models.user :as db]
            [sample.helpers :refer :all]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [noir.session :as session]))

(defn login-page [& [email]]
  [:div.login-form
   [:h1 "Login with existing account"]
   (form-to [:post "/login"]
            (anti-forgery-field)
            (input-control text-field "email" "Email" email)
            (input-control password-field "password" "Password")
            (submit-button {:class "btn btn-success"} "Login"))])

(defn registration-page [& [name email]]
  [:div.registration-form
   [:h1 "Let's create an account"]
   (form-to [:post "/register"]
            (anti-forgery-field)
            (input-control text-field "name" "Name" name)
            (input-control text-field "email" "Email" email)
            (input-control password-field "password" "Password")
            (input-control password-field "password_confirmation" "Repeat password")
            (submit-button {:class "btn btn-success"} "Create account"))])
