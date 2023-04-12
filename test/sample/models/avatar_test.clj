(ns sample.models.avatar-test
  (:require
   [clojure.test :refer [deftest is]]
   [sample.models.avatar :as subject]))

(deftest create-avatar-test
  (is (not= nil
         (:id (subject/create-avatar {:user_id 1, :name "test.jpg"})))))