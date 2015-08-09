(ns sample.routes.profile
  (:require [compojure.core :refer :all]
            [noir.response :as resp]
            [noir.session :as session]
            [sample.models.user :as db]
            [sample.views.layout :as layout]
            [sample.views.profile :as view]))

(defn remote-user [id]
  (db/delete-user id)
  (session/clear!)
  (resp/redirect "/"))

(defn profile-page []
  (layout/common 
    (view/profile-page)))

(defn delete-profile []
  (remote-user (session/get :user-id)))

(defroutes profile-routes
  (GET "/profile" [] (profile-page))
  (POST "/profile/delete" [] (delete-profile)))
