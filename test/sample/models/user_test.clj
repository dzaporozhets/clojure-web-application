(ns sample.models.user-test
  (:require
   [clojure.test :refer [deftest is]]
   [sample.features.helpers :refer :all]
   [sample.models.user :as subject]))

(deftest get-user-by-email-test
  (create-user)
  (is (not= nil
         (subject/get-user-by-email foo-email))))