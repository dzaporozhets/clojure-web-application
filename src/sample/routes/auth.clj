(ns sample.routes.auth
  (:require [hiccup.form :refer :all]
            [compojure.core :refer :all]
            [sample.models.user :as db]
            [sample.views.layout :as layout]
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

(defn error-item [[error]]
  [:div.text-danger error])

(defn input-control [type id name & [value]]
  [:div.form-group
   (list
     (label id name)
     (vali/on-error (keyword id) error-item)
     (type {:class "form-control"} id value))])

(defn format-error [id ex]
  (cond
    (and (instance? org.postgresql.util.PSQLException ex)
         (= 0 (.getErrorCode ex)))
    (str "User with same email already exists")
    :else
    "An error accured while processing this request"))

(defn login-page [& [email]]
  (layout/common
    [:div.login-form
     [:h1 "Login with existing account"]
     (form-to [:post "/login"]
              (input-control text-field "email" "Email" email)
              (input-control password-field "password" "Password")
              (submit-button {:class "btn btn-success"} "Login"))]))

(defn registration-page [& [name email]]
  (layout/common
    [:div.registration-form
     [:h1 "Let's create an account"]
     (form-to [:post "/register"]
              (input-control text-field "name" "Name" name)
              (input-control text-field "email" "Email" email)
              (input-control password-field "password" "Password")
              (input-control password-field "password_confirmation" "Repeat password")
              (submit-button {:class "btn btn-success"} "Create account"))]))

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
