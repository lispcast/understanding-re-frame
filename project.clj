(defproject understanding-re-frame "0.1.0-SNAPSHOT"
  :description "Code to accompany Understanding Re-frame, a course on PurelyFunctional.tv"
  :url "https://purelyfunctional.tv/courses/understanding-re-frame/"
  :license {:name "CC0 1.0 Universal (CC0 1.0) Public Domain Dedication"
            :url "http://creativecommons.org/publicdomain/zero/1.0/"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.10.2"]
                 [org.clojure/clojurescript "1.10.764"]
                 [re-frame "1.1.2"]
                 [clj-commons/secretary "1.2.4"]
                 [compojure "1.6.2"]
                 [day8.re-frame/http-fx "0.2.2"]
                 [com.bhauman/figwheel-main "0.2.12"]
                 [cljsjs/material-ui "4.10.2-0"]]

  :source-paths ["src"]

  :aliases {"fig-dev"   ["trampoline" "run" "-m" "figwheel.main" "--" "--build" "dev" "--repl"]
            "fig-build" ["trampoline" "run" "-m" "figwheel.main" "--" "-O" "advanced" "--build-once" "dev"]}

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    :target-path]

  ;; setting up nREPL for Figwheel and ClojureScript dev
  :profiles {:dev {:dependencies [[cider/piggieback "0.5.2"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; for CIDER
                   :plugins [[cider/cider-nrepl "0.25.8"]]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   ;; need to add the compliled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})
