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
