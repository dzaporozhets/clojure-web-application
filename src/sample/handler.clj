(ns sample.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [migratus.core :as migratus]
            [sample.routes.home :refer [home-routes]]
            [sample.routes.profile :refer [profile-routes]]
            [sample.routes.auth :refer [auth-routes]]
            [sample.views.layout :as layout]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

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

(defroutes static-routes
  (route/resources "/")
  (route/not-found (not-found)))

(def app-routes
  (routes
    auth-routes
    home-routes
    profile-routes
    static-routes))

(def app
  (wrap-defaults app-routes site-defaults))
