(ns understanding-re-frame.shopping-cart
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  :shopping-cart/items
  (fn [db]
    (get-in db [:shopping-cart :items] [])))

(re-frame/reg-event
  :shopping-cart/add-item
  (fn [db [_ item-id]]
    (update-in db [:shopping-cart :items] (fnil conj []) item-id)))

