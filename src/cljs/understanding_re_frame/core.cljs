(ns understanding-re-frame.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [understanding-re-frame.events :as events]
            [understanding-re-frame.routes :as routes]
            [understanding-re-frame.views :as views]
            [understanding-re-frame.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
