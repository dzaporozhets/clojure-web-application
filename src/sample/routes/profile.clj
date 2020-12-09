(ns sample.routes.profile
  (:require [compojure.core :refer :all]
            [sample.crypt :as crypt]
            [ring.util.response :as response]
            [sample.helpers :refer :all]
            [sample.models.user :as db]
            [sample.views.layout :as layout]
            [sample.views.profile :as view]))

(defn wrap-current-user-id [handler]
  (fn [request]
    (let [user-id (:user-id (:session request))]
      (handler (assoc request :user-id user-id)))))

(defn remove-user [id]
  (if (db/delete-user id)
    (assoc (response/redirect "/") :session nil)))

(defn profile-page [user]
  (layout/common (view/profile-page user) user))

(defn password-page [user]
  (layout/common (view/password-page user) user))

(defn update-password [current-password new-password confirm-password user]
  (if (crypt/verify current-password (:encrypted_password user))
    (if (= new-password confirm-password)
      (do
        (db/update-user (:id user) {:encrypted_password (crypt/encrypt new-password)})
        (assoc (response/redirect "/login") :session nil))
      (layout/common
        (view/password-page user {:confirm-password "Confirmation password does not match"}) user))
    (layout/common
      (view/password-page user {:current-password "Incorrect current password"}) user)))

(defroutes profile-routes
  (wrap-current-user-id
    (context "/profile" {:keys [user-id]}
             (GET "/" []
                  (if user-id
                    (profile-page (get-user user-id))
                    (response/redirect "/login")))
             (GET "/password" []
                  (if user-id
                    (password-page (get-user user-id))
                    (response/redirect "/login")))
             (POST "/password/update" [current-password new-password confirm-password]
                   (if user-id
                     (update-password current-password new-password confirm-password (get-user user-id))
                     (response/redirect "/login")))
             (POST "/delete" []
                   (if user-id
                     (remove-user user-id)
                     (response/redirect "/login"))))))
