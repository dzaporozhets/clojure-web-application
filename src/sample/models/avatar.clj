(ns sample.models.avatar
  (:require [clojure.java.jdbc :as sql]
            [sample.db :refer :all]))

(defn get-avatar-by-name [name]
  (sql/query db
             ["SELECT * FROM avatars WHERE name = ?", name]
             {:result-set-fn first}))

(defn get-avatar-by-user [user-id]
  (sql/query db
             ["SELECT * FROM avatars WHERE user_id = ?", user-id]
             {:result-set-fn last}))

(defn create-avatar [avatar]
  (first (sql/insert! db :avatars avatar)))

(defn delete-avatar-by-user [user-id]
  (sql/delete! db :avatars ["user_id = ?", user-id]))
