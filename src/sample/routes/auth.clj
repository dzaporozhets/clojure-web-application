(ns sample.routes.auth
  (:require [hiccup.form :refer :all]
            [compojure.core :refer :all]
            [sample.models.user :as db]
            [sample.views.layout :as layout]
            [sample.views.auth :as view]
            [noir.validation :as vali]
            [noir.util.crypt :as crypt]
            [noir.session :as session]
            [noir.response :as resp]))

(defn valid? [email password password_confirmation]
  (vali/rule (vali/has-value? name)
             [:name "You should provide name"])
  (vali/rule (vali/has-value? email)
             [:email "You should provide email"])
  (vali/rule (vali/min-length? password 6)
             [:password "Password must be at least 6 characters"])
  (vali/rule (= password password_confirmation)
             [:password "Password conformation does not match"])
  (not (vali/errors? :email :password :password_confirmation)))

(defn user-to-session [user]
  (do
    (session/put! :user-id (:id user))
    (session/put! :user-name (:name user))
    (session/put! :user-email (:email user))))

(defn format-error [id ex]
  (cond
    (and (instance? org.postgresql.util.PSQLException ex)
         (zero? (.getErrorCode ex)))
    (str "User with same email already exists")
    :else
    "An error accured while processing this request"))

(defn login-page [& [email]]
  (layout/common
    (view/login-page email)))

(defn registration-page [& [name email]]
  (layout/common
    (view/registration-page name email)))

(defn handle-login [email password]
  (let [user (db/get-user-by-email email)]
    (if (and user (crypt/compare password (:encrypted_password user)))
      (do
        (user-to-session user)
        (resp/redirect "/"))
      (do
        (vali/rule false [:email "Email or password is invalid"])
        (login-page)))))

(defn handle-logout []
  (session/clear!)
  (resp/redirect "/"))

(defn handle-registration [name email password password_confirmation]
  (if (valid? email password password_confirmation)
    (try
      (db/create-user {:name name :email email :encrypted_password (crypt/encrypt password)})
      (user-to-session (db/get-user-by-email email))
      (resp/redirect "/")
      (catch Exception ex
        (vali/rule false [:email (format-error email ex)])
        (registration-page)))
    (registration-page name email)))

(defroutes auth-routes
  (GET "/register" []
       (registration-page))
  (POST "/register" [name email password password_confirmation]
        (handle-registration name email password password_confirmation))
  (GET "/login" []
       (login-page))
  (POST "/login" [email password]
       (handle-login email password))
  (GET "/logout" []
       (handle-logout)))
