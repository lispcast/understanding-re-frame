(ns understanding-re-frame.events
  (:require [re-frame.core :as re-frame]
            [understanding-re-frame.db :as db]))

(re-frame/reg-event-fx
 ::load-app
 (fn  [{:keys [db]} _]
   {:db (merge db db/default-db)
    :ajax {}}))

(re-frame/reg-event-db
 ::set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))
