(ns sample.routes.files
  (:require [compojure.core :refer :all]
            [sample.models.avatar :as avatar-db]
            [ring.util.response :refer [file-response]]
            [ring.util.codec :refer [url-decode]]))

(defn avatar-file [avatar]
  (file-response (str "resources/public/avatars/" (url-decode (:name avatar)))))

(defroutes files-routes
  (context "/files" {}
           (GET "/avatars/:name" [name]
                (if-let [avatar (avatar-db/get-avatar-by-name name)]
                  (avatar-file avatar)))))
