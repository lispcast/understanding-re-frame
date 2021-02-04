(ns understanding-re-frame.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [secretary.core :as secretary]
            [goog.events :as gevents]
            [goog.history.EventType :as EventType]
            [re-frame.core :as re-frame]
            [understanding-re-frame.events :as events]
            ))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  ;; --------------------
  ;; define routes here
  (defroute "/" []
    (re-frame/dispatch [::events/set-active-panel :home-panel]))

  (defroute "/about" []
    (re-frame/dispatch [::events/set-active-panel :about-panel]))

  (defroute "/components" []
    (re-frame/dispatch [::events/set-active-panel :components-panel]))

  (defroute "/hiccup" []
    (re-frame/dispatch [::events/set-active-panel :hiccup-panel]))

  (defroute "/forms" []
    (re-frame/dispatch [::events/set-active-panel :forms-panel]))

  (defroute "/atoms" []
    (re-frame/dispatch [::events/set-active-panel :atoms-panel]))

  (defroute "/events" []
    (re-frame/dispatch [::events/set-active-panel :events-panel]))

  (defroute "/subscriptions" []
    (re-frame/dispatch [::events/set-active-panel :subscriptions-panel]))

  (defroute "/interceptors" []
    (re-frame/dispatch [::events/set-active-panel :interceptors-panel]))

  (defroute "/shopping-cart" []
    (re-frame/dispatch [::events/set-active-panel :shopping-cart-panel]))


  ;; --------------------
  (hook-browser-navigation!))
