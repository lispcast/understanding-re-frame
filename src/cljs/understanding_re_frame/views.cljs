(ns understanding-re-frame.views
  (:require [re-frame.core :as re-frame]
            [understanding-re-frame.subs :as subs]
            [understanding-re-frame.components :as comps]))


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
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div
     [show-panel @active-panel]
     [:hr]
     [:ul
      [:li [:a {:href "#/components"} "Components"]]]]))
