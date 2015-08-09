(ns sample.features.auth
  (:require [sample.handler :refer [app]]
            [sample.models.user :as db]
            [noir.util.crypt :as crypt]
            [kerodon.core :refer :all]
            [kerodon.test :refer :all]
            [clojure.test :refer :all]))


(defn create-user []
  (db/delete-user-by-email "foo@example.com")
  (db/create-user {:name "Foo" 
                   :email "foo@example.com" 
                   :encrypted_password (crypt/encrypt "123456")}))

(use-fixtures :each
              (fn [f]
                (create-user)
                (f)))

(deftest user-login
  (-> (session app)
      (visit "/")
      (follow "Login")
      (fill-in "Email" "foo@example.com")
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
