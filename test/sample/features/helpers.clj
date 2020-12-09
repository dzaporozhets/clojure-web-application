(ns sample.features.helpers
  (:require [sample.handler :refer [app]]
            [sample.models.user :as db]
            [sample.crypt :as crypt]
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
