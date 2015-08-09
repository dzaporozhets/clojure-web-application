(ns sample.features.home
  (:require [sample.handler :refer [app]]
            [kerodon.core :refer :all]
            [kerodon.test :refer :all]
            [clojure.test :refer :all]))

(deftest homepage-greeting
  (-> (session app)
      (visit "/")
      (within [:h1]
        (has (some-text? "Hello")))))

(deftest not-found
  (-> (session app)
      (visit "/blabla")
      (within [:h1]
        (has (some-text? "404. Page not found!")))))
