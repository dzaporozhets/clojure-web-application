(ns sample.models.user
  (:require [clojure.java.jdbc :as sql]
            [sample.db :refer :all]))

(defn create-user [user]
  (sql/insert! db :users user))

(defn get-user-by-email [email]
  (sql/query db
             ["SELECT * FROM users WHERE email = ?", email]
             {:result-set-fn first}))

(defn get-user-by-id [id]
  (sql/query db
             ["SELECT * FROM users WHERE id = ?", id]
             {:result-set-fn first}))

(defn delete-user [id]
  (sql/delete! db :users ["id = ?", id]))

(defn delete-user-by-email [email]
  (sql/delete! db :users ["email = ?", email]))

(defn update-user [id params]
  (sql/update! db :users params ["id = ?" id]))
