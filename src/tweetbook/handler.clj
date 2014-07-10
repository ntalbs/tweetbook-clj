(ns tweetbook.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [noir.session :as session]
            [ring.middleware.session.memory :refer [memory-store]]
            [tweetbook.routes.auth :refer [auth-routes]]
            [tweetbook.routes.home :refer [home-routes]]))

(defn init []
  (println "tweetbook is starting"))

(defn destroy []
  (println "tweetbook is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes auth-routes home-routes app-routes)
      (handler/site)
      (wrap-base-url)
      (session/wrap-noir-session {:store (memory-store)})))