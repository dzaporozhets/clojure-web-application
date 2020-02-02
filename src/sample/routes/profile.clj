(ns sample.routes.profile
  (:require [compojure.core :refer :all]
            [noir.response :as resp]
            [noir.session :as session]
            [noir.util.crypt :as crypt]
            [sample.helpers :refer :all]
            [sample.models.user :as db]
            [sample.views.layout :as layout]
            [sample.views.profile :as view]))

(defn remove-user [id]
  (db/delete-user id)
  (session/clear!)
  (resp/redirect "/"))

(defn profile-page []
  (layout/common
    (view/profile-page)))

(defn password-page []
  (layout/common
    (view/password-page)))

(defn delete-profile []
  (remove-user (session/get :user-id)))

(defn update-password [current-password new-password confirm-password]
  (let [user (current-user)]
    (if (crypt/compare current-password (:encrypted_password user))
      (if (= new-password confirm-password)
        (do
          (db/update-user (:id user) {:encrypted_password (crypt/encrypt new-password)})
          (session/clear!)
          (resp/redirect "/login"))
        (str "Confirmation password does not match"))
      (str "Incorrect current password"))))

(defroutes profile-routes
  (GET "/profile" [] (profile-page))
  (GET "/profile/password" [] (password-page))
  (POST "/profile/password/update" [current-password new-password confirm-password]
        (update-password current-password new-password confirm-password))
  (POST "/profile/delete" [] (delete-profile)))
