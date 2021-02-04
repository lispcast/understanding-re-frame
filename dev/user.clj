(ns user
  (:require [figwheel.main.api :as f]))

;; user is a namespace that the Clojure runtime looks for and
;; loads if its available

;; You can place helper functions in here. This is great for starting
;; and stopping your webserver and other development services

;; The definitions in here will be available if you run `lein repl` or launch a
;; Clojure repl some other way

;; You have to ensure that the libraries you :require are listed in your dependencies



(defn fig-start
  "This starts the figwheel server and watch based auto-compiler."
  []
  (f/start {:watch-dirs ["src"]
            :css-dirs ["resources/public/css"]
            :ring-server-options {:port 3449}
            :mode :serve}
           "dev"))

(defn fig-stop
  "Stop the figwheel server and watch based auto-compiler."
  []
  (f/stop "dev"))

(defn cljs-repl
  "Launch a ClojureScript REPL that is connected to your build and host environment."
  []
  (f/cljs-repl "dev"))
