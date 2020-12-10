(ns sample.features.profile
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

(deftest profile-page
  (-> (session app)
      (visit "/")
      (follow "Login")
      (fill-in "Email" foo-email)
      (fill-in "Password" "123456")
      (press "Login")
      (follow-redirect)
      (visit "/profile")
      (within [:h1]
        (has (some-text? "Profile")))))

(deftest profile-without-user
  (-> (session app)
      (visit "/profile")
      (follow-redirect)
      (within [:h1]
        (has (some-text? "Login with existing account")))))

(deftest profile-change-name
  (-> (session app)
      (visit "/")
      (follow "Login")
      (fill-in "Email" foo-email)
      (fill-in "Password" "123456")
      (press "Login")
      (follow-redirect)
      (visit "/profile/edit")
      (fill-in "User name" "XYZ")
      (press "Save changes")
      (follow-redirect)
      (within [:h1]
        (has (some-text? "Profile")))
      (within [:.user-info]
        (has (some-text? "XYZ")))))

(deftest profile-change-password
  (-> (session app)
      (visit "/")
      (follow "Login")
      (fill-in "Email" foo-email)
      (fill-in "Password" "123456")
      (press "Login")
      (follow-redirect)
      (visit "/profile/password")
      (fill-in "Current password" "123456")
      (fill-in "New password" "7890123")
      (fill-in "Confirm new password" "7890123")
      (press "Change password")
      (follow-redirect)
      (within [:h1]
        (has (some-text? "Login with existing account")))
      (fill-in "Email" foo-email)
      (fill-in "Password" "7890123")
      (press "Login")
      (follow-redirect)
      (within [:h1]
        (has (some-text? "Hello Foo")))))

(deftest profile-remove-user
  (-> (session app)
      (visit "/")
      (follow "Login")
      (fill-in "Email" foo-email)
      (fill-in "Password" "123456")
      (press "Login")
      (follow-redirect)
      (visit "/profile")
      (press "Delete account")
      (follow-redirect)
      (visit "/login")
      (fill-in "Email" foo-email)
      (fill-in "Password" "123456")
      (press "Login")
      (within [:form]
        (has (some-text? "Email or password is invalid")))))
