(ns understanding-re-frame.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.java.io :as io]
            [cheshire.core :as json]))


(defonce users (atom {"han"        {:avatar "han.jpg"        :password "XXX" :username "han"}
                      "leia"       {:avatar "leia.jpg"       :password "XXX" :username "leia"}
                      "luke"       {:avatar "luke.jpg"       :password "XXX" :username "luke"}
                      "c3po"       {:avatar "c3po.jpg"       :password "XXX" :username "c3po"}
                      "r2d2"       {:avatar "r2d2.jpg"       :password "XXX" :username "r2d2"}
                      "chewie"     {:avatar "chewie.jpg"     :password "XXX" :username "chewie"}
                      "obiwan"     {:avatar "obiwan.jpg"     :password "XXX" :username "obiwan"}
                      "darthvader" {:avatar "darthvader.jpg" :password "XXX" :username "darthvader"}}))

(defn complete-avatar-url [filename scheme server port]
  (str (name scheme) "://"  server ":" port "/avatar/" filename))

(defn up-avatar [user req]
  (when (some? user)
    (if (some? (:avatar user))
      (update user :avatar complete-avatar-url
              (:scheme req) (:server-name req) (:server-port req))
      user)))

(defn get-user [req username]
  (-> {:username username :exists (contains? @users username)}
      (merge (get @users username))
      (dissoc :password)
      (up-avatar req)))

(defroutes app-routes
  (GET "/users" req
       (let [u (-> req :params :u)]
         {:body (json/generate-string (get-user req u))
          :headers {"Content-type" "application/json"}
          :status 200}))
  (POST "/users" req
        (let [u (-> req :params :u)
              p (-> req :params :p)]
          (println req)
          (prn @users)
          (swap! users assoc u {:username u :password p})
          (println (get-user req u))
          {:body (json/generate-string (get-user req u))
           :headers {"Content-type" "text/plain"}
           :status 201}))
  (POST "/avatar" req
        (let [image (-> req :params :image)
              username (-> req :params :u)
              [_ ext] (re-matches #".*([.][^.]+)" (:filename image))
              fn (str (java.util.UUID/randomUUID) ext)
              file (-> (io/file "resources")
                       (io/file "public")
                       (io/file "avatar")
                       (io/file fn))]
          (io/make-parents file)
          (io/copy (:tempfile image) (io/file file))
          (swap! users assoc-in [username :avatar] fn)
          {:body (json/generate-string (get-user req username))
           :headers {"Content-type" "application/json"}
           :status 201}))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-defaults api-defaults)
      (wrap-multipart-params)
      (wrap-reload)
      (wrap-cors :access-control-allow-origin  [#".*"]
                 :access-control-allow-methods [:get :post])))