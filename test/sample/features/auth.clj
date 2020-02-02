(ns sample.features.auth
  (:require [sample.handler :refer [app]]
            [sample.features.helpers :refer :all]
            [kerodon.core :refer :all]
            [kerodon.test :refer :all]
            [clojure.test :refer :all]))

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

(deftest user-login-invalid
  (-> (session app)
      (visit "/")
      (follow "Login")
      (fill-in "Email" foo-email)
      (fill-in "Password" "654321")
      (press "Login")
      (within [:form]
        (has (some-text? "Email or password is invalid")))))

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
