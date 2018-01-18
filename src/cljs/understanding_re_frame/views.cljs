(ns understanding-re-frame.views
  (:require [re-frame.core :as re-frame]
            [understanding-re-frame.subs :as subs]
            [understanding-re-frame.components :as comps]
            [understanding-re-frame.hiccup :as hiccup]
            [understanding-re-frame.forms :as forms]
            [understanding-re-frame.atoms :as atoms]
            [understanding-re-frame.re-frame-events :as events]
            [understanding-re-frame.subscriptions :as subscriptions]
            [understanding-re-frame.interceptors :as interceptors]
            [understanding-re-frame.shopping-cart :as cart]))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div (str "Hello from " @name ". This is the Home Page.")
     [:div [:a {:href "#/about"} "go to About Page"]]]))


;; about

(defn about-panel []
  [:div "This is the About Page."
   [:div [:a {:href "#/"} "go to Home Page"]]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :components-panel [comps/components-panel]
    :hiccup-panel [hiccup/hiccup-panel]
    :forms-panel [forms/forms-panel]
    :atoms-panel [atoms/atoms-panel]
    :events-panel [events/events-panel]
    :subscriptions-panel [subscriptions/subscriptions-panel]
    :interceptors-panel [interceptors/interceptors-panel]
    :shopping-cart-panel [cart/panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div
     [show-panel @active-panel]
     [:hr]
     [:ul
      [:li [:a {:href "#/components"} "Components"]]
      [:li [:a {:href "#/hiccup"} "Hiccup"]]
      [:li [:a {:href "#/forms"} "Forms"]]
      [:li [:a {:href "#/atoms"} "Atoms"]]
      [:li [:a {:href "#/events"} "Events"]]
      [:li [:a {:href "#/subscriptions"} "Subscriptions"]]
      [:li [:a {:href "#/interceptors"} "Interceptors"]]
      [:li [:a {:href "#/shopping-cart"} "Shopping Cart"]]]]))

