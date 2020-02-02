(ns sample.db)

(def db (or (System/getenv "DATABASE_URL")
          "postgresql://localhost:5432/sample"))
