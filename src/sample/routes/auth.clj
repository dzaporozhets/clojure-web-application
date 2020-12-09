(ns sample.routes.auth
  (:require [hiccup.form :refer :all]
            [compojure.core :refer :all]
            [ring.util.response :as response]
            [sample.crypt :as crypt]
            [sample.models.user :as db]
            [sample.helpers :refer :all]
            [sample.views.layout :as layout]
            [sample.views.auth :as view]
            [struct.core :as st]))

(def auth-register-scheme
  {:name [st/required st/string]
   :email [st/required st/email]
   :password [st/required [st/min-count 6]]
   :password-confirmation [st/required [st/identical-to :password]]})

(defn validate-user [name email password password-confirmation]
  (st/validate {:name name
                :email email
                :password password
                :password-confirmation password-confirmation} auth-register-scheme))

(defn user-to-session [user]
  {:user-id (:id user)
   :user-name (:name user)
   :user-email (:email user)})

(defn login-page [& [email errors]]
  (layout/common
    (view/login-page email errors)))

(defn registration-page [& [name email errors]]
  (layout/common
    (view/registration-page name email errors)))

(defn handle-login [email password]
  (let [user (db/get-user-by-email email)]
    (if (and user (crypt/verify password (:encrypted_password user)))
      (assoc (response/redirect "/") :session (user-to-session user))
      (login-page email {:email "Email or password is invalid"}))))

(defn handle-logout []
  (assoc (response/redirect "/") :session nil))

(defn handle-registration [name email password password-confirmation]
  (let [errors (first (validate-user name email password password-confirmation))]
    (if errors
      (registration-page name email errors)
      (if (db/get-user-by-email email)
        (registration-page name email {:email "User with the same email already exists"})
        (do
          (db/create-user {:name name :email email :encrypted_password (crypt/encrypt password)})
          (let [user (db/get-user-by-email email)]
            (assoc (response/redirect "/") :session (user-to-session user))))))))

(defroutes auth-routes
  (GET "/login" []
       (login-page))
  (GET "/logout" []
       (handle-logout))
  (GET "/register" []
       (registration-page))
  (POST "/login" [email password]
       (handle-login email password))
  (POST "/register" [name email password password-confirmation]
        (handle-registration name email password password-confirmation)))
