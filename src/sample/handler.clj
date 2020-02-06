(ns sample.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [noir.util.middleware :as noir-middleware]
            [noir.session :as session]
            [migratus.core :as migratus]
            [sample.routes.home :refer [home-routes]]
            [sample.routes.profile :refer [profile-routes]]
            [sample.routes.auth :refer [auth-routes]]
            [sample.views.layout :as layout]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [noir.session :as session]))

(def migratus-config
  {:store :database
   :migration-dir "migrations"
   :db (or (System/getenv "DATABASE_URL") "postgresql://localhost:5432/sample")})

(defn init []
  (migratus/migrate migratus-config))

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
    :middleware [wrap-anti-forgery]))
