(ns understanding-re-frame.atoms
  (:require [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

(defonce my-state (reagent/atom {}))
(defonce my-other (reagent/atom {}))

(defn sub-component [a]
  (let [s (reagent/atom 0)]
    (js/setInterval #(swap! s inc) 500)
    (fn [a]
      (js/console.log "SUB-COMPONENT")
      [:div
       [:div a]
       [:div @s]
       [:div @my-other]])))

(defn atoms-panel []
  (js/console.log "ATOMS-PANEL")
  [:div
   [:h1 "Atoms"]
   [sub-component (:name @my-state)]
   [:div
    (pr-str @my-state)]])

(comment

  (reset! my-state {:name "Eric"})
  (reset! my-state {})

  (reset! my-other "Hello")

  (swap! my-state inc)
  (swap! my-state assoc :age 36)
  (swap! my-state assoc :name "Eric")

  (swap! my-state update :age inc)

  (swap! my-state assoc-in [:people 1] {:name "Eric"
                                        :age 36})
  (swap! my-state update-in [:people 1 :age] inc)
  
  (update {} :pets conj :dog)
  )
