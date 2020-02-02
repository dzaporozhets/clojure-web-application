(ns sample.features.auth
  (:require [sample.handler :refer [app]]
            [sample.models.user :as db]
            [noir.util.crypt :as crypt]
            [kerodon.core :refer :all]
            [kerodon.test :refer :all]
            [clojure.test :refer :all]))

(def foo-email "foo@example.com")

(defn create-user []
  (if-not (db/get-user-by-email foo-email)
    (db/create-user {:name "Foo"
                     :email foo-email
                     :encrypted_password (crypt/encrypt "123456")})))

(defn remove-users []
  (db/delete-user-by-email foo-email)
  (db/delete-user-by-email "bar@example.com"))

(use-fixtures :each
              (fn [f]
                (create-user)
                (f)
                (remove-users)))

(deftest user-login
  (-> (session app)
      (visit "/")
      (follow "Login")
      (fill-in "Email" foo-email)
      (fill-in "Password" "123456")
      (press "Login")
      (follow-redirect)
      (within [:h1]
        (has (some-text? "Hello Foo")))))

(deftest user-signup
  (-> (session app)
      (visit "/")
      (follow "Register")
      (fill-in "Name" "Bar")
      (fill-in "Email" "bar@example.com")
      (fill-in "Password" "123456")
      (fill-in "Repeat password" "123456")
      (press "Create account")
      (follow-redirect)
      (within [:h1]
        (has (some-text? "Hello Bar")))))

(deftest user-signup-exists
  (-> (session app)
      (visit "/")
      (follow "Register")
      (fill-in "Name" "Foo")
      (fill-in "Email" foo-email)
      (fill-in "Password" "123456")
      (fill-in "Repeat password" "123456")
      (press "Create account")
      (within [:form]
        (has (some-text? "User with same email already exists")))))
