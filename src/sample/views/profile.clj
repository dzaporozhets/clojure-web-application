(ns sample.views.profile
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.element :refer :all]
            [hiccup.form :refer :all]
            [clj-time.coerce :as c]
            [clj-time.format :as f]
            [sample.models.user :as db]
            [sample.helpers :refer :all]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [noir.session :as session]))

(defn profile-page []
  (let [user (current-user)]
    [:div
     [:h1 "Profile"]
     [:div {:class "user-info"}
      [:p
       [:span "Name: "]
       [:strong (:name user)]]
      [:p
       [:span "Email: "]
       [:strong (:email user)]]
      [:p
       [:span "Member since: "]
       [:strong (f/unparse (f/formatters :date) (c/from-date (:timestamp user)))]]]
     [:hr]
     [:div
      [:a {:href "/profile/password" :class "btn btn-default"} "Change password"]]
     [:br]
     [:form {:action "/profile/delete" :method "POST"}
      (anti-forgery-field)
      [:button {:class "btn btn-danger" :onclick "return confirm(\"Are you sure?\");"} "Delete account"]]]))

(defn password-page []
  [:div
   [:h1 "Change password"]
   (form-to [:post "/profile/password/update"]
            (anti-forgery-field)
            (input-control password-field "current-password" "Current password" nil true)
            (input-control password-field "new-password" "New password" nil true)
            (input-control password-field "confirm-password" "Confirm new password" nil true)
            (submit-button {:class "btn btn-primary"} "Change password"))])
