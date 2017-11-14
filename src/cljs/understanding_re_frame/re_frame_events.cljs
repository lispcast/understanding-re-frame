(ns understanding-re-frame.re-frame-events
  (:require [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

(def app-state (reagent/atom {}))

(defn ajax-post [url options]
  ;; fake method
  )

(defn buy-button [item-id]
  [:button
   {:on-click (fn [e]
                (.preventDefault e)
                (rf/dispatch [:buy-product item-id]))}
   "Buy"])

(defn events-panel []
  [:div
   [buy-button 100]])
