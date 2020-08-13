(ns sample.routes.home
  (:require [compojure.core :refer :all]
            [sample.helpers :refer :all]
            [sample.views.home :as view]
            [sample.views.layout :as layout]))

(defn home [user]
  (layout/common (view/home user) user))

(defroutes home-routes
  (GET "/" {{:keys [user-id]} :session}
       (home (get-user user-id))))
