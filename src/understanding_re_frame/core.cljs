(ns understanding-re-frame.core
  (:require [reagent.core :as reagent]
            [reagent.dom :as dom]
            [re-frame.core :as re-frame]
            [understanding-re-frame.events :as events]
            [understanding-re-frame.routes :as routes]
            [understanding-re-frame.views :as views]
            [understanding-re-frame.config :as config]))

(routes/app-routes)
(re-frame/dispatch-sync [::events/load-app])

(enable-console-print!)

(re-frame/clear-subscription-cache!)
(dom/render [views/main-panel]
            (.getElementById js/document "app"))