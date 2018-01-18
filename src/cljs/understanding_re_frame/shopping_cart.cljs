(ns understanding-re-frame.shopping-cart
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-db
  :init-products
  (fn [db]
    (assoc db :products [{:id 0
                          :name "Pie"}
                         {:id 1
                          :name "Cake"}
                         {:id 2
                          :name "Ice Cream"}])))

(re-frame/reg-sub
  :products
  (fn [db]
    (:products db)))

(re-frame/dispatch-sync [:init-products])

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

(re-frame/reg-event-db
  :shopping-cart/add-item
  (fn [db [_ product]]
    (update-in db [:shopping-cart :items] (fnil conj []) product)))

(defn cart-icon []
  [:div
   [:img {:src "/cart-icon.png"}]
   @(re-frame/subscribe [:shopping-cart/items-count])])

(defn cart-items []
  (when-let [items @(re-frame/subscribe [:shopping-cart/discounted-items])]
    [:ul
     (for [item items]
       [:li (:name item) " " (:price item)])]))

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
                                                product]))}
           "Add to cart"]]))]]

   [:hr]

   [:div
    [:h1 "Shopping Cart"]
    [:ol
     (doall
       (for [item @(re-frame/subscribe [:shopping-cart/items])]
         [:li
          (:name item)]))]]])
