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

(rf/reg-event-db
  :notify-error
  (fn [db [_ resp]]
    (assoc db :error (:error resp))))

(rf/reg-event-db
  :add-cart-confirm
  (fn [db [_ item-id]]
    (let [items (get-in db [:cart :items])
          items (mapv (fn [item]
                        (if (= (:item item) item-id)
                          (assoc item :confirmed? true)
                          item))
                  items)]
      (assoc-in db [:cart :items] items))))

(rf/reg-event-fx
  :buy-product
  (fn [cofx [_ item-id]]
    {:db (update-in (:db cofx)
           [:cart :items] conj {:item item-id
                                :confirmed? false})
     :http-xhrio {:uri (str "http://url.com/product/" item-id "/puchase")
                  :method :post
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:add-cart-confirm item-id]
                  :on-failure [:notify-error]}}))

(defn buy-button [item-id]
  [:button
   {:on-click (fn [e]
                (.preventDefault e)
                (rf/dispatch [:buy-product item-id]))}
   "Buy"])

(defn events-panel []
  [:div
   [buy-button 100]])
