(ns sample.routes.home
  (:require [compojure.core :refer :all]
            [sample.views.home :as view]
            [sample.views.layout :as layout]))

(defn home []
  (layout/common (view/home)))

(defroutes home-routes
  (GET "/" [] (home)))
