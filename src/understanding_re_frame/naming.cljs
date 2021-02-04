(ns understanding-re-frame.naming
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]))

;; Naming
;;  name so you can reconstruct what happened later
;;    name so you capture the intent
;;  name based on domain concepts, not technical concepts
;;  Events
;;    capture intent
;;    NOT effect (technical)
;;    NOT UI actions
;;  Subscriptions
;;    describing the question
;;    NOT implementation
;;    NOT component

;; Favorites

(re-frame/reg-event-db
  :add-to-favorites
  (fn [db [_ entity-id]]
    (assoc-in db [:favorites entity-id] true)))

(re-frame/reg-event-db
  :remove-from-favorites
  (fn [db [_ entity-id]]
    (update db :favorites dissoc entity-id)))

(re-frame/reg-sub
  :in-favorites?
  (fn [db [_ entity-id]]
    (get-in db [:favorites entity-id] false)))

