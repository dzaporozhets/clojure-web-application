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
      [:p
       [:a {:href "/profile/password"} "Change password"]]
     [:hr]
     [:form {:action "/profile/delete" :method "POST"}
      (anti-forgery-field)
      [:button {:class "btn btn-danger btn-sm"} "Delete account"]]]))

(defn password-page []
  [:div
   [:h1 "Change password"]
   (form-to [:post "/profile/password/update"]
            (input-control password-field "current-password" "Current password")
            (input-control password-field "new-password" "New password")
            (input-control password-field "confirm-password" "Confirm new password")
            (submit-button {:class "btn btn-primary"} "Change password"))])
