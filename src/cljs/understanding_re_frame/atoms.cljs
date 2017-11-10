(ns understanding-re-frame.atoms
  (:require [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

(defonce my-state (reagent/atom {}))



(defn atoms-panel []
  [:div
   [:h1 "Atoms"]
   
   [:div
    (pr-str @my-state)]])

(comment

  (reset! my-state {:name "Eric"})
  (reset! my-state {})

  (swap! my-state inc)
  (swap! my-state assoc :age 36)

  (swap! my-state update :age inc)

  (swap! my-state assoc-in [:people 1] {:name "Eric"
                                        :age 36})
  (swap! my-state update-in [:people 1 :age] inc)
  
  (update {} :pets conj :dog)
  )
