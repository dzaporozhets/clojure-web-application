(ns sample.views.profile
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.element :refer :all]
            [hiccup.form :refer :all]
            [clj-time.coerce :as c]
            [clj-time.format :as f]
            [sample.models.avatar :as avatar-db]
            [sample.models.user :as db]
            [sample.helpers :refer :all]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

(defn profile-page [user]
  [:div
   [:h1 "Profile"]
   [:div {:class "user-info"}
    (if-let [avatar (avatar-db/get-avatar-by-user (:id user))]
      [:div
       [:img {:src (avatar-uri (:name avatar)) :width 200 :class "avatar-preview"}]])
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
   [:div {:class "btn-group"}
    [:a {:href "/profile/edit" :class "btn btn-default"} "Edit profile"]
    [:a {:href "/profile/password" :class "btn btn-default"} "Change password"]]
   [:hr]
   [:form {:action "/profile/delete" :method "POST"}
    (anti-forgery-field)
    [:button {:class "btn btn-danger" :type "submit" :onclick "return confirm(\"Are you sure?\");"} "Delete account"]]])

(defn profile-edit-page [user & [errors]]
  [:div
   [:h1 "Edit profile"]
   (form-to {:enctype "multipart/form-data"} [:post "/profile/update"]
            (anti-forgery-field)
            [:div {:class "change-user-info"}
             (input-control text-field "name" "User name" (:name user) true)]
            [:div {:class "form-group"}
               (label "file" "Avatar")
               (file-upload :file)]
            [:div
             (submit-button {:class "btn btn-primary"} "Save changes")])])

(defn password-page [user & [errors]]
  [:div
   [:h1 "Change password"]
   (form-to [:post "/profile/password/update"]
            (anti-forgery-field)
            (input-control password-field "current-password" "Current password" nil true (:current-password errors))
            (input-control password-field "new-password" "New password" nil true)
            (input-control password-field "confirm-password" "Confirm new password" nil true (:confirm-password errors))
            (submit-button {:class "btn btn-primary"} "Change password"))])
