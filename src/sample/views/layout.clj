(ns sample.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.element :refer [link-to]]
            [noir.session :as session]))

(defn guest-menu []
  (list
    [:ul {:class "nav navbar-nav navbar-right"}
     [:li
      [:a {:class "login" :href "/login"} "Login"]]
     [:li
      [:a {:class "Register" :href "/register"} "Register"]]]))

(defn user-menu [user]
  (list
    [:ul {:class "nav navbar-nav navbar-right"}
     [:li
      [:a {:class "profile" :href "/profile"} "Profile"]]
     [:li
      [:a {:class "logout" :href "/logout"} "Logout"]]]))

(defn base [& content]
  (html5
    [:head
     [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
     [:meta {:name "description" :content "Sample application"}]
     [:meta {:name "keywords" :content "images pictures"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     [:title "Sample application"]
     [:link {:rel "icon" :href "/img/favicon.ico" :type "image/x-icon"}]
     (include-css "/css/bootstrap.min.css")
     (include-css "/css/application.css")
     (include-js "/js/jquery-1.11.3.min.js")
     (include-js "/js/application.js")]
    [:body content]))

(defn common [& content]
  (base
    [:header.navbar.navbar-default.navbar-static-top.navbar-default
     [:div.container
      [:div.navbar-header
       [:a.navbar-brand {:href "/"}
        [:strong "sample"]]]
      (if-let [user (session/get :user-id)]
        (user-menu user)
        (guest-menu))]]
    [:div.container content]))
