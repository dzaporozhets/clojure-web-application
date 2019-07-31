FROM clojure
COPY . /usr/src/app
WORKDIR /usr/src/app
CMD ["lein", "ring", "server-headless", "5000"]
EXPOSE 5000
