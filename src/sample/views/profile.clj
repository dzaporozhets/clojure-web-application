(ns sample.views.profile
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.element :refer :all]
            [clj-time.coerce :as c]
            [clj-time.format :as f]
            [sample.models.user :as db]
            [noir.session :as session]))

(defn profile-page []
  (let [user (db/get-user-by-id (session/get :user-id))]
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
     [:form {:action "/profile/delete" :method "POST"} 
      [:button {:class "btn btn-danger btn-sm"} "Delete account"]]]))
