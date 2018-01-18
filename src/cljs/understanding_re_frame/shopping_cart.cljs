(ns understanding-re-frame.shopping-cart
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-db
  :init-products
  (fn [db]
    (assoc db :products {0 {:id 0
                            :name "Pie"}
                         1 {:id 1
                            :name "Cake"}
                         2 {:id 2
                            :name "Ice Cream"}})))

(re-frame/reg-sub
  :product-index
  (fn [db]
    (:products db)))

(re-frame/reg-sub
  :products
  (fn []
    (re-frame/subscribe [:product-index]))
  (fn [pidx]
    (vals pidx)))

(re-frame/reg-sub
  :shopping-cart
  (fn [db]
    (:shopping-cart db)))

(re-frame/dispatch-sync [:init-products])

(re-frame/reg-sub
  :shopping-cart/items
  (fn []
    [(re-frame/subscribe [:product-index])
     (re-frame/subscribe [:shopping-cart])])
  (fn [[pidx cart]]
    (for [[id quantity]
          (get-in cart [:items] {})]
      {:quantity quantity
       :product  (get pidx id)})))

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

(re-frame/reg-event-db
  :shopping-cart/add-item
  (fn [db [_ product]]
    (update-in db [:shopping-cart :items product] (fnil inc 0))))

(defn cart-icon []
  [:div
   [:img {:src "/cart-icon.png"}]
   @(re-frame/subscribe [:shopping-cart/items-count])])

(defn cart-items []
  (when-let [items @(re-frame/subscribe [:shopping-cart/discounted-items])]
    [:ul
     (for [item items]
       [:li (:name item) " " (:price item)])]))

(re-frame/reg-event-db
  :shopping-cart/dec-item
  (fn [db [_ item-id]]
    (if (<= (get-in db [:shopping-cart :items item-id]) 1)
      (update-in db [:shopping-cart :items] dissoc item-id)
      (update-in db [:shopping-cart :items item-id] dec))))

(defn panel []
  [:div
   [:div
    [:h1 "Products"]
    [:ol
     (doall
       (for [product @(re-frame/subscribe [:products])]
         [:li (:name product) " -- "
          [:a {:href "#"
               :on-click #(do
                            (.preventDefault %)
                            (re-frame/dispatch [:shopping-cart/add-item
                                                (:id product)]))}
           "Add to cart"]]))]]

   [:hr]
   [:div (pr-str @(re-frame/subscribe [:products]))]
   [:hr]

   [:div
    [:h1 "Shopping Cart"]
    [:div (pr-str @(re-frame/subscribe [:shopping-cart/items]))]
    [:ol
     (doall
       (for [item @(re-frame/subscribe [:shopping-cart/items])]
         [:li
          (get-in item [:product :name])
          " -- "
          (get-in item [:quantity])

          " "
          [:a {:href "#"
               :on-click #(do
                            (.preventDefault %)
                            (re-frame/dispatch [:shopping-cart/dec-item
                                                (:id (:product item))]))}
           "Rem 1"]]))
     ]]
   [:hr]
   [:div (pr-str @(re-frame/subscribe [:shopping-cart]))]])
