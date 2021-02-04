(ns understanding-re-frame.re-frame-events
  (:require [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [re-frame.db :as db]))

(def app-state (reagent/atom {}))

(defn ajax-post [url options]
  ;; fake method
  )

(rf/reg-fx
  :db
  (fn [new-db]
    (reset! db/app-db new-db)))

(rf/reg-cofx
  :db
  (fn [cofx]
    (assoc cofx :db @db/app-db)))

(rf/reg-cofx
  :datetime
  (fn [cofx]
    (assoc cofx :now (js/Date.))))

(defonce last-tempid (atom 0))

(rf/reg-cofx
  :tempid
  (fn [cofx]
    (assoc cofx :tempid (swap! last-tempid inc))))

(rf/reg-fx
  :dispatch
  rf/dispatch)

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
  [(rf/inject-cofx :datetime)
   (rf/inject-cofx :tempid)]
  (fn [cofx [_ item-id]]
    {:db (update-in (:db cofx)
           [:cart :items] conj {:item item-id
                                :confirmed? false
                                :time-added (:now cofx)
                                :id (:tempid cofx)})
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
