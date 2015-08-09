(ns sample.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [noir.util.middleware :as noir-middleware]
            [noir.session :as session]
            [sample.routes.home :refer [home-routes]]
            [sample.routes.profile :refer [profile-routes]]
            [sample.routes.auth :refer [auth-routes]]
            [sample.views.layout :as layout]
            [noir.session :as session]))

(defn user-page [_]
  (session/get :user-id))

(defn not-found []
  (layout/base
    [:center
     [:h1 "404. Page not found!"]]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found (not-found)))

(def app 
  (noir-middleware/app-handler 
    [auth-routes 
     home-routes
     profile-routes
     app-routes]
    :access-rules [user-page]))
