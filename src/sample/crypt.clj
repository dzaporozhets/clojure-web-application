(ns sample.crypt
  (:require [clojurewerkz.scrypt.core :as sc]))

(defn encrypt [string]
  (sc/encrypt string 16384 8 1))

(defn verify [string encrypted]
  (boolean
   (if (and string encrypted)
    (sc/verify string encrypted))))
