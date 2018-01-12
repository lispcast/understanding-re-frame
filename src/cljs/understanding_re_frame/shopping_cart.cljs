(ns understanding-re-frame.shopping-cart
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  :shopping-cart/items
  (fn [db]
    (get-in db [:shopping-cart :items] [])))

(re-frame/reg-sub
  :shopping-cart/items-count
  (fn []
    (re-frame/subscribe [:shopping-cart/items]))
  (fn [cart-items]
    (count cart-items)))

(re-frame/reg-sub
  :coupon
  (fn [db]
    (:coupon db)))

(re-frame/reg-sub
  :coupon/code
  (fn []
    (re-frame/subscribe [:coupon]))
  (fn [coupon]
    (:code coupon)))

(re-frame/reg-sub
  :coupon/discount
  (fn []
    (re-frame/subscribe [:coupon]))
  (fn [coupon]
    (:discount coupon 0.0)))

(defn apply-discount [price discount]
  (- price (* discount price)))

(re-frame/reg-sub
  :shopping-cart/discounted-items
  (fn []
    [(re-frame/subscribe [:coupon/discount])
     (re-frame/subscribe [:shopping-cart/items])])
  (fn [[discount items]]
    (mapv #(update % :price apply-discount discount) items)))

(re-frame/reg-event
  :shopping-cart/add-item
  (fn [db [_ item-id]]
    (update-in db [:shopping-cart :items] (fnil conj []) item-id)))

(defn cart-icon []
  [:div
   [:img {:src "/cart-icon.png"}]
   @(re-frame/subscribe [:shopping-cart/items-count])])

(defn cart-items []
  (when-let [items @(re-frame/subscribe [:shopping-cart/discounted-items])]
    [:ul
     (for [item items]
       [:li (:name item) " " (:price item)])]))
